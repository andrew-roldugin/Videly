package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Subscription;
import ru.vsu.csf.group7.exceptions.NotFoundException;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
public class SubscriptionService {

    private final UserService userService;
    private final ChannelService channelService;

    @Autowired
    public SubscriptionService(UserService userService, ChannelService channelService) {
        this.userService = userService;
        this.channelService = channelService;
    }

    public Subscription subscribe(String channelId) throws ExecutionException, InterruptedException {
        DocumentReference documentReference = userService.getCurrentUserRef()
                .collection("subscriptions")
                .document();

        DocumentReference channelReference = channelService.getChannelReference(channelId);
        DocumentSnapshot snapshot = channelReference
                .get()
                .get();

        if (!snapshot.exists()) {
            throw new NotFoundException(String.format("Канал с id %s не найден", channelId));
        }

        Subscription subscription = new Subscription();
        subscription.setId(documentReference.getId());
        subscription.setChannelRef(channelReference);
        documentReference.create(subscription);
        return subscription;
    }

    public List<Subscription> getMySubscriptions(int limit, int offset) throws ExecutionException, InterruptedException {
       return userService.getCurrentUserRef()
                .collection("subscriptions")
                .offset(offset)
                .limit(limit)
                .get()
                .get()
                .toObjects(Subscription.class)
                .stream()
                .filter(subscription -> {
                    try {
                        return subscription.exist();
                    } catch (NullPointerException e) {
                        return false;
                    }
                })
                .toList();
    }
}
