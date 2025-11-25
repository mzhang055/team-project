package view;

import interface_adapter.create_account.CreateAccountController;
import interface_adapter.create_account.CreateAccountViewModel;
import javax.swing.*;
import java.awt.*;

public class CreateAccountView extends JPanel {
    private final CreateAccountController controller;
    private final CreateAccountViewModel viewModel;
    private final ViewManager viewManager;

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JTextField heightField = new JTextField(10);
    private final JTextField weightField = new JTextField(10);
    private final JTextField allergiesField = new JTextField(15);
    private final JCheckBox veganCheckBox = new JCheckBox("Vegan");

    private final JLabel messageLabel = new JLabel(" ");

    public CreateAccountView(CreateAccountController controller,
                             CreateAccountViewModel viewModel, ViewManager viewManager) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewManager = viewManager;

        initUI();
    }

    private void initUI(){
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Username:"), c);
        c.gridx = 1;
        add(usernameField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Password:"), c);
        c.gridx = 1;
        add(passwordField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Height(cm):"), c);
        c.gridx = 1;
        add(heightField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Weight(kg):"), c);
        c.gridx = 1;
        add(weightField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        add(new JLabel("Allergies:"), c);
        c.gridx = 1;
        add(allergiesField, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        add(veganCheckBox, c);
        row ++;

        JButton createButton = new JButton("Create");
        JButton backButton = new JButton("Back to Login");

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        add(createButton, c);
        c.gridx = 1;
        add(backButton, c);
        row ++;

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        messageLabel.setForeground(Color.RED);
        add(messageLabel, c);

        createButton.addActionListener(e -> onCreate());
        backButton.addActionListener(e -> viewManager.showLoginView());
        //TODO
    }

    private void onCreate(){
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
        } catch (NumberFormatException ex){
            messageLabel.setText("Height and weight must be numeric");
            return;
        }

        controller.create(username, password, height, weight, allergies, vegan);

        messageLabel.setText(viewModel.getMessage());

        if (viewModel.isSuccess()){
            viewManager.showLoginView();
            //TODO
        }
    }
}
