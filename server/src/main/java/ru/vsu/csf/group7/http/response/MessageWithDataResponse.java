package ru.vsu.csf.group7.http.response;

import lombok.*;

@Getter
@Setter
public class MessageWithDataResponse<R> extends MessageResponse {

    private R data;

    public MessageWithDataResponse(String message, R data) {
        super(message);
        this.data = data;
    }

    public MessageWithDataResponse(String[] message, R data) {
        super(message);
        this.data = data;
    }
}
