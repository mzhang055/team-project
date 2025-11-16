package interface_adapter.view_leaderboard;

import java.util.List;
public class LeaderboardViewmodel {
    private List<String> usernames;
    private List<Integer> scores;
    private String message;

    public void setLeaderboardData(List<String> usernames, List<Integer> scores) {
        this.usernames = usernames;
        this.scores = scores;
    }
    public List<String> getUsernames() {
        return usernames;
    }
    public List<Integer> getScores() {
        return scores;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
