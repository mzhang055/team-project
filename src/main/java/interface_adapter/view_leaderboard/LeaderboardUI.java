package interface_adapter.view_leaderboard;

import use_case.view_leaderboard.LeaderboardInteractor;
import java.awt.*;
import javax.swing.*;
import java.util.List;
public class LeaderboardUI extends JFrame {
    private final LeaderboardViewmodel viewModel;
    private final JList<String> leaderboardList;
    private final JButton refreshButton;
    private final LeaderboardInteractor interactor;
    public LeaderboardUI() {
        this.viewModel = new LeaderboardViewmodel();
        LeaderboardPresenter presenter = new LeaderboardPresenter(viewModel);
        this.interactor = new LeaderboardInteractor(presenter);

        setTitle("    Leaderboard    ");
        setSize(300,400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        leaderboardList = new JList<>();
        add(new JScrollPane(leaderboardList),BorderLayout.CENTER);

        refreshButton = new JButton("Refresh Leaderboard");
        refreshButton.addActionListener(e -> refreshLeaderboard());
        add(refreshButton,BorderLayout.SOUTH);
    }
    private void refreshLeaderboard() {
        interactor.getLeaderboard("currentUser");
        List<String> users = viewModel.getUsernames();
        List<Integer> scores = viewModel.getScores();
        if (users == null || scores == null) {
            leaderboardList.setListData(new String[]{"no data available"});
            return;
        }
        String[] displayData = new String[users.size()];
        for (int i = 0; i < users.size(); i++) {
            displayData[i] = (i + 1) + ". " + users.get(i) + " - " + scores.get(i);
        }
        leaderboardList.setListData(displayData);
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            LeaderboardUI ui = new LeaderboardUI();
            ui.setVisible(true);
            ui.refreshLeaderboard();

        });
    }
}
