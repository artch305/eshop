package com.epam.eshop.entity;

import java.util.Arrays;

/**
 * Created by artch on 01.10.2020.
 */
public enum Category {
    MONITORS("monitors"),
    KEYBOARDS("keyboards");

    private String databaseValue;

    Category(String category) {
        this.databaseValue = category;
    }

    public String getDatabaseValue() {
        return databaseValue;
    }

    public static Category getCategoryByName(String category) {
        return Arrays.stream(values())
                .filter(categoryEnum -> categoryEnum.getDatabaseValue().equals(category))
                .findFirst()
                .orElseThrow(() -> new IllegalAccessError("Not found category - " + category));
    }
}
