package ru.vsu.csf.group7.http.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
public class UpdateUserRequest {
    @NotEmpty
    @Size(min = 6)
    private String password;
    //private boolean isBanned = false;
}
