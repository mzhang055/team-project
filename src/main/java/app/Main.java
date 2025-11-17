package app;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().createAndShowGUI());
    }

    private void createAndShowGUI(){

        JFrame mainFrame = new JFrame("Dashboard");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .setDashboardView()
                .addDashboardUseCase()
                .setLogMealsView()
                .addLogMealsUseCase()
                .build();

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }
}
