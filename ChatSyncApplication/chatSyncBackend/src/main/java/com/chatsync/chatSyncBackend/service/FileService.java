package com.chatsync.chatSyncBackend.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public ResponseEntity<?> uploadUserProfileHandler(MultipartFile file, String userId);

    public ResponseEntity<byte[]> getProfileImageView(String userId);

    public ResponseEntity<?> uploadGroupProfileHandler(MultipartFile file, String groupId);

    public ResponseEntity<byte[]> getGroupProfileImageView(String groupId);
}
