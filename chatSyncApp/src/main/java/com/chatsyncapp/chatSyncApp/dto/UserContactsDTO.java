package com.chatsyncapp.chatSyncApp.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContactsDTO {
    public String userId;
    public String username;
    public List<UserDTO> contacts;
}
