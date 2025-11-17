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

        // TODO: Fill w/ Info
        JPanel dashboard = new JPanel();
        mainFrame.add(dashboard, BorderLayout.CENTER);

        mainFrame.pack();
        mainFrame.setVisible(true);
    }
}
