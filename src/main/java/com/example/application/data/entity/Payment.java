package com.example.application.data.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.application.common.Common;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

// @Entity
public class Payment extends AbstractEntity{
    private static List<String> paymentMethodsList;

    static {
        paymentMethodsList = Common.getPaymentMethods();
    }

    @Id
    @NotBlank
    private int payment_id;

    @NotNull
    @OneToOne
    private Purchase purchase;

    @NotNull
    @JoinColumn(name = "paymentMethod")
    private String paymentMethod;

    @NotNull
    private String dateOfPayment;

    public Payment() {
        this.payment_id = -1;
        paymentMethod = "---";
        this.dateOfPayment = LocalDateTime.now().toString();
    }

    public Payment(String paymentMethod, Purchase purchase) {
        validatePaymentMethod(paymentMethod);
        this.paymentMethod = paymentMethod;
        this.purchase = purchase;
        this.dateOfPayment = LocalDateTime.now().toString();
    }

    public Payment(int payment_id, String paymentMethod, String dateOfPayment, Purchase purchase) {
        validatePaymentMethod(paymentMethod);
        this.payment_id = payment_id;
        this.paymentMethod = paymentMethod;
        this.dateOfPayment = dateOfPayment;
        this.purchase = purchase;
    }

    private void validatePaymentMethod(String paymentMethod) {
        if(!Payment.paymentMethodsList.contains(paymentMethod)) {
            throw new IllegalArgumentException("Illegal payment method: " + paymentMethod);
        }
    }

    public int getId() {
        return payment_id;
    }

    public void setId(int payment_id) {
        this.payment_id = payment_id;
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

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }
}
