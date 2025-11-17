package view;

import interface_adapter.dashboard.DashboardState;
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
    private JLabel fiber = new JLabel("--");
    private JLabel sugars = new JLabel("--");

    // lists that will update
    private DefaultListModel<String> recipeModel;
    private DefaultListModel<String> friendModel;

    public DashboardView(DashboardViewModel dashboardViewModel, String userId) {
        this.dashboardViewModel = dashboardViewModel;
        this.userId = userId;
        this.dashboardViewModel.addPropertyChangeListener(this);

        this.setSize(900, 600);
        this.setLayout(new BorderLayout(10, 10));

        recipeModel = new DefaultListModel<>();
        JList<String> recipeList = new JList<>(recipeModel);
        this.add(new JScrollPane(recipeList), BorderLayout.WEST);

        friendModel = new DefaultListModel<>();
        JList<String> friendList = new JList<>(friendModel);
        this.add(new JScrollPane(friendList), BorderLayout.EAST);

        JPanel nutrition = new JPanel();
        nutrition.setLayout(new BoxLayout(nutrition, BoxLayout.Y_AXIS));

        JPanel caloriesRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        caloriesRow.add(new JLabel("Calories:"));
        caloriesRow.add(this.calories);
        nutrition.add(caloriesRow);

        JPanel remainingRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        remainingRow.add(new JLabel("Remaining:"));
        remainingRow.add(this.remaining);
        nutrition.add(remainingRow);

        JPanel proteinRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        proteinRow.add(new JLabel("Protein:"));
        proteinRow.add(this.protein);
        nutrition.add(proteinRow);

        JPanel carbsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        carbsRow.add(new JLabel("Carbs:"));
        carbsRow.add(this.carbs);
        nutrition.add(carbsRow);

        JPanel fatsRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fatsRow.add(new JLabel("Fats:"));
        fatsRow.add(this.fats);
        nutrition.add(fatsRow);

        JPanel fiberRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fiberRow.add(new JLabel("Fiber:"));
        fiberRow.add(this.fiber);
        nutrition.add(fiberRow);

        JPanel sugarRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sugarRow.add(new JLabel("Sugars:"));
        sugarRow.add(this.sugars);
        nutrition.add(sugarRow);

        this.add(nutrition, BorderLayout.CENTER);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // initalize the buttons
        setTargetButton = new JButton("Set Target");
        logMealsButton = new JButton("Log Meals");
        saveRecipeButton= new JButton("Save Recipe");
        profileButton = new JButton("Profile");
        viewHistoryButton = new JButton("View History");
        // link buttons to the view
        setTargetButton.addActionListener(e -> goToSetTarget());
        logMealsButton.addActionListener(e -> goToLogMeals() );
        saveRecipeButton.addActionListener(e -> goToSaveRecipe());
        viewHistoryButton.addActionListener(e -> goToHistory());
        profileButton.addActionListener(e -> goToProfile());
        // add the buttons
        buttons.add(setTargetButton);
        buttons.add(logMealsButton);
        buttons.add(saveRecipeButton);
        buttons.add(viewHistoryButton);
        buttons.add(profileButton);
        // add to Dashboard
        this.add(buttons, BorderLayout.SOUTH);
    }

    // TODO: Change Views
    private void goToSetTarget(){}
    private void goToLogMeals(){}
    private void goToSaveRecipe(){}
    private void goToHistory(){}
    private void goToProfile(){}

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            DashboardState state = (DashboardState) evt.getNewValue();
            calories.setText(String.valueOf(state.getCalories()));
            remaining.setText(String.valueOf(state.getRemaining()));
            protein.setText(String.valueOf(state.getProtein()));
            carbs.setText(String.valueOf(state.getCarbs()));
            fats.setText(String.valueOf(state.getFats()));
            fiber.setText(String.valueOf(state.getFibers()));
            sugars.setText(String.valueOf(state.getSugars()));

            recipeModel.clear();
            recipeModel.addAll(state.getRecipeNames());

            friendModel.clear();
            friendModel.addAll(state.getFriendNames());
        }
    }
}
