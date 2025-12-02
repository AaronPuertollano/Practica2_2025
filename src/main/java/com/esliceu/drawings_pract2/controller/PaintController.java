package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.User;
import com.esliceu.drawings_pract2.service.PaintPermissionService;
import com.esliceu.drawings_pract2.service.PaintService;
import com.esliceu.drawings_pract2.service.PaintVersionService;
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
    PaintPermissionService paintPermissionService;

    @Autowired
    PaintVersionService paintVersionService;

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

        //Envez de no hacerlo puedo avisar de que hay otro con el mismo nombre y sobreeescribir, asi opdria empezar
        // con las versiones
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

    @PostMapping("/paint/changePublic")
    @ResponseBody
    public Map<String, Object> changePublic(HttpSession session,
                                            @RequestParam int paintId,
                                            @RequestParam boolean isPublic) {
        Map<String, Object> json = new HashMap<>();

        String username = (String) session.getAttribute("username");
        int ownerId = userService.getId(username);

        Paint paint = paintService.findById(paintId);
        if (paint == null) {
            json.put("success", false);
            json.put("message", "Paint not found");
            return json;
        }

        if (paint.getOwnerId() != ownerId) {
            json.put("success", false);
            json.put("message", "You are not the owner of this paint");
            return json;
        }

        if (paint.isPublic()){
            paint.setPublic(false);
        } else {
            paint.setPublic(isPublic);
        }

        paint.setLastModified(LocalDateTime.now());
        paintService.update(paint);

        json.put("success", true);
        json.put("isPublic", isPublic);
        return json;
    }

    @PostMapping("/paint/changeTrash")
    @ResponseBody
    public Map<String, Object> changeTrash(HttpSession session,
                                           @RequestParam int paintId,
                                           @RequestParam boolean isTrash) {

        Map<String, Object> json = new HashMap<>();

        String username = (String) session.getAttribute("username");
        int ownerId = userService.getId(username);

        Paint paint = paintService.findById(paintId);
        if (paint == null) {
            json.put("success", false);
            json.put("message", "Paint not found");
            return json;
        }

        if (paint.getOwnerId() != ownerId) {
            json.put("success", false);
            json.put("message", "You are not the owner of this paint");
            return json;
        }

        if (paint.isInTrash()){
            paint.setInTrash(false);
        } else {
            paint.setInTrash(isTrash);
        }

        paint.setLastModified(LocalDateTime.now());
        paintService.update(paint);

        json.put("success", true);
        json.put("isTrash", isTrash);
        return json;
    }

    @PostMapping("/paint/delete")
    @ResponseBody
    public Map<String, Object> deletePaint(HttpSession session,
                                           @RequestParam int paintId) {

        Map<String, Object> json = new HashMap<>();

        String username = (String) session.getAttribute("username");
        int ownerId = userService.getId(username);

        Paint paint = paintService.findById(paintId);
        if (paint == null) {
            json.put("success", false);
            json.put("message", "Paint not found");
            return json;
        }

        if (paint.getOwnerId() != ownerId) {
            json.put("success", false);
            json.put("message", "You are not the owner of this paint");
            return json;
        }

        paintService.delete(paintId);

        json.put("success", true);
        return json;
    }


    @PostMapping("/paint/share")
    @ResponseBody
    public Map<String, Object> sharePaint(HttpSession session,
                                          @RequestParam int paintId,
                                          @RequestParam String username,
                                          @RequestParam boolean canWrite) {

        Map<String, Object> json = new HashMap<>();
        String currentUser = (String) session.getAttribute("username");
        int ownerId = userService.getId(currentUser);

        Paint paint = paintService.findById(paintId);
        if (paint == null) {
            json.put("success", false);
            json.put("message", "Paint not found");
            return json;
        }

        if (paint.getOwnerId() != ownerId) {
            json.put("success", false);
            json.put("message", "You are not the owner");
            return json;
        }

        Integer targetUserId = userService.getId(username);
        if (targetUserId == null) {
            json.put("success", false);
            json.put("message", "User not found");
            return json;
        }

        paintPermissionService.sharePaint(paintId, targetUserId, canWrite);
        json.put("success", true);
        return json;
    }


    @PostMapping("/paint/unshare")
    @ResponseBody
    public Map<String, Object> unsharePaint(HttpSession session,
                                            @RequestParam int paintId,
                                            @RequestParam String username) {
        Map<String, Object> json = new HashMap<>();
        String currentUser = (String) session.getAttribute("username");
        int ownerId = userService.getId(currentUser);

        Paint paint = paintService.findById(paintId);
        if (paint.getOwnerId() != ownerId) {
            json.put("success", false);
            json.put("message", "You are not the owner");
            return json;
        }

        Integer targetUserId = userService.getId(username);
        if (targetUserId == null) {
            json.put("success", false);
            json.put("message", "User not found");
            return json;
        }

        paintPermissionService.unsharePaint(paintId, targetUserId);
        json.put("success", true);
        return json;
    }

    @PostMapping("/paint/copy")
    @ResponseBody
    public Map<String, Object> copyPaint(HttpSession session,
                                         @RequestParam int paintId) {
        Map<String, Object> json = new HashMap<>();

        String currentUser = (String) session.getAttribute("username");
        int userId = userService.getId(currentUser);

        Paint original = paintService.findById(paintId);
        if (original == null) {
            json.put("success", false);
            json.put("message", "Paint not found");
            return json;
        }

        int randomNum = (int)(Math.random() * 9999);
        String newName = original.getName() + "_copy" + randomNum;

        Paint copy = new Paint();
        copy.setName(newName);
        copy.setData(original.getData());
        copy.setOwnerId(userId);
        copy.setPublic(false);
        copy.setInTrash(false);

        paintService.addPaint(copy);

        json.put("success", true);
        json.put("newPaintId", copy.getId());
        return json;
    }

    @GetMapping("/paint/view")
    public String viewPaint(@RequestParam int paintId,
                            HttpSession session,
                            Model model) {

        String currentUser = (String) session.getAttribute("username");

        Paint paint = paintService.findById(paintId);

        if (paint == null) {
            return "redirect:/error";
        }

        model.addAttribute("paintName", paint.getName());
        model.addAttribute("drawingData", paint.getData());

        return "view";
    }


}
