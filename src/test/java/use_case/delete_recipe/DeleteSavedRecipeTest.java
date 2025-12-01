package use_case.delete_recipe;

import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DeleteSavedRecipeInteractor.
 * This class is written without any mocking framework.
 * It aims for 100% line and branch coverage of:
 *  - DeleteSavedRecipeInteractor
 *  - DeleteSavedRecipeInputData
 *  - DeleteSavedRecipeOutputData
 */
public class DeleteSavedRecipeTest {
    private static class RecordingPresenter implements DeleteSavedRecipeOutputBoundary {
        DeleteSavedRecipeOutputData lastOutput;

        @Override
        public void present(DeleteSavedRecipeOutputData outputData) {
            this.lastOutput = outputData;
        }
    }

    private User createUser(String username) {
        return new User(username, "password");
    }

    @Test
    void testNullUsername() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when username is null");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when username is null");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when username is null");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData(null, "recipeId");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No user logged in.", presenter.lastOutput.getMessage());
    }

    @Test
    void testBlankUsername() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when username is blank");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when username is blank");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when username is blank");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("   ", "recipeId");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No user logged in.", presenter.lastOutput.getMessage());
    }

    @Test
    void testNullRecipeId() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when recipeId is null");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when recipeId is null");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when recipeId is null");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("alice", null);

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No recipe selected.", presenter.lastOutput.getMessage());
    }

    @Test
    void testBlankRecipeId() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                fail("existsByUsername should not be called when recipeId is blank");
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when recipeId is blank");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when recipeId is blank");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("alice", "   ");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("No recipe selected.", presenter.lastOutput.getMessage());
    }

    @Test
    void testUserDoesNotExist() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return false;
            }

            @Override
            public User getUser(String username) {
                fail("getUser should not be called when user does not exist");
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when user does not exist");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("bob", "r1");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("User not found.", presenter.lastOutput.getMessage());
    }

    @Test
    void testUserNullFromDAO() {
        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return true;
            }

            @Override
            public User getUser(String username) {
                return null;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when getUser returns null");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("charlie", "r1");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("User not found.", presenter.lastOutput.getMessage());
    }

    @Test
    void testRecipeNotSaved() {
        User user = createUser("diana");
        user.addRecipe("other-id");

        UserDataAccessInterface userDAO = new UserDataAccessInterface() {
            @Override
            public boolean existsByUsername(String username) {
                return true;
            }

            @Override
            public User getUser(String username) {
                return user;
            }

            @Override
            public void save(User user) {
                fail("save(User) should not be called when recipe is not saved");
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        };

        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("diana", "missing-id");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertFalse(presenter.lastOutput.isSuccess());
        assertEquals("Recipe is not saved.", presenter.lastOutput.getMessage());
        assertTrue(user.getRecipeIds().contains("other-id"));
    }

    @Test
    void testSuccessfulDelete() {
        User user = createUser("eric");
        user.addRecipe("keep-id");
        user.addRecipe("delete-id");

        class StubUserDAO implements UserDataAccessInterface {
            boolean existsCalled;
            boolean getUserCalled;
            boolean saveCalled;
            User savedUser;

            @Override
            public boolean existsByUsername(String username) {
                existsCalled = true;
                return true;
            }

            @Override
            public User getUser(String username) {
                getUserCalled = true;
                return user;
            }

            @Override
            public void save(User user) {
                saveCalled = true;
                this.savedUser = user;
            }

            @Override
            public List<User> getAllUsers() {
                return Collections.emptyList();
            }
        }

        StubUserDAO userDAO = new StubUserDAO();
        RecordingPresenter presenter = new RecordingPresenter();
        DeleteSavedRecipeInteractor interactor =
                new DeleteSavedRecipeInteractor(userDAO, presenter);

        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("eric", "delete-id");

        interactor.execute(input);

        assertNotNull(presenter.lastOutput);
        assertTrue(presenter.lastOutput.isSuccess());
        assertEquals("Recipe deleted.", presenter.lastOutput.getMessage());

        assertTrue(userDAO.existsCalled);
        assertTrue(userDAO.getUserCalled);
        assertTrue(userDAO.saveCalled);
        assertNotNull(userDAO.savedUser);
        // "delete-id" should be removed, "keep-id" should remain.
        assertFalse(userDAO.savedUser.getRecipeIds().contains("delete-id"));
        assertTrue(userDAO.savedUser.getRecipeIds().contains("keep-id"));
    }

    @Test
    void testInputData() {
        DeleteSavedRecipeInputData input =
                new DeleteSavedRecipeInputData("userX", "recipeX");

        assertEquals("userX", input.getUsername());
        assertEquals("recipeX", input.getRecipeId());
    }

    @Test
    void testOutputData() {
        DeleteSavedRecipeOutputData output =
                new DeleteSavedRecipeOutputData(true, "OK");

        assertTrue(output.isSuccess());
        assertEquals("OK", output.getMessage());
    }
}
