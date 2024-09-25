package com.ecommerce.ecommerce.models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity
public class Product {


    @Id
    @GeneratedValue
    private Long id;
    private String name;


    private Long categoryId;

    private Long brandId;
    private Long sizeId;

    private Long colorId;

    private String description;


    private double price;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long genderId) {
        this.brandId = genderId;
    }

    public Long getSizeId() {
        return sizeId;
    }

    public void setSizeId(Long sizeId) {
        this.sizeId = sizeId;
    }

    public Long getColorId() {
        return colorId;
    }

    public void setColorId(Long colorId) {
        this.colorId = colorId;
    }

    public Long getCategoryId() {
        return categoryId;
    }


    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }


    public double getPrice() {
        return price;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getDescription() {
        return description;
    }


    public void setDescription(String description) {
        this.description = description;
    }
}
