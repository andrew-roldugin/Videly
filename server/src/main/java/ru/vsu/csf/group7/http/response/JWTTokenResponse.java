package ru.vsu.csf.group7.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JWTTokenResponse {
    private String accessToken;
    private String refreshToken;
    private String customToken;

    public JWTTokenResponse(String[] tokens) {
        this.accessToken = tokens[0];
        this.refreshToken = tokens[1];
        this.customToken = null;
    }
}
