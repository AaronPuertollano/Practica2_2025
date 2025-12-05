package com.esliceu.drawings_pract2.service;

import com.esliceu.drawings_pract2.dao.PaintDAO;
import com.esliceu.drawings_pract2.dao.PaintPermissionDAO;
import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.Paint_Permissons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaintPermissionService {

    @Autowired
    PaintPermissionDAO paintPermissionDAO;

    @Autowired
    PaintDAO paintDAO;

    public List<Paint_Permissons> getPermissionsForUser(int userId) {
        return paintPermissionDAO.findByUser(userId);
    }

    public boolean userCanWrite(int userId, int paintId) {
        return paintPermissionDAO.hasWritePermission(userId, paintId);
    }

    public void sharePaint(int paintId, int userId, boolean canWrite) {
        paintPermissionDAO.addPermission(userId, paintId, canWrite);
    }

    public void unsharePaint(int paintId, int userId) {
        paintPermissionDAO.removePermission(userId, paintId);
    }

    public List<Paint> getSharedWithUser(int userId) {
        List<Paint_Permissons> permissions = paintPermissionDAO.findByUser(userId);
        List<Paint> sharedPaints = new ArrayList<>();

        for (Paint_Permissons perm : permissions) {
            Paint paint = paintDAO.findById(perm.getPaintId());
            if (paint != null && !paint.isInTrash()) {
                sharedPaints.add(paint);
            }
        }

        return sharedPaints;
    }

    public  boolean canView(int ownerId, int paintId){
        Boolean result = paintPermissionDAO.canView(ownerId, paintId);
        return result != null && result;
    }
}
