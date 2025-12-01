package use_case.dashboard;

import data_access.InMemoryMealDataAccessObject;
import data_access.InMemoryUserDataAccessObject;
import entities.Meal;
import entities.NutritionalInfo;
import entities.User;
import app.GoalsGuiMain;

import java.util.List;

public class DashboardInteractor implements DashboardInputBoundary{
    private final InMemoryUserDataAccessObject inMemoryUserDataAccessObject;
    private final InMemoryMealDataAccessObject inMemoryMealDataAccessObject;

    private final DashboardOutputBoundary presenter;

    public DashboardInteractor(InMemoryUserDataAccessObject inMemoryUserDataAccessObject, InMemoryMealDataAccessObject inMemoryMealDataAccessObject, DashboardOutputBoundary presenter) {
        this.inMemoryUserDataAccessObject = inMemoryUserDataAccessObject;
        this.inMemoryMealDataAccessObject = inMemoryMealDataAccessObject;
        this.presenter = presenter;
    }

    @Override
    public void loadDashboard(DashboardInputData input){
        String userId = input.getUserId();
        User user = inMemoryUserDataAccessObject.getUser(userId);
        List<Meal> meals = inMemoryMealDataAccessObject.getMealsByUserId(userId);
        DashboardOutputData output = getTotal(meals);
        presenter.updateDashboard(output);
    }

    private DashboardOutputData getTotal(List<Meal> meals){
        double calories = 0.0;
        double proteins = 0.0;
        double fats = 0.0;
        double carbs = 0.0;
        double fibers = 0.0;
        double sugars = 0.0;

        for (Meal meal : meals){
            NutritionalInfo x = meal.getNutritionalInfo();
            calories += x.getCalories();
            proteins += x.getProtein();
            fats += x.getFat();
            carbs += x.getCarbohydrates();
            fibers += x.getFiber();
            sugars += x.getSugar();
        }

        double remaining = 0.0;
        if (GoalsGuiMain.isActiveGoalSet()) {
            remaining = GoalsGuiMain.getActiveTargetCalories() - calories;
            if (remaining < 0) {
                remaining = 0.0;
            }
        }

        return new DashboardOutputData(calories, remaining, proteins, carbs, fats, fibers, sugars);
    }
}
