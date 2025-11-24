package interface_adapter.dashboard;

import use_case.dashboard.DashboardInputBoundary;
import use_case.dashboard.DashboardInputData;

public class DashboardController {
    private final DashboardInputBoundary interactor;

    public DashboardController(DashboardInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void loadDashboard(String userId) {
        DashboardInputData inputData = new DashboardInputData(userId);
        interactor.loadDashboard(inputData);
    }
}
