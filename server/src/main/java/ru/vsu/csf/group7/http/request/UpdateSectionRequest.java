package ru.vsu.csf.group7.http.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UpdateSectionRequest {
    @NotEmpty(message = "Имя раздела обязательно для заполнения")
    private String sectionName;
    private String description;
}
