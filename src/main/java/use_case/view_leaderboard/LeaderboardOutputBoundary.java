package use_case.view_leaderboard;

import java.util.List;

public interface LeaderboardOutputBoundary {
    void showLeaderboard(List<LeaderboardEntry> leaderboard);
}
