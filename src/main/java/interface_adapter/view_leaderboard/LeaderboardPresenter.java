package interface_adapter.view_leaderboard;

import use_case.view_leaderboard.LeaderboardOutputBoundary;
import use_case.view_leaderboard.LeaderboardOutputData;

import java.util.List;

public class LeaderboardPresenter implements LeaderboardOutputBoundary {
    private final LeaderboardViewmodel viewModel;
    public LeaderboardPresenter(LeaderboardViewmodel viewModel){
        this.viewModel = viewModel;
    }
    @Override
public void prepareSuccessView(LeaderboardOutputData data){
        List<String> users = data.getRankedUsernames();
        List<Integer> scores = data.getScores();

        viewModel.setLeaderboardData(users, scores);
    System.out.println("Leaderboard updated successfully!");
    System.out.println("Users: " + data.getRankedUsernames());
    System.out.println("Scores: " + data.getScores());}
    @Override
    public void prepareFailView(String errorMessage){
        viewModel.setMessage(errorMessage);
        System.out.println("update failed!"+errorMessage);
    }
}

