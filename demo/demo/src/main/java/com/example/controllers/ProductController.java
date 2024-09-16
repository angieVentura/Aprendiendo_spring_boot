package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.services.ProductService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import com.example.models.ProductModel;
@RestController
@RequestMapping("/products")
public class ProductController {
	
	ProductService product = new ProductService();

    @GetMapping("/{nombre}")
    public ResponseEntity<ProductModel> getMax(@PathVariable String num) {
        if (num.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        
        ProductModel producto = product.getProductBy(num).get();
    
       
       return ResponseEntity.ok(producto);
    }
}