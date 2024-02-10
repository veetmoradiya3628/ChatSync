package com.chatsync.chatSyncBackend.service;

import com.chatsync.chatSyncBackend.model.Role;

public interface RoleService{
    Role getRoleByRoleName(String reqRole);
}
