package com.esliceu.drawings_pract2.service;

import com.esliceu.drawings_pract2.dao.PaintDAO;
import com.esliceu.drawings_pract2.model.Paint;
import com.esliceu.drawings_pract2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaintService {

    @Autowired
    PaintDAO paintDAO;

    public void addPaint(Paint paint) {
        paintDAO.save(paint);
    }

    public void update(Paint paint){
        paintDAO.update(paint);
    }

    public void delete(int paintId){
        paintDAO.delete(paintId);
    }

    public Paint findById(int id){
        return paintDAO.findById(id);
    }

    public List<Paint> findByOwner(int ownerId){
        return paintDAO.findByOwner(ownerId);
    }

    public List<Paint> findPublicPaints(){
        return paintDAO.findPublicPaints();
    }
    public List<Paint> findByOwnerAndTrash(int ownerId, boolean inTrash){
        return paintDAO.findByOwnerAndTrash(ownerId, inTrash);
    }

    public Paint existsByNameAndOwner(String name, int ownerId){
        return paintDAO.existsByNameAndOwner(name, ownerId);
    }

}
