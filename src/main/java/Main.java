import javax.swing.*;
import java.awt.*;

public class Main {
    // TODO: Review where to place main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI(){

        JFrame mainFrame = new JFrame("Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // TODO: Fill w/ Info
        JPanel dashboard = new JPanel();
        mainFrame.add(dashboard, BorderLayout.CENTER);

        // TODO: Fill w/ Info
        JScrollPane leaderboard = new JScrollPane();
        mainFrame.add(leaderboard, BorderLayout.EAST);

        // TODO: Link to Other Windows
        JPanel navBar = new JPanel(new FlowLayout(FlowLayout.CENTER));
        String[] navLabels = {"Set Target", "Log Meal", "Save Recipes", "Profile"};
        for (String label : navLabels) {
            JButton button = new JButton(label);
            navBar.add(button);
        }
        mainFrame.add(navBar, BorderLayout.SOUTH);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
