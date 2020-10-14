package com.epam.eshop.entity;

/**
 * Created by artch on 25.09.2020.
 */
public class UserSettings {

    private int id;
    private int userId;
    private String language;

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

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
