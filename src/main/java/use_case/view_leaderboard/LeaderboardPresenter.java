package use_case.view_leaderboard;
import java.util.List;

public class LeaderboardPresenter implements LeaderboardOutputBoundary {
    @Override
    public void showLeaderboard(List<LeaderboardEntry> entries) {
        System.out.println("     leaderboard     ");
        int rank = 1;
        for (LeaderboardEntry entry:  entries) {
            System.out.println(rank++ + "  " + entry.getUsername()
                    + " - " +entry.getCompletion() + "%");
            rank++;
        }
    }
}
