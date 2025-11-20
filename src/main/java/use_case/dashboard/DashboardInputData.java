package use_case.dashboard;

public class DashboardInputData {
    private final String userId;

    public DashboardInputData(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
