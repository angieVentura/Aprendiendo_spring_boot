package com.ecommerce.ecommerce.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String size;

    @ManyToMany(mappedBy = "sizes")
    private List<Product> products;



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
