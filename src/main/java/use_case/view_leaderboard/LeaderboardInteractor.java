package use_case.view_leaderboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaderboardInteractor implements LeaderboardInputBoundary {
    private LeaderboardOutputBoundary presenter;
    private LeaderboardDataAccess dataAccess;
    public LeaderboardInteractor(LeaderboardOutputBoundary presenter, LeaderboardDataAccess dataAccess) {
        this.presenter = presenter;
        this.dataAccess = dataAccess;
    }
@Override
    public void getLeaderboard(String username) {
        if (username == null || username.isEmpty()) {
            presenter.prepareFailView("Please enter a valid username!");
            return;
        }
        try {
            List<Map.Entry<String, Integer>> sortedLeaderboard = dataAccess.getSortedLeaderboard();
            List<String> rankedUsernames = new ArrayList<>();
            List<Integer> scores = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : sortedLeaderboard) {
                rankedUsernames.add(entry.getKey());
                scores.add(entry.getValue());
            }

            LeaderboardOutputData outputData = new LeaderboardOutputData(rankedUsernames, scores);
            presenter.prepareSuccessView(outputData);
        } catch (Exception e) {
            presenter.prepareFailView("Error retrieving leaderboard: " + e.getMessage());
        }

        }
}
