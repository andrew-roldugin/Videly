package ru.vsu.csf.group7.http.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Токены, сгенерированные для пользователя")
public class JWTTokenSuccessResponse {
   private boolean success;
   private String message;
   private JWTTokenResponse tokens;
}
