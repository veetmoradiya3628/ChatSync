package com.chatsync.chatSyncBackend.service.Impl;

import com.chatsync.chatSyncBackend.model.Role;
import com.chatsync.chatSyncBackend.repostiroy.RoleRepository;
import com.chatsync.chatSyncBackend.service.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role getRoleByRoleName(String reqRole) {
        if(this.roleRepository.existsByRoleName(reqRole)) return this.roleRepository.findByRoleName(reqRole);
        return this.roleRepository.save(new Role(reqRole));
    }
}
