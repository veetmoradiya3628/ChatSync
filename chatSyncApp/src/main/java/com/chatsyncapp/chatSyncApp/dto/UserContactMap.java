package com.chatsyncapp.chatSyncApp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserContactMap {
    private String userId;
    private String contactId;
}
