package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.Messages;
import com.chatsync.chatSyncBackend.model.Threads;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends PagingAndSortingRepository<Messages, String>, JpaRepository<Messages, String> {
    Page<Messages> findByThreadOrderByCreatedAtDesc(Threads threadId, Pageable pageable);
}
