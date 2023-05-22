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

@Repository
public class ClientRepository/*  extends JpaRepository<Client, Long>*/{
    
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Client> findAll() {
        return jdbcTemplate.query("SELECT * FROM client", new ClientMapper());
    }

    public Client findClientById(int id) {
        String query = "SELECT * FROM client WHERE client_id = ?";

        return jdbcTemplate.queryForObject(query, new ClientMapper(), id);
    }

    public int save(Client client) {
        if(client.getId() == -1) {
            return add(client);
        } else {
            return update(client);
        }
    }

    private int add(Client client) {
        String query = "INSERT INTO client (login, mail, dateOfJoin) VALUES (?, ?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, client.getLogin());
                ps.setString(2, client.getMail());
                ps.setString(3, client.getDateOfJoin());
                return ps;
            }
        }, holder);
        int newClientId = holder.getKey().intValue();
        client.setId(newClientId);

        return client.getId();
    }

    private int update(Client client) {
        String query = "UPDATE client SET login = ?, mail = ?, dateOfJoin = ? WHERE client_id = ?";

        jdbcTemplate.update(query, client.getLogin(), client.getMail(), client.getDateOfJoin(), client.getId());

        return client.getId();
    }

    public boolean delete(Client client) {
        String query = "DELETE FROM client WHERE client_id = ?";
        int result = jdbcTemplate.update(query, client.getId());
        return result > 0;
    }


    class ClientMapper implements RowMapper<Client> {

        @Override
        public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
            Client client = new Client(rs.getInt("client_id"), rs.getString("login"), rs.getString("mail"), rs.getString("dateOfJoin"));
            return client;
        }
    }

}
