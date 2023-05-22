package com.example.application.data.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Review extends AbstractEntity {
    
    @Id
    @NotBlank
    private int review_id;

    @NotNull
    private String dateOfReview;

    @NotNull
    private int rate;

    @NotNull
    private String reviewBody;

    @ManyToOne
    @JoinColumn(name = "product")
    private Product product;

    public Review() {
        review_id = -1;
        this.dateOfReview = LocalDateTime.now().toString();
    }

    // public Review(int rate, String reviewBody, Product product) {
    //     this.dateOfReview = LocalDateTime.now().toString();
    //     validateRate(rate);
    //     this.rate = rate;
    //     this.reviewBody = reviewBody;
    //     this.product_id = product;
    // }

    public Review(int review_id, int rate, String reviewBody, String dateOfReview, Product product) {
        this.review_id = review_id;
        this.dateOfReview = dateOfReview;
        validateRate(rate);
        this.rate = rate;
        this.reviewBody = reviewBody;
        this.product = product;
    }

    public int getId() {
        return review_id;
    }

    public void setId(int review_id) {
        this.review_id = review_id;
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
        return product;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public void setReviewBody(String reviewBody) {
        this.reviewBody = reviewBody;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    private void validateRate(int rate) {
        if(rate > 5 || rate < 1) {
            throw new IllegalArgumentException("Rate cannot be lower than 1 and grater than 5");
        }
    }
}
