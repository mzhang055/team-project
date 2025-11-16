package view_leaderboard;

public class LeaderboardEntry {
    private String username;
    private int completion;

    public LeaderboardEntry(String username, int completion) {
        this.username = username;
        this.completion = completion;
    }
    public  String getUsername() {
        return username;
    }
    public int getCompletion() {
        return completion;
    }
}
