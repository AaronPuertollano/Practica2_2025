package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public class PaintDAOsql implements PaintDAO{
    @Autowired
    JdbcTemplate jdbc;

    private RowMapper<Paint> paintMapper = (rs, rowNum) -> {
        Paint p = new Paint();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setData(rs.getString("data"));
        p.setOwnerId(rs.getInt("owner_id"));
        p.setPublic(rs.getBoolean("is_public"));
        p.setInTrash(rs.getBoolean("in_trash"));

        Timestamp created = rs.getTimestamp("creation_date");
        if (created != null) p.setCreationDate(created.toLocalDateTime());

        Timestamp modified = rs.getTimestamp("last_modified");
        if (modified != null) p.setLastModified(modified.toLocalDateTime());

        return p;
    };

    @Override
    public void save(Paint paint) {

        String sql = """
            INSERT INTO paints (name, data, owner_id, is_public, in_trash)
            VALUES (?, ?, ?, ?, ?)
        """;

        jdbc.update(sql, paint.getName(), paint.getData(), paint.getOwnerId(),
                paint.isPublic(), paint.isInTrash());
    }

    @Override
    public void update(Paint paint) {

        String sql = """
            UPDATE paints
            SET name = ?, data = ?, is_public = ?, in_trash = ?
            WHERE id = ?
        """;

        jdbc.update(sql, paint.getName(), paint.getData(),
                paint.isPublic(), paint.isInTrash(), paint.getId());
    }

    @Override
    public void delete(int paintId) {

        String sql = "DELETE FROM paints WHERE id = ?";
        jdbc.update(sql, paintId);
    }

    @Override
    public Paint findById(int id) {
        try {
            return jdbc.queryForObject(
                    "SELECT * FROM paints WHERE id = ?",
                    paintMapper,
                    id
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Paint> findByOwner(int ownerId) {
        return jdbc.query(
                "SELECT * FROM paints WHERE owner_id = ?",
                paintMapper,
                ownerId
        );
    }

    @Override
    public List<Paint> findPublicPaints() {
        return jdbc.query(
                "SELECT * FROM paints WHERE is_public = 1 AND in_trash = 0",
                paintMapper
        );
    }

    @Override
    public List<Paint> findByOwnerAndTrash(int ownerId, boolean inTrash) {
        return jdbc.query(
                "SELECT * FROM paints WHERE owner_id = ? AND in_trash = ?",
                paintMapper,
                ownerId,
                inTrash
        );
    }

    @Override
    public Paint existsByNameAndOwner(String name, int ownerId) {
        try {
            return jdbc.queryForObject(
                    "SELECT * FROM paints WHERE name = ? AND owner_id = ?",
                    paintMapper,
                    name,
                    ownerId
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}
