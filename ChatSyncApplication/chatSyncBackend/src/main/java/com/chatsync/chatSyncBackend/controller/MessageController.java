package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.dto.MessageDto;
import com.chatsync.chatSyncBackend.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/message")
@CrossOrigin("*")
@Slf4j
public class MessageController {
    private final String LOG_TAG = "MessageController";
    private MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/sent-message")
    public ResponseEntity<?> sentMessageController(@RequestBody MessageDto messageDto){
        log.info(LOG_TAG + " sentMessageController called with " + messageDto);
        return messageService.sentMessageService(messageDto);
    }

    @GetMapping("/get-messages/{threadId}/user/{userId}")
    public ResponseEntity<?> getMessagesForThreadController(@PathVariable String threadId,
                                                            @PathVariable String userId,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) {
        log.info("{} getMessagesForThreadController called for threadId: {} with page: {} and size: {} by userId : {}", LOG_TAG, threadId, page, size, userId);
        return messageService.getMessagesForThreadService(threadId, page, size, userId);
    }
}
