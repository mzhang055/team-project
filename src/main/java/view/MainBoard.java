package view;

import javax.swing.*;
import java.awt.*;

public class MainBoard extends JFrame {
    private final JPanel contentPanel = new JPanel(new CardLayout());
    public JPanel getContentPanel;

    public MainBoard() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(null);
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }
}