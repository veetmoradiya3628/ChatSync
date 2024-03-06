package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.service.FileService;
import com.chatsync.chatSyncBackend.utils.FilePathUtils;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    private static final String LOG_TAG = "FileServiceImpl";
    public Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Value("${userProfilePictureUploadDirectory}")
    private String USER_PROFILE_PICTURE_UPLOAD_PATH;

    @Value("${groupProfilePictureUploadDirectory}")
    private String GROUP_PROFILE_PICTURE_UPLOAD_PATH;

    private UserServiceImpl userService;
    private GroupServiceImpl groupService;

    public FileServiceImpl(UserServiceImpl userService, GroupServiceImpl groupService) {
        this.userService = userService;
        this.groupService = groupService;
    }


    @Override
    public ResponseEntity<?> uploadUserProfileHandler(MultipartFile file, String userId) {
        try {
            logger.info(LOG_TAG + " uploadUserProfileHandler called for userID : " + userId);

            // validate userId
            if (!this.userService.isUserExistsById(userId)) {
                logger.info(LOG_TAG + " User with provided userId not exists!!, userId : " + userId);
                return ResponseHandler.generateResponse("User with provided userId not exists!!", HttpStatus.NOT_FOUND, null);
            }

            // proceeding with uploading profile image
            Files.copy(file.getInputStream(), Paths.get(USER_PROFILE_PICTURE_UPLOAD_PATH + File.separator + userId + ".jpg"), StandardCopyOption.REPLACE_EXISTING);

            // update path in user table for profile
            String profilePath = FilePathUtils.USER_PROFILE_API_PATH + userId;
            this.userService.updateUserProfilePath(userId, profilePath);

            return ResponseHandler.generateResponse("Profile picture uploaded successfully!!", HttpStatus.OK, null);
        } catch (Exception e) {
            logger.info(LOG_TAG + " uploadUserProfileHandler Exception : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured while uploading file", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<byte[]> getProfileImageView(String userId) {
        try {
            if (this.userService.isUserExistsById(userId)) {
                Path imagePath = Paths.get(this.USER_PROFILE_PICTURE_UPLOAD_PATH).resolve(userId + ".jpg");
                Resource imageResource = new UrlResource(imagePath.toUri());

                if (imageResource.exists() && imageResource.isReadable()) {
                    try (InputStream inputStream = imageResource.getInputStream()) {
                        byte[] imageBytes = inputStream.readAllBytes();

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate media type based on your image type
                        headers.setContentLength(imageBytes.length);

                        return new ResponseEntity<>(imageBytes, headers, org.springframework.http.HttpStatus.OK);
                    } catch (IOException e) {
                        logger.error(LOG_TAG + " Error reading image file: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    }
                } else {
                    logger.info(LOG_TAG + " file not exists with userId : " + userId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } else {
                logger.info(LOG_TAG + " user not exists with userId : " + userId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            logger.info(LOG_TAG + " getProfileImageView Exception : " + e.getMessage());
            return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Override
    public ResponseEntity<?> uploadGroupProfileHandler(MultipartFile file, String groupId) {
        try {
            logger.info(LOG_TAG + " uploadGroupProfileHandler called for groupID : " + groupId);

            // validate groupId
            if(!this.groupService.isGroupExistsById(groupId)){
                logger.info(LOG_TAG + " Group with provided groupId not exists!!, userId : " + groupId);
                return ResponseHandler.generateResponse("Group with provided groupId not exists!!", HttpStatus.NOT_FOUND, null);
            }

            // upload a groupImage
            Files.copy(file.getInputStream(), Paths.get(GROUP_PROFILE_PICTURE_UPLOAD_PATH + File.separator + groupId + ".jpg"), StandardCopyOption.REPLACE_EXISTING);

            // update in DB
            String profilePath = FilePathUtils.GROUP_PROFILE_API_PATH + groupId;
            this.groupService.updateGroupProfilePath(groupId, profilePath);

            return ResponseHandler.generateResponse("Group profile picture uploaded successfully!!", HttpStatus.OK, null);
        }catch (Exception e){
            logger.info(LOG_TAG + " uploadGroupProfileHandler Exception : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception occured while uploading file", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<byte[]> getGroupProfileImageView(String groupId) {
        try {
            if (this.groupService.isGroupExistsById(groupId)) {
                Path imagePath = Paths.get(this.GROUP_PROFILE_PICTURE_UPLOAD_PATH).resolve(groupId + ".jpg");
                Resource imageResource = new UrlResource(imagePath.toUri());

                if (imageResource.exists() && imageResource.isReadable()) {
                    try (InputStream inputStream = imageResource.getInputStream()) {
                        byte[] imageBytes = inputStream.readAllBytes();

                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate media type based on your image type
                        headers.setContentLength(imageBytes.length);

                        return new ResponseEntity<>(imageBytes, headers, org.springframework.http.HttpStatus.OK);
                    } catch (IOException e) {
                        logger.error(LOG_TAG + " Error reading image file: " + e.getMessage());
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                    }
                } else {
                    logger.info(LOG_TAG + " file not exists with userId : " + groupId);
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
                }
            } else {
                logger.info(LOG_TAG + " user not exists with userId : " + groupId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        }catch (Exception e){
            logger.info(LOG_TAG + " getGroupProfileImageView Exception : " + e.getMessage());
            return ResponseEntity.status(org.springframework.http.HttpStatus.NOT_FOUND).body(null);
        }
    }
}
