package com.epam.eshop.filter;

import java.util.List;

/**
 * Created by artch on 10.10.2020.
 */
public abstract class AbstractFilters {

    private double minPrice;
    private double maxPrice;
    private List<String> producers;
    private List<Boolean> active;

    public List<Boolean> getActive() {
        return active;
    }

    public void setActive(List<Boolean> active) {
        this.active = active;
    }

    private String conditions = "";
    private String conditionForOrdering = "";
    private String orderingName;

    public String getConditions() {
        return conditions;
    }

    private String delimiterBetweenConditions = " and ";
    private String delimiterBetweenWords = "', '";

    abstract public String getFiltersCategory();

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(double minPrice) {
        this.minPrice = minPrice;
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        this.maxPrice = maxPrice;
    }

    public List<String> getProducers() {
        return producers;
    }

    public void setProducers(List<String> producers) {
        this.producers = producers;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }

    public String getConditionForOrdering() {
        return conditionForOrdering;
    }

    public void setConditionForOrdering(String conditionForOrdering) {
        this.conditionForOrdering = conditionForOrdering;
    }

    public String getOrderingName() {
        return orderingName;
    }

    public void setOrderingName(String orderingName) {
        this.orderingName = orderingName;
    }

    public String getDelimiterBetweenConditions() {
        return delimiterBetweenConditions;
    }

    public void setDelimiterBetweenConditions(String delimiterBetweenConditions) {
        this.delimiterBetweenConditions = delimiterBetweenConditions;
    }

    public String getDelimiterBetweenWords() {
        return delimiterBetweenWords;
    }

    public void setDelimiterBetweenWords(String delimiterBetweenWords) {
        this.delimiterBetweenWords = delimiterBetweenWords;
    }

}
