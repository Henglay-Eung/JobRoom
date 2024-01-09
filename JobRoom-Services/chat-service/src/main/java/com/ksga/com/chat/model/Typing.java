package com.ksga.com.chat.model;

import java.util.UUID;

public class Typing {
    private UUID senderId;
    private String sender;
    private String receiverName;
    private String receiverUsername;
    public Typing(){}

    public Typing(UUID senderId, String sender, String receiverName, String receiverUsername) {
        this.senderId = senderId;
        this.sender = sender;
        this.receiverName = receiverName;
        this.receiverUsername = receiverUsername;
    }

    public String getReceiverUsername() {
        return receiverUsername;
    }

    public void setReceiverUsername(String receiverUsername) {
        this.receiverUsername = receiverUsername;
    }

    public UUID getSenderId() {
        return senderId;
    }

    public void setSenderId(UUID senderId) {
        this.senderId = senderId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }
}
