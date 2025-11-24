package com.esliceu.drawings_pract2.service;

import com.esliceu.drawings_pract2.dao.UserDAO;
import com.esliceu.drawings_pract2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public boolean validateUser(String username, String password) {
        return userDAO.checkUser(username, password);
    }

    public boolean addUser(User user) {
        return userDAO.addUser(user);
    }

}
