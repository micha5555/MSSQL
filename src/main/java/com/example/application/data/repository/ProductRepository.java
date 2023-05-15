package com.example.application.data.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.application.data.entity.Product;

@Repository
public class ProductRepository/*  extends JpaRepository<Product, Long>*/{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM category", new CategoryMapper());
    }

    class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product category = new Product(rs.getInt("category_id"), rs.getString("category_name"));
            return category;
        }
    }
}
