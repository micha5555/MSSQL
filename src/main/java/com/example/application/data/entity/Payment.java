package com.example.application.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.application.common.Common;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;

@Entity
public class Payment extends AbstractEntity{
    
    private static List<String> paymentMethodsList;

    static {
        paymentMethodsList = Common.getPaymentMethods();
    }

    @NotNull
    @OneToOne
    private Purchase purchase;

    @NotNull
    private String paymentMethod;

    @NotNull
    private String dateOfPayment;

    public Payment() {
        paymentMethod = "---";
        this.dateOfPayment = LocalDateTime.now().toString();
    }

    public Payment(String paymentMethod, Purchase purchase) {
        validatePaymentMethod(paymentMethod);
        this.paymentMethod = paymentMethod;
        this.purchase = purchase;
        this.dateOfPayment = LocalDateTime.now().toString();
    }

    private void validatePaymentMethod(String paymentMethod) {
        if(!Payment.paymentMethodsList.contains(paymentMethod)) {
            throw new IllegalArgumentException("Illegal payment method: " + paymentMethod);
        }
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Purchase getPurchase() {
        return purchase;
    }

    public void setPurchase(Purchase purchase) {
        this.purchase = purchase;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }
}
