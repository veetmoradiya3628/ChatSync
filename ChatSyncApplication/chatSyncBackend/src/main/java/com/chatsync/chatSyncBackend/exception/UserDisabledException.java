package com.chatsync.chatSyncBackend.exception;

public class UserDisabledException extends RuntimeException{
    public UserDisabledException(String message) {
        super(message);
    }
}
