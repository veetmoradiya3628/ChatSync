package com.chatsync.chatSyncBackend.controller;

import com.chatsync.chatSyncBackend.dto.GroupDto;
import com.chatsync.chatSyncBackend.model.utils.GroupMemberRole;
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
    public ResponseEntity<?> createGroupController(@RequestBody GroupDto groupDto) {
        logger.info(LOG_TAG + " createGroupController called with : " + groupDto.toString());
        return this.groupService.createGroupService(groupDto);
    }

    @GetMapping("{groupId}")
    public ResponseEntity<?> getGroupInformationController(@PathVariable("groupId") String groupId) {
        logger.info(LOG_TAG + " getGroupInformationController called with : " + groupId);
        return this.groupService.getGroupInformation(groupId);
    }

    @PostMapping("/add-user")
    public ResponseEntity<?> addMemberToGroupController(@RequestParam String groupId,
                                                        @RequestParam String userId,
                                                        @RequestParam String groupMemberRole) {
        logger.info(LOG_TAG + " addMemberToGroupController called with groupId : " + groupId + ", userId : " + userId + " , groupMemberRole : " + groupMemberRole);
        return this.groupService.addMemberToGroup(groupId, userId, groupMemberRole);
    }

    @PostMapping("/remove-user")
    public ResponseEntity<?> removeMemberFromGroupController(@RequestParam String groupId,
                                                             @RequestParam String userId) {
        logger.info(LOG_TAG + " removeMemberFromGroupController called with groupId : " + groupId + " ,userId : " + userId);
        return this.groupService.removeMemberFromGroup(groupId, userId);
    }

    @PostMapping("/update-user-role")
    public ResponseEntity<?> updateUserRoleToGroupController(@RequestParam String groupId,
                                                             @RequestParam String userId,
                                                             @RequestParam String role) {
        logger.info(LOG_TAG + " updateUserRoleToGroupController called with groupId : " + groupId + ", userId : " + userId + " , groupMemberRole : " + role);
        return this.groupService.updateUserRoleToGroup(userId, role, groupId);
    }

    @PostMapping("/delete-group/{groupId}")
    public ResponseEntity<?> deleteGroupController(@PathVariable("groupId") String groupId){
        logger.info(LOG_TAG + " deleteGroupController called with groupId : " + groupId);
        return this.groupService.deleteGroup(groupId);
    }

    @GetMapping("/getGroupsForUser/{userId}")
    public ResponseEntity<?> getGroupsForUserController(@PathVariable("userId") String userId){
        logger.info(LOG_TAG + " getGroupsForUserController called with userId : " + userId);
        return this.groupService.getGroupsForUser(userId);
    }
}
