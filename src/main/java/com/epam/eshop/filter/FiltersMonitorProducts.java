package com.epam.eshop.filter;

import com.epam.eshop.entity.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by artch on 29.09.2020.
 */
public class FiltersMonitorProducts extends AbstractFilters {

    private static final Logger LOGGER = LoggerFactory.getLogger(FiltersMonitorProducts.class);

    private int brightness;
    private List<String> diagonals;
    private List<String> panelTypes;
    private String filtersCategory = Category.MONITORS.getDatabaseValue();

    @Override
    public String getFiltersCategory() {
        return filtersCategory;
    }

    public void setDiagonals(List<String> diagonals) {
        this.diagonals = diagonals;
    }

    public List<String> getDiagonals() {
        return diagonals;
    }

    public void setPanelTypes(List<String> panelTypes) {
        this.panelTypes = panelTypes;
    }

    public List<String> getPanelTypes() {
        return panelTypes;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

}
