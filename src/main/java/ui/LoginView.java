package ui;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginViewModel;
import javax.swing.*;
import java.awt.*;

public class LoginView extends JFrame {
    private final LoginController loginController;
    private final LoginViewModel loginViewModel;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JLabel messageLabel = new JLabel(" ");

    public LoginView(LoginController loginController, LoginViewModel loginViewModel,
                     Runnable onLoginSuccess, Runnable onGoToCreateAccount){
        super("Login");
        this.loginController = loginController;
        this.loginViewModel = loginViewModel;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        panel.add(new JLabel("Username:"), c);
        c.gridx = 1;
        panel.add(usernameField, c);

        c.gridx = 0;
        c.gridy = 1;
        panel.add(new JLabel("Password:"), c);
        c.gridx = 1;
        panel.add(passwordField, c);

        JButton loginButton = new JButton("Log in");
        JButton createAccountButton = new JButton("Create account");

        c.gridx = 0;
        c.gridy = 2;
        panel.add(loginButton, c);
        c.gridx = 1;
        panel.add(createAccountButton, c);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, c);
        add(panel);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());

            loginController.login(username, password);

            messageLabel.setText(loginViewModel.getMessage());
            if (loginViewModel.isSuccess()){
                if (onLoginSuccess != null){
                    onLoginSuccess.run();
                }
            }
        });
        createAccountButton.addActionListener(e -> {
            if (onGoToCreateAccount != null){
                onGoToCreateAccount.run();
            }
        });
    }
}
