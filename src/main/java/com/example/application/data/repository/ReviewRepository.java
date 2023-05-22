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

import com.example.application.data.entity.Product;
import com.example.application.data.entity.Review;

@Repository
public class ReviewRepository/* extends JpaRepository<Review, Long>*/{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ProductRepository productRepository;

    public List<Review> findAll() {
        return jdbcTemplate.query("SELECT * FROM review", new ReviewMapper());
    }

    public Review findReviewById(int id) {
        String query = "SELECT * FROM review WHERE review_id = ?";

        return jdbcTemplate.queryForObject(query, new ReviewMapper(), id);
        
    }

    public int save(Review review) {
        if(review.getId() == -1) {
            return add(review);
        } else {
            return update(review);
        }
    }

    private int add(Review review) {
        System.out.println(review);
        String query = "INSERT INTO review (product, rate, reviewBody, dateOfReview) VALUES (?, ?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, review.getProduct().getId());
                ps.setInt(2, review.getRate());
                ps.setString(3, review.getReviewBody());
                ps.setString(4, review.getDateOfReview());
                return ps;
            }
        }, holder);
        int newReviewId = holder.getKey().intValue();
        review.setId(newReviewId);

        return review.getId();
    }

    private int update(Review review) {
        System.out.println(review);
        String query = "UPDATE review SET product = ?, rate = ?, reviewBody = ?, dateOfReview = ? WHERE review_id = ?";

        jdbcTemplate.update(query, review.getProduct().getId(), review.getRate(), review.getReviewBody(), review.getDateOfReview(), review.getId());

        return review.getId();
    }

    public boolean delete(Review review) {
        System.out.println(review.getId());
        System.out.println(review.getReviewBody());
        String query = "DELETE FROM review WHERE review_id = ?";
        int result = jdbcTemplate.update(query, review.getId());
        System.out.println(result);
        return result > 0;
    }


    class ReviewMapper implements RowMapper<Review> {

        @Override
        public Review mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product reviewProduct = productRepository.findProductById(rs.getInt("product"));
            Review review = new Review(rs.getInt("review_id"), rs.getInt("rate"), rs.getString("reviewBody"), rs.getString("dateOfReview"), reviewProduct);
            return review;
        }
    }
}
