package use_case.goals;

import app.GoalsGuiMain;
import interface_adapter.dashboard.DashboardState;
import interface_adapter.dashboard.DashboardViewModel;

public class SetTargetUseCase {

    private final DashboardViewModel dashboardViewModel;

    public SetTargetUseCase(DashboardViewModel dashboardViewModel) {
        this.dashboardViewModel = dashboardViewModel;
    }

    public void openGoalHelper() {
        GoalsGuiMain.open((targetCalories, proteinGrams, carbGrams, fatGrams) -> {
            DashboardState state = dashboardViewModel.getState();
            if (state == null) {
                state = new DashboardState();
            }

            int calsInt = (int) Math.round(targetCalories);
            int protInt = (int) Math.round(proteinGrams);
            int carbInt = (int) Math.round(carbGrams);
            int fatInt  = (int) Math.round(fatGrams);

            state.setCaloriesTarget(calsInt);
            state.setProteinTarget(protInt);
            state.setCarbsTarget(carbInt);
            state.setFatsTarget(fatInt);

            state.setCalories(0);
            state.setProtein(0);
            state.setCarbs(0);
            state.setFats(0);
            state.setFibers(0);
            state.setSugars(0);

            state.setRemaining(calsInt);

            dashboardViewModel.setState(state);
            dashboardViewModel.firePropertyChange();
        });
    }
}
