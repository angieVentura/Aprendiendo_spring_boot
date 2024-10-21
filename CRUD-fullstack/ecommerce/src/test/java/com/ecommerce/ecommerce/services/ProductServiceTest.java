package com.ecommerce.ecommerce.services;


import com.ecommerce.ecommerce.models.Product;
import com.ecommerce.ecommerce.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.data.jpa.domain.Specification;


import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ProductServiceTest {


    @Test
    void filterProductsOk() {
        List<Product> mockProducts = new ArrayList<>();
        mockProducts.add(new Product(1L, "Product 1", "Product 1", 100.0));


        ProductRepository productRepository = mock(ProductRepository.class);


        when(productRepository.findAll(any(Specification.class))).thenReturn(mockProducts);


        ProductService productService = new ProductService(productRepository);


        List<Product> filteredProducts = productService.filterProducts(1L, null, null, null, 0.0, 0.0);


        assertEquals(1, filteredProducts.size());
        assertEquals("Product 1", filteredProducts.get(0).getName());


        verify(productRepository, times(1)).findAll(any(Specification.class));
    }


}

