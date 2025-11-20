package interface_adapter.logout;

import javax.swing.*;
import java.awt.event.*;

public class LogoutUI {
    private LogoutController controller;

    public LogoutUI(LogoutController controller) {
        this.controller = controller;
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Logout");
        JButton logoutButton = new JButton("Logout");
        JTextField usernameField = new JTextField(15);

        JLabel messageLabel = new JLabel("");
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                controller.logout(username);
                messageLabel.setText(controller.getViewModelMessage());
            }
        });
        JPanel panel = new JPanel();
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(logoutButton);
        panel.add(messageLabel);

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
