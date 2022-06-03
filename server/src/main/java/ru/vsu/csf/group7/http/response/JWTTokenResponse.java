package ru.vsu.csf.group7.http.response;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema()
public class JWTTokenResponse {
    @SchemaProperty(name = "токен доступа")
    private String accessToken;
    @SchemaProperty(name = "токен для обновления")
    private String refreshToken;
    @SchemaProperty(name = "кастомный токен, отпарвляемый на клиент")
    private String customToken;

    public JWTTokenResponse(String[] tokens) {
        this.accessToken = tokens[0];
        this.refreshToken = tokens[1];
        this.customToken = null;
    }
}
