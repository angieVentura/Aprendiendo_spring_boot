package com.CRUD.CRUD.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CRUD.CRUD.entity.Product;

public interface ProductRepo extends JpaRepository<Product, Long> {

}
