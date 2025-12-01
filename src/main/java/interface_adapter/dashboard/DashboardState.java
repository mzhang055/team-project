package interface_adapter.dashboard;

import java.util.ArrayList;
import java.util.List;

public class DashboardState {
    // === Targets for the day ===
    private int caloriesTarget;
    private int proteinTarget;
    private int carbsTarget;
    private int fatsTarget;
    private int fiberTarget;
    private int sugarTarget;

    private double calories;
    private double remaining;
    private double protein;
    private double carbs;
    private double fats;
    private double fibers;
    private double sugars;
    private List<String> recipeNames;
    private List<String> friendNames;

    public DashboardState() {
        this.recipeNames = new ArrayList<>();
        this.friendNames = new ArrayList<>();
    }

    public DashboardState(DashboardState other) {
        this.calories = other.calories;
        this.remaining = other.remaining;
        this.protein = other.protein;
        this.carbs = other.carbs;
        this.fats = other.fats;
        this.fibers = other.fibers;
        this.sugars = other.sugars;
        this.recipeNames = new ArrayList<>(other.recipeNames);
        this.friendNames = new ArrayList<>(other.friendNames);
    }

    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }

    public double getRemaining() { return remaining; }
    public void setRemaining(double remaining) { this.remaining = remaining; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getFats() { return fats; }
    public void setFats(double fats) { this.fats = fats; }

    public double getFibers() { return fibers; }
    public void setFibers(double fibers) { this.fibers = fibers; }

    public double getSugars() { return sugars; }
    public void setSugars(double sugars) {this.sugars = sugars; }

    public List<String> getRecipeNames() { return recipeNames; }
    public void setRecipeNames(List<String> recipeNames) { this.recipeNames = recipeNames; }

    public List<String> getFriendNames() { return friendNames; }
    public void setFriendNames(List<String> friendNames) { this.friendNames = friendNames; }

    public int getCaloriesTarget() {
        return caloriesTarget;
    }

    public void setCaloriesTarget(int caloriesTarget) {
        this.caloriesTarget = caloriesTarget;
    }

    public int getProteinTarget() {
        return proteinTarget;
    }

    public void setProteinTarget(int proteinTarget) {
        this.proteinTarget = proteinTarget;
    }

    public int getCarbsTarget() {
        return carbsTarget;
    }

    public void setCarbsTarget(int carbsTarget) {
        this.carbsTarget = carbsTarget;
    }

    public int getFatsTarget() {
        return fatsTarget;
    }

    public void setFatsTarget(int fatsTarget) {
        this.fatsTarget = fatsTarget;
    }

    public int getFiberTarget() {
        return fiberTarget;
    }

    public void setFiberTarget(int fiberTarget) {
        this.fiberTarget = fiberTarget;
    }

    public int getSugarTarget() {
        return sugarTarget;
    }

    public void setSugarTarget(int sugarTarget) {
        this.sugarTarget = sugarTarget;
    }

}
