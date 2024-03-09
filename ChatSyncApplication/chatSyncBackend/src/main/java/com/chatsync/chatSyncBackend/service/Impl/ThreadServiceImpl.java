package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.dto.ThreadDto;
import com.chatsync.chatSyncBackend.model.Group;
import com.chatsync.chatSyncBackend.model.Threads;
import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.model.utils.ConversationType;
import com.chatsync.chatSyncBackend.repostiroy.ThreadRepository;
import com.chatsync.chatSyncBackend.service.ThreadService;
import com.chatsync.chatSyncBackend.service.UserService;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class ThreadServiceImpl implements ThreadService {

    private final String LOG_TAG = "ThreadServiceImpl";

    private ThreadRepository threadRepository;
    private GroupServiceImpl groupService;
    private UserServiceImpl userService;

    public ThreadServiceImpl(ThreadRepository threadRepository, GroupServiceImpl groupService, UserServiceImpl userService) {
        this.threadRepository = threadRepository;
        this.groupService = groupService;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> createNewThreadService(ThreadDto threadDto) {
        try {
            log.info(LOG_TAG + " createNewThreadService called with " + threadDto);
            ConversationType convType = threadDto.getConversationType();
            switch (convType) {
                case GROUP:
                    return createGroupThread(threadDto);
                case ONE_TO_ONE:
                    return createOneToOneChatThread(threadDto);
                case null:
                    return ResponseHandler.generateResponse("Not a valid conversation type", HttpStatus.NOT_ACCEPTABLE, null);
            }
        } catch (Exception e) {
            log.info(LOG_TAG + " exception in createNewThreadService : " + e.getMessage());
            return ResponseHandler.generateResponse("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> getThreadsForUserService(String userId) {
        try {
            log.info(LOG_TAG + " getThreadsForUserService called with userId : " + userId);
            if (this.userService.isUserExistsById(userId)) {
                List<Threads> threads = new ArrayList<>();

                threads.addAll(getOneToOneConvThreads(userId)); // getting one to one thread
                threads.addAll(getGroupConvThreads(userId)); // getting group thread

                // sort the threads based on update timer
                log.info(LOG_TAG + " threads cnt : " + threads.size());
                threads.sort(Comparator.comparing(Threads::getUpdatedAt));

                // convert to responseDTO
                List<ThreadDto> resp = convertToThreadDto(threads, userId);

                return ResponseHandler.generateResponse(null, HttpStatus.OK, resp);
            }
            log.info(LOG_TAG + " user not exists with userId : " + userId);
            return ResponseHandler.generateResponse("User not exists with userId : " + userId, HttpStatus.NOT_FOUND, null);
        } catch (Exception e) {
            log.info(LOG_TAG + " exception in getThreadsForUserService : " + e.getMessage());
            return ResponseHandler.generateResponse("Something went wrong!!", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private List<ThreadDto> convertToThreadDto(List<Threads> threads, String userId) {
        List<ThreadDto> threadDTOs = new ArrayList<>();

        threads.forEach(thread -> {
            threadDTOs.add(getThreadDto(thread, userId));
        });

        return threadDTOs;
    }

    private ThreadDto getThreadDto(Threads thread, String userId) {
        log.info(LOG_TAG + " getThreadDto called with thread " + thread);
        ThreadDto resp = new ThreadDto();
        ConversationType conversationType = thread.getConversationType();
        switch (conversationType) {
            case ONE_TO_ONE :
                List<String> userIds = List.of(thread.getConversationId().split("_"));
                log.info(LOG_TAG + " users : " + userIds);
                User user = null;
                if (userIds.get(0).equals(userId)){
                    user = this.userService.getUserByUserId(userIds.get(1));
                }else if (userIds.get(1).equals(userId)){
                    user = this.userService.getUserByUserId(userIds.get(0));
                }

                if (user != null){
                    resp.setConversationName(user.getFirstName() + " " + user.getLastName());
                    resp.setProfileImage(user.getProfileImage());
                }

                resp.setConversationId(thread.getConversationId());
                resp.setUserA(userId);
                resp.setUserB(user.getUserId());
                break;
            case GROUP:
                resp.setProfileImage(thread.getConversationGroupId().getGroupProfileImage());
                resp.setConversationGroupId(thread.getConversationGroupId().getGroupId());
                resp.setConversationName(thread.getConversationGroupId().getGroupName());
                resp.setMemberCnt(thread.getConversationGroupId().getMembers().size());
                break;
        }
        resp.setConversationType(thread.getConversationType());
        resp.setThreadId(thread.getThreadId());
        resp.setCreatedAt(thread.getCreatedAt());
        resp.setUpdatedAt(thread.getUpdatedAt());
        log.info(LOG_TAG + " generated resp ThreadDto : " + resp);
        return resp;
    }

    public ResponseEntity<?> createGroupThread(ThreadDto threadDto) {
        log.info(LOG_TAG + " createGroupThread called...");
        String groupId = threadDto.getConversationGroupId();
        if (this.groupService.isGroupExistsById(groupId)) {
            if (!this.threadRepository.existsByConversationGroupId(new Group(groupId))) {
                log.info(LOG_TAG + " thread not exists so going for creating new thread");

                Threads newThread = Threads.builder()
                        .conversationType(ConversationType.GROUP)
                        .conversationGroupId(new Group(groupId))
                        .build();

                newThread = this.threadRepository.save(newThread);
                log.info(LOG_TAG + " newThread : " + newThread);
                return ResponseHandler.generateResponse("Thread details", HttpStatus.OK, newThread);
            } else {
                Threads thread = this.threadRepository.findByConversationGroupId(new Group(groupId));
                log.info(LOG_TAG + " thead already exists " + thread.toString());
                return ResponseHandler.generateResponse("Thread details", HttpStatus.OK, thread);
            }
        }
        return ResponseHandler.generateResponse("Group not exists with provided groupId : " + groupId, HttpStatus.NOT_FOUND, null);
    }

    public ResponseEntity<?> createOneToOneChatThread(ThreadDto threadDto) {
        log.info(LOG_TAG + " createOneToOneChatThread called...");
        String senderId = threadDto.getUserA();
        String receiverId = threadDto.getUserB();
        if (this.userService.isUserExistsById(senderId) && this.userService.isUserExistsById(receiverId)) {
            List<String> convIds = generateConvId(threadDto.getUserA(), threadDto.getUserB());
            log.info(LOG_TAG + " convIds : " + convIds);
            if (this.threadRepository.existsByConversationId(convIds.get(0))) {
                Threads thread = this.threadRepository.findByConversationId(convIds.get(0));
                return ResponseHandler.generateResponse("Thread details", HttpStatus.OK, thread);
            } else if (this.threadRepository.existsByConversationId(convIds.get(1))) {
                Threads thread = this.threadRepository.findByConversationId(convIds.get(1));
                return ResponseHandler.generateResponse("Thread details", HttpStatus.OK, thread);
            } else {
                Threads newThread = Threads.builder()
                        .conversationId(convIds.get(0))
                        .conversationType(ConversationType.ONE_TO_ONE)
                        .build();
                newThread = this.threadRepository.save(newThread);
                return ResponseHandler.generateResponse("Thread details", HttpStatus.OK, newThread);
            }
        }
        return ResponseHandler.generateResponse("sender or receiver is not exists", HttpStatus.NOT_FOUND, null);
    }

    public List<Threads> getOneToOneConvThreads(String userId) {
        return this.threadRepository.getThreadsBasedOnUserId(userId);
    }

    public List<Threads> getGroupConvThreads(String userId) {
        List<String> groupIds = this.groupService.getGroupIdsForUser(userId);
        log.info(LOG_TAG + " groupIds for user : " + groupIds);
        List<Threads> groupThreads = new ArrayList<>();
        groupIds.forEach(groupId -> {
            if (this.threadRepository.existsByConversationGroupId(new Group(groupId))) {
                groupThreads.add(this.threadRepository.findByConversationGroupId(new Group(groupId)));
            }
        });
        log.info(LOG_TAG + " groupThreadCnt : " + groupThreads.size());
        return groupThreads;
    }

    public List<String> generateConvId(String userA, String userB) {
        List<String> convIds = new ArrayList<>();
        convIds.add(userA + "_" + userB);
        convIds.add(userB + "_" + userA);
        return convIds;
    }

}
