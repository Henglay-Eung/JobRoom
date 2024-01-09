package com.ksga.com.chat.controller;

import com.ksga.com.chat.model.Person;
import com.ksga.com.chat.repository.MessageRep;
import com.ksga.com.chat.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserRepo userRep;

    @Autowired
    public void setUserRep(UserRepo userRep) {
        this.userRep = userRep;
    }

    private MessageRep messageRep;

    @Autowired
    public void setMessageRep(MessageRep messageRep) {
        this.messageRep = messageRep;
    }

    @GetMapping("/users")
    public List<Person> findAll() {
        return userRep.findAll();
    }

    @PostMapping("/users")
    @CrossOrigin
    public ResponseEntity<Person> add(@RequestBody Person user) {
        userRep.save(user);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/company/{senderId}")
    public ResponseEntity<List<Integer>> getReceiverIdCmp(@PathVariable int senderId){
        List receiverIds = messageRep.getReceiverIdBySenderCmp(senderId);
        return ResponseEntity.ok(receiverIds);
    }


    @GetMapping("/employees/{senderId}")
    public ResponseEntity<List<Integer>> getReceiverIdEmp(@PathVariable int senderId){
        List receiverIds = messageRep.getReceiverIdBySenderEmp(senderId);
        return ResponseEntity.ok(receiverIds);
    }

}
