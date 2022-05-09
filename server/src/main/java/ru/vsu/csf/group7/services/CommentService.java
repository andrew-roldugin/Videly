package ru.vsu.csf.group7.services;

import com.google.cloud.firestore.DocumentReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vsu.csf.group7.entity.Comment;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateCommentRequest;
import ru.vsu.csf.group7.http.request.UpdateCommentRequest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CommentService {

    private final VideoService videoService;
    private final UserService userService;

    @Autowired
    public CommentService(VideoService videoService, UserService userService) {
        this.videoService = videoService;
        this.userService = userService;
    }

    public Comment write(CreateCommentRequest req) {
        Comment comment = new Comment(req);
        comment.setAuthor(userService.getCurrentUserRef());
        DocumentReference reference = videoService.getVideoReference(req.getVideoId())
                .collection("comments")
                .document();
        comment.setId(reference.getId());
        reference.create(comment);
        return comment;
    }

    public List<Comment> getAllComments(String videoId, int limit, int offset) throws ExecutionException, InterruptedException {
        return videoService.getVideoReference(videoId)
                .collection("comments")
                .whereEqualTo("deleted", false)
                .limit(limit)
                .offset(offset)
                .get()
                .get()
                .toObjects(Comment.class)
                .stream()
                .filter(comment -> {
                    try {
                        return userService.userProfileIsActive(comment.getAuthor());
                    } catch (ExecutionException | InterruptedException e) {
                        return false;
                    }
                })
                .toList();
    }

    public Comment update(UpdateCommentRequest request) throws ExecutionException, InterruptedException {
        DocumentReference comments = videoService.getVideoReference(request.getVideoId())
                .collection("comments")
                .document(request.getCommentId());
        comments.update(Map.of("content", request.getContent()));
        return comments.get().get().toObject(Comment.class);
    }

    public void delete(String videoId, String commentId, boolean flag) throws ExecutionException, InterruptedException {
        DocumentReference comment = videoService.getVideoReference(videoId)
                .collection("comments")
                .document(commentId);
        if (!comment.get().get().exists())
            throw new NotFoundException("Комментарий не найден");

        if (flag) {
            comment.delete();
        } else {
            comment.update(Map.of("deleted", true));
        }
    }
}
