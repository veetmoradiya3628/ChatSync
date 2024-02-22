package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.Group;
import com.chatsync.chatSyncBackend.model.GroupMembers;
import com.chatsync.chatSyncBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, String> {
    GroupMembers getGroupMembersByGroupAndUser(Group group, User user);
    boolean existsByGroupAndUser(Group group, User user);
}
