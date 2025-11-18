package use_case.dashboard;

import entities.NutritionalInfo;
import entities.User;

import java.util.ArrayList;
import java.util.List;

public class DashboardOutputData {
    private final double calories;
    private final double remaining = 0.0;
    private final double protein;
    private final double carbohydrates;
    private final double fat;
    private final double fiber;
    private final double sugar;
    private List<String> recipeNames;
    private List<String> friendNames;

    // TODO: Initalize remaining & recipelist
    public DashboardOutputData(User user) {

        calories = nutritionalInfo.getCalories();
        protein = nutritionalInfo.getProtein();
        carbohydrates = nutritionalInfo.getCarbohydrates();
        fat = nutritionalInfo.getFat();
        fiber = nutritionalInfo.getFiber();
        sugar = nutritionalInfo.getSugar();

        friendNames = user.getFriendsUsernames();
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

    public List<String> getRecipeNames() {return recipeNames; }

    public List<String> getFriendNames() { return friendNames; }
}
