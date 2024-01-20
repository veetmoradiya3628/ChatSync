package com.chatsyncapp.chatSyncApp.service.impl;

import com.chatsyncapp.chatSyncApp.dto.UserDTO;
import com.chatsyncapp.chatSyncApp.service.ContactsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContactsServiceImpl implements ContactsService {
    private static final String LOGGER_TAG = "ContactsServiceImpl : ";
    Logger logger = LoggerFactory.getLogger(ContactsServiceImpl.class);

    @Override
    public List<UserDTO> getAllActiveAnd() {
        logger.info(LOGGER_TAG + " getAllActiveAnd called");
        return new ArrayList<UserDTO>();
    }
}
