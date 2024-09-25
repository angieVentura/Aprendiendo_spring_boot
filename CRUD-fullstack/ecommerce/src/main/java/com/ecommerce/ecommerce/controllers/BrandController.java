package com.ecommerce.ecommerce.controllers;

import com.ecommerce.ecommerce.models.Brand;
import com.ecommerce.ecommerce.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class BrandController {

    @Autowired
    private BrandRepository brandRepo;
    @GetMapping("/brands")
    public List<Brand> getAllbrands() {
        return brandRepo.findAll();
    }
}
