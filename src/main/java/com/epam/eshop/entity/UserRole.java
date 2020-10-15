package com.epam.eshop.entity;

/**
 * Created by artch on 22.09.2020.
 */
public class UserRole {
    
    public static final String ADMINISTRATOR = "administrator";
    public static final String CUSTOMER = "customer";
    
    private int id;
    private String role;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String name) {
        this.role = name;
    }
}
