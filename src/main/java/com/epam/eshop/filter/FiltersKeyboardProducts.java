package com.epam.eshop.filter;

import com.epam.eshop.entity.Category;

import java.util.List;

/**
 * Created by artch on 30.09.2020.
 */
public class FiltersKeyboardProducts extends AbstractFilters {

    private List<String> connectionTypes;
    private List<Boolean> mechanical;
    private List<String> lightColors;
    private final String filtersCategory = Category.KEYBOARDS.getDatabaseValue();

    @Override
    public String getFiltersCategory() {
        return filtersCategory;
    }

    public List<String> getConnectionTypes() {
        return connectionTypes;
    }

    public void setConnectionTypes(List<String> connectionTypes) {
        this.connectionTypes = connectionTypes;
    }

    public List<Boolean> getMechanical() {
        return mechanical;
    }

    public void setMechanical(List<Boolean> mechanical) {
        this.mechanical = mechanical;
    }

    public List<String> getLightColors() {
        return lightColors;
    }

    public void setLightColors(List<String> lightColors) {
        this.lightColors = lightColors;
    }

}
