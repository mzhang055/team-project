package ui;

import interface_adapter.create_account.CreateAccountController;
import interface_adapter.create_account.CreateAccountViewModel;
import javax.swing.*;
import java.awt.*;

public class CreateAccountView extends JFrame {
    private final CreateAccountController createAccountController;
    private final CreateAccountViewModel createAccountViewModel;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JTextField heightField = new JTextField(10);
    private final JTextField weightField = new JTextField(10);
    private final JTextField allergiesField = new JTextField(15);
    private final JCheckBox veganCheckBox = new JCheckBox("Vegan");

    private final JLabel messageLabel = new JLabel(" ");
    public CreateAccountView(CreateAccountController createAccountController,
                             CreateAccountViewModel createAccountViewModel,
                             Runnable onCreateSuccess, Runnable onBackToLogin){
        super("Create Account");
        this.createAccountController = createAccountController;
        this.createAccountViewModel = createAccountViewModel;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 350);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        c.gridx = 0;
        c.gridy = row;
        panel.add(new JLabel("Username:"), c);
        c.gridx = 1;
        panel.add(usernameField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        panel.add(new JLabel("Password:"), c);
        c.gridx = 1;
        panel.add(passwordField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        panel.add(new JLabel("Height(cm):"), c);
        c.gridx = 1;
        panel.add(heightField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        panel.add(new JLabel("Weight(kg):"), c);
        c.gridx = 1;
        panel.add(weightField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        panel.add(new JLabel("Allergies:"), c);
        c.gridx = 1;
        panel.add(allergiesField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        panel.add(veganCheckBox, c);
        row ++;

        JButton createButton = new JButton("Create");
        JButton backButton = new JButton("Back to Login");

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        panel.add(createButton, c);
        c.gridx = 1;
        panel.add(backButton, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        panel.add(messageLabel, c);

        add(panel);

        createButton.addActionListener(e -> {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword());
            String allergies = allergiesField.getText().trim();
            boolean vegan = veganCheckBox.isSelected();

            double height = 0.0;
            double weight = 0.0;
            try {
                if (!heightField.getText().trim().isEmpty()){
                    height = Double.parseDouble(heightField.getText().trim());
                }
                if (!weightField.getText().trim().isEmpty()){
                    weight = Double.parseDouble(weightField.getText().trim());
                }
            }
            catch (NumberFormatException ex){
                messageLabel.setText("Height and weight must be numbers");
                return;
            }

            createAccountController.create(username, password, height, weight, allergies, vegan);
            messageLabel.setText(createAccountViewModel.getMessage());

            if (createAccountViewModel.isSuccess()){
                if (onCreateSuccess != null){
                    onCreateSuccess.run();
                }
            }
        });
        backButton.addActionListener(e -> {
            if (onBackToLogin != null){
                onBackToLogin.run();
            }
        });
    }
}
