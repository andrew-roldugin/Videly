package ru.vsu.csf.group7.services;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.entity.User;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.exceptions.UserNotFoundException;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;

import javax.print.Doc;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class ChannelService {

    @Autowired
    private UserService userService;

    public Channel create(CreateChannelRequest request) throws ExecutionException, InterruptedException {
        Channel channel = new Channel(request);

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference channelsReference = firestore
                .collection("channels")
                .document();

        DocumentReference userRef = userService.getUserRef();

//        DocumentReference channelsReference =  userRef
//                .collection("channels")
//                .document();

        channel.setUserRef(userRef);

        firestore.batch()
                .create(channelsReference, channel)
                .update(userRef, Map.of("channelRef", channelsReference))
                .commit()
                .get();

        return channel;
    }

    public Channel findByChannelId(String channelId) throws ExecutionException, InterruptedException, NotFoundException {
        // /users/vvnKSVK0hffCIfdZrruYRY2Gh0B2/channels/H34VgIs05UN2VjygYMsC
        DocumentSnapshot snapshot = getChannelReference(channelId)
                .get()
                .get();

        if (!channelIsActive(snapshot.getReference())) {
            throw new NotFoundException(String.format("Канал с id %s не найден", channelId));
        }

        return snapshot.toObject(Channel.class);
    }

    public Channel findByUserId(String uId) throws ExecutionException, InterruptedException, NotFoundException, NullPointerException {
        DocumentSnapshot snapshot = FirestoreClient.getFirestore()
                .collection("users")
                .document(uId)
                .get()
                .get();


        DocumentReference channelRef = (DocumentReference) Objects.requireNonNull(snapshot.get("channelRef"));
        if (!channelIsActive(channelRef)) {
            throw new NotFoundException("Канал не найден или удален");
        }

//        if (!user.exists()
//                || Boolean.TRUE.equals(user.getBoolean("deleted"))
//                || Boolean.TRUE.equals(user.getBoolean("banned")))
//            throw new UserNotFoundException();
//
//        Channel channel = ((DocumentReference) Objects.requireNonNull(user.get("channelRef")))
//                .get()
//                .get()
//                .toObject(Channel.class);
//
//        if (channel == null || channel.isDeleted())
//            throw new NotFoundException("Канал не найден или удален");

        return channelRef.get().get().toObject(Channel.class);
    }

    public List<Channel> getAll() throws ExecutionException, InterruptedException {
        List<Channel> channels = FirestoreClient.getFirestore()
                .collection("channels")
                .whereEqualTo("deleted", false)
                .get()
                .get()
                .toObjects(Channel.class);
        return filter(channels);
    }

    public void deleteByChannelId(String channelId, boolean fullDelete) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference channelRef = getChannelReference(channelId);

        WriteBatch writeBatch = firestore.batch()
                .update((DocumentReference) Objects.requireNonNull(channelRef.get().get().get("userRef")), Map.of("channelRef", ""));
        if (fullDelete) {
            writeBatch.delete(channelRef).commit();
        } else {
            Map<String, Object> extraInfo = new HashMap<>();
            extraInfo.put("deletedBy", userService.getUserRef());
            extraInfo.put("reason", "");
            extraInfo.put("timestamp", Timestamp.now());

            writeBatch.update(channelRef, Map.of("deleted", true, "extra", extraInfo)).commit();
        }
    }

    public Channel updateById(UpdateChannelRequest req, String channelId) throws ExecutionException, InterruptedException {
        DocumentReference channel = getChannelReference(channelId);
        channel.update(req.toMap());

        return channel.get().get().toObject(Channel.class);
    }

    protected DocumentReference getChannelReference(String channelId) {
        return FirestoreClient.getFirestore()
                .collection("channels")
                .document(channelId);
    }

    public List<Channel> findChannels(SearchChannelQuery q) throws ExecutionException, InterruptedException {
        List<Channel> channels = FirestoreClient.getFirestore()
                .collection("channels")
                .whereEqualTo("deleted", false)
                .orderBy("name")
                .whereGreaterThanOrEqualTo("name", q.getName().toUpperCase())
                .whereLessThanOrEqualTo("name", q.getName().toLowerCase() + "\uf8ff")
                .get()
                .get()
                .toObjects(Channel.class);
        return filter(channels);
    }

    private List<Channel> filter(List<Channel> channels) {
        return channels.stream()
                .filter(channel -> {
                    DocumentSnapshot snapshot = null;
                    try {
                        snapshot = channel.getUserRef().get().get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                    return Boolean.FALSE.equals(snapshot.getBoolean("deleted")) && Boolean.FALSE.equals(snapshot.getBoolean("banned"));
                })
                .toList();
    }

    public boolean channelIsActive(DocumentReference channelRef) throws ExecutionException, InterruptedException, NullPointerException {
        DocumentSnapshot snapshot = channelRef.get().get();
        boolean profileIsActive = userService.userProfileIsActive(((DocumentReference) Objects.requireNonNull(snapshot.get("userRef"))));
        Channel channel = snapshot.toObject(Channel.class);
        return profileIsActive && !Objects.requireNonNull(channel).isDeleted();
    }
}
