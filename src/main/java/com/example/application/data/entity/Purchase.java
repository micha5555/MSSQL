package com.example.application.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Purchase extends AbstractEntity{
    
    //Przerobić żeby była lista
    // @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Product> orderedProducts;

    @ManyToOne
    private Client client;

    @NotNull
    private String dateOfOrder;

    public Purchase() {
        this.dateOfOrder = LocalDateTime.now().toString();
    }
    
    public Purchase(Set<Product> orderedProducts, Client client) {
        this.orderedProducts = orderedProducts;
        this.client = client;
        this.dateOfOrder = LocalDateTime.now().toString();
    }

    public Set<Product> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(Set<Product> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public String getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(String dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
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
        return String.format("Client <br>: " + client.getlogin() + "; Ordered products:\n%s%nDate of order: %s", orderedProductsInString, dateOfOrder);
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
