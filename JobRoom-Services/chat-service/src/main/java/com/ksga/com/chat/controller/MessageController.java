package com.ksga.com.chat.controller;

import com.ksga.com.chat.model.Chat;
import com.ksga.com.chat.repository.MessageRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class MessageController {
    private MessageRep messageRep;

    @Autowired
    public void setMessageRep(MessageRep messageRep) {
        this.messageRep = messageRep;
    }

    @GetMapping("/messages")
    public Chat findAllBySenderIdAndReceiverIdOrderByDate(@RequestParam ("room_number") String roomNumber) {
        return messageRep.getChatByRoomNumber(roomNumber);
    }
}
