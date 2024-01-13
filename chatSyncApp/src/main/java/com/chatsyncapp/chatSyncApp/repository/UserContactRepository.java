package com.chatsyncapp.chatSyncApp.repository;

import com.chatsyncapp.chatSyncApp.model.UserContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactRepository extends JpaRepository<UserContacts, String> {
}
