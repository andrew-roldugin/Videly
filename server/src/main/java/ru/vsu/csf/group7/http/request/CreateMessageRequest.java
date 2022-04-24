package ru.vsu.csf.group7.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateMessageRequest {
    @NotEmpty(message = "Напишите что-нибудь...")
    private String content;
    private Long topicId;
}
