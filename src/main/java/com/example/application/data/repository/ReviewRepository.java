package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{
    
}
