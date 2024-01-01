package com.chatsyncapp.chatSyncApp.repository;

import com.chatsyncapp.chatSyncApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByEmail(String email);
}