package ru.vsu.csf.group7.services;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.UserDetailsImpl;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
public class VideoService {

    private final ChannelService channelService;
    private final UserService userService;

    @Autowired
    public VideoService(ChannelService channelService, UserService userService) {
        this.channelService = channelService;
        this.userService = userService;
    }

    public Video create(CreateVideoRequest request) throws ExecutionException, InterruptedException {
        Video video = new Video(request);

        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference channelReference = channelService.getChannelReference(request.getChannelId());
        video.setChannelRef(channelReference);

        DocumentReference documentReference = firestore.collection("videos").document();
        video.setId(documentReference.getId());

        firestore.batch()
                .create(documentReference, video)
                .create(channelReference.collection("channel_videos").document(documentReference.getId()), Map.of("ref", documentReference))
//                .update(channelReference, "videos", FieldValue.arrayUnion(documentReference))
                .commit();

        return video;
    }


    public Video updateVideoById(UpdateVideoRequest req, String videoId) throws ExecutionException, InterruptedException, NotFoundException {
        DocumentReference documentReference = getVideoReference(videoId);

        documentReference.update(req.toMap());

        return Objects.requireNonNull(documentReference.get().get().toObject(Video.class));
    }

    public List<Video> findVideos(SearchVideoQuery q) throws ExecutionException, InterruptedException {
        Query query = FirestoreClient.getFirestore().collection("videos")
                .whereEqualTo("deleted", false)
                .whereEqualTo("visible", true)
                .orderBy("title")
//                .startAt(q.getTitle().toUpperCase())
//                .endAt(q.getTitle().toUpperCase() + "\uf8ff");
                .whereGreaterThanOrEqualTo("title", q.getTitle().toUpperCase())
                .whereLessThanOrEqualTo("title", q.getTitle().toLowerCase() + "\uf8ff");

        if (q.getChannelId() != null && !q.getChannelId().isEmpty())
            query = query.whereEqualTo("channelRef", channelService.getChannelReference(q.getChannelId()));

        QuerySnapshot queryDocumentSnapshots = query
                .limit(q.getLimit())
                .offset(q.getOffset())
                .get()
                .get();

        return queryDocumentSnapshots.toObjects(Video.class);
    }

    public List<Video> getAllVideosInChannel(String channelId, int limit, int offset) throws ExecutionException, InterruptedException {
//        String id = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

        return channelService.getChannelReference(channelId)
                .collection("channel_videos")
                .whereEqualTo("deleted", false)
                .limit(limit)
                .offset(offset)
                .get()
                .get()
                .toObjects(Video.class);
//                .whereEqualTo("visible", true);

    }

    public Video findVideoById(String videoId) throws ExecutionException, InterruptedException, NotFoundException {
        DocumentReference videoReference = getVideoReference(videoId);
        DocumentSnapshot snapshot = videoReference
                .get()
                .get();

        videoReference.update("views", FieldValue.increment(1));

        Video video = snapshot.toObject(Video.class);
        try {
            if (!video.isVisible() || video.isDeleted())
                throw new NotFoundException(String.format("Видео с id %s не найдено", videoId));

            if (!channelService.channelIsActive(((DocumentReference) Objects.requireNonNull(snapshot.get("channelRef"))))) {
                throw new NotFoundException("Канал не найден или удален");
            }
        } catch (NullPointerException e) {
            throw new NotFoundException("Видео не найдено");
        }

        return video;
    }

    public void deleteById(String videoId, boolean fullDelete) throws ExecutionException, InterruptedException, NullPointerException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference reference = getVideoReference(videoId);

        DocumentReference videoInChannelRef = ((DocumentReference) Objects.requireNonNull(reference.get().get().get("channelRef")))
                .collection("channel_videos")
                .document(videoId);

        if (fullDelete) {
            firestore.batch()
                    .delete(videoInChannelRef)
                    .delete(reference)
                    .commit();
        } else {
            Map<String, Object> extraInfo = new HashMap<>();
            extraInfo.put("deletedBy", userService.getUserRef());
            extraInfo.put("reason", "");
            extraInfo.put("timestamp", Timestamp.now());
            reference.update(Map.of("deleted", true, "extra", extraInfo));
            Objects.requireNonNull(videoInChannelRef).delete();
        }
    }

    protected DocumentReference getVideoReference(String videoId) {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference reference = firestore
                .collection("videos")
                .document(videoId);
        return reference;
    }

    public void updateRating(String videoId) throws ExecutionException, InterruptedException {
//        String id = ((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        DocumentReference document = userService.getUserRef()
                .collection("additional_user_info")
                .document("likes");

        DocumentReference videoReference = getVideoReference(videoId);

//        if (Boolean.TRUE.equals(document.get().get().get(videoId))){
//            videoReference.update("likes", FieldValue.increment(-1));
//            document.update(videoId, false);
//        }else{
//            videoReference.update("likes", FieldValue.increment(1));
//            document.update(videoId, true);
//        }
        boolean v = Boolean.TRUE.equals(document.get().get().getBoolean(videoId));
//        int likes = Integer.parseInt(videoReference.get().get().get("likes").toString());
//        videoReference.update(Map.of("likes", likes + (!v ? 1 : -1)));
        videoReference.update("likes", FieldValue.increment(!v ? 1 : -1));
        document.set(Map.of(videoId, !v), SetOptions.merge());
    }
}
