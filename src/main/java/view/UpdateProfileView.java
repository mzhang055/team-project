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

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel heightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heightPanel.add(new JLabel("Height:"));
        heightPanel.add(heightField);
        formPanel.add(heightPanel);

        JPanel weightPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        weightPanel.add(new JLabel("Weight:"));
        weightPanel.add(weightField);
        formPanel.add(weightPanel);

        JPanel allergiesPanel = new JPanel(new BorderLayout());
        allergiesPanel.add(new JLabel("Allergies:"), BorderLayout.NORTH);
        allergiesArea.setLineWrap(true);
        allergiesArea.setWrapStyleWord(true);
        allergiesPanel.add(new JScrollPane(allergiesArea), BorderLayout.CENTER);
        formPanel.add(allergiesPanel);

        JPanel veganPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        veganPanel.add(veganCheck);
        formPanel.add(veganPanel);

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
