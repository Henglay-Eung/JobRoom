package com.ksga.com.chat.configuration;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PreDestroy;

@Configuration
public class SocketIOServerConfiguration {
    //   private static final String SOCKET_IO_HOST = "0.0.0.0";
    private static final String SOCKET_IO_HOST = "0.0.0.0";
    //http://35.197.132.204:31000/
//    private static final int SOCKET_IO_PORT = 8888;
     private static final int SOCKET_IO_PORT = 30003;

    private SocketIOServer socketIOServer;

    @Bean
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(SOCKET_IO_HOST);
        config.setPort(SOCKET_IO_PORT);
        socketIOServer = new SocketIOServer(config);
        socketIOServer.start();
        socketIOServer.addConnectListener(new ConnectListener() {
            @Override
            public void onConnect(SocketIOClient client) {
                System.out.println("Client connected: " + client.getSessionId());
            }
        });
        socketIOServer.addDisconnectListener(new DisconnectListener() {
            @Override
            public void onDisconnect(SocketIOClient client) {
                System.out.println("Client disconnected: " + client.getSessionId());
            }
        });
        return socketIOServer;
    }
    @PreDestroy
    public void stopSocketIOServer(){
        this.socketIOServer.stop();
    }

}
