package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint_Versions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaintVersionDAOsql implements PaintVersionDAO {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public void save(Paint_Versions version) {
        String sql = "INSERT INTO paint_versions (paint_id, version_number, data) VALUES (?, ?, ?)";
        jdbc.update(sql, version.getPaintId(), version.getVersionNumber(), version.getData());
    }

    @Override
    public Integer getLastVersionNumber(int paintId) {
        String sql = "SELECT MAX(version_number) FROM paint_versions WHERE paint_id = ?";
        return jdbc.queryForObject(sql, Integer.class, paintId);
    }

    @Override
    public List<Paint_Versions> findByPaintId(int paintId) {
        String sql = "SELECT * FROM paint_versions WHERE paint_id = ? ORDER BY version_number ASC";
        return jdbc.query(sql, (rs, rowNum) -> {
            Paint_Versions v = new Paint_Versions();
            v.setId(rs.getInt("id"));
            v.setPaintId(rs.getInt("paint_id"));
            v.setVersionNumber(rs.getInt("version_number"));
            v.setData(rs.getString("data"));
            return v;
        }, paintId);
    }
}
