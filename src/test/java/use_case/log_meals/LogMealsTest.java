package use_case.log_meals;

import data_access.MealDataAccessInterface;
import data_access.UserDataAccessInterface;
import entities.Meal;
import entities.MealType;
import entities.NutritionalInfo;
import entities.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the meal logging feature.
 * Tests Use Case and Entity layers to achieve 100% code coverage.
 */
public class LogMealsTest {

    // ==================== ENTITY TESTS ====================

    // ---------- NutritionalInfo Tests ----------

    @Test
    void testNutritionalInfo_BuilderWithValidValues() {
        NutritionalInfo nutritionInfo = NutritionalInfo.builder()
                .calories(250.5)
                .protein(15.2)
                .carbohydrates(30.0)
                .fat(10.5)
                .fiber(5.0)
                .sugar(8.0)
                .build();

        assertEquals(250.5, nutritionInfo.getCalories(), 0.001);
        assertEquals(15.2, nutritionInfo.getProtein(), 0.001);
        assertEquals(30.0, nutritionInfo.getCarbohydrates(), 0.001);
        assertEquals(10.5, nutritionInfo.getFat(), 0.001);
        assertEquals(5.0, nutritionInfo.getFiber(), 0.001);
        assertEquals(8.0, nutritionInfo.getSugar(), 0.001);
    }

    @Test
    void testNutritionalInfo_NegativeValues_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> NutritionalInfo.builder().calories(-100).build());
        assertThrows(IllegalArgumentException.class, () -> NutritionalInfo.builder().protein(-10).build());
        assertThrows(IllegalArgumentException.class, () -> NutritionalInfo.builder().carbohydrates(-20).build());
        assertThrows(IllegalArgumentException.class, () -> NutritionalInfo.builder().fat(-5).build());
        assertThrows(IllegalArgumentException.class, () -> NutritionalInfo.builder().fiber(-3).build());
        assertThrows(IllegalArgumentException.class, () -> NutritionalInfo.builder().sugar(-7).build());
    }

    @Test
    void testNutritionalInfo_Equals() {
        NutritionalInfo info1 = NutritionalInfo.builder().calories(200).protein(10).build();
        NutritionalInfo info2 = NutritionalInfo.builder().calories(200).protein(10).build();
        assertEquals(info1, info2);
    }

    // ---------- MealType Tests ----------

    @Test
    void testMealType_AllValues() {
        assertEquals("Breakfast", MealType.BREAKFAST.getDisplayName());
        assertEquals("Lunch", MealType.LUNCH.getDisplayName());
        assertEquals("Dinner", MealType.DINNER.getDisplayName());
        assertEquals("Snack", MealType.SNACK.getDisplayName());
    }

    @Test
    void testMealType_ValueOf() {
        assertEquals(MealType.BREAKFAST, MealType.valueOf("BREAKFAST"));
        assertThrows(IllegalArgumentException.class, () -> MealType.valueOf("INVALID"));
    }

    // ---------- Meal Tests ----------

    @Test
    void testMeal_BuilderWithAllFields() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 11, 30, 12, 0);
        NutritionalInfo nutritionInfo = NutritionalInfo.builder().calories(300).build();

        Meal meal = Meal.builder("Chicken Breast")
                .id("test-id")
                .mealType(MealType.LUNCH)
                .userId("user123")
                .nutritionalInfo(nutritionInfo)
                .loggedAt(timestamp)
                .build();

        assertEquals("test-id", meal.getId());
        assertEquals("Chicken Breast", meal.getFoodName());
        assertEquals(MealType.LUNCH, meal.getMealType());
        assertEquals("user123", meal.getUserId());
        assertEquals(nutritionInfo, meal.getNutritionalInfo());
        assertEquals(timestamp, meal.getLoggedAt());
    }

    @Test
    void testMeal_BuilderAutoGeneratesIdAndTimestamp() {
        Meal meal = Meal.builder("Apple")
                .mealType(MealType.SNACK)
                .userId("user123")
                .build();

        assertNotNull(meal.getId());
        assertNotNull(meal.getLoggedAt());
    }

    @Test
    void testMeal_BuilderTrimsWhitespace() {
        Meal meal = Meal.builder("  Pizza  ")
                .mealType(MealType.DINNER)
                .userId("user123")
                .build();

        assertEquals("Pizza", meal.getFoodName());
    }

    @Test
    void testMeal_InvalidInputs_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> Meal.builder(null));
        assertThrows(IllegalArgumentException.class, () -> Meal.builder(""));
        assertThrows(IllegalArgumentException.class, () -> Meal.builder("   "));
        assertThrows(IllegalArgumentException.class, () -> Meal.builder("Toast").mealType(null));
        assertThrows(IllegalArgumentException.class, () -> Meal.builder("Eggs").userId(null));
        assertThrows(IllegalStateException.class, () -> Meal.builder("Cereal").userId("user123").build());
        assertThrows(IllegalStateException.class, () -> Meal.builder("Yogurt").mealType(MealType.BREAKFAST).build());
    }

    @Test
    void testMeal_Equals() {
        Meal meal1 = Meal.builder("Pizza").id("same-id").mealType(MealType.DINNER).userId("user123").build();
        Meal meal2 = Meal.builder("Burger").id("same-id").mealType(MealType.LUNCH).userId("user456").build();
        Meal meal3 = Meal.builder("Pizza").id("diff-id").mealType(MealType.DINNER).userId("user123").build();

        assertEquals(meal1, meal2); // Same ID
        assertNotEquals(meal1, meal3); // Different ID
    }

    // ==================== USE CASE TESTS ====================

    // ---------- LogMealsInputData Tests ----------

    @Test
    void testInputData_AllFields() {
        LogMealsInputData inputData = new LogMealsInputData("Chicken Salad", MealType.LUNCH, "user123");

        assertEquals("Chicken Salad", inputData.getFoodName());
        assertEquals(MealType.LUNCH, inputData.getMealType());
        assertEquals("user123", inputData.getUserId());
    }

    // ---------- LogMealsOutputData Tests ----------

    @Test
    void testOutputData_SuccessAndFailure() {
        Meal meal = Meal.builder("Chicken").mealType(MealType.LUNCH).userId("user123").build();

        LogMealsOutputData success = new LogMealsOutputData(meal, true);
        assertTrue(success.isSuccess());

        LogMealsOutputData failure = new LogMealsOutputData(meal, false);
        assertFalse(failure.isSuccess());
    }

    // ---------- LogMealsInteractor Tests ----------

    @Test
    void testInteractor_FetchNutrition_Success() {
        NutritionalInfo nutritionInfo = NutritionalInfo.builder().calories(250).protein(15).build();

        NutritionApiInterface nutritionApi = foodName -> Optional.of(nutritionInfo);

        LogMealsOutputBoundary outputBoundary = new LogMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMealsOutputData outputData) {
                assertNotNull(outputData.getMeal());
                assertEquals("Chicken Salad", outputData.getMeal().getFoodName());
                assertEquals(nutritionInfo, outputData.getMeal().getNutritionalInfo());
            }

            @Override
            public void prepareSaveSuccessView(LogMealsOutputData outputData) {
                fail("Should not call prepareSaveSuccessView");
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not call prepareFailView");
            }
        };

        LogMealsInteractor interactor = new LogMealsInteractor(null, nutritionApi, null, outputBoundary);
        interactor.fetchNutrition(new LogMealsInputData("Chicken Salad", MealType.LUNCH, "user123"));
    }

    @Test
    void testInteractor_FetchNutrition_InvalidFoodName() {
        LogMealsOutputBoundary outputBoundary = new LogMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMealsOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareSaveSuccessView(LogMealsOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Food name cannot be empty", error);
            }
        };

        LogMealsInteractor interactor = new LogMealsInteractor(null, null, null, outputBoundary);
        interactor.fetchNutrition(new LogMealsInputData(null, MealType.LUNCH, "user123"));
        interactor.fetchNutrition(new LogMealsInputData("", MealType.LUNCH, "user123"));
        interactor.fetchNutrition(new LogMealsInputData("   ", MealType.LUNCH, "user123"));
    }

    @Test
    void testInteractor_FetchNutrition_ApiReturnsEmpty() {
        NutritionApiInterface nutritionApi = foodName -> Optional.empty();

        LogMealsOutputBoundary outputBoundary = new LogMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMealsOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareSaveSuccessView(LogMealsOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Could not fetch nutritional information for: Pizza", error);
            }
        };

        LogMealsInteractor interactor = new LogMealsInteractor(null, nutritionApi, null, outputBoundary);
        interactor.fetchNutrition(new LogMealsInputData("Pizza", MealType.DINNER, "user123"));
    }

    @Test
    void testInteractor_SaveMeal_Success() {
        Meal meal = Meal.builder("Steak").id("meal-123").mealType(MealType.DINNER).userId("user456").build();
        User user = new User("testuser", "password");

        MealDataAccessInterface mealDataAccess = new MealDataAccessInterface() {
            @Override
            public boolean save(Meal meal) {
                return true;
            }

            @Override
            public Optional<Meal> getMealById(String mealId) {
                return Optional.empty();
            }

            @Override
            public java.util.List<Meal> getMealsByUserId(String userId) {
                return null;
            }

            @Override
            public boolean delete(String mealId) {
                return false;
            }

            @Override
            public boolean hasLoggedMeals(String userId) {
                return false;
            }

            @Override
            public boolean update(Meal meal) {
                return false;
            }
        };

        UserDataAccessInterface userDataAccess = new UserDataAccessInterface() {
            @Override
            public User getUser(String username) {
                return user;
            }

            @Override
            public void save(User user) {
                assertTrue(user.getMealIds().contains("meal-123"));
            }

            @Override
            public boolean existsByUsername(String username) {
                return false;
            }

            @Override
            public java.util.List<User> getAllUsers() {
                return null;
            }
        };

        LogMealsOutputBoundary outputBoundary = new LogMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMealsOutputData outputData) {
                fail("Should not call prepareSuccessView");
            }

            @Override
            public void prepareSaveSuccessView(LogMealsOutputData outputData) {
                assertTrue(outputData.isSuccess());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Should not fail");
            }
        };

        LogMealsInteractor interactor = new LogMealsInteractor(mealDataAccess, null, userDataAccess, outputBoundary);
        interactor.saveMeal(meal, "user456");
    }

    @Test
    void testInteractor_SaveMeal_Failure() {
        Meal meal = Meal.builder("Burger").mealType(MealType.LUNCH).userId("user789").build();

        MealDataAccessInterface mealDataAccess = new MealDataAccessInterface() {
            @Override
            public boolean save(Meal meal) {
                return false; // Simulate failure
            }

            @Override
            public Optional<Meal> getMealById(String mealId) {
                return Optional.empty();
            }

            @Override
            public java.util.List<Meal> getMealsByUserId(String userId) {
                return null;
            }

            @Override
            public boolean delete(String mealId) {
                return false;
            }

            @Override
            public boolean hasLoggedMeals(String userId) {
                return false;
            }

            @Override
            public boolean update(Meal meal) {
                return false;
            }
        };

        LogMealsOutputBoundary outputBoundary = new LogMealsOutputBoundary() {
            @Override
            public void prepareSuccessView(LogMealsOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareSaveSuccessView(LogMealsOutputData outputData) {
                fail("Should not succeed");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Failed to save meal", error);
            }
        };

        LogMealsInteractor interactor = new LogMealsInteractor(mealDataAccess, null, null, outputBoundary);
        interactor.saveMeal(meal, "user789");
    }
}
