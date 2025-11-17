package interface_adapter.dashboard;

import use_case.dashboard.DashboardOutputBoundary;
import use_case.dashboard.DashboardOutputData;

public class DashboardPresenter implements DashboardOutputBoundary {
    private final DashboardViewModel dashboardViewModel;

    public DashboardPresenter(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    // TODO: ADD the remaining calories, friendslist stuff
    @Override
    public void updateDashboard(DashboardOutputData dashboardOutputData) {
        double calories = dashboardOutputData.getCalories();
        double protein = dashboardOutputData.getProtein();
        double carbs = dashboardOutputData.getCarbs();
        double fats = dashboardOutputData.getFats();
        double sugar = dashboardOutputData.getSugar();
        double fiber = dashboardOutputData.getFiber();

        DashboardState state = new DashboardState();
        state.setCalories(calories);
        state.setProtein(protein);
        state.setCarbs(carbs);
        state.setFats(fats);
        state.setFibers(fiber);
        state.setSugars(sugar);
        dashboardViewModel.setState(state);
    }
}
