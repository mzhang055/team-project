package view;

import entities.MealType;
import interface_adapter.SessionManager;
import interface_adapter.dashboard.DashboardController;
import interface_adapter.log_meals.LogMealsController;
import interface_adapter.log_meals.LogMealsViewModel;
import data_access.MealDataAccessInterface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * View for logging meals manually.
 */
public class LogMealsView extends JFrame implements PropertyChangeListener {

    private final String viewName = "Log Meals";
    private final LogMealsViewModel viewModel;
    private final LogMealsController logMealsController;
    private final MealDataAccessInterface mealDataAccess;
    private final SessionManager sessionManager;
    private DashboardController dashboardController;

    private JTextArea resultArea;
    private JTextField foodNameField;
    private JComboBox<MealType> mealTypeComboBox;
    private JButton saveButton;
    private JPanel buttonPanel;

    public LogMealsView(LogMealsViewModel viewModel, LogMealsController logMealsController,
                        MealDataAccessInterface mealDataAccess, SessionManager sessionManager) {
        this.viewModel = viewModel;
        this.logMealsController = logMealsController;
        this.mealDataAccess = mealDataAccess;
        this.sessionManager = sessionManager;

        viewModel.addPropertyChangeListener(this);

        initializeUI();
    }

    /**
     * Set the dashboard controller to pass to LoggedMealsView
     *
     * @param dashboardController the dashboard controller
     */
    public void setDashboardController(DashboardController dashboardController) {
        this.dashboardController = dashboardController;
    }

    private void initializeUI() {
        setTitle("Log Meals - Manual Entry");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Top panel with input fields
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Meal Type:"));
        mealTypeComboBox = new JComboBox<>(MealType.values());
        inputPanel.add(mealTypeComboBox);

        inputPanel.add(new JLabel("Food Name:"));
        foodNameField = new JTextField();
        inputPanel.add(foodNameField);

        JButton fetchButton = new JButton("Fetch Nutrition");
        fetchButton.addActionListener(e -> fetchNutrition());
        inputPanel.add(new JLabel()); // Empty cell
        inputPanel.add(fetchButton);

        add(inputPanel, BorderLayout.NORTH);

        // Button panel at bottom
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        saveButton = new JButton("Save Meal");
        saveButton.setEnabled(false); // Disabled initially
        saveButton.addActionListener(e -> saveMeal());
        buttonPanel.add(saveButton);

        JButton viewMealsButton = new JButton("View All Logged Meals");
        viewMealsButton.addActionListener(e -> openViewLoggedMealsWindow());
        buttonPanel.add(viewMealsButton);

        // Center panel with results
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        resultArea.setText("Enter a food name and click 'Fetch Nutrition' to get nutrition info from CalorieNinjas API\n\n");

        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Allow Enter key to submit
        foodNameField.addActionListener(e -> fetchNutrition());
    }

    private void fetchNutrition() {
        String foodName = foodNameField.getText().trim();
        MealType mealType = (MealType) mealTypeComboBox.getSelectedItem();

        if (foodName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a food name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current logged-in user from the session
        String currentUser = sessionManager.getCurrentUsername();
        if (currentUser == null || currentUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must be logged in to log meals", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultArea.append("Fetching nutrition data for: " + foodName + "...\n");

        // Fetch nutrition without saving (will be stored in session by the presenter)
        logMealsController.fetchNutrition(foodName, mealType, currentUser);
    }

    private void saveMeal() {
        // Get the pending meal from the session (which was set by the presenter after fetch)
        if (!sessionManager.hasPendingMeal()) {
            JOptionPane.showMessageDialog(this, "Please fetch nutrition data first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Get the current logged-in user from the session
        String currentUser = sessionManager.getCurrentUsername();
        if (currentUser == null || currentUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must be logged in to save meals", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Save the meal
        logMealsController.saveMeal(sessionManager.getPendingMeal(), currentUser);
        saveButton.setEnabled(false); // Disable after saving
    }

    private void openViewLoggedMealsWindow() {
        // Get the current logged-in user from the session
        String currentUser = sessionManager.getCurrentUsername();
        if (currentUser == null || currentUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must be logged in to view meals", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LoggedMealsView viewLoggedMealsView = new LoggedMealsView(mealDataAccess, currentUser);
        // Pass the dashboard controller so edits can trigger dashboard refresh
        if (dashboardController != null) {
            viewLoggedMealsView.setDashboardController(dashboardController);
        }
        viewLoggedMealsView.setVisible(true);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (viewModel.getSuccessMessage() != null) {
            resultArea.append("\n" + viewModel.getSuccessMessage() + "\n");
            resultArea.append("=" + "=".repeat(60) + "\n\n");
            foodNameField.setText("");
            // Enable save button after successful fetch
            saveButton.setEnabled(true);
        } else if (viewModel.getErrorMessage() != null) {
            resultArea.append("ERROR: " + viewModel.getErrorMessage() + "\n\n");
            JOptionPane.showMessageDialog(this, viewModel.getErrorMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            // Disable save button on error
            saveButton.setEnabled(false);
        }
    }

    public String getViewName() {return viewName;}
}
