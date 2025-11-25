package app;

import data_access.TheMealDBRecipeDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import interface_adapter.recipe.*;
import use_case.recipe_log.DeleteSavedRecipeInputBoundary;
import use_case.recipe_log.DeleteSavedRecipeInteractor;
import use_case.recipe_log.GetSavedRecipesInputBoundary;
import use_case.recipe_log.GetSavedRecipesInteractor;
import use_case.recipe_log.SaveRecipeInputBoundary;
import use_case.recipe_log.SaveRecipeInteractor;
import use_case.recipe_search.RecipeSearchDataAccessInterface;
import use_case.recipe_search.SearchRecipeInputBoundary;
import use_case.recipe_search.SearchRecipeInteractor;
import use_case.recipe_search.SearchRecipeOutputBoundary;
import view.RecipeMenuView;

public class RecipeModuleBuilder {

    private final UserDataAccessInterface userDataAccess;

    public RecipeModuleBuilder(UserDataAccessInterface userDataAccess) {
        this.userDataAccess = userDataAccess;
    }

    public RecipeMenuView buildRecipeMenuView(String username) {
        try {
            RecipeSearchDataAccessInterface searchDAO =
                    new TheMealDBRecipeDataAccessObject();

            RecipeSearchViewModel searchVM = new RecipeSearchViewModel();
            RecipeSavedViewModel savedVM  = new RecipeSavedViewModel();

            SearchRecipeOutputBoundary searchPresenter =
                    new RecipeSearchPresenter(searchVM);
            SaveRecipePresenter savePresenter =
                    new SaveRecipePresenter(searchVM);
            RecipeSavedPresenter savedPresenter =
                    new RecipeSavedPresenter(savedVM);

            SearchRecipeInputBoundary searchInteractor =
                    new SearchRecipeInteractor(searchDAO, searchPresenter);

            SaveRecipeInputBoundary saveInteractor =
                    new SaveRecipeInteractor(userDataAccess, savePresenter);

            GetSavedRecipesInputBoundary getSavedInteractor =
                    new GetSavedRecipesInteractor(userDataAccess, searchDAO, savedPresenter);

            DeleteSavedRecipeInputBoundary deleteSavedInteractor =
                    new DeleteSavedRecipeInteractor(userDataAccess, savedPresenter);

            RecipeSearchController searchController =
                    new RecipeSearchController(searchInteractor);

            SaveRecipeController saveController =
                    new SaveRecipeController(saveInteractor);

            RecipeSavedController savedController =
                    new RecipeSavedController(getSavedInteractor, deleteSavedInteractor);

            return new RecipeMenuView(
                    searchController,
                    saveController,
                    searchVM,
                    savedController,
                    savedVM,
                    username
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to build Recipe module", e);
        }
    }
}
