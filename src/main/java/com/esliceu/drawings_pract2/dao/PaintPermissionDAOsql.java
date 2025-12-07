package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint_Permissons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaintPermissionDAOsql implements PaintPermissionDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public List<Paint_Permissons> findByUser(int userId) {
        String sql = "SELECT * FROM paint_permissions WHERE user_id = ?";
        return jdbc.query(sql, (rs, rowNum) -> {
            Paint_Permissons p = new Paint_Permissons();
            p.setId(rs.getInt("id"));
            p.setUserId(rs.getInt("user_id"));
            p.setPaintId(rs.getInt("paint_id"));
            p.setCanWrite(rs.getBoolean("can_write"));
            return p;
        }, userId);
    }

    @Override
    public boolean hasWritePermission(int userId, int paintId) {
        String sql = "SELECT can_write FROM paint_permissions WHERE user_id = ? AND paint_id = ?";

        try {
            // Usamos queryForObject. La columna can_write es TINYINT en sql, que a la practica es un Boolean en Java
            Boolean canWrite = jdbc.queryForObject(sql, Boolean.class, userId, paintId);
            //Retornamos el valor directamente
            return canWrite != null ? canWrite : false;

        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    @Override
    public void addPermission(int userId, int paintId, boolean canWrite) {
        String sql = "INSERT INTO paint_permissions (user_id, paint_id, can_write) VALUES (?, ?, ?)";
        jdbc.update(sql, userId, paintId, canWrite);
    }

    @Override
    public void removePermission(int userId, int paintId) {
        String sql = "DELETE FROM paint_permissions WHERE user_id = ? AND paint_id = ?";
        jdbc.update(sql, userId, paintId);
    }

    @Override
    public Boolean canView(int userId, int paintId) {
        String sql = "SELECT COUNT(*) FROM paint_permissions WHERE user_id = ? AND paint_id = ?";
        Integer count = jdbc.queryForObject(sql, Integer.class, userId, paintId);
        return count != null && count > 0;
    }

}

