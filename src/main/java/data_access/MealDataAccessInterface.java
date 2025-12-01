package data_access;

import entities.Meal;
import java.util.List;

/**
 * Interface for meal persistence operations.
 * This defines the contract for storing and retrieving meals.
 */
public interface MealDataAccessInterface {

    /**
     * Saves a meal to the data store.
     *
     * @param meal the meal to save
     * @return true if save was successful, false otherwise
     */
    boolean save(Meal meal);

    /**
     * Retrieves all meals for a specific user.
     *
     * @param userId the user's ID
     * @return list of meals for the user (empty list if none found)
     */
    List<Meal> getMealsByUserId(String userId);

    boolean update(Meal meal);

    boolean delete(String mealID);
}