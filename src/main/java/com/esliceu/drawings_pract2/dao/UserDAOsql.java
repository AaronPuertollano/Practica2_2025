package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAOsql implements UserDAO{

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public boolean addUser(User user) {
        String sql = "INSERT INTO users (name, username, password) VALUES (?, ?, ?)";
        int rows = jdbc.update(sql, user.getName(), user.getUsername(), user.getPassword());
        return rows > 0;
    }

    @Override
    public boolean checkUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ? AND password = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }


}
