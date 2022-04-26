package ru.vsu.csf.group7.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.vsu.csf.group7.exceptions.ApiException;
import ru.vsu.csf.group7.http.response.JWTTokenResponse;
import ru.vsu.csf.group7.http.response.JWTTokenSuccessResponse;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TokenService {

    @Value("${webApiKey}")
    private String webApiKey;

    public JWTTokenResponse getVerifiedIdToken(String customToken) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://www.googleapis.com/identitytoolkit/v3/relyingparty/verifyCustomToken?key=" + webApiKey;

        Map<String, Object> body = new HashMap<>();
        body.put("token", customToken);
        body.put("returnSecureToken", true);

        ResponseEntity<Object> response = restTemplate.postForEntity(url, body, Object.class);
        if (response.getStatusCode() == HttpStatus.OK){
            LinkedHashMap responseBody = (LinkedHashMap) response.getBody();
            String tokens[] = new String[]{(String) responseBody.get("idToken"), (String) responseBody.get("refreshToken")};
            return new JWTTokenResponse(tokens);
        }
        throw new ApiException("Произошла ошибка при авторизации\nПричина: не удается проверить токен");
    }
}
