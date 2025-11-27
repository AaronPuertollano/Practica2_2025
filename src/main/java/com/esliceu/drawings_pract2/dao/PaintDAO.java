package com.esliceu.drawings_pract2.dao;

import com.esliceu.drawings_pract2.model.Paint;

import java.util.List;

public interface PaintDAO {

    void save(Paint paint);
    void update(Paint paint);
    void delete(int paintId);
    Paint findById(int id);
    List<Paint> findByOwner(int ownerId);
    List<Paint> findPublicPaints();
    List<Paint> findByOwnerAndTrash(int ownerId, boolean inTrash);
    boolean existsByNameAndOwner(String name, int ownerId);


}
