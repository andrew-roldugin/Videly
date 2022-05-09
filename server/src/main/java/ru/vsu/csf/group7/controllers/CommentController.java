package ru.vsu.csf.group7.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vsu.csf.group7.controllers.interfaces.ICommentAPI;
import ru.vsu.csf.group7.dto.CommentDTO;
import ru.vsu.csf.group7.entity.Comment;
import ru.vsu.csf.group7.exceptions.NotFoundException;
import ru.vsu.csf.group7.http.request.CreateCommentRequest;
import ru.vsu.csf.group7.http.request.UpdateCommentRequest;
import ru.vsu.csf.group7.http.response.MessageResponse;
import ru.vsu.csf.group7.services.CommentService;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
@RequestMapping("/api/comment")
@Log4j2
public class CommentController implements ICommentAPI {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    @PostMapping("/write")
    public ResponseEntity<CommentDTO> writeNewComment(CreateCommentRequest request) {
        CommentDTO commentDTO = CommentDTO.fromComment(commentService.write(request));
        return ResponseEntity.ok(commentDTO);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<Object> getAllComments(String videoId, int limit, int offset) {
        try {
            List<CommentDTO> comments = commentService.getAllComments(videoId, limit, offset).stream()
                    .map(CommentDTO::fromComment)
                    .toList();
            return ResponseEntity.ok(comments);
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка"));
        }
    }

    @Override
    @PatchMapping("/edit")
    public ResponseEntity<Object> update(UpdateCommentRequest request) {
        try {
            Comment comment = commentService.update(request);
            return ResponseEntity.ok(CommentDTO.fromComment(comment));
        } catch (NullPointerException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка"));
        }
    }

    @Override
    @DeleteMapping("/delete")
    public ResponseEntity<MessageResponse> delete(String commentId, String videoId, boolean fullDelete) {
        try {
            commentService.delete(videoId, commentId, fullDelete);
            return ResponseEntity.ok(new MessageResponse("Успешно"));
        } catch (NullPointerException | NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (ExecutionException | InterruptedException e) {
            log.error(e.getLocalizedMessage());
            return ResponseEntity.internalServerError().body(new MessageResponse("Произошла неизвестная ошибка"));
        }
    }
}
