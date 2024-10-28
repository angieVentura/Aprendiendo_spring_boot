package com.practica.contador.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContadorController {

    @GetMapping("/")
    public String index(){
        return "index";
    }
}
