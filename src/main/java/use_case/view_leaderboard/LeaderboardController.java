package use_case.view_leaderboard;

public class LeaderboardController {
    private LeaderboardInputBoundary interactor;
    public LeaderboardController(LeaderboardInputBoundary interactor) {
        this.interactor = interactor;
    }
    public void viewLeaderboard() {
        interactor.getleaderboard();
    }
}
