package use_case.recipe_search;

import entities.Recipe;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for SearchRecipe.
 * Goal: 100% line coverage for this interactor.
 */
class RecipeSearchTest {

    private static class RecordingPresenter implements SearchRecipeOutputBoundary {
        SearchRecipeOutputData lastOutput;

        @Override
        public void present(SearchRecipeOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    @Test
    void testEmptyKeyword() {
        RecipeSearchDataAccessInterface dao = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("DAO should not be called when keyword is empty");
                return List.of();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be used in this interactor");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipeInteractor interactor = new SearchRecipeInteractor(dao, presenter);

        SearchRecipeInputData inputData = new SearchRecipeInputData("   ");

        interactor.execute(inputData);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("Recipe name cannot be empty.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }


    @Test
    void testNoRecipesFound() {
        RecipeSearchDataAccessInterface dao = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                assertEquals("pasta", name);
                return List.of();  // 空结果
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be used");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipeInteractor interactor = new SearchRecipeInteractor(dao, presenter);

        SearchRecipeInputData inputData = new SearchRecipeInputData("  pasta  ");

        interactor.execute(inputData);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No recipe matches.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testRecipesFound() {
        RecipeSearchDataAccessInterface dao = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                return Arrays.asList((Recipe) null, (Recipe) null);
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be used");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipeInteractor interactor = new SearchRecipeInteractor(dao, presenter);

        SearchRecipeInputData inputData = new SearchRecipeInputData("chicken");

        interactor.execute(inputData);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals(2, presenter.lastOutput.getRecipes().size());
        assertEquals("Found 2 recipe(s).", presenter.lastOutput.getMessage());
    }

    @Test
    void testIOExceptionFromDao() {
        RecipeSearchDataAccessInterface dao = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) throws IOException {
                throw new IOException("network error");
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be used");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipeInteractor interactor = new SearchRecipeInteractor(dao, presenter);

        SearchRecipeInputData inputData = new SearchRecipeInputData("rice");

        interactor.execute(inputData);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals(
                "Error while calling recipe API: network error",
                presenter.lastOutput.getMessage()
        );
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }

    @Test
    void testOutputDataWithNullRecipes() {
        SearchRecipeOutputData output =
                new SearchRecipeOutputData(null, "msg", false);

        assertNotNull(output.getRecipes());
        assertTrue(output.getRecipes().isEmpty());

        assertEquals("msg", output.getMessage());
        assertFalse(output.isSuccess());
    }

    @Test
    void testNullKeyword() {
        RecipeSearchDataAccessInterface dao = new RecipeSearchDataAccessInterface() {
            @Override
            public List<Recipe> searchRecipesByName(String name) {
                fail("DAO should not be called when keyword is null");
                return List.of();
            }

            @Override
            public Recipe getRecipeById(String id) {
                fail("getRecipeById should not be used");
                return null;
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        SearchRecipeInteractor interactor = new SearchRecipeInteractor(dao, presenter);

        SearchRecipeInputData inputData = new SearchRecipeInputData(null);

        interactor.execute(inputData);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("Recipe name cannot be empty.", presenter.lastOutput.getMessage());
        assertTrue(presenter.lastOutput.getRecipes().isEmpty());
    }
}