package ru.vsu.csf.group7.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class MessageResponse {
   private String[] messages;
   private Object data;

   public MessageResponse(String[] messages) {
      this.messages = messages;
   }

   public MessageResponse(String message) {
      this.messages = new String[] {message};
   }

   public MessageResponse(String message, Object data) {
      this(message);
      this.data = data;
   }
}
