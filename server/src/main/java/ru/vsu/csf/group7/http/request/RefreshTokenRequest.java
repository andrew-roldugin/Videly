package ru.vsu.csf.group7.http.request;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class RefreshTokenRequest {
    @NotEmpty
    private String refreshToken;
}
