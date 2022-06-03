package ru.vsu.csf.group7.http.request;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Schema(description = "Запрос на поиск каналов по разным критериям")
@Data
public class SearchChannelQuery {
    @NotEmpty
    @SchemaProperty(name = "Имя, по которому будет выполнен поиск каналов")
    private String name;
}
