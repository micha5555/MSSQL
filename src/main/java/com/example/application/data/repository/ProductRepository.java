package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.application.data.entity.Category;
import com.example.application.data.entity.Product;

@Repository
public class ProductRepository/*  extends JpaRepository<Product, Long>*/{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CategoryRepository categoryRepository;

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", new ProductMapper());
    }

    public List<Product> findProductsFromProductsInPurchaseTabelWithPurchaseId(int purchaseId) {
        String query = "SELECT * FROM product WHERE product_id IN (SELECT product_id FROM productsInPurchase WHERE purchase_id = ?)";
        return jdbcTemplate.query(query, new ProductMapper(), purchaseId);
    }

    public Product findProductById(int id) {
        String query = "SELECT * FROM product WHERE product_id = ?";

        return jdbcTemplate.queryForObject(query, new ProductMapper(), id);
        
    }

    public int save(Product product) {
        if(product.getId() == -1) {
            return add(product);
        } else {
            return update(product);
        }
    }

    private int add(Product product) {
        String query = "INSERT INTO product (name, quantity, description, price, category) VALUES (?, ?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, product.getName());
                ps.setInt(2, product.getQuantity());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                ps.setInt(5, product.getCategory().getId());
                return ps;
            }
        }, holder);
        int newProductId = holder.getKey().intValue();
        product.setId(newProductId);

        return product.getId();
    }

    private int update(Product product) {
        String query = "UPDATE product SET name = ?, quantity = ?, description = ?, price = ?, category = ? WHERE product_id = ?";

        jdbcTemplate.update(query, product.getName(), product.getQuantity(), product.getDescription(), product.getPrice(), product.getCategory().getId(), product.getId());

        return product.getId();
    }

    public boolean delete(Product product) {
        String query = "DELETE FROM product WHERE product_id = ?";
        int result = jdbcTemplate.update(query, product.getId());
        return result > 0;
    }

    class ProductMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category productCategory = categoryRepository.findCategoryById(rs.getInt("category"));
            Product product = new Product(rs.getInt("product_id"), rs.getString("name"), rs.getInt("quantity"), rs.getString("description"), rs.getDouble("price"), productCategory);
            return product;
        }
    }
}
