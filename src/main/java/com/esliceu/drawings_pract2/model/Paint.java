package com.esliceu.drawings_pract2.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Paint {

    private int id;
    private String name;
    private String data;
    private int ownerId;
    private boolean isPublic;
    private boolean inTrash;
    private LocalDateTime creationDate;
    private LocalDateTime lastModified;

    public Paint() {}

    public Paint(String name, String data, int ownerId) {
        this.name = name;
        this.data = data;
        this.ownerId = ownerId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }

    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }

    public boolean isPublic() { return isPublic; }
    public void setPublic(boolean aPublic) { isPublic = aPublic; }

    public boolean isInTrash() { return inTrash; }
    public void setInTrash(boolean inTrash) { this.inTrash = inTrash; }

    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public LocalDateTime getLastModified() { return lastModified; }
    public void setLastModified(LocalDateTime lastModified) { this.lastModified = lastModified; }

    @Override
    public String toString() {
        return "Paint{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", ownerId=" + ownerId +
                ", isPublic=" + isPublic +
                ", inTrash=" + inTrash +
                '}';
    }
}

