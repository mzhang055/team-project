package view;

import interface_adapter.recipe.RecipeSavedController;
import interface_adapter.recipe.RecipeSavedViewModel;
import interface_adapter.recipe.RecipeSearchController;
import interface_adapter.recipe.RecipeSearchViewModel;
import interface_adapter.recipe.SaveRecipeController;

import javax.swing.*;
import java.awt.*;

public class RecipeMenuView extends JFrame {

    private final RecipeSearchController searchController;
    private final SaveRecipeController saveController;
    private final RecipeSearchViewModel searchViewModel;

    private final RecipeSavedController savedController;
    private final RecipeSavedViewModel savedViewModel;

    public RecipeMenuView(RecipeSearchController searchController,
                          SaveRecipeController saveController,
                          RecipeSearchViewModel searchViewModel,
                          RecipeSavedController savedController,
                          RecipeSavedViewModel savedViewModel) {
        super("Recipe");

        this.searchController = searchController;
        this.saveController = saveController;
        this.searchViewModel = searchViewModel;
        this.savedController = savedController;
        this.savedViewModel = savedViewModel;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 200);
        setLocationRelativeTo(null);

        JButton searchButton = new JButton("Recipe Search");
        JButton savedButton = new JButton("Recipe Saved");

        searchButton.addActionListener(e -> openSearchWindow());
        savedButton.addActionListener(e -> openSavedWindow());

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(searchButton);
        panel.add(savedButton);

        setContentPane(panel);
    }

    private void openSearchWindow() {
        RecipeSearchView view = new RecipeSearchView(
                searchController, saveController, searchViewModel);
        view.setVisible(true);
    }

    private void openSavedWindow() {
        RecipeSavedView view = new RecipeSavedView(
                savedController, savedViewModel);
        view.setVisible(true);
    }
}
