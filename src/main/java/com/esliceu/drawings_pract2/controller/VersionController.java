package com.esliceu.drawings_pract2.controller;

import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.Paint_Versions;
import com.esliceu.drawings_pract2.service.PaintService;
import com.esliceu.drawings_pract2.service.PaintVersionService;
import com.esliceu.drawings_pract2.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/version")
public class VersionController {

    @Autowired
    UserService userService;

    @Autowired
    PaintService paintService;

    @Autowired
    PaintVersionService versionService;

    @GetMapping("/view")
    public String viewVersion(@RequestParam int versionId,
                              HttpSession session,
                              Model model) {

        Paint_Versions version = versionService.findById(versionId);
        if (version == null) return "error/404";

        // IMPORTANTE: enviar los datos JSON
        model.addAttribute("drawingData", version.getData());
        model.addAttribute("version", version);

        return "version_view";
    }

    @PostMapping("/restore")
    @ResponseBody
    public Object restoreVersion(@RequestParam int paintId,
                                 @RequestParam int versionId,
                                 HttpSession session) {

        String username = (String) session.getAttribute("username");
        if (username == null)
            return "{\"success\": false, \"message\": \"Not logged in\"}";

        int userId = userService.getId(username);

        Paint original = paintService.findById(paintId);
        if (original == null || original.getOwnerId() != userId) {
            return "{\"success\": false, \"message\": \"Access denied\"}";
        }

        Paint_Versions version = versionService.findById(versionId);
        if (version == null) {
            return "{\"success\": false, \"message\": \"Version not found\"}";
        }

        Paint newPaint = new Paint();
        newPaint.setName(original.getName() + " _version" + version.getVersionNumber());
        newPaint.setOwnerId(userId);
        newPaint.setOwnerId(userId);
        newPaint.setPublic(false);
        newPaint.setInTrash(false);
        newPaint.setData(version.getData());

        paintService.addPaint(newPaint);

        return "{\"success\": true}";
    }

}
