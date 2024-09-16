package com.example.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.models.ProductModel;

@Service
public class ProductService {

	List<ProductModel> products = Arrays.asList(
			new ProductModel(100, "lol"),
			new ProductModel(1004, "lo4l")
			);
	
	
	public Optional<ProductModel> getProductBy(String name) {
		return products.stream().filter(u -> u.getName().equalsIgnoreCase(name)).findFirst();
	}
	
}
