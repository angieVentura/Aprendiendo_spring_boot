package com.ecommerce.ecommerce.repositories;


import com.ecommerce.ecommerce.models.Category;
import com.ecommerce.ecommerce.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface CategoryRepository extends JpaRepository<Category,Long>, JpaSpecificationExecutor<Category> {


}
