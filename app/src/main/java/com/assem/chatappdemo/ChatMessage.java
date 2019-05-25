package com.assem.chatappdemo;

import com.google.firebase.firestore.PropertyName;

public class ChatMessage {

    private String id;
    private String sender;
    private String receiver;
    private String message;
    @PropertyName(value = "is_seen")
    private boolean isSeen;

    public ChatMessage() {
    }

    public ChatMessage(String sender, String receiver, String message, boolean isSeen) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.isSeen = isSeen;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @PropertyName(value = "is_seen")
    public boolean isSeen() {
        return isSeen;
    }

    @PropertyName(value = "is_seen")
    public void setSeen(boolean seen) {
        isSeen = seen;
    }
}
