package com.epam.eshop.entity;

import java.util.Arrays;

/**
 * Created by artch on 16.10.2020.
 */
public enum UserRole {
    ADMINISTRATOR("administrator"),
    CUSTOMER("customer");

    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public static UserRole getUserRoleByName(String role) {
        return Arrays.stream(values())
                .filter(categoryEnum -> categoryEnum.getRole().equals(role))
                .findFirst()
                .orElseThrow(() -> new IllegalAccessError("Not found category - " + role));
    }
}
