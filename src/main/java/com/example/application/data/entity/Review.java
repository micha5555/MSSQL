package com.example.application.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

// @Entity
public class Review extends AbstractEntity {
    
    @NotNull
    private String dateOfReview;

    @NotNull
    private int rate;

    @NotNull
    private String reviewBody;

    @ManyToOne
    private Product product_id;

    public Review() {
        this.dateOfReview = LocalDateTime.now().toString();
    }

    public Review(int rate, String reviewBody, Product product) {
        this.dateOfReview = LocalDateTime.now().toString();
        validateRate(rate);
        this.rate = rate;
        this.reviewBody = reviewBody;
        this.product_id = product;
    }

    public String getDateOfReview() {
        return dateOfReview;
    }

    public int getRate() {
        return rate;
    }

    public String getReviewBody() {
        return reviewBody;
    }

    public Product getProduct() {
        return product_id;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public void setProduct(Product product) {
        this.product_id = product;
    }

    private void validateRate(int rate) {
        if(rate > 5 || rate < 1) {
            throw new IllegalArgumentException("Rate cannot be lower than 1 and grater than 5");
        }
    }
}
