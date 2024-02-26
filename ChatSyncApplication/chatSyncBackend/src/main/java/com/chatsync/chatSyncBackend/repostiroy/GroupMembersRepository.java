package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.Group;
import com.chatsync.chatSyncBackend.model.GroupMembers;
import com.chatsync.chatSyncBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, String> {
    GroupMembers getGroupMembersByGroupAndUser(Group group, User user);
    boolean existsByGroupAndUser(Group group, User user);

    List<GroupMembers> getGroupMembersByUser(User user);

    @Query(value = "SELECT count(*) from tbl_group_members where group_id = :groupId", nativeQuery = true)
    Integer getMembersCountForGroup(@Param("groupId") String groupId);
}
