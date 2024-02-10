package com.chatsync.chatSyncBackend.repostiroy;

import com.chatsync.chatSyncBackend.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, String>{
    Role findByRoleName(String roleName);
    Boolean existsByRoleName(String roleName);
}
