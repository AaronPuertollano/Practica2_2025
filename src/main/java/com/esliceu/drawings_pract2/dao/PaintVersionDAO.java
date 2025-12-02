package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint_Versions;

import java.util.List;

public interface PaintVersionDAO {

    void saveVersion(Paint_Versions version);
    List<Paint_Versions> getVersionsByPaint(int paintId);
    Paint_Versions getVersion(int paintId, int versionNumber);

}
