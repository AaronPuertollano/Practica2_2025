package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.Paint_Permissons;
import com.esliceu.drawings_pract2.model.Paint_Versions;
import com.esliceu.drawings_pract2.service.PaintPermissionService;
import com.esliceu.drawings_pract2.service.PaintService;
import com.esliceu.drawings_pract2.service.PaintVersionService;
import com.esliceu.drawings_pract2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    PaintVersionService paintVersionService;

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

        //Selecciona todos los dibujos de un susario y despues lo filtra con la condición de que nó estre en trash
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
    public String VersionsPage(@RequestParam("paintId") int paintId, HttpSession req, Model model) {

        String username = (String) req.getAttribute("username");
        if (username == null) {
            return "redirect:/login";
        }

        int userId = userService.getId(username);

        Paint paint = paintService.findById(paintId);
        if (paint == null) {
            return "redirect:/private";
        }

        boolean isOwner = paint.getOwnerId() == userId;
        boolean hasPermission = paintPermissionService.canView(userId, paintId);

        if (!isOwner && !hasPermission) {
            return "redirect:/private";
        }

        List<Paint_Versions> versions = paintVersionService.findByPaintId(paintId);

        model.addAttribute("paint", paint);
        model.addAttribute("versions", versions);

        return "versions";

    }

}
