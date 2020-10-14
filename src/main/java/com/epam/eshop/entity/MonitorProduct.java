package com.epam.eshop.entity;

/**
 * Created by artch on 22.09.2020.
 */
public class MonitorProduct extends Product {
    private int monitorProductId;
    private double diagonal;
    private String panelType;
    private int brightness;

    public int getMonitorProductId() {
        return monitorProductId;
    }

    public void setMonitorProductId(int monitorProductId) {
        this.monitorProductId = monitorProductId;
    }

    public double getDiagonal() {
        return diagonal;
    }

    public void setDiagonal(double diagonal) {
        this.diagonal = diagonal;
    }

    public String getPanelType() {
        return panelType;
    }

    public void setPanelType(String panelType) {
        this.panelType = panelType;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    @Override
    public String toString() {
        return "<b>producer: </b>" + getProducer() + ", <b>name:</b> " + getName() + ", <b>diagonal:</b> " +
                diagonal + ", <b>panel type:</b> " + panelType + ", <b>price:</b> " + getPrice() + "$";
    }
}
