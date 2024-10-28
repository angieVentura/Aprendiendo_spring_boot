package com.practica.calculadora.config;

import com.practica.calculadora.service.CalculadoraHandle;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final CalculadoraHandle calculadoraHandle;

    public WebSocketConfig (CalculadoraHandle calculadoraHandle){
        this.calculadoraHandle = calculadoraHandle;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(calculadoraHandle, "/calculadora").setAllowedOrigins("*");
    }
}
