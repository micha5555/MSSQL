package com.example.application.data.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Category extends AbstractEntity{
    
    @Id
    @NotBlank
    private int category_id;

    @NotBlank
    private String category_name;

    public Category() {
        this.category_id = -1;
        this.category_name = "---";
    }

    public Category(int id, String name) {
        // this.setId(i);
        this.category_id = id;
        this.category_name = name;
    }

    public String getName() {
        return category_name;
    }

    public void setName(String name) {
        this.category_name = name;
    }

    public void setId(int id) {
        this.category_id = id;
    }

    public int getId() {
        return category_id;
    }

    @Override
    public String toString() {
        return category_name;
    }
}