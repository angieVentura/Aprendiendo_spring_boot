package com.ecommerce.ecommerce.dto;

public class SizeDto {
    private Long id;
    private String size;

    public SizeDto(Long id, String size) {
        this.id = id;
        this.size = size;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
