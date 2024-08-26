package com.CRUD.CRUD.controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.CRUD.CRUD.entity.*;
import com.CRUD.CRUD.repo.ProductRepo;

@RestController
class ProductController {

	@Autowired
	private ProductRepo productRepo;
	
	@GetMapping("/")
	public String page() {
		return "Welcome";
	}
	
	@GetMapping("/product")
	public List<Product> getUsers(){
		return productRepo.findAll();
	}
	
	@GetMapping("/product/{id}")
	public Optional<Product> getProduct(@PathVariable long id){
		return productRepo.findById(id);
	}
	
	@PostMapping("/save")
	public String saveProduct(@RequestBody Product product) {
		productRepo.save(product);
		return "Saved";
	}
	
	@PutMapping("update/{id}")
	public String updateUser(@PathVariable long id, @RequestBody Product product) {
		Product updateProduct = productRepo.findById(id).get();
		updateProduct.setName(product.getName());
		updateProduct.setDescription(product.getDescription());
		updateProduct.setPrice(product.getPrice());
		productRepo.save(updateProduct);
		return "Updated";
	}
	
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable long id) {
		Product deleteProduct = productRepo.findById(id).get();
		productRepo.delete(deleteProduct);
		return "Deleted product with id " + id;
	}
}
