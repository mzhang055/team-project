package use_case.dashboard;

import entities.NutritionalInfo;
import entities.User;

import java.util.List;

public class DashboardOutputData {
    private final double calories;
    private final double protein;
    private final double carbohydrates;
    private final double fat;
    private final double fiber;
    private final double sugar;

    // TODO: ADD REMAINING & FRIENDLIST & SAVED RECIPES
    public DashboardOutputData(User user, NutritionalInfo nutritionalInfo) {
        calories = nutritionalInfo.getCalories();
        protein = nutritionalInfo.getProtein();
        carbohydrates = nutritionalInfo.getCarbohydrates();
        fat = nutritionalInfo.getFat();
        fiber = nutritionalInfo.getFiber();
        sugar = nutritionalInfo.getSugar();
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbs() {
        return carbohydrates;
    }

    public double getFats() {
        return fat;
    }

    public double getFiber() {
        return fiber;
    }

    public double getSugar() {
        return sugar;
    }
}
