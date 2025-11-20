package view;

import entities.Recipe;
import interface_adapter.recipe.RecipeSearchController;
import interface_adapter.recipe.RecipeSearchViewModel;
import interface_adapter.recipe.SaveRecipeController;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RecipeSearchView extends JFrame {

    private final RecipeSearchController searchController;
    private final SaveRecipeController saveController;
    private final RecipeSearchViewModel viewModel;
    private final String username;
    private final RecipeMenuView menuView;

    private JTextField searchField;
    private JTextArea detailArea;
    private JButton saveButton;

    private DefaultListModel<String> listModel;
    private JList<String> resultList;

    public RecipeSearchView(RecipeSearchController searchController,
                            SaveRecipeController saveController,
                            RecipeSearchViewModel viewModel,
                            String username,
                            RecipeMenuView menuView) {
        super("Recipe Search");
        this.searchController = searchController;
        this.saveController = saveController;
        this.viewModel = viewModel;
        this.username = username;
        this.menuView = menuView;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 500);
        setLocationRelativeTo(null);

        initUI();
        refreshFromViewModel();
    }

    private void initUI() {
        searchField = new JTextField();
        JButton searchButton = new JButton("Search");

        JPanel topPanel = new JPanel(new BorderLayout(5, 5));
        topPanel.add(new JLabel("Recipe name: "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        listModel = new DefaultListModel<>();
        resultList = new JList<>(listModel);
        resultList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        detailArea = new JTextArea();
        detailArea.setEditable(false);
        detailArea.setLineWrap(true);
        detailArea.setWrapStyleWord(true);

        JScrollPane listScroll = new JScrollPane(resultList);
        JScrollPane detailScroll = new JScrollPane(detailArea);

        JSplitPane centerSplit = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT, listScroll, detailScroll);
        centerSplit.setDividerLocation(250);

        saveButton = new JButton("Save Recipe");
        JButton backButton = new JButton("Back");

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(backButton);
        bottomPanel.add(saveButton);

        Container content = getContentPane();
        content.setLayout(new BorderLayout(10, 10));
        content.add(topPanel, BorderLayout.NORTH);
        content.add(centerSplit, BorderLayout.CENTER);
        content.add(bottomPanel, BorderLayout.SOUTH);
        ((JComponent) content).setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        searchButton.addActionListener(e -> onSearch());
        saveButton.addActionListener(e -> onSave());
        backButton.addActionListener(e -> onBack());

        resultList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onSelectionChanged(resultList.getSelectedIndex());
            }
        });
    }

    private void onSearch() {
        String keyword = searchField.getText().trim();
        searchController.search(keyword);
        refreshFromViewModel();
    }

    private void onSave() {
        Recipe selected = viewModel.getSelectedRecipe();
        if (selected == null) {
            JOptionPane.showMessageDialog(this,
                    "No recipe selected.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        saveController.save(username, selected);

        JOptionPane.showMessageDialog(this,
                viewModel.getLastSaveMessage(),
                "Save Recipe",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void onBack() {
        if (menuView != null) {
            menuView.setVisible(true);
        }
        dispose();
    }

    private void onSelectionChanged(int index) {
        if (index < 0) {
            viewModel.setSelectedIndex(-1);
            detailArea.setText("");
            saveButton.setEnabled(false);
            return;
        }
        viewModel.setSelectedIndex(index);
        Recipe selected = viewModel.getSelectedRecipe();
        if (selected == null) {
            detailArea.setText("");
            saveButton.setEnabled(false);
        } else {
            detailArea.setText(formatRecipe(selected));
            saveButton.setEnabled(true);
        }
    }

    private void refreshFromViewModel() {
        listModel.clear();
        List<Recipe> recipes = viewModel.getRecipes();

        if (recipes.isEmpty()) {
            detailArea.setText(viewModel.getSearchResultText());
            saveButton.setEnabled(false);
            return;
        }

        for (Recipe r : recipes) {
            String summary = nullSafe(r.getName())
                    + " (" + nullSafe(r.getCategory())
                    + ", " + nullSafe(r.getArea()) + ")";
            listModel.addElement(summary);
        }

        int idx = viewModel.getSelectedIndex();
        if (idx < 0 || idx >= recipes.size()) {
            idx = 0;
            viewModel.setSelectedIndex(idx);
        }
        resultList.setSelectedIndex(idx);
        detailArea.setText(formatRecipe(recipes.get(idx)));
        saveButton.setEnabled(viewModel.isCanSave());
    }

    private String formatRecipe(Recipe r) {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ").append(nullSafe(r.getName())).append("\n");
        sb.append("Category: ").append(nullSafe(r.getCategory())).append("\n");
        sb.append("Area: ").append(nullSafe(r.getArea())).append("\n");
        sb.append("Image: ").append(nullSafe(r.getImageUrl())).append("\n\n");

        sb.append("Ingredients:\n");
        for (var ing : r.getIngredients()) {
            sb.append(" - ").append(ing).append("\n");
        }

        sb.append("\nInstructions:\n");
        sb.append(nullSafe(r.getInstructions())).append("\n");
        return sb.toString();
    }

    private String nullSafe(String s) {
        return s == null ? "" : s;
    }
}