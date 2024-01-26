package com.chatsyncapp.chatSyncApp.repository;

import com.chatsyncapp.chatSyncApp.model.User;
import com.chatsyncapp.chatSyncApp.model.UserContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContactRepository extends JpaRepository<UserContacts, String> {
    List<UserContacts> findByUserId(User user);

    List<UserContacts> findByUserIdAndContactUserId(User user, User contactUser);
}
