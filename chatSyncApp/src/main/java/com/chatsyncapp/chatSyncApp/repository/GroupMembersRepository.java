package com.chatsyncapp.chatSyncApp.repository;

import com.chatsyncapp.chatSyncApp.model.GroupMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupMembersRepository extends JpaRepository<GroupMembers, Long> {
}
