package com.example.application.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.application.data.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long>{
    
}
