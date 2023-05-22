package com.example.application.data.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.example.application.data.entity.Client;
import com.example.application.data.entity.Product;
import com.example.application.data.entity.Purchase;

@Repository
public class PurchaseRepository/* extends JpaRepository<Purchase, Long>*/{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ClientRepository clientRepository;

    public List<Purchase> findAll() {
        return jdbcTemplate.query("SELECT * FROM purchase", new PurchaseMapper());
    }

    public Purchase findPurchaseById(int id) {
        String query = "SELECT * FROM purchase WHERE purchase_id = ?";

        return jdbcTemplate.queryForObject(query, new PurchaseMapper(), id);
        
    }

    public int save(Purchase purchase) {
        if(purchase.getId() == -1) {
            return add(purchase);
        } else {
            return update(purchase);
        }
    }

    private int add(Purchase purchase) {
        String queryPurchase = "INSERT INTO purchase (client, dateOfOrder) VALUES (?, ?)";
        String queryProductsInPurchase = "INSERT INTO productsInPurchase (product_id, purchase_id) VALUES (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(queryPurchase, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, purchase.getClient().getId());
                ps.setString(2, purchase.getDateOfOrder());
                return ps;
            }
        }, holder);
        int newPurchaseId = holder.getKey().intValue();
        purchase.setId(newPurchaseId);
        for(Product p : purchase.getOrderedProducts()) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(queryProductsInPurchase, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, p.getId());
                    ps.setInt(2, newPurchaseId);
                    return ps;
                }
            }, holder);
        }

        return purchase.getId();
    }

    private int update(Purchase purchase) {
        String query = "UPDATE purchase SET client = ?, dateOfOrder = ? WHERE purchase_id = ?";
        String queryDeleteFromProductsInPurchase = "DELETE FROM productsInPurchase WHERE purchase_id = ?";
        String queryAddToProductsInPurchase = "INSERT INTO productsInPurchase (product_id, purchase_id) VALUES (?, ?)";
        jdbcTemplate.update(query, purchase.getClient().getId(), purchase.getDateOfOrder(), purchase.getId());
        jdbcTemplate.update(queryDeleteFromProductsInPurchase, purchase.getId());
        for(Product p : purchase.getOrderedProducts()) {
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(queryAddToProductsInPurchase, Statement.RETURN_GENERATED_KEYS);
                    ps.setInt(1, p.getId());
                    ps.setInt(2, purchase.getId());
                    return ps;
                }
            });
        }

        return purchase.getId();
    }

    public boolean delete(Purchase purchase) {
        String query1 = "DELETE FROM productsInPurchase WHERE purchase_id = ?";
        String query2 = "DELETE FROM purchase WHERE purchase_id = ?";
        int result1 = jdbcTemplate.update(query1, purchase.getId());
        int result2 = jdbcTemplate.update(query2, purchase.getId());
        return result1 * result2 > 0;
    }


    class PurchaseMapper implements RowMapper<Purchase> {
        @Override
        public Purchase mapRow(ResultSet rs, int rowNum) throws SQLException {
            // Category productCategory = categoryRepository.findCategoryById(rs.getInt("category"));
            List<Product> productsInPurchase = productRepository.findProductsFromProductsInPurchaseTabelWithPurchaseId(rs.getInt("purchase_id"));
            Client clientOfPurchase = clientRepository.findClientById(rs.getInt("client"));
            Purchase purchase = new Purchase(rs.getInt("purchase_id"), productsInPurchase, clientOfPurchase, rs.getString("dateOfOrder"));
            return purchase;
        }
    }
}
