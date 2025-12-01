package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.Paint_Permissons;
import com.esliceu.drawings_pract2.service.PaintPermissionService;
import com.esliceu.drawings_pract2.service.PaintService;
import com.esliceu.drawings_pract2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ListController {

    @Autowired
    PaintService paintService;

    @Autowired
    UserService userService;

    @Autowired
    PaintPermissionService paintPermissionService;

    @GetMapping("/public")
    public String publicPage(HttpSession req, Model model) {

        List<Paint> paintList = paintService.findPublicPaints();

        model.addAttribute("paintList", paintList);

        return "public";
    }

    @GetMapping("/private")
    public String privatePage(HttpSession req, Model model) {
        String username = (String) req.getAttribute("username");
        int ownerId = userService.getId(username);

        List<Paint> paintList = paintService.findByOwner(ownerId)
                .stream()
                .filter(p -> !p.isInTrash())
                .toList();

        model.addAttribute("paintList", paintList);
        return "private";
    }

    @GetMapping("/trash")
    public String TrashPage(HttpSession req, Model model) {
        String username = (String) req.getAttribute("username");
        int ownerId = userService.getId(username);

        List<Paint> paintList = paintService.findByOwnerAndTrash(ownerId,true);

        model.addAttribute("paintList", paintList);
        return "trash";
    }

    @GetMapping("/shared")
    public String SharedPage(HttpSession req, Model model) {
        String username = (String) req.getAttribute("username");
        int userId = userService.getId(username);

        List<Paint> sharedPaints = paintPermissionService.getSharedWithUser(userId);

        List<Paint_Permissons> permissions = paintPermissionService.getPermissionsForUser(userId);

        model.addAttribute("paintList", sharedPaints);
        model.addAttribute("permissions", permissions);
        return "sharedWhitMe";
    }

    @GetMapping("/versions")
    public String VersionsPage(HttpSession req, Model model) {
        String username = (String) req.getAttribute("username");
        int ownerId = userService.getId(username);

        model.addAttribute("paintList", new ArrayList<>());

        return "versions";
    }

}
