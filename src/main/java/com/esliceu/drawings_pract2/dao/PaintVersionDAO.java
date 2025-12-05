package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint_Versions;

import java.util.List;

public interface PaintVersionDAO {
    void save(Paint_Versions version);
    Integer getLastVersionNumber(int paintId);
    List<Paint_Versions> findByPaintId(int paintId);
}
