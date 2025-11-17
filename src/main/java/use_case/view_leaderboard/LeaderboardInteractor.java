package use_case.view_leaderboard;
import java.util.Arrays;
import java.util.List;

// using exmaple data, need to change

public class LeaderboardInteractor implements LeaderboardInputBoundary {
    private LeaderboardOutputBoundary presenter;
    public LeaderboardInteractor(LeaderboardOutputBoundary presenter) {
        this.presenter = presenter;
    }
@Override
    public void getLeaderboard(String username) {
        if (username == null || username.isEmpty()) {
            presenter.prepareFailView("Please enter a valid username!");
            return;
        }
        List<String> users = Arrays.asList("Nikki","Annie","Amanda","Mandy","Joyce","Sylus","Xavier","Calab");
        List<Integer> scores = Arrays.asList(100,99,98,97,95,93,92,90);
        presenter.prepareSuccessView(new LeaderboardOutputData(users, scores));
}

}
