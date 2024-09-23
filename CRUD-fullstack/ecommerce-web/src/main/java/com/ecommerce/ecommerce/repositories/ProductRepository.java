package com.ecommerce.ecommerce.repositories;

import com.ecommerce.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {

}
