package com.chatsync.chatSyncBackend.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    public String userId;
    public String username;
    public String password;
    public String email;
    public String firstName;
    public String lastName;
    public boolean isActive;
    public String phoneNo;
    public String profileImage;
    public List<String> roles;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
