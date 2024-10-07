package com.ecommerce.ecommerce.dto;

public class ColorDto {
    private Long id;
    private String color;

    public ColorDto( Long id, String color) {
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