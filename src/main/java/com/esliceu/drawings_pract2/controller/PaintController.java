package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.User;
import com.esliceu.drawings_pract2.service.PaintService;
import com.esliceu.drawings_pract2.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
public class PaintController {

    @Autowired
    PaintService paintService;

    @Autowired
    UserService userService;

    @GetMapping("/paint")
    public String paintPage(HttpSession req) {
        return "paint";
    }

    @PostMapping("/paint")
    @ResponseBody
    public Map<String, Object> saveDrawing(HttpSession session,
                                           @RequestParam String name,
                                           @RequestParam String drawingData) {

        Map<String, Object> json = new HashMap<>();
        String user = (String) session.getAttribute("username");

        if (user == null) {
            json.put("success", false);
            json.put("message", "Not logged in");
            return json;
        }

        int ownerId = userService.getId(user);

        if (paintService.existsByNameAndOwner(name, ownerId)) {
            json.put("success", false);
            json.put("message", "You already have a drawing with that name.");
            return json;
        }

        Paint paint = new Paint();
        paint.setName(name);
        paint.setData(drawingData);
        paint.setOwnerId(ownerId);
        paint.setPublic(false);
        paint.setInTrash(false);
        paint.setCreationDate(LocalDateTime.now());
        paint.setLastModified(LocalDateTime.now());

        paintService.addPaint(paint);

        json.put("success", true);
        return json;
    }

}
