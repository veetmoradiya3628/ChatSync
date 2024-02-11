package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByVerificationToken(String token);

    boolean existsByResetPasswordOtp(String otp);

    boolean existsByEmailAndVerificationToken(String email, String token);
}
