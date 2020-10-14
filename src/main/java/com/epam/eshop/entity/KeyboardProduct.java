package com.epam.eshop.entity;

/**
 * Created by artch on 22.09.2020.
 */
public class KeyboardProduct extends Product {
    private int keyboardProductId;
    private String connectionType;
    private boolean mechanical;
    private String lightColor;

    public int getKeyboardProductId() {
        return keyboardProductId;
    }

    public void setKeyboardProductId(int keyboardProductId) {
        this.keyboardProductId = keyboardProductId;
    }

    public String getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(String connectionType) {
        this.connectionType = connectionType;
    }

    public boolean isMechanical() {
        return mechanical;
    }

    public void setMechanical(boolean mechanical) {
        this.mechanical = mechanical;
    }

    public String getLightColor() {
        return lightColor;
    }

    public void setLightColor(String lightColor) {
        this.lightColor = lightColor;
    }

    @Override
    public String toString() {
        return "<b>producer: </b>" + getProducer() + ", <b>name:</b> " + getName() + ", <b>mechanical:</b> " +
                (mechanical ? "Yes" : "No") + ", <b>Light color:</b> " + (lightColor.isEmpty() ? " -" : lightColor) + ", <b>price:</b> " + getPrice() + "$";
    }
}
