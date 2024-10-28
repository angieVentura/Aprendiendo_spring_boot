package com.practica.contador.config;

import com.practica.contador.service.ContadorHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final ContadorHandler contadorHandler;

    public WebSocketConfig (ContadorHandler contadorHandler){
        this.contadorHandler = contadorHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(contadorHandler, "/contador").setAllowedOrigins("*");
    }
}
