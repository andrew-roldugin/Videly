package ru.vsu.csf.group7.http.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
//@AllArgsConstructor
public class MessageResponse {
   private String[] messages;

   public MessageResponse(String[] messages) {
      this.messages = messages;
   }

   public MessageResponse(String messages) {
      this.messages = new String[] {messages};
   }

}
