import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.create_account.CreateAccountInputData;
import use_case.create_account.CreateAccountInteractor;
import use_case.create_account.CreateAccountOutputBoundary;
import use_case.create_account.CreateAccountOutputData;

import static org.junit.jupiter.api.Assertions.*;

class CreateAccountInteractorTest {
    private UserDataAccessInterface userDataAccess;
    private TestCreateAccountPresenter presenter;
    private CreateAccountInteractor interactor;

    private static class TestCreateAccountPresenter implements CreateAccountOutputBoundary {
        CreateAccountOutputData successData;
        String failMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        public void prepareSuccessView(CreateAccountOutputData outputData) {
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
        presenter = new TestCreateAccountPresenter();
        interactor = new CreateAccountInteractor(userDataAccess, presenter);
    }

    @Test
    void success() {
        CreateAccountInputData inputData = new CreateAccountInputData("Enna",
                "tskr", 160.0, 50.0, "milk", false);
        interactor.execute(inputData);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.successData);
        assertEquals("Enna", presenter.successData.getUsername());

        assertTrue(userDataAccess.existsByUsername("Enna"));
        User saved = userDataAccess.getUser("Enna");
        assertEquals(160.0, saved.getHeight());
        assertEquals(50.0, saved.getWeight());
        assertEquals("milk", saved.getAllergies());
        assertFalse(saved.isVegan());
    }

    @Test
    void failWhenUsernameAlreadyExists() {
        userDataAccess.save(new User("Elira",
                "nice", 170.0, 60.0, "", false));
        CreateAccountInputData inputData = new CreateAccountInputData("Elira",
                "nice", 170.0, 60.0, "", false);
        interactor.execute(inputData);
        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
        User saved = userDataAccess.getUser("Elira");
        assertEquals("nice", saved.getPassword());
    }
}
