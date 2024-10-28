package com.practica.calculadora.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.practica.calculadora.config.WebSocketConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class CalculadoraHandle extends TextWebSocketHandler {

    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<WebSocketSession>();

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus close) throws Exception {
        super.afterConnectionClosed(session, close);
        sessions.remove(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
        super.handleTextMessage(session, msg);

        String msj = msg.getPayload().trim();

        session.sendMessage(new TextMessage(message(msj)));
    }

    public String message (String msg) throws JsonProcessingException {

        Map<String, String> mensaje = new HashMap<>();

        mensaje.put("Resultado", msg);

        return objectMapper.writeValueAsString(new TextMessage(msg));
    }

}
