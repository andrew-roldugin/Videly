package ru.vsu.csf.group7.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateTopicRequest {
//    @NotEmpty(message = "Ссылка на пользователя обязательна!")
    private Long userId;
    @NotEmpty(message = "Укажите название темы")
    private String topicName;
    private Long sectionId;
}
