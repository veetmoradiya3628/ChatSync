package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.dto.GroupDto;
import com.chatsync.chatSyncBackend.dto.UserDto;
import com.chatsync.chatSyncBackend.model.Group;
import com.chatsync.chatSyncBackend.model.GroupMembers;
import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.model.utils.GroupMemberRole;
import com.chatsync.chatSyncBackend.repostiroy.GroupMembersRepository;
import com.chatsync.chatSyncBackend.repostiroy.GroupRepository;
import com.chatsync.chatSyncBackend.service.GroupService;
import com.chatsync.chatSyncBackend.utils.ResponseHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {
    private static String LOG_TAG = "GroupServiceImpl";
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private GroupRepository groupRepository;
    private GroupMembersRepository groupMembersRepository;
    private UserServiceImpl userService;

    public GroupServiceImpl(GroupRepository groupRepository, GroupMembersRepository groupMembersRepository, UserServiceImpl userService) {
        this.groupRepository = groupRepository;
        this.groupMembersRepository = groupMembersRepository;
        this.userService = userService;
    }

    @Override
    public ResponseEntity<?> createGroupService(GroupDto groupDto) {
        try {
            logger.info(LOG_TAG + " createGroupService called with : " + groupDto.toString());
            // validate for group already exists with this name
            if (groupDto.getGroupName() != null && !this.groupRepository.existsByGroupName(groupDto.getGroupName())){
                List<UserDto> admins = groupDto.getAdmins();
                if (admins.isEmpty()){
                    return ResponseHandler.generateResponse("At least one admin member is required to create a group!!", HttpStatus.OK, null);
                }

                List<UserDto> members = groupDto.getMembers();

                Group group = Group.builder()
                        .groupName(groupDto.getGroupName())
                        .groupProfileImage(groupDto.getGroupProfileImage())
                        .isDeleted(Boolean.FALSE)
                        .build();
                logger.info(LOG_TAG + " built group object from request data : " + group.toString());
                Group savedGroup = this.groupRepository.save(group);
                logger.info(LOG_TAG + " saved group object to database : " + savedGroup);

                // no validation for member and admin uuid's as of now, its assumed that it will be valid only, later i can put here
                String savedGroupId = savedGroup.getGroupId();

                // proceeding for adding admin
                if (!admins.isEmpty()){
                    logger.info(LOG_TAG + " proceeding for adding admin to group : " + admins.size());
                    admins.forEach(adminUser -> {
                        addMemberToGroup(adminUser.getUserId(), savedGroupId, GroupMemberRole.ADMIN);
                    });
                    logger.info(LOG_TAG + " member addition is completed for group : " + admins.size());
                }

                // proceeding for adding member to group
                if (members != null && !members.isEmpty()){
                    logger.info(LOG_TAG + " proceeding for adding member to group : " + members.size());
                    members.forEach(member -> {
                        addMemberToGroup(member.getUserId(), savedGroupId, GroupMemberRole.MEMBER);
                    });
                    logger.info(LOG_TAG + " member addition is successful for group : " + members.size());
                }

                savedGroup = this.groupRepository.findById(savedGroupId).get();
                return ResponseHandler.generateResponse("Group created successfully!!", HttpStatus.OK, savedGroup);

            }else{
                return ResponseHandler.generateResponse("Invalid group name or group already exists with this group name", HttpStatus.NOT_ACCEPTABLE, null);
            }
        } catch (Exception e) {
            logger.info(LOG_TAG + "Exception occurred in the function createGroupService : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> getGroupInformation(String groupId) {
        try {
            if (this.isGroupExistsById(groupId)) {
                Group group = this.groupRepository.findById(groupId).get();
                logger.info(LOG_TAG + " groupInfo : " + group);

                List<UserDto> members = new ArrayList<>();
                List<UserDto> admins = new ArrayList<>();
                group.getMembers().forEach(member -> {
                    GroupMembers groupMember = this.groupMembersRepository.getGroupMembersByGroupAndUser(new Group(groupId), member);

                    UserDto user = UserDto.builder()
                            .userId(member.getUserId())
                            .username(member.getUsername())
                            .email(member.getEmail())
                            .profileImage(member.getProfileImage())
                            .isActive(member.isActive())
                            .phoneNo(member.getPhoneNo())
                            .firstName(member.getFirstName())
                            .lastName(member.getLastName())
                            .createdAt(member.getCreatedAt())
                            .updatedAt(member.getUpdatedAt()).build();

                    if (groupMember.getGroupMemberRole().equals(GroupMemberRole.ADMIN)) {
                        admins.add(user);
                    } else if (groupMember.getGroupMemberRole().equals(GroupMemberRole.MEMBER)) {
                        members.add(user);
                    }
                });

                GroupDto groupDto = GroupDto.builder()
                        .groupId(group.getGroupId())
                        .groupName(group.getGroupName())
                        .groupProfileImage(group.getGroupProfileImage())
                        .members(members)
                        .admins(admins)
                        .isDeleted(group.getIsDeleted())
                        .createdAt(group.getCreatedAt())
                        .updatedAt(group.getUpdatedAt()).build();

                // format further for response data

                return ResponseHandler.generateResponse("Group information for provided groupId", HttpStatus.OK, groupDto);
            } else {
                logger.info(LOG_TAG + " group not exists with provided groupId : " + groupId);
                return ResponseHandler.generateResponse(" group not exists with provided groupId : " + groupId, HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception e) {
            logger.info(LOG_TAG + "Exception occurred in the function getGroupInformation : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> addMemberToGroup(String groupId, String userId, String groupMemberRole) {
        try{
            logger.info(LOG_TAG + " addMemberToGroup started ...");
            if (this.isGroupExistsById(groupId)){
                if (this.userService.isUserExistsById(userId)){
                    groupMemberRole = groupMemberRole.toUpperCase();
                    if (groupMemberRole.equals(String.valueOf(GroupMemberRole.MEMBER)) || groupMemberRole.equals(String.valueOf(GroupMemberRole.ADMIN))){
                        if(!this.groupMembersRepository.existsByGroupAndUser(new Group(groupId),
                                new User(userId))){

                            GroupMembers groupMember = GroupMembers.builder()
                                    .group(new Group(groupId))
                                    .user(new User(userId))
                                    .groupMemberRole(GroupMemberRole.valueOf(groupMemberRole)).build();

                            logger.info(LOG_TAG + " final object to be stored : " + groupMember.toString());
                            groupMember = this.groupMembersRepository.save(groupMember);
                            return ResponseHandler.generateResponse("Member added to group successfully", HttpStatus.OK, groupMember);

                        }else{
                            logger.info(LOG_TAG + " user is already a part of group");
                            return ResponseHandler.generateResponse("user is already a part of group", HttpStatus.OK, null);
                        }
                    }else{
                        logger.info(LOG_TAG + " groupMember role is not valid");
                        return ResponseHandler.generateResponse("Invalid group member role passed", HttpStatus.NOT_FOUND, null);
                    }
                }else{
                    logger.info(LOG_TAG + " user not exists by userId : " + userId);
                    return ResponseHandler.generateResponse("User not exists with userId : " + userId, HttpStatus.NOT_FOUND, null);
                }
            }else{
                logger.info(LOG_TAG + " group not exists by groupId : " + groupId);
                return ResponseHandler.generateResponse("Group not exists by groupId : " + groupId, HttpStatus.NOT_FOUND, null);
            }
        }catch (Exception e){
            logger.info(LOG_TAG + "Exception occurred in the function addMemberToGroup : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @Override
    public ResponseEntity<?> removeMemberFromGroup(String groupId, String userId) {
        try{
            logger.info(LOG_TAG + " removeMemberFromGroup started ...");
            if (this.groupMembersRepository.existsByGroupAndUser(new Group(groupId), new User(userId))){
                GroupMembers groupMember = this.groupMembersRepository.getGroupMembersByGroupAndUser(new Group(groupId), new User(userId));

                logger.info(LOG_TAG + " removing object : " + groupMember.toString());
                this.groupMembersRepository.delete(groupMember);

                return ResponseHandler.generateResponse("Member removed from group successfully!!", HttpStatus.OK, null);
            }else{
                logger.info(LOG_TAG + " entry does not exists with provided groupID : " + groupId +" and userId : " + userId);
                return ResponseHandler.generateResponse("User is not part of group!!", HttpStatus.NOT_FOUND, null);
            }
        }catch (Exception e){
            logger.info(LOG_TAG + "Exception occurred in the function removeMemberFromGroup : " + e.getMessage());
            return ResponseHandler.generateResponse("Exception : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    private boolean isGroupExistsById(String groupId){
        return this.groupRepository.findById(groupId).isPresent();
    }

    private void addMemberToGroup(String userId, String groupId, GroupMemberRole role){
        GroupMembers groupMember = GroupMembers.builder()
                .group(new Group(groupId))
                .user(new User(userId))
                .groupMemberRole(role).build();
        this.groupMembersRepository.save(groupMember);
    }
}
