package app;

import entities.Goal;
import use_case.goals.GoalService;
import use_case.goals.GoalStore;
import view.GoalView.GoalSummaryPanel;
import view.GoalView.SetGoalPanel;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class GoalsGuiMain {

    private final GoalService service;
    private final String userId = "u1"; // demo user id

    private CardLayout layout;
    private JPanel rootPanel;
    private GoalSummaryPanel summaryPanel;

    public GoalsGuiMain(GoalService service) {
        this.service = service;
    }

    private void initAndShow() {
        layout = new CardLayout();
        rootPanel = new JPanel(layout);

        summaryPanel = new GoalSummaryPanel(service, userId);
        SetGoalPanel setGoalPanel = new SetGoalPanel(service, userId, goal -> {
            summaryPanel.showGoal(goal);
            layout.show(rootPanel, "SUMMARY");
        });

        rootPanel.add(setGoalPanel, "SET");
        rootPanel.add(summaryPanel, "SUMMARY");

        JButton showActiveGoalsButton = new JButton("Show Active Goals");
        showActiveGoalsButton.addActionListener(e -> onShowActiveGoals());

        JPanel topBar = new JPanel(new BorderLayout());
        JPanel rightBox = new JPanel();
        rightBox.setLayout(new FlowLayout(FlowLayout.RIGHT));
        rightBox.add(showActiveGoalsButton);
        topBar.add(rightBox, BorderLayout.NORTH);

        JPanel main = new JPanel(new BorderLayout());
        main.add(topBar, BorderLayout.NORTH);
        main.add(rootPanel, BorderLayout.CENTER);

        JFrame frame = new JFrame("Nutrition Dashboard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(main);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        layout.show(rootPanel, "SET");
    }

    private void onShowActiveGoals() {
        LocalDate today = LocalDate.now();
        var activeOpt = service.activeOn(userId, today);
        String message;
        if (activeOpt.isPresent()) {
            Goal g = activeOpt.get();
            message = String.format(
                    "Active goal for %s:%n" +
                    "- Calories: %d%n" +
                    "- Protein: %d g%n" +
                    "- Carbs: %d g%n" +
                    "- Fats: %d g%n" +
                    "- Start: %s%n" +
                    "- End: %s",
                    today,
                    g.getCalories(),
                    g.getProtein(),
                    g.getCarbs(),
                    g.getFats(),
                    g.getStartDate(),
                    g.getEndDate() == null ? "(open-ended)" : g.getEndDate().toString()
            );
        } else {
            message = "There is no active goal for today. Set one first on the main page.";
        }

        JOptionPane.showMessageDialog(
                null,
                message,
                "Active Goals",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {
        GoalStore store = new GoalStore();
        GoalService service = new GoalService(store);

        SwingUtilities.invokeLater(() -> {
            GoalsGuiMain app = new GoalsGuiMain(service);
            app.initAndShow();
        });
    }
}
