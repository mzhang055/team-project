package view;

import interface_adapter.Navigation;
import interface_adapter.remove_friend.RemoveFriendDialog;
import use_case.view_leaderboard.LeaderboardInteractor;
import interface_adapter.remove_friend.RemoveFriendController;
import interface_adapter.view_leaderboard.LeaderboardPresenter;
import interface_adapter.view_leaderboard.LeaderboardViewmodel;
import use_case.view_leaderboard.LeaderboardDataAccess;

import java.awt.*;
import javax.swing.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LeaderboardView extends JPanel {
    private final LeaderboardViewmodel viewModel;
    private final JPanel leaderboardPanel;
    private final JButton refreshButton;
    private final JButton backButton;
    private final LeaderboardInteractor interactor;
    private Navigation navigation;

    private RemoveFriendController removeFriendController;
    private String currentUsername;

    public LeaderboardView(RemoveFriendController removeFriendController, String currentUsername) {
        this.removeFriendController = removeFriendController;
        this.currentUsername = currentUsername;
        this.navigation = null;

        this.viewModel = new LeaderboardViewmodel();
        LeaderboardPresenter presenter = new LeaderboardPresenter(viewModel);
        LeaderboardDataAccess dataAccess = new LeaderboardDataAccess();
        this.interactor = new LeaderboardInteractor(presenter, dataAccess);

        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new BorderLayout());

        backButton = new JButton("← Back to Dashboard");
        backButton.addActionListener(e -> goBackToDashboard());

        topPanel.add(backButton, BorderLayout.WEST);

        JLabel titleLabel = new JLabel("Leaderboard", SwingConstants.NORTH);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.CENTER);

        JLabel instructionLabel = new JLabel("Click on any username to remove as friend");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        instructionLabel.setForeground(Color.BLACK);
        add(instructionLabel, BorderLayout.SOUTH);

        this.add(topPanel, BorderLayout.NORTH);

        leaderboardPanel = new JPanel();
        leaderboardPanel.setLayout(new BoxLayout(leaderboardPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(leaderboardPanel);
        add(scrollPane, BorderLayout.CENTER);

        refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> refreshLeaderboard());

        add(refreshButton, BorderLayout.SOUTH);

        refreshLeaderboard();
    }

    private void goBackToDashboard() {
        if (navigation != null) {
            navigation.goTo("Dashboard");
        } else {
            System.out.println("Navigation not set for LeaderboardView");
        }
    }

    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }

    private void refreshLeaderboard() {
        interactor.getLeaderboard("currentUser");
        List<String> users = viewModel.getUsernames();
        List<Integer> scores = viewModel.getScores();
        leaderboardPanel.removeAll();
        if (users == null || scores == null) {
            JLabel noDataLabel = new JLabel("no data available");
            noDataLabel.setHorizontalAlignment(SwingConstants.CENTER);
            leaderboardPanel.add(noDataLabel);
        } else {
            for (int i = 0; i < users.size(); i++) {
                String user = users.get(i);
                int score = scores.get(i);

                JLabel userLabel = new JLabel((i + 1) + ". " + user + " - " + score);
                userLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

                if (user.equals(currentUsername)) {
                    userLabel.setForeground(Color.BLUE);
                    userLabel.setFont(userLabel.getFont().deriveFont(Font.BOLD));
                }

                final String username = user;
                userLabel.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (!username.equals(currentUsername)) {
                            showRemoveFriendDialog(username);
                        } else {
                            JOptionPane.showMessageDialog(LeaderboardView.this,
                                    "You cannot remove yourself as friend",
                                    "Info",
                                    JOptionPane.INFORMATION_MESSAGE);
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (!username.equals(currentUsername)) {
                            userLabel.setForeground(Color.RED);
                            userLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if (!username.equals(currentUsername)) {
                            userLabel.setForeground(Color.BLACK);
                        } else {
                            userLabel.setForeground(Color.BLUE);
                        }
                    }
                });
                leaderboardPanel.add(userLabel);
            }
        }
        leaderboardPanel.revalidate();
        leaderboardPanel.repaint();
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(()->{
            RemoveFriendController testController = createTestController();
            String testUsername = "currentUser";

            JFrame testFrame = new JFrame("Leaderboard Test");
            testFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            testFrame.setSize(350, 450);

            LeaderboardView leaderboardPanel = new LeaderboardView(testController, testUsername);
            testFrame.add(leaderboardPanel);
            testFrame.setVisible(true);

        });
    }
    private static RemoveFriendController createTestController(){
        return null;
    }

    private void showRemoveFriendDialog(String friendUsername){
        RemoveFriendDialog dialog = new RemoveFriendDialog();
        boolean userConfirmed = dialog.showDialog(friendUsername);

        if (userConfirmed){
            removeFriendController.execute(currentUsername, friendUsername);

            refreshLeaderboard();
        }
    }
    public String getViewname(){
        return "Leaderboard";
    }

    public void setRemoveFriendController(RemoveFriendController controller) {
        this.removeFriendController = controller;
    }
}
