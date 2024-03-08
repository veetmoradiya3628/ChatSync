package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.dto.ThreadDto;
import com.chatsync.chatSyncBackend.service.Impl.ThreadServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thread")
@CrossOrigin("*")
@Slf4j
public class ThreadController {
    private final String LOG_TAG = "ThreadController";
    private ThreadServiceImpl threadService;

    public ThreadController(ThreadServiceImpl threadService) {
        this.threadService = threadService;
    }

    @PostMapping("/create-new-thread")
    public ResponseEntity<?> createNewThreadController(@RequestBody ThreadDto threadDto){
        log.info(LOG_TAG + " createdNewThread called with " + threadDto.toString());
        return threadService.createNewThreadService(threadDto);
    }

    @GetMapping("/get-threads/{userId}")
    public ResponseEntity<?> getThreadsForUserController(@PathVariable("userId") String userId){
        log.info(LOG_TAG + " getThreadsForUserController called with userId : " + userId);
        return threadService.getThreadsForUserService(userId);
    }

}
