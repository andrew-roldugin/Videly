package ru.vsu.csf.group7.entity;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.annotation.Exclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.csf.group7.http.request.CreateCommentRequest;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

@Data
@NoArgsConstructor
public class Comment {
    private String id;
    private String content;
    private DocumentReference author;
    private boolean isDeleted = false;
    private Timestamp ts = Timestamp.now();

    public Comment(CreateCommentRequest req) {
        this.content = req.getContent();
    }

//    @Exclude
//    public User getUserAccount() throws ExecutionException, InterruptedException {
//        return author.get().get().toObject(User.class);
//    }

    @Exclude
    public Channel getChannelInfo() throws ExecutionException, InterruptedException {
        return this.author.get().get().toObject(Channel.class);
    }
}
