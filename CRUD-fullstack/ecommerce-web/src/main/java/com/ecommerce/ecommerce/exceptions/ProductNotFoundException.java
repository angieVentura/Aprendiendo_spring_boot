package com.ecommerce.ecommerce.exceptions;

public class ProductNotFoundException extends RuntimeException{
    public ProductNotFoundException(Long id){
        super("Product no encontrao: " + id);
    }
}
