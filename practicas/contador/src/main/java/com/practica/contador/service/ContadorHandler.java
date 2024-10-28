package com.practica.contador.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ContadorHandler extends TextWebSocketHandler {
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished (WebSocketSession session) throws Exception{
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
        super.handleTextMessage(session, message);

        String msg = message.getPayload().trim();

        int countWords = msg.isEmpty() ? 0 : msg.split("\\s+").length;

        session.sendMessage(new TextMessage(message("El texto enviado tiene " + countWords)));
    }

    public String message(String msj) throws JsonProcessingException {
        Map<String, String> mensaje = new HashMap<>();
        mensaje.put("Mensaje", msj);

        return objectMapper.writeValueAsString(msj);
    }
}
