package com.epam.eshop.entity;

import java.util.Arrays;

/**
 * Created by artch on 16.10.2020.
 */
public enum UserStatus {
    ACTIVE("active"),
    BANNED("banned");

    private String status;

    UserStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public static UserStatus getUserStatusByName(String status) {
        return Arrays.stream(values())
                .filter(categoryEnum -> categoryEnum.getStatus().equals(status))
                .findFirst()
                .orElseThrow(() -> new IllegalAccessError("Not found category - " + status));
    }
}
