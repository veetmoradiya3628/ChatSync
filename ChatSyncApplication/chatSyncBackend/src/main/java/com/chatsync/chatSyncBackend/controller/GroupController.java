package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.dto.GroupDto;
import com.chatsync.chatSyncBackend.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
@CrossOrigin("*")
public class GroupController {
    private static String LOG_TAG = "GroupController";
    private GroupService groupService;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @PostMapping("/create-group")
    public ResponseEntity<?> createGroupController(@RequestBody GroupDto groupDto){
        logger.info(LOG_TAG + " createGroupController called with : " + groupDto.toString());
        return this.groupService.createGroupService(groupDto);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupInformationController(@PathVariable("groupId") String groupId){
        logger.info(LOG_TAG + " getGroupInformationController called with : " + groupId);
        return this.groupService.getGroupInformation(groupId);
    }
}
