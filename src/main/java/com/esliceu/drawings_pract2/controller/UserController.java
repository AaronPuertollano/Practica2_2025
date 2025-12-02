package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.User;
import com.esliceu.drawings_pract2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam String username,
                              @RequestParam String password,
                              Model model, HttpServletRequest req, HttpServletResponse resp) {

        String passwordHash = String.valueOf(password.hashCode());

        if (userService.validateUser(username, passwordHash)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            return "redirect:/paint";
        } else {
            model.addAttribute("error", "Usuario o contraseña incorrectos");
            return "login";
        }
    }


    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String username,
                               @RequestParam String password) {

        String passwordHash = String.valueOf(password.hashCode());

        userService.addUser(new User(name, username, passwordHash));
        return "redirect:/login";
    }

    @PostMapping("/logout")
    public String registerUser(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/login";
    }
}

