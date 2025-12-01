package use_case.Login;

import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.login.LoginInputData;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import static org.junit.jupiter.api.Assertions.*;

class LoginInteractorTest {
    private UserDataAccessInterface userDataAccess;
    private TestLoginPresenter presenter;
    private LoginInteractor interactor;

    private static class TestLoginPresenter implements LoginOutputBoundary {
        LoginOutputData successData;
        String failMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        public void prepareSuccessView(LoginOutputData outputData) {
            successCalled = true;
            successData = outputData;
        }

        public void prepareFailView(String errorMessage) {
            failCalled = true;
            failMessage = errorMessage;
        }
    }

    @BeforeEach
    void setUp(){
        userDataAccess = new InMemoryUserDataAccessObject();
        presenter = new TestLoginPresenter();
        interactor = new LoginInteractor(userDataAccess, presenter);
        userDataAccess.save(new User("Enna",
                "tskr", 160.0, 50.0, "milk", false));
    }

    @Test
    void success() {
        LoginInputData inputData = new LoginInputData("Enna", "tskr");
        interactor.execute(inputData);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);
        assertNotNull(presenter.successData);
        assertEquals("Enna", presenter.successData.getUsername());
    }

    @Test
    void failWhenUserNotFound() {
        LoginInputData inputData = new LoginInputData("who", "what");
        interactor.execute(inputData);
        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
    }

    @Test
    void failWithWrongPassword() {
        LoginInputData inputData = new LoginInputData("Enna", "wrong");
        interactor.execute(inputData);
        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
    }
}
