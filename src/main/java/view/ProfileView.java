package view;

import interface_adapter.Navigation;
import interface_adapter.profile.ProfileController;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ProfileView extends JPanel implements PropertyChangeListener {
    private final String viewName =  "Profile";
    private final ProfileViewModel profileViewModel;
    private final Navigation navigation;
    private ProfileController profileController = null;

    // buttons that will click
    private final JButton updateProfileButton = new JButton("Update Profile");
    private final JButton backButton = new JButton("Back");

    // labels that will update
    private JLabel username = new JLabel("--");
    private JLabel password = new JLabel("--");
    private JLabel height = new JLabel("--");
    private JLabel weight = new JLabel("--");
    private JLabel allergies = new JLabel("--");
    private JLabel vegan = new JLabel("--");

    public ProfileView(ProfileViewModel profileViewModel, Navigation navigation) {
        this.profileViewModel = profileViewModel;
        this.profileViewModel.addPropertyChangeListener(this);
        this.navigation = navigation;

        setLayout(new BorderLayout(10, 10));

        JPanel labelsPanel = new JPanel();
        labelsPanel.setLayout(new BoxLayout(labelsPanel, BoxLayout.Y_AXIS));

        JPanel usernameRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        usernameRow.add(new JLabel("Username:"));
        usernameRow.add(this.username);
        labelsPanel.add(usernameRow);

        JPanel passwordRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        passwordRow.add(new JLabel("Password:"));
        passwordRow.add(this.password);
        labelsPanel.add(passwordRow);

        JPanel heightRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        heightRow.add(new JLabel("Height:"));
        heightRow.add(this.height);
        labelsPanel.add(heightRow);

        JPanel weightRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        weightRow.add(new JLabel("Weight:"));
        weightRow.add(this.weight);
        labelsPanel.add(weightRow);

        JPanel allergiesRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        allergiesRow.add(new JLabel("Allergies:"));
        allergiesRow.add(this.allergies);
        labelsPanel.add(allergiesRow);

        JPanel veganRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        veganRow.add(new JLabel("Vegan:"));
        veganRow.add(this.vegan);
        labelsPanel.add(veganRow);

        add(labelsPanel, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));

        updateProfileButton.addActionListener(e -> goToUpdateProfile());
        backButton.addActionListener(e -> returnToDashboard());

        buttonsPanel.add(updateProfileButton);
        buttonsPanel.add(backButton);

        add(buttonsPanel, BorderLayout.SOUTH);
    }

    private void returnToDashboard(){
        navigation.goTo("Dashboard");
    }

    private void goToUpdateProfile(){navigation.goTo("Update Profile");}

    @Override
    public void propertyChange(PropertyChangeEvent evt){
        if (evt.getPropertyName().equals("state")){
            ProfileState state = (ProfileState) evt.getNewValue();
            username.setText(state.getUsername());
            password.setText(state.getPassword());
            height.setText(String.valueOf(state.getHeight()));
            weight.setText(String.valueOf(state.getWeight()));
            allergies.setText(state.getAllergies());
            vegan.setText(String.valueOf(state.getVegan()));
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }
}
