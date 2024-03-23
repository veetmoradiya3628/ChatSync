package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.Group;
import com.chatsync.chatSyncBackend.model.Messages;
import com.chatsync.chatSyncBackend.model.Threads;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ThreadRepository extends JpaRepository<Threads, String> {
    Boolean existsByConversationGroupId(Group group);

    Threads findByConversationGroupId(Group group);

    Boolean existsByConversationId(String conversationId);

    Threads findByConversationId(String conversationId);

    @Query(value = "SELECT * FROM tbl_threads WHERE conv_id LIKE %:userId%", nativeQuery = true)
    List<Threads> getThreadsBasedOnUserId(@Param("userId") String userId);
}
