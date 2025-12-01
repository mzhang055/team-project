package view;

import interface_adapter.Navigation;
import interface_adapter.dashboard.DashboardController;
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
    private final String viewName =  "Dashboard";
    private final DashboardViewModel dashboardViewModel;
    private final Navigation navigation;
    private DashboardController dashboardController = null;
    private RecipeMenuView recipeMenuView;
    private LogMealsView logMealsView = null;

    // buttons that will click
    private final JButton setTargetButton;
    private final JButton logMealsButton;
    private final JButton saveRecipeButton;
    private final JButton profileButton;
    private final JButton addFriendsButton;
    private final JButton leaderboardButton;
    private final JButton logoutButton;

    // labels that will update
    // might be final
    private JLabel calories = new JLabel("--");
    private JLabel remaining = new JLabel("--");
    private JLabel protein = new JLabel("--");
    private JLabel carbs = new JLabel("--");
    private JLabel fats = new JLabel("--");
    private JLabel fiber = new JLabel("--");
    private JLabel sugars = new JLabel("--");

    // lists that will update
    private DefaultListModel<String> recipeModel;
    private DefaultListModel<String> friendModel;

    public DashboardView(DashboardViewModel dashboardViewModel, Navigation navigation, RecipeMenuView recipeMenuView) {
        this.dashboardViewModel = dashboardViewModel;
        this.dashboardViewModel.addPropertyChangeListener(this);
        this.navigation = navigation;
        this.recipeMenuView = recipeMenuView;

        this.setSize(900, 600);
        this.setLayout(new BorderLayout(10, 10));

        recipeModel = new DefaultListModel<>();
        JList<String> recipeList = new JList<>(recipeModel);
        recipeList.setVisibleRowCount(15);
        JScrollPane recipeScroll = new JScrollPane(recipeList);
        recipeScroll.setPreferredSize(new Dimension(200, 400));
        recipeScroll.setMinimumSize(new Dimension(200, 400));
        this.add(recipeScroll, BorderLayout.WEST);

        friendModel = new DefaultListModel<>();
        JList<String> friendList = new JList<>(friendModel);
        friendList.setVisibleRowCount(15);
        JScrollPane friendScroll = new JScrollPane(friendList);
        friendScroll.setPreferredSize(new Dimension(200, 400));
        friendScroll.setMinimumSize(new Dimension(200, 400));
        this.add(friendScroll, BorderLayout.EAST);

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

        nutrition.setPreferredSize(new Dimension(400, 400));
        nutrition.setMinimumSize(new Dimension(300, 300));
        this.add(nutrition, BorderLayout.CENTER);

        final JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        // initalize the buttons
        setTargetButton = new JButton("Set Target");
        logMealsButton = new JButton("Log Meals");
        saveRecipeButton= new JButton("Recipe");
        addFriendsButton = new JButton("Add Friends");
        profileButton = new JButton("Profile");
        leaderboardButton = new JButton("Leaderboard");
        logoutButton = new JButton("Logout");
        // link buttons to the view
        setTargetButton.setEnabled(false);
        setTargetButton.addActionListener(e -> goToSetTarget());
        logMealsButton.addActionListener(e -> goToLogMeals() );
        saveRecipeButton.addActionListener(e -> goToSaveRecipe());
        addFriendsButton.addActionListener(e -> goToAddFriends());
        leaderboardButton.setEnabled(false);
        leaderboardButton.addActionListener(e -> goToLeaderboard());
        profileButton.addActionListener(e -> goToProfile());
        logoutButton.setEnabled(false);
        logoutButton.addActionListener(e -> goToLogout());
        // add the buttons
        buttons.add(setTargetButton);
        buttons.add(logMealsButton);
        buttons.add(saveRecipeButton);
        buttons.add(addFriendsButton);
        buttons.add(leaderboardButton);
        buttons.add(profileButton);
        buttons.add(logoutButton);
        // add to Dashboard
        buttons.setPreferredSize(new Dimension(900, 50));
        buttons.setMinimumSize(new Dimension(800, 50));
        this.add(buttons, BorderLayout.SOUTH);
    }

    private void goToSetTarget(){
    }
    private void goToLogMeals(){
        if (logMealsView != null) {
            logMealsView.setVisible(true);
        }
    }
    private void goToSaveRecipe(){
        if (recipeMenuView != null) {
            recipeMenuView.setVisible(true);
        }
    }

    private void goToAddFriends(){navigation.goTo("Add Friend");}

    private void goToProfile(){
        navigation.goTo("Profile");
    }

    // TODO: refer to navigation & how login & addFriends are added
    private void goToLeaderboard(){}

    private void goToLogout(){}

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

    public String getViewName() {
        return viewName;
    }

    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    public void setRecipeMenuView(RecipeMenuView recipeMenuView) {
        this.recipeMenuView = recipeMenuView;
    }

    public void setLogMealsView(LogMealsView logMealsView) {
        this.logMealsView = logMealsView;
    }
}
