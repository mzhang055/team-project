package use_case.view_leaderboard;

//这里要改数据
public class LeaderboardInteractor implements LeaderboardInputBoundary {
    private LeaderboardOutputBoundary presenter;
    public LeaderboardInteractor(LeaderboardOutputBoundary presenter) {
        this.presenter = presenter;
    }
@Override
    public void getleaderboard() {

}

}
