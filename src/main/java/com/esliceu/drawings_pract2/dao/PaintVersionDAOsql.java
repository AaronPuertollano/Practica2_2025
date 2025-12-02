package com.esliceu.drawings_pract2.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaintVersionDAOsql {

    @Autowired
    JdbcTemplate jdbc;

}
