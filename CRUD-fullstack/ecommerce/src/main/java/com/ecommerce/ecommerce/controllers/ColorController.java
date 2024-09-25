package com.ecommerce.ecommerce.controllers;

import com.ecommerce.ecommerce.models.Color;
import com.ecommerce.ecommerce.repositories.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ColorController {

    @Autowired
    private ColorRepository colorRepo;
    @GetMapping("/colors")
    public List<Color> getAllColors() {
        return colorRepo.findAll();
    }
}
