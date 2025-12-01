package use_case.UpdateProfile;

import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.update_profile.UpdateProfileInputData;
import use_case.update_profile.UpdateProfileInteractor;
import use_case.update_profile.UpdateProfileOutputBoundary;
import use_case.update_profile.UpdateProfileOutputData;

import static org.junit.jupiter.api.Assertions.*;

class UpdateProfileInteractorTest {
    private UserDataAccessInterface userDataAccess;
    private TestUpdatePresenter presenter;
    private UpdateProfileInteractor interactor;

    private static class TestUpdatePresenter implements UpdateProfileOutputBoundary {
        UpdateProfileOutputData successData;
        String failMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        public void prepareSuccessView(UpdateProfileOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        public void prepareFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    @BeforeEach
    void setUp() {
        userDataAccess = new InMemoryUserDataAccessObject();
        presenter = new TestUpdatePresenter();
        interactor = new UpdateProfileInteractor(userDataAccess, presenter);

        userDataAccess.save(new User("Enna",
                "tskr", 160.0, 50.0, "milk", false));
    }

    @Test
    void success() {
        UpdateProfileInputData inputData = new UpdateProfileInputData("Enna",
                180.0, 60.0, "milk, peanut", true);
        interactor.execute(inputData);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        User updated = userDataAccess.getUser("Enna");
        assertEquals(180.0, updated.getHeight());
        assertEquals(60.0, updated.getWeight());
        assertEquals("milk, peanut", updated.getAllergies());
        assertTrue(updated.isVegan());
    }

    @Test
    void failWhenUserNotFound() {
        UpdateProfileInputData inputData = new UpdateProfileInputData("Reimu",
                170.0, 60.0, "", false);
        interactor.execute(inputData);
        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
    }
}
