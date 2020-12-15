package com.example.demo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostPersist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
public class Product {
    @Id @GeneratedValue
    private Long id;
    private String name;
    private int stock;

    // get/set 메서드
    public Long getId() {
		return id;
	}
	public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(final int stock) {
        this.stock = stock;
    }
 
    @PostPersist
    public void eventPublish(){
        ProductChanged productChanged = new ProductChanged();
        productChanged.setProductId(this.getId());
        productChanged.setProductName(this.getName());
        productChanged.setProductStock(this.getStock());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;

        try {
            json = objectMapper.writeValueAsString(productChanged);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("JSON format exception", e);
        }
        System.out.println(json);
    }
}