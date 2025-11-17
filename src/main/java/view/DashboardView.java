package view;

import interface_adapter.dashboard.DashboardController;
import interface_adapter.dashboard.DashboardViewModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is logged into the program.
 */
public class DashboardView extends JPanel implements PropertyChangeListener{
    private final DashboardViewModel dashboardViewModel;
    private DashboardController dashboardController;
    private final String userId;

    // buttons that will click
    private final JButton setTargetButton;
    private final JButton logMealsButton;
    private final JButton saveRecipeButton;
    private final JButton profileButton;
    private final JButton viewHistoryButton;

    // labels that will update
    private JLabel calories = new JLabel("--");
    private JLabel remaining = new JLabel("--");
    private JLabel protein = new JLabel("--");
    private JLabel carbs = new JLabel("--");
    private JLabel fats =new JLabel("--");

    // lists that will update
    private DefaultListModel<String> recipeModel;
    private DefaultListModel<String> friendModel;

    public DashboardView(DashboardViewModel dashboardViewModel, DashboardController dashboardController, String userId) {
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardController = dashboardController;
        this.userId = userId;
        this.dashboardViewModel.addPropertyChangeListener(this);

        // TODO: Set title & sizing
        this.setLayout(new BorderLayout());

        recipeModel = new DefaultListModel<>();
        JList<String> recipeList = new JList<>(recipeModel);
        this.add(new JScrollPane(recipeList), BorderLayout.WEST);

        friendModel = new DefaultListModel<>();
        JList<String> friendList = new JList<>(friendModel);
        this.add(new JScrollPane(friendList), BorderLayout.EAST);

        JPanel nutrition = new JPanel();
        nutrition.setLayout(new BoxLayout(nutrition, BoxLayout.Y_AXIS));
        nutrition.add(new JLabel("Calories:"));
        nutrition.add(new JLabel("Remaining:"));
        nutrition.add(new JLabel("Protein:"));
        nutrition.add(new JLabel("Carbs:"));
        nutrition.add(new JLabel("Fats:"));
        nutrition.add(calories);
        nutrition.add(remaining);
        nutrition.add(protein);
        nutrition.add(carbs);
        nutrition.add(fats);
        this.add(nutrition, BorderLayout.CENTER);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // initalize the buttons
        setTargetButton = new JButton("Set Target");
        logMealsButton = new JButton("Log Meals");
        saveRecipeButton= new JButton("Save Recipe");
        profileButton = new JButton("Profile");
        viewHistoryButton = new JButton("View History");
        // link buttons to the controller
        setTargetButton.addActionListener(e -> dashboardController.goToSetTarget());
        logMealsButton.addActionListener(e -> dashboardController.goToLogMeals() );
        saveRecipeButton.addActionListener(e -> dashboardController.goToSaveRecipe());
        viewHistoryButton.addActionListener(e -> dashboardController.goToHistory());
        profileButton.addActionListener(e -> dashboardController.goToProfile());
        // add the buttons
        buttons.add(setTargetButton);
        buttons.add(logMealsButton);
        buttons.add(saveRecipeButton);
        buttons.add(viewHistoryButton);
        buttons.add(profileButton);
        // add to Dashboard
        this.add(buttons, BorderLayout.SOUTH);
    }

    // TODO: Override Property Change
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

}
