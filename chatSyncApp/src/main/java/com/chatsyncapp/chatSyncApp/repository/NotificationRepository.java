package com.chatsyncapp.chatSyncApp.repository;

import com.chatsyncapp.chatSyncApp.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {
}
