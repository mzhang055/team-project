package view;

import interface_adapter.Navigation;
import interface_adapter.profile.ProfileController;
import interface_adapter.update_profile.UpdateProfileController;
import interface_adapter.update_profile.UpdateProfileState;
import interface_adapter.update_profile.UpdateProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UpdateProfileView extends JPanel implements PropertyChangeListener{
    private final String viewName = "Update Profile";
    private final UpdateProfileViewModel updateProfileViewModel;
    private final Navigation navigation;
    private UpdateProfileController updateProfileController = null;

    // field that will update
    private final JTextField heightField = new JTextField(10);
    private final JTextField weightField = new JTextField(10);
    private final JTextArea allergiesArea = new JTextArea(4, 15);

    // buttons that will click
    private final JButton updateButton = new JButton("Update");
    private final JButton backButton = new JButton("Back");
    private final JCheckBox veganCheck = new JCheckBox("Vegan?");

    public UpdateProfileView(UpdateProfileViewModel updateProfileViewModel, Navigation navigation) {
        this.updateProfileViewModel = updateProfileViewModel;
        this.navigation = navigation;
        this.updateProfileViewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Height:"), gbc);
        gbc.gridx = 1;
        formPanel.add(heightField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        formPanel.add(new JLabel("Weight:"), gbc);
        gbc.gridx = 1;
        formPanel.add(weightField, gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Allergies:"), gbc);
        gbc.gridx = 1;
        allergiesArea.setLineWrap(true);
        allergiesArea.setWrapStyleWord(true);
        JScrollPane scroll = new JScrollPane(allergiesArea);
        scroll.setPreferredSize(new Dimension(150, 80));
        formPanel.add(scroll, gbc);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        veganCheck.setHorizontalTextPosition(SwingConstants.LEFT);
        formPanel.add(veganCheck, gbc);

        add(formPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        updateButton.addActionListener(e -> updateProfile());
        buttonsPanel.add(updateButton);
        backButton.addActionListener(e -> goToProfile());
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void updateProfile(){
        String heightText = heightField.getText().trim();
        String weightText = weightField.getText().trim();

        try {
            double height = Double.parseDouble(heightText);
            double weight = Double.parseDouble(weightText);

            updateProfileController.updateProfile(
                    height,
                    weight,
                    allergiesArea.getText(),
                    veganCheck.isSelected()
            );

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Height and weight must be numbers.");
        }
    }

    private void goToProfile(){
        navigation.goTo("Profile");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if (!"state".equals(evt.getPropertyName())) return;
        UpdateProfileState state = (UpdateProfileState) evt.getNewValue();
        if (!state.getErrorMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (!state.getMessage().isEmpty()) {
            JOptionPane.showMessageDialog(this, state.getMessage(), "Success", JOptionPane.INFORMATION_MESSAGE);
        }
        updateButton.setEnabled(state.isUpdateEnabled());
    }

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(UpdateProfileController updateProfileController) {
        this.updateProfileController = updateProfileController;
    }
}
