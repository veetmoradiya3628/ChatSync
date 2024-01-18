package com.chatsyncapp.chatSyncApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private String profileImage;
}
