package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.User;

public interface UserDAO {

    boolean addUser(User user);

    boolean checkUser(String username, String password);

    Integer getIdByUsername(String username);

    boolean exist(String username);
}
