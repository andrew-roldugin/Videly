package ru.vsu.csf.group7.services;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;

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

        Channel channel = snapshot.toObject(Channel.class);
        if (channel == null || channel.isDeleted())
            throw new NotFoundException(String.format("Канал с id %s не найден", channelId));
        return channel;
    }

    public Channel findByUserId(String uId) throws ExecutionException, InterruptedException, NotFoundException, NullPointerException {
        DocumentSnapshot user = FirestoreClient.getFirestore()
                .collection("users")
                .document(uId)
                .get()
                .get();


        Channel channel = ((DocumentReference) Objects.requireNonNull(user.get("channelRef")))
                .get()
                .get()
                .toObject(Channel.class);

        if (channel == null || channel.isDeleted())
            throw new NotFoundException("Канал не найден или удален");

        return channel;
    }

    public List<Channel> getAll() throws ExecutionException, InterruptedException {
        return FirestoreClient.getFirestore()
                .collection("channels")
                .whereEqualTo("deleted", false)
                .get()
                .get()
                .toObjects(Channel.class);
    }

    public void deleteByChannelId(String channelId, boolean fullDelete) throws ExecutionException, InterruptedException {
        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference channelRef = getChannelReference(channelId);

        WriteBatch writeBatch = firestore.batch()
                .update((DocumentReference) Objects.requireNonNull(channelRef.get().get().get("userRef")), Map.of("channelRef", ""));
        if (fullDelete) {
            writeBatch.delete(channelRef).commit();
        } else {
            writeBatch.update(channelRef, Map.of("deleted", true)).commit();
        }
    }

    public Channel updateById(UpdateChannelRequest req, String channelId) throws ExecutionException, InterruptedException {
        DocumentReference channel = getChannelReference(channelId);
        channel.update(req.toMap());

        return channel.get().get().toObject(Channel.class);
    }

    private DocumentReference getChannelReference(String channelId) {
        return FirestoreClient.getFirestore()
                .collection("channels")
                .document(channelId);
    }

    public List<Channel> findChannels(SearchChannelQuery q) throws ExecutionException, InterruptedException {
        return FirestoreClient.getFirestore()
                .collection("channels")
                .whereEqualTo("name", q.getName())
                .whereEqualTo("deleted", false)
                .get()
                .get()
                .toObjects(Channel.class);
    }
}
