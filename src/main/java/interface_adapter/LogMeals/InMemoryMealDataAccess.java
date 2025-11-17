package interface_adapter.LogMeals;

import entities.Meal;
import use_case.LogMeals.MealDataAccessInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory implementation of MealDataAccessInterface.
 * Stores meals in memory using a HashMap.
 */
public class InMemoryMealDataAccess implements MealDataAccessInterface {

    private final Map<String, Meal> meals = new HashMap<>();
    private final Map<String, List<String>> userMeals = new HashMap<>();

    @Override
    public boolean save(Meal meal) {
        if (meal == null || meal.getId() == null) {
            return false;
        }

        meals.put(meal.getId(), meal);

        userMeals.computeIfAbsent(meal.getUserId(), k -> new ArrayList<>()).add(meal.getId());

        return true;
    }

    @Override
    public List<Meal> getMealsByUserId(String userId) {
        List<String> mealIds = userMeals.getOrDefault(userId, new ArrayList<>());
        List<Meal> userMealsList = new ArrayList<>();

        for (String mealId : mealIds) {
            Meal meal = meals.get(mealId);
            if (meal != null) {
                userMealsList.add(meal);
            }
        }

        return userMealsList;
    }

    @Override
    public Optional<Meal> getMealById(String mealId) {
        return Optional.ofNullable(meals.get(mealId));
    }

    @Override
    public boolean delete(String mealId) {
        Meal meal = meals.remove(mealId);
        if (meal != null) {
            List<String> mealIds = userMeals.get(meal.getUserId());
            if (mealIds != null) {
                mealIds.remove(mealId);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasLoggedMeals(String userId) {
        List<String> mealIds = userMeals.get(userId);
        return mealIds != null && !mealIds.isEmpty();
    }

    @Override
    public boolean update(Meal meal) {
        if (meal == null || meal.getId() == null) {
            return false;
        }

        if (!meals.containsKey(meal.getId())) {
            return false;
        }

        meals.put(meal.getId(), meal);
        return true;
    }
}
