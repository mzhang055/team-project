package use_case.LogMeals;

import entities.Meal;
import java.util.List;
import java.util.Optional;

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

    /**
     * Retrieves a specific meal by its ID.
     *
     * @param mealId the meal's ID
     * @return Optional containing the meal if found, empty otherwise
     */
    Optional<Meal> getMealById(String mealId);

    /**
     * Deletes a meal by its ID.
     *
     * @param mealId the meal's ID
     * @return true if deletion was successful, false otherwise
     */
    boolean delete(String mealId);

    /**
     * Checks if any meals exist for a user.
     *
     * @param userId the user's ID
     * @return true if user has logged meals, false otherwise
     */
    boolean hasLoggedMeals(String userId);

    /**
     * Updates an existing meal with new information.
     *
     * @param meal the updated meal (must have existing ID)
     * @return true if update was successful, false otherwise
     */
    boolean update(Meal meal);
}
