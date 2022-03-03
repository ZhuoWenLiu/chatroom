package com.liuzhuowen.chatroom.endpoint;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class HttpSessionConfigurator extends ServerEndpointConfig.Configurator {
    public HttpSessionConfigurator() {
        System.out.println("HttpSessionConfigurator()");
    }

    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        Object httpSession = request.getHttpSession();
        if (httpSession != null) {
            sec.getUserProperties().put("httpSession", httpSession);
        }
    }
}
