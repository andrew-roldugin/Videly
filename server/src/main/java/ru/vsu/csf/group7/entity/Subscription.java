package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Data
@Log4j2
public class Subscription {
    private String id;
    @Exclude
    private Channel channel;
    private DocumentReference channelRef;
    private Timestamp followedSince = Timestamp.now();

    @Exclude
    public Channel getChannel() {
        try {
            return this.channel != null
                    ? this.channel
                    : (this.channel = channelRef.get().get().toObject(Channel.class));
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getLocalizedMessage());
        }
        return null;
    }

    @Exclude
    public boolean exist() throws NullPointerException{
        Objects.requireNonNull(this.getChannel(), "Канал не найден или удален");
        return this.channel.isActive();
    }
}
