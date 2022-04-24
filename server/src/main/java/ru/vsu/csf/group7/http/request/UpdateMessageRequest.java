package ru.vsu.csf.group7.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateMessageRequest {
    private String content;
}
