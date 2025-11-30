package use_case.view_leaderboard;
import java.util.List;
import java.util.Map;

public interface LeaderboardDataAccessInterface {
    List<Map.Entry<String, Integer>> getSortedLeaderboard();
    Map<String, Integer> getLeaderboardData();
}
