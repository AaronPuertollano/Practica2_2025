package com.esliceu.drawings_pract2.service;

import com.esliceu.drawings_pract2.dao.PaintVersionDAO;
import com.esliceu.drawings_pract2.model.Paint_Versions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaintVersionService {

    @Autowired
    PaintVersionDAO paintVersionDAO;

    public void saveVersion(Paint_Versions version){
        paintVersionDAO.save(version);
    }

    public int getLastVersionNumber(int paintId){
        Integer last = paintVersionDAO.getLastVersionNumber(paintId);
        return last != null ? last : 0;
    }

    public List<Paint_Versions> findByPaintId(int paintId){
        return paintVersionDAO.findByPaintId(paintId);
    }
}
