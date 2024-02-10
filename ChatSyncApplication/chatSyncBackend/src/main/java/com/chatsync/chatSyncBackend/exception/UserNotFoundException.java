package com.chatsync.chatSyncBackend.exception;

public class UserNotFoundException extends Exception {
    public UserNotFoundException(){
        super("User with this username not found in database!!!");
    }
}
