package com.ksga.com.chat.controller;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.ksga.com.chat.model.Chat;
import com.ksga.com.chat.model.Typing;
import com.ksga.com.chat.model.Person;
import com.ksga.com.chat.repository.MessageRep;
import com.ksga.com.chat.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class ChatController {
    private SocketIONamespace namespace;
    private Map<String, UUID> users = new HashMap<>();

    public SocketIONamespace getNamespace() {
        return namespace;
    }

    private SocketIOServer socketIOServer;
    private UserRepo userRep;
    private MessageRep messageRep;

    @Autowired
    public void setMessageRep(MessageRep messageRep) {
        this.messageRep = messageRep;
    }

    @Autowired
    public void setUserRep(UserRepo userRep) {
        this.userRep = userRep;
    }

    @Autowired
    public ChatController(SocketIOServer socketIOServer) {
        this.socketIOServer = socketIOServer;
        this.namespace = socketIOServer.addNamespace("/chat");
        this.namespace.addConnectListener(onConnectListener);
        this.namespace.addDisconnectListener(onDisconnectListener);
        this.namespace.addEventListener("sendMessage", Chat.class, onUserSendMessage);
        this.namespace.addEventListener("userJoin", Person.class, onUserJoinChat);
        this.namespace.addEventListener("userTyping", Typing.class, onUserTyping);
        this.namespace.addEventListener(     "userStopTyping", Typing.class, onUserStopTyping);
//        socketIOServer.addConnectListener(onConnectListener);
//        socketIOServer.addDisconnectListener(onDisconnectListener);
//        socketIOServer.addEventListener("sendMessage", Message.class, onUserSendMessage);
    }

    public ConnectListener onConnectListener = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            System.out.println("Connected!");
        }
    };
    public DisconnectListener onDisconnectListener = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
            namespace.getBroadcastOperations().sendEvent("userLeft", users.get(client));
            users.remove(client);
            namespace.getBroadcastOperations().sendEvent("count", users.size());
            System.out.println("Client " + client.getSessionId() + " disconnected");
        }
    };
    public DataListener<Chat> onUserSendMessage = new DataListener<Chat>() {

        @Override
        public void onData(SocketIOClient client, Chat data, AckRequest ackSender) throws Exception {
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            data.setDate(timestamp);
            String roomNumber = data.getSenderId() + data.getSenderName() + data.getReceiverId() + data.getReceiverName();
            data.setRoomNumber(roomNumber);
            Chat chat = messageRep.getChatByRoomNumber(data.getRoomNumber());
            System.out.println(data);
            if(chat!=null)
                data.setId(chat.getId());
            messageRep.save(data);

            UUID receiver = users.get(data.getReceiverUsername());
//
           namespace.getClient(receiver).sendEvent("newMessage", data);
            System.out.println(data);
            System.out.println("works");
        }
    };
    public DataListener<Person> onUserJoinChat = new DataListener<Person>() {
        @Override
        public void onData(SocketIOClient client, Person user, AckRequest ackSender) throws Exception {

            users.put(user.getUsername(), UUID.fromString(user.getSessionId()));
            namespace.getBroadcastOperations().sendEvent("newUser", user);
            namespace.getBroadcastOperations().sendEvent("count", users.size());
        }
    };
    public DataListener<Typing> onUserTyping = new DataListener<Typing>() {
        @Override
        public void onData(SocketIOClient client, Typing typing, AckRequest arg2) throws Exception {
            UUID receiver = users.get(typing.getReceiverUsername());
            System.out.println(typing.getReceiverName());
            namespace.getClient(receiver).sendEvent("userTyping", typing);
        }
    };

    public DataListener<Typing> onUserStopTyping = new DataListener<Typing>() {
        @Override
        public void onData(SocketIOClient client, Typing typing, AckRequest arg2) throws Exception {

            UUID receiver = users.get(typing.getReceiverUsername());
            namespace.getClient(receiver).sendEvent("userStopTyping", typing);
        }
    };
}
