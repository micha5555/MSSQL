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

@Repository
public class CategoryRepository/* extends JpaRepository<Category, Long>*/{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Category> findAll() {
        return jdbcTemplate.query("SELECT * FROM category", new CategoryMapper());
    }

    public Category findCategoryById(int id) {
        String query = "SELECT * FROM category WHERE category_id = ?";

        return jdbcTemplate.queryForObject(query, new CategoryMapper(), id);
        
    }

    public int save(Category category) {
        if(category.getId() == -1) {
            return add(category);
        } else {
            return update(category);
        }
    }

    private int add(Category category) {
        String query = "INSERT INTO category (category_name) VALUES (?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, category.getName());
                return ps;
            }
        }, holder);
        int newCategoryId = holder.getKey().intValue();
        category.setId(newCategoryId);

        return category.getId();
    }

    private int update(Category category) {
        String query = "UPDATE category SET category_name = ? WHERE category_id = ?";

        jdbcTemplate.update(query, category.getName(), category.getId());

        return category.getId();
    }

    public boolean delete(Category category) {
        String query = "DELETE FROM category WHERE category_id = ?";
        int result = jdbcTemplate.update(query, category.getId());
        return result > 0;
    }


    class CategoryMapper implements RowMapper<Category> {

        @Override
        public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
            Category category = new Category(rs.getInt("category_id"), rs.getString("category_name"));
            return category;
        }
    }
}
