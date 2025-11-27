package com.esliceu.drawings_pract2.model;

public class Paint_Permissons {

    private int id;
    private int userId;
    private int paintId;
    private boolean canWrite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPaintId() {
        return paintId;
    }

    public void setPaintId(int paintId) {
        this.paintId = paintId;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }
}
