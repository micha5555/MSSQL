package com.example.application.data.entity;

import java.util.ArrayList;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
public class Product extends AbstractEntity{

    @Id
    @NotBlank
    private int product_id;

    @NotEmpty
    private String name;

    private String description;

    @NotNull
    private int quantity;

    @NotNull
    private double price;

    @OneToOne
    // @NotNull
    @JoinColumn(name = "category")
    private Category category;

    // @ManyToMany
    // // @NotNull
    // private Set<Purchase> purchases;

    public Product(){
        this.product_id = -1;
    }

    public Product(int product_id, String name, int quantity, String description, double price, Category category) {
        this.product_id = product_id;
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public void setId(int id) {
        this.product_id = id;
    }

    public int getId() {
        return product_id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    @Override
    public String toString() {
        return name;
    }

    // public Set<Purchase> getPurchases() {
    //     return purchases;
    // }

    // public void setPurchases(Set<Purchase> purchases) {
    //     this.purchases = purchases;
    // }
}
