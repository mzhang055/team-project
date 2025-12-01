package use_case.dashboard;

import data_access.InMemoryMealDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entities.Meal;
import entities.MealType;
import entities.NutritionalInfo;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import entities.User;
import java.time.LocalDateTime;

class DashboardTest {

    @Test
    void loadDashboard_withMeals_computesCorrectTotals() {

        InMemoryUserDataAccessObject userDao = new InMemoryUserDataAccessObject();
        InMemoryMealDataAccessObject mealDao = new InMemoryMealDataAccessObject();

        String userId = "Sunny";
        User user = new User(userId, "Yellow");
        userDao.save(user);

        NutritionalInfo n = new NutritionalInfo.Builder()
                .calories(200)
                .protein(10)
                .carbohydrates(30)
                .fat(5)
                .fiber(3)
                .sugar(12)
                .build();

        Meal meal = Meal.builder("Test Food")
                .mealType(MealType.DINNER)
                .nutritionalInfo(n)
                .userId(userId)
                .loggedAt(LocalDateTime.now().minusHours(1))
                .build();

        mealDao.save(meal);

        DashboardOutputBoundary presenter = new DashboardOutputBoundary() {
            @Override
            public void updateDashboard(DashboardOutputData output) {
                assertNotNull(output);
                assertEquals(200, output.getCalories());
                assertEquals(10, output.getProtein());
                assertEquals(5, output.getFats());
                assertEquals(30, output.getCarbs());
                assertEquals(3, output.getFiber());
                assertEquals(12, output.getSugar());
            }
        };

        DashboardInteractor interactor = new DashboardInteractor(userDao, mealDao, presenter);
        DashboardInputData input = new DashboardInputData(userId);

        interactor.loadDashboard(input);
    }

    @Test
    void loadDashboard_noMeals_returnsZeroTotals() {

        InMemoryUserDataAccessObject userDao = new InMemoryUserDataAccessObject();
        InMemoryMealDataAccessObject mealDao = new InMemoryMealDataAccessObject();

        String userId = "Rainy";
        User user = new User(userId, "Skies");
        userDao.save(user);

        DashboardOutputBoundary presenter = new DashboardOutputBoundary() {
            @Override
            public void updateDashboard(DashboardOutputData output) {
                assertNotNull(output);
                assertEquals(0, output.getCalories());
                assertEquals(0, output.getProtein());
                assertEquals(0, output.getFats());
                assertEquals(0, output.getCarbs());
                assertEquals(0, output.getFiber());
                assertEquals(0, output.getSugar());
            }
        };

        DashboardInteractor interactor = new DashboardInteractor(userDao, mealDao, presenter);
        DashboardInputData input = new DashboardInputData(userId);

        interactor.loadDashboard(input);
    }
}
