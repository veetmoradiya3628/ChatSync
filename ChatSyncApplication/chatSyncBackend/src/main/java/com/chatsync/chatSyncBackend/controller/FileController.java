package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.service.Impl.FileServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/files")
@CrossOrigin("*")
public class FileController {

    public static String LOG_TAG = "FileController";
    public Logger logger = LoggerFactory.getLogger(FileController.class);
    public FileServiceImpl fileService;

    public FileController(FileServiceImpl fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload/profilePicture")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                                  @RequestParam String userId){
        logger.info(LOG_TAG + " uploadProfilePicture called with userId : " + userId);
        return fileService.uploadUserProfileHandler(file, userId);
    }

    @GetMapping("/view/profile/{userId}")
    public ResponseEntity<byte[]>viewProfileImage(@PathVariable("userId") String userId){
        logger.info(LOG_TAG + " viewProfileImage called for userId : " + userId);
        return fileService.getProfileImageView(userId);
    }

}
