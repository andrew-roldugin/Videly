package ru.vsu.csf.group7.http.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class CreateSectionRequest {
    @NotEmpty(message = "Имя раздела обязательно для заполнения")
    private String sectionName;
    private Long embeddedInSectionId;
    private String description;
}
