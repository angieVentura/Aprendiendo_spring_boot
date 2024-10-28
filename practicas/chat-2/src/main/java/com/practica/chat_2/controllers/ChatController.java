package com.practica.chat_2.controllers;

import org.springframework.web.bind.annotation.GetMapping;

public class ChatController {
    @GetMapping("/")
    public String showPage(){
        return "index";
    }
}
