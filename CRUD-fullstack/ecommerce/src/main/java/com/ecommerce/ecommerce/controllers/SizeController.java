package com.ecommerce.ecommerce.controllers;

import com.ecommerce.ecommerce.models.Size;
import com.ecommerce.ecommerce.repositories.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class SizeController {

    @Autowired
    private SizeRepository sizeRepo;
    @GetMapping("/sizes")
    public List<Size> getAllSizes() {
        return sizeRepo.findAll();
    }
}
