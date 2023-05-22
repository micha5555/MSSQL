package com.example.application.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// @Entity
public class Purchase extends AbstractEntity{
    
    @Id
    @NotBlank
    private int purchase_id;

    //Przerobić żeby była lista
    // @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Product> orderedProducts;

    @ManyToOne
    private Client client;

    @NotNull
    private String dateOfOrder;

    public Purchase() {
        purchase_id = -1;
        this.dateOfOrder = LocalDateTime.now().toString();
    }
    
    public Purchase(List<Product> orderedProducts, Client client) {
        this.orderedProducts = orderedProducts;
        this.client = client;
        this.dateOfOrder = LocalDateTime.now().toString();
    }

    public Purchase(int purchase_id, List<Product> orderedProducts, Client client, String dateOfOrder) {
        this.purchase_id = purchase_id;
        this.orderedProducts = orderedProducts;
        this.client = client;
        this.dateOfOrder = dateOfOrder;
    }

    public List<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() {
        return purchase_id;
    }

    public void setId(int purchase_id) {
        this.purchase_id = purchase_id;
    }

    @Override
    public String toString() {
        String orderedProductsInString = "";
        String newline = System.getProperty("line.separator");
        for(Product p : orderedProducts) {
            orderedProductsInString += p;
            orderedProductsInString = orderedProductsInString + "\n";
        }
        // System.out.println(orderedProductsInString);
        return String.format("Client <br>: " + client.getLogin() + "; Ordered products:\n%s%nDate of order: %s", orderedProductsInString, dateOfOrder);
    }




}
