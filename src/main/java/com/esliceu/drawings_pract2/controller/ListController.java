package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.service.PaintService;
import com.esliceu.drawings_pract2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ListController {

    @Autowired
    PaintService paintService;

    @Autowired
    UserService userService;

    @GetMapping("/public")
    public String publicPage(HttpSession req) {

        /*List<Paint> publicPaints = paintService.getAllPaints();
        req.setAttribute("paintList", publicPaints);*/

        return "public";
    }

    @GetMapping("/private")
    public String privatePage(HttpSession req) {
        return "private";
    }

    @GetMapping("/trash")
    public String TrashPage(HttpSession req) {
        return "trash";
    }

    @GetMapping("/shared")
    public String SharedPage(HttpSession req) {
        return "sharedWhitMe";
    }

    @GetMapping("/versions")
    public String VersionsPage(HttpSession req) {
        return "versions";
    }

}
