package view;

import interface_adapter.Navigation;
import interface_adapter.profile.ProfileController;
import interface_adapter.update_profile.UpdateProfileController;
import interface_adapter.update_profile.UpdateProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class UpdateProfileView extends JPanel implements PropertyChangeListener{
    private final String viewName =  "Update Profile";
    private final UpdateProfileViewModel updateProfileViewModel;
    private final Navigation navigation;
    private UpdateProfileController updateProfileController = null;

    // buttons that will click
    private final JButton updateButton = new JButton("Update");
    private final JButton backButton = new JButton("Back");

    public UpdateProfileView(UpdateProfileViewModel updateProfileViewModel, Navigation navigation) {
        this.updateProfileViewModel = updateProfileViewModel;
        this.navigation = navigation;

        setLayout(new BorderLayout(10, 10));

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        updateButton.setEnabled(false);
        updateButton.addActionListener(e -> updateProfile());
        buttonsPanel.add(updateButton);
        backButton.addActionListener(e -> goToProfile());
        buttonsPanel.add(backButton);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void updateProfile(){
    }

    private void goToProfile(){
        navigation.goTo("Profile");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt){

    }

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(UpdateProfileController updateProfileController) {
        this.updateProfileController = updateProfileController;
    }
}
