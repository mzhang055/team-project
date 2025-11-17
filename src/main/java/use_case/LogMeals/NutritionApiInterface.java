package use_case.LogMeals;

import entities.NutritionalInfo;
import java.util.Optional;

/**
 * Interface for fetching nutritional information from an external API.
 * This defines the contract for retrieving nutrition data.
 */
public interface NutritionApiInterface {

    /**
     * Fetches nutritional information for a given food item.
     *
     * @param foodName the name of the food/meal
     * @return Optional containing NutritionalInfo if found, empty if API call fails or food not found
     */
    Optional<NutritionalInfo> getNutritionalInfo(String foodName);
}
