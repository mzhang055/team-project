package use_case.view_leaderboard;

import java.util.List;

public interface LeaderboardOutputBoundary {
    void prepareSuccessView(LeaderboardOutputData data);
    void prepareFailView(String errorMessage);
}
