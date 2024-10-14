package com.ecommerce.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private String imagePath;

    private List<CategoryDto> categories;
    private BrandDto brand;
    private List<SizeDto> sizes;
    private List<ColorDto> colors;

    private List<Long> categories_id;
    private List<Long> sizes_id;
    private List<Long> colors_id;
    private Long brand_id;

    public List<Long> getCategories_id() {
        return categories_id;
    }

    public void setCategories_id(List<Long> categories_id) {
        this.categories_id = categories_id;
    }

    public List<Long> getSizes_id() {
        return sizes_id;
    }

    public void setSizes_id(List<Long> sizes_id) {
        this.sizes_id = sizes_id;
    }

    public List<Long> getColors_id() {
        return colors_id;
    }

    public void setColors_id(List<Long> colors_id) {
        this.colors_id = colors_id;
    }

    public Long getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(Long brand_id) {
        this.brand_id = brand_id;
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<CategoryDto> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDto> categories) {
        this.categories = categories;
    }

    public BrandDto getBrand() {
        return brand;
    }

    public void setBrand(BrandDto brand) {
        this.brand = brand;
    }

    public List<SizeDto> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeDto> sizes) {
        this.sizes = sizes;
    }

    public List<ColorDto> getColors() {
        return colors;
    }

    public void setColors(List<ColorDto> colors) {
        this.colors = colors;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
