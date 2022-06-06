package ru.vsu.csf.group7.services;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.Video;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateVideoRequest;
import ru.vsu.csf.group7.http.request.SearchVideoQuery;
import ru.vsu.csf.group7.http.request.UpdateVideoRequest;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

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
        if (!channelReference.get().get().exists())
            throw new NotFoundException("Канал не найден");

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

        return documentReference.get().get().toObject(Video.class);
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

        List<Video> videos = queryDocumentSnapshots
                .toObjects(Video.class).stream()
                .filter(Video::isAvailable)
                .toList();

        return videos;
    }

    public List<Video> getAllVideosOnChannel(String channelId, int limit, int offset) throws ExecutionException, InterruptedException {
        DocumentReference channelReference = channelService.getChannelReference(channelId);
        if (!channelService.channelIsActive(channelReference, new Channel[1]))
            throw new NotFoundException("Канал не найден или удален");

        return channelReference
                .collection("channel_videos")
                .limit(limit)
                .offset(offset)
                .get()
                .get()
                .getDocuments()
                .stream()
                .map(queryDocumentSnapshot -> ((DocumentReference) queryDocumentSnapshot.get("ref")))
                //.filter(Objects::nonNull)
                .map(ref -> {
                    try {
                        return ref.get().get().toObject(Video.class); // ссылка -> snapshot
                    } catch (Exception ignored) {
                    }
                    return null;
                }).filter(Objects::nonNull)
                .toList();
    }

    public Video findVideoById(String videoId) throws ExecutionException, InterruptedException, NotFoundException {
        DocumentReference videoReference = getVideoReference(videoId);
        if (!isVideoAvailable(videoReference)) {
            throw new NotFoundException("Видео не найдено или удалено");
        }

        DocumentSnapshot snapshot = videoReference
                .get()
                .get();

        videoReference.update("views", FieldValue.increment(1));

        return snapshot.toObject(Video.class);
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
            extraInfo.put("deletedBy", userService.getCurrentUserRef());
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
        DocumentReference document = userService.getCurrentUserRef()
                .collection("likes")
                .document("user_likes");

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

    public boolean isVideoAvailable(@Nonnull DocumentReference reference) {
        try {
            Video video = reference.get().get().toObject(Video.class);
            Objects.requireNonNull(video, "Видео не найдено или удалено");
            return video.isAvailable();
        } catch (InterruptedException | ExecutionException e) {
            return false;
        }
//        try {
//            Video video = reference.get().get().toObject(Video.class);
//            if (!video.isVisible() || video.isDeleted())
//                return false;
//            DocumentReference channelRef = (DocumentReference) Objects.requireNonNull(reference.get().get().get("channelRef"), "Видео должно принадлежать какому-либо каналу"); //ссылка на канал
//            if (!channelService.channelIsActive(channelRef, null))
//                return false;
//        } catch (NullPointerException | ExecutionException | InterruptedException e) {
//            return false;
//        }
//        return true;
    }
}