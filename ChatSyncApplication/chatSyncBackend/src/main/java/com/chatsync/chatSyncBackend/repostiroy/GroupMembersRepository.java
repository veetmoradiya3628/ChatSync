package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, String> {
}
