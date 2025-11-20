package ui.GoalView;

import entities.Goal;
import use_case.goals.CreateGoalRequest;
import use_case.goals.GoalCreatedListener;
import use_case.goals.GoalService;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class SetGoalPanel extends JPanel {

    private final GoalService service;
    private final String userId;
    private final GoalCreatedListener listener;

    private final JTextField caloriesField = new JTextField(10);
    private final JTextField proteinField  = new JTextField(10);
    private final JTextField carbsField    = new JTextField(10);
    private final JTextField fatsField     = new JTextField(10);
    private final JLabel statusLabel       = new JLabel(" ");

    public SetGoalPanel(GoalService service, String userId, GoalCreatedListener listener) {
        this.service = service;
        this.userId = userId;
        this.listener = listener;
        initLayout();
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

        JButton saveButton = new JButton("Set Goal");
        saveButton.addActionListener(e -> onSave());

        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        add(saveButton, c);

        row++;
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 2;
        statusLabel.setForeground(Color.DARK_GRAY);
        add(statusLabel, c);
    }

    private void addRow(GridBagConstraints c, int row, String label, JTextField field) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        add(new JLabel(label), c);

        c.gridx = 1;
        add(field, c);
    }

    private void onSave() {
        try {
            int calories = Integer.parseInt(caloriesField.getText().trim());
            int protein  = Integer.parseInt(proteinField.getText().trim());
            int carbs    = Integer.parseInt(carbsField.getText().trim());
            int fats     = Integer.parseInt(fatsField.getText().trim());

            CreateGoalRequest req = new CreateGoalRequest(
                    userId,
                    calories,
                    protein,
                    carbs,
                    fats,
                    LocalDate.now(),
                    null
            );

            Goal created = service.create(req);
            statusLabel.setText("Goal created. Navigating to summary...");
            if (listener != null) {
                listener.onGoalCreated(created);
            }

        } catch (NumberFormatException ex) {
            statusLabel.setText("Please enter valid integers.");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            statusLabel.setText("Error: " + ex.getMessage());
        }
    }
}
