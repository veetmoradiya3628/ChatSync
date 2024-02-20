package com.chatsync.chatSyncBackend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserContactDto {
    private String userContactId;
    private String userId;
    private String contactId;
    private UserDto userDetail;
    private UserDto contactDetail;
}
