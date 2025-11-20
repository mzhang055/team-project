package use_case.view_leaderboard;
import java.util.List;
public class LeaderboardOutputData {
    private final List<String> rankedUsernames;
    private final List<Integer> scores;
    public LeaderboardOutputData(List<String> rankedUsernames, List<Integer> scores) {
        this.rankedUsernames = rankedUsernames;
        this.scores = scores;
    }
    public List<String> getRankedUsernames() {
        return rankedUsernames;
    }
    public List<Integer> getScores() {
        return scores;
    }
}
