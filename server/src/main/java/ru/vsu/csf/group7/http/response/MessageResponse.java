package ru.vsu.csf.group7.http.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class MessageResponse {
    protected String[] messages;

    public MessageResponse(String message) {
        this.messages = new String[]{message};
    }

    public MessageResponse(String[] messages) {
        this.messages = messages;
    }
}
