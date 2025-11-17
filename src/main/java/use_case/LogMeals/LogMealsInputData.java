package use_case.LogMeals;

import entities.MealType;

/**
 * Input data for the Log Meals use case.
 * Contains all information needed from the user to log a meal.
 */
public class LogMealsInputData {
    private final String foodName;
    private final MealType mealType;
    private final String userId;

    /**
     * Constructs input data for logging a meal.
     *
     * @param foodName the name of the food/meal entered by user
     * @param mealType the type of meal (breakfast, lunch, dinner, snack)
     * @param userId the ID of the user logging the meal
     */
    public LogMealsInputData(String foodName, MealType mealType, String userId) {
        this.foodName = foodName;
        this.mealType = mealType;
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public MealType getMealType() {
        return mealType;
    }

    public String getUserId() {
        return userId;
    }
}
