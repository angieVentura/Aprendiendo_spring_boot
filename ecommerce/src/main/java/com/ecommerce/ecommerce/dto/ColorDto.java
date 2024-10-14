package com.ecommerce.ecommerce.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ColorDto {
    private Long id;
    private String color;

    @JsonCreator
    public ColorDto(@JsonProperty("id") Long id, @JsonProperty("color") String color) {
        this.color = color;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
