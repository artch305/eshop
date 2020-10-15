package com.epam.eshop.entity;

/**
 * Created by artch on 22.09.2020.
 */
public class UserStatus {
    public static final String ACTIVE = "active";
    public static final String BANNED = "banned";
    
    private int id;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String name) {
        this.status = name;
    }
}
