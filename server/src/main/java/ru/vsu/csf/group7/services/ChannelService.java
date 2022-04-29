package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Channel;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateChannelRequest;
import ru.vsu.csf.group7.http.request.SearchChannelQuery;
import ru.vsu.csf.group7.http.request.UpdateChannelRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class ChannelService {
    public Channel getByUserId(String userId) throws ExecutionException, InterruptedException, NotFoundException{
        List<QueryDocumentSnapshot> queryDocumentSnapshots = FirestoreClient.getFirestore().collection("channels")
                .whereEqualTo("userRef", "users/" + userId)
                .get()
                .get()
                .getDocuments();
        if (queryDocumentSnapshots.isEmpty())
            throw new NotFoundException();

        return  queryDocumentSnapshots.get(0)
                .toObject(Channel.class);
    }

    public Channel create(CreateChannelRequest request) throws ExecutionException, InterruptedException {
        Channel channel = new Channel(request);

        DocumentReference channelsReference = FirestoreClient.getFirestore().collection("channels")
                .document();

        FirestoreClient.getFirestore().batch()
                .create(channelsReference, channel)
                .update(channel.getUserRef(), Map.of("channelRef", channelsReference))
                .commit()
                .get();

        return null;
    }

    public Channel findById(String channelId) {
        return null;
    }

    public void deleteById(String channelId) {

    }

    public Channel updateById(UpdateChannelRequest req, String channelId) {
        return null;
    }

    public List<Channel> findChannels(SearchChannelQuery q) {
        return null;
    }
}
