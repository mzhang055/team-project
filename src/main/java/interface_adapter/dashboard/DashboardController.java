package interface_adapter.dashboard;

public class DashboardController {
    private final ViewManager viewManager;

    public DashboardController(ViewManager viewManager) {
        this.viewManager = viewManager;
    }

    public void goToSetTarget() { viewManager.show("SetTargetView"); }
    public void goToLogMeals() { viewManager.show("LogMealView"); }
    public void goToSaveRecipe() { viewManager.show("SaveRecipeView"); }
    public void goToProfile() { viewManager.show("ProfileView"); }
    public void goToHistory() { viewManager.show("HistoryView"); }
}
