package com.practica.chat_2.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ChatHandler extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception{
        super.afterConnectionEstablished(session);

        sessions.add(session);

        String welcomeMessage = createWelcomeMessage("lol");
        session.sendMessage(new TextMessage(welcomeMessage));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        super.handleTextMessage(session,message);
        for(WebSocketSession webSocketSession : sessions){
            webSocketSession.sendMessage(message);
        }
    }

    public String createWelcomeMessage(String name) throws JsonProcessingException {
        String currentTime = new SimpleDateFormat("HH:mm:ss").format(new Date());
        Map<String, String> message = new HashMap<>();
        message.put("Name: ", name);
        message.put("Message: ", "Holi");
        message.put("time", currentTime);

        return objectMapper.writeValueAsString(message);
    }
}
