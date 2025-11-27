package com.esliceu.drawings_pract2.model;

import java.time.LocalDateTime;

public class Paint_Versions {

    private int id;
    private int paintId;
    private int versionNumber;
    private String data;
    private LocalDateTime savedAt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPaintId() {
        return paintId;
    }

    public void setPaintId(int paintId) {
        this.paintId = paintId;
    }

    public int getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(int versionNumber) {
        this.versionNumber = versionNumber;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public LocalDateTime getSavedAt() {
        return savedAt;
    }

    public void setSavedAt(LocalDateTime savedAt) {
        this.savedAt = savedAt;
    }
}
