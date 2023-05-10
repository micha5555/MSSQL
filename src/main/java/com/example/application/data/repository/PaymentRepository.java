package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long>{
    
}
