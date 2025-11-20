package interface_adapter.dashboard;

import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;
import view.DashboardView;

import java.util.List;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;

    public DashboardPresenter(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    @Override
    public void updateDashboard(DashboardOutputData dashboardOutputData) {
        double calories = dashboardOutputData.getCalories();
        double remaining = dashboardOutputData.getRemaining();
        double protein = dashboardOutputData.getProtein();
        double carbs = dashboardOutputData.getCarbs();
        double fats = dashboardOutputData.getFats();
        double sugar = dashboardOutputData.getSugar();
        double fiber = dashboardOutputData.getFiber();
        // List<String> recipeNames = dashboardOutputData.getRecipeNames();
        // List<String> friendNames =  dashboardOutputData.getFriendNames();

        DashboardState state = new DashboardState();
        state.setCalories(calories);
        state.setRemaining(remaining);
        state.setProtein(protein);
        state.setCarbs(carbs);
        state.setFats(fats);
        state.setFibers(fiber);
        state.setSugars(sugar);
        // state.setRecipeNames(recipeNames);
        // state.setFriendNames(friendNames);
        dashboardViewModel.setState(state);
    }
}
