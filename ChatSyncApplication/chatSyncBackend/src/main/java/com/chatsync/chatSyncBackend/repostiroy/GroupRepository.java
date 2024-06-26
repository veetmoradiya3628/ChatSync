package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, String> {
    boolean existsByGroupName(String groupName);
}
