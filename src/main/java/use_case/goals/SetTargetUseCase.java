package use_case.goals;

import app.GoalsGuiMain;
import interface_adapter.dashboard.DashboardViewModel;
import interface_adapter.dashboard.DashboardState;

public class SetTargetUseCase {

    private final DashboardViewModel dashboardViewModel;

    public SetTargetUseCase(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    public void openGoalHelper() {
        GoalsGuiMain.open((targetCalories, proteinGrams, carbGrams, fatGrams) -> {
            // Get current state or create a new one
            DashboardState state = dashboardViewModel.getState();
            if (state == null) {
                state = new DashboardState();
            }

            // Set the values on the state
            state.setCalories(0);                 // start at 0 eaten
            state.setRemaining(targetCalories);   // or calsInt if you want ints
            state.setProtein(proteinGrams);
            state.setCarbs(carbGrams);
            state.setFats(fatGrams);

            dashboardViewModel.setState(state);

            dashboardViewModel.firePropertyChange(); // propertyName="state"
        });
    }
}