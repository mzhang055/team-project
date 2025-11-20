package use_case.dashboard;

import entities.NutritionalInfo;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class DashboardOutputData {
    private final double calories;
    private final double remaining;
    private final double protein;
    private final double carbohydrates;
    private final double fat;
    private final double fiber;
    private final double sugar;
    // private List<String> recipeNames;
    // private List<String> friendNames;

    public DashboardOutputData(double calories,
                               double remaining,
                               double protein,
                               double carbohydrates,
                               double fat,
                               double fiber,
                               double sugar) {
        this.calories = calories;
        this.remaining = remaining;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.fiber = fiber;
        this.sugar = sugar;
        // this.recipeNames = recipeNames;
        // this.friendNames = friendNames;
    }

    public double getCalories() {return calories;}

    public double getRemaining() {return remaining;}

    public double getProtein() {return protein;}

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

    // public List<String> getRecipeNames() {return recipeNames; }

    // public List<String> getFriendNames() { return friendNames; }
}
