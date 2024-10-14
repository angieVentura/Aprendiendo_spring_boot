package com.ecommerce.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryDto {
    private Long id;
    private String category;

    @JsonCreator
    public CategoryDto(@JsonProperty("id") Long id, @JsonProperty("category") String category) {
        this.id = id;
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
