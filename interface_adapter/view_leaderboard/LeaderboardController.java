package interface_adapter.view_leaderboard;

import use_case.view_leaderboard.LeaderboardInputBoundary;

public class LeaderboardController {
    private LeaderboardInputBoundary leaderboardUsecase;
    public LeaderboardController(LeaderboardInputBoundary leaderboardUsecase) {
        this.leaderboardUsecase = leaderboardUsecase;
    }
    public void viewLeaderboard(String username){
        leaderboardUsecase.getLeaderboard(username);
    }
}
