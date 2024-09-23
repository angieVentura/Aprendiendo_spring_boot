package com.ecommerce.ecommerce.controllers;

import com.ecommerce.ecommerce.exceptions.ProductNotFoundException;
import com.ecommerce.ecommerce.models.Product;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
public class ProductController {
    @Autowired
    private ProductRepository productRepo;

    @PostMapping("/product")
    public Product newProduct(@RequestBody Product newProduct) {
        return productRepo.save(newProduct);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productRepo.findById(id)
            .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @PutMapping("/product/{id}")
    public Product updateProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        return productRepo.findById(id)
            .map(product -> {
                product.setName(newProduct.getName());
                product.setDescription(newProduct.getDescription());
                product.setPrice(newProduct.getPrice());

                return productRepo.save(product);
            }).orElseThrow(() -> new ProductNotFoundException(id));
    }

    @DeleteMapping("/product/{id}")
    public String deleteProduct(@PathVariable Long id){
        if(!productRepo.existsById(id)){
            throw new ProductNotFoundException(id);
        }

        productRepo.deleteById(id);
        return "Product con el ID " + id + " eliminao.";
    }
}

