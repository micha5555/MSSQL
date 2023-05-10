package com.example.application.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.application.data.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{
    
    @Query("select p from Purchase p where p.client.login = :searchTerm") 
    List<Purchase> search(@Param("searchTerm") String searchTerm); 

}
