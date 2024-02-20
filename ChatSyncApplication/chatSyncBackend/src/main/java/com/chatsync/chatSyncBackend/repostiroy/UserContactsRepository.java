package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.User;
import com.chatsync.chatSyncBackend.model.UserContacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserContactsRepository extends JpaRepository<UserContacts, String> {
    boolean existsByUserAndContact(User user, User contact);

    UserContacts findByUserAndContact(User user, User contact);

    List<UserContacts> findByUser(User user);
}
