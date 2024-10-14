package com.ecommerce.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BrandDto {

    private Long id;
    private String brand;

    public BrandDto() {}

    @JsonCreator
    public BrandDto(@JsonProperty("id") Long id, @JsonProperty("brand") String brand) {
        this.id = id;
        this.brand = brand;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
