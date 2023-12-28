package com.chatsyncserver.chatSyncServer.repository;

import com.chatsyncserver.chatSyncServer.model.AppRoles;
import com.chatsyncserver.chatSyncServer.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(AppRoles name);
}
