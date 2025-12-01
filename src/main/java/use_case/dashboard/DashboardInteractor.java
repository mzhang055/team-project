package use_case.dashboard;

import data_access.MealDataAccessInterface;
import data_access.UserDataAccessInterface;
import entities.Meal;
import entities.NutritionalInfo;
import entities.User;

import java.util.List;

public class DashboardInteractor implements DashboardInputBoundary{
    private final UserDataAccessInterface userDataAccess;
    private final MealDataAccessInterface mealDataAccess;

    private final DashboardOutputBoundary presenter;

    public DashboardInteractor(UserDataAccessInterface userDataAccess, MealDataAccessInterface mealDataAccess, DashboardOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.mealDataAccess = mealDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void loadDashboard(DashboardInputData input){
        String userId = input.getUserId();
        User user = userDataAccess.getUser(userId);
        List<Meal> meals = mealDataAccess.getMealsByUserId(userId);
        DashboardOutputData output = getTotal(meals);
        presenter.updateDashboard(output);
    }

    private DashboardOutputData getTotal(List<Meal> meals){
        double calories = 0.0;
        double remaining = 0.0;
        double proteins = 0.0;
        double fats = 0.0;
        double carbs = 0.0;
        double fibers = 0.0;
        double sugars = 0.0;
        for(Meal meal : meals){
            NutritionalInfo x = meal.getNutritionalInfo();
            calories += x.getCalories();
            proteins += x.getProtein();
            fats += x.getFat();
            carbs += x.getCarbohydrates();
            fibers += x.getFiber();
            sugars += x.getSugar();
        }
        return new DashboardOutputData(calories, remaining, proteins, carbs, fats, fibers, sugars);
    }
}
