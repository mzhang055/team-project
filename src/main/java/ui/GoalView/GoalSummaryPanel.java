package ui.GoalView;

import entities.Goal;
import use_case.goals.GoalService;
import use_case.goals.UpdateGoalRequest;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class GoalSummaryPanel extends JPanel {

    private final GoalService service;
    private final String userId;

    private Goal currentGoal;

    private final JTextField caloriesField = new JTextField(10);
    private final JTextField proteinField  = new JTextField(10);
    private final JTextField carbsField    = new JTextField(10);
    private final JTextField fatsField     = new JTextField(10);
    private final JTextField startField    = new JTextField(10);
    private final JTextField endField      = new JTextField(10);

    private final JButton editButton       = new JButton("Edit");
    private final JButton saveButton       = new JButton("Save Changes");
    private final JLabel statusLabel       = new JLabel(" ");

    public GoalSummaryPanel(GoalService service, String userId) {
        this.service = service;
        this.userId = userId;
        initLayout();
        loadActiveGoal();
    }

    private void initLayout() {
        setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addRow(c, row++, "Calories:", caloriesField);
        addRow(c, row++, "Protein (g):", proteinField);
        addRow(c, row++, "Carbs (g):", carbsField);
        addRow(c, row++, "Fats (g):", fatsField);
        addRow(c, row++, "Start date:", startField);
        addRow(c, row++, "End date:", endField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);

        editButton.addActionListener(e -> setEditing(true));
        saveButton.addActionListener(e -> onSave());

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        add(buttonPanel, c);

        row++;
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        statusLabel.setForeground(Color.DARK_GRAY);
        add(statusLabel, c);

        setEditing(false);
    }

    private void addRow(GridBagConstraints c, int row, String label, JTextField field) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        add(new JLabel(label), c);

        c.gridx = 1;
        add(field, c);
    }

    private void setEditing(boolean editing) {
        caloriesField.setEditable(editing);
        proteinField.setEditable(editing);
        carbsField.setEditable(editing);
        fatsField.setEditable(editing);

        startField.setEditable(false);
        endField.setEditable(false);
    }


    public void showGoal(Goal goal) {
        this.currentGoal = goal;
        populateFields();
        statusLabel.setText("Goal loaded (id=" + goal.getId() + "). Click Edit to modify.");
    }

    private void loadActiveGoal() {
        service.activeOn(userId, LocalDate.now()).ifPresent(this::showGoal);
    }

    private void populateFields() {
        if (currentGoal == null) {
            return;
        }
        caloriesField.setText(String.valueOf(currentGoal.getCalories()));
        proteinField.setText(String.valueOf(currentGoal.getProtein()));
        carbsField.setText(String.valueOf(currentGoal.getCarbs()));
        fatsField.setText(String.valueOf(currentGoal.getFats()));
        startField.setText(currentGoal.getStartDate().toString());
        endField.setText(currentGoal.getEndDate() == null ? "" : currentGoal.getEndDate().toString());
        setEditing(false);
    }

    private void onSave() {
        if (currentGoal == null) {
            statusLabel.setText("No goal to update.");
            return;
        }
        try {
            int calories = Integer.parseInt(caloriesField.getText().trim());
            int protein  = Integer.parseInt(proteinField.getText().trim());
            int carbs    = Integer.parseInt(carbsField.getText().trim());
            int fats     = Integer.parseInt(fatsField.getText().trim());

            UpdateGoalRequest updateReq = new UpdateGoalRequest(
                    calories,
                    protein,
                    carbs,
                    fats,
                    null,
                    null
            );

            Goal updated = service.update(currentGoal.getId(), userId, updateReq);
            currentGoal = updated;
            populateFields();
            statusLabel.setText("Goal updated.");
        } catch (NumberFormatException ex) {
            statusLabel.setText("Please enter valid integers.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }
}
