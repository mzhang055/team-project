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

        JLabel titleLabel = new JLabel("Your Profile");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        titlePanel.add(titleLabel);
        this.add(titlePanel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new GridLayout(0, 2, 10, 10)); // 2 columns: label + value
        infoPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder("Profile Info"),
                BorderFactory.createEmptyBorder(20, 20, 20, 20) // padding inside the box
        ));
        infoPanel.setPreferredSize(new Dimension(400, 400));

        infoPanel.add(new JLabel("Username:"));
        infoPanel.add(this.username);

        infoPanel.add(new JLabel("Password:"));
        infoPanel.add(this.password);

        infoPanel.add(new JLabel("Height:"));
        infoPanel.add(this.height);

        infoPanel.add(new JLabel("Weight:"));
        infoPanel.add(this.weight);

        infoPanel.add(new JLabel("Allergies:"));
        infoPanel.add(this.allergies);

        infoPanel.add(new JLabel("Vegan:"));
        infoPanel.add(this.vegan);

        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(infoPanel);
        add(centerWrapper, BorderLayout.CENTER);

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
