package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint_Permissons;

import java.util.List;

public interface PaintPermissionDAO {

    List<Paint_Permissons> findByUser(int userId);
    boolean hasWritePermission(int userId, int paintId);
    void addPermission(int userId, int paintId, boolean canWrite);
    void removePermission(int userId, int paintId);

}
