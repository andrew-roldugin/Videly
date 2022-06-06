package ru.vsu.csf.group7.services;

import com.google.api.client.util.Maps;
import com.google.cloud.Timestamp;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class ChannelService {

    private final UserService userService;

    @Autowired
    public ChannelService(UserService userService) {
        this.userService = userService;
    }

    public Channel create(CreateChannelRequest request) throws ExecutionException, InterruptedException {
        Channel channel = new Channel(request);

        Firestore firestore = FirestoreClient.getFirestore();
        DocumentReference channelsReference = firestore
                .collection("channels")
                .document();

        DocumentReference userRef = userService.getCurrentUserRef();

        channel.setId(channelsReference.getId());
        channel.setUserRef(userRef);

        firestore.batch()
                .create(channelsReference, channel)
                .update(userRef, Map.of("channelRef", channelsReference))
                .commit();

        return channel;
    }

    public Channel findByChannelId(String channelId) throws ExecutionException, InterruptedException, NotFoundException {
        DocumentSnapshot snapshot = getChannelReference(channelId)
                .get()
                .get();

        Channel[] channel = new Channel[1];
        if (!channelIsActive(snapshot.getReference(), channel)) {
            throw new NotFoundException(String.format("Канал с id %s не найден", channelId));
        }

        return channel[0];
    }

    public Channel findByUserId(String userId) throws ExecutionException, InterruptedException, NotFoundException, NullPointerException {
        DocumentSnapshot userSnapshot = FirestoreClient.getFirestore()
                .collection("users")
                .document(userId)
                .get()
                .get();

        DocumentReference channelRef = (DocumentReference) Objects.requireNonNull(userSnapshot.get("channelRef"), "Пользователь еще не создал канал");

        Channel[] channel = new Channel[1];

        if (!channelIsActive(channelRef, channel)) {
            throw new NotFoundException("Канал не найден или удален");
        }

        return channel[0];
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
        DocumentReference channelRef = getChannelReference(channelId); //ссылка на канал

        DocumentReference userRef = (DocumentReference) Objects.requireNonNull(channelRef.get().get().get("userRef")); //ссылка на автора видео

        HashMap<String, Object> map = Maps.newHashMap();
        map.put("channelRef", null);
        WriteBatch writeBatch = firestore.batch()
                .update(userRef, map);

        if (fullDelete) {
            writeBatch.delete(channelRef);
        } else {
            Map<String, Object> extraInfo = new HashMap<>();
            extraInfo.put("deletedBy", userService.getCurrentUserRef());
            extraInfo.put("reason", "");
            extraInfo.put("timestamp", Timestamp.now());

            writeBatch.update(channelRef, Map.of("deleted", true, "extra", extraInfo));
        }
        writeBatch.commit();
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
                    try {
                        return channel.isActive();
                    } catch (NullPointerException e) {
                        return false;
                    }
                })
                .toList();
    }

    public boolean channelIsActive(DocumentReference channelRef, Channel[] channel) throws ExecutionException, InterruptedException, NullPointerException {
        DocumentSnapshot snapshot = channelRef.get().get();
//        boolean userProfileIsActive = userService.userProfileIsActive(((DocumentReference) Objects.requireNonNull(snapshot.get("userRef"))));
        channel[0] = snapshot.toObject(Channel.class);
        return channel[0] != null && channel[0].isActive();
    }
}
