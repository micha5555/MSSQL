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

import com.example.application.data.entity.Payment;
import com.example.application.data.entity.Purchase;

@Repository
public class PaymentRepository/* extends JpaRepository<Payment, Long>*/{
     
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    PurchaseRepository purchaseRepository;

    public List<Payment> findAll() {
        return jdbcTemplate.query("SELECT * FROM payment", new PaymentMapper());
    }

    public Payment findPaymentById(int id) {
        String query = "SELECT * FROM payment WHERE payment_id = ?";

        return jdbcTemplate.queryForObject(query, new PaymentMapper(), id);
    }

    public int save(Payment payment) {
        if(payment.getId() == -1) {
            return add(payment);
        } else {
            return update(payment);
        }
    }

    private int add(Payment payment) {
        String query = "INSERT INTO payment (paymentMethod, purchase) VALUES (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, payment.getPaymentMethod());
                ps.setInt(2, payment.getPurchase().getId());
                return ps;
            }
        }, holder);
        int newPaymentId = holder.getKey().intValue();
        payment.setId(newPaymentId);

        return payment.getId();
    }

    private int update(Payment payment) {
        String query = "UPDATE payment SET paymentMethod = ?, purchase = ? WHERE payment_id = ?";

        jdbcTemplate.update(query, payment.getPaymentMethod(), payment.getPurchase().getId(), payment.getId());

        return payment.getId();
    }

    public boolean delete(Payment payment) {
        String query = "DELETE FROM payment WHERE payment_id = ?";
        int result = jdbcTemplate.update(query, payment.getId());
        return result > 0;
    }


    class PaymentMapper implements RowMapper<Payment> {
        @Override
        public Payment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Purchase purchase = purchaseRepository.findPurchaseById(rs.getInt("purchase"));
            Payment payment = new Payment(rs.getInt("payment_id"), rs.getString("paymentMethod"), rs.getString("dateOfPayment"), purchase);
            return payment;
        }
    }
}
