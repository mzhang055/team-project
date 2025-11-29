package use_case.view_leaderboard;

import java.util.Map;
import use_case.goals.GoalCompletionHistory;
import use_case.goals.UserManager;
import java.util.*;

public class LeaderboardDataAccess {

    public Map<String,Integer> getLeaderboardData() {
        Map<String, Integer> leaderboard = new HashMap<>();
        List<String> allUsers = UserManager.getAllUsers();

        for (String user : allUsers) {
            int completedDays = GoalCompletionHistory.getUserCompletedDays(user);
            leaderboard.put(user, completedDays);
        }
        return leaderboard;
    }
    public List<Map.Entry<String, Integer>> getSortedLeaderboard() {
        Map<String, Integer> data = getLeaderboardData();
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<>(data.entrySet());

        sortedList.sort((a, b) -> b.getValue().compareTo(a.getValue()));

        return sortedList;
    }
}
