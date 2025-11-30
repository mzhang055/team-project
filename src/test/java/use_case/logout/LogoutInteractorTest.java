package use_case.logout;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class LogoutInteractorTest {

    @Test
    void sucessTest() {
        System.out.println("Success");
        LogoutOutputBoundary successPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                assertEquals("Paul", user.getUsername());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Success test should not call prepareFailView");
            }
        };
        LogoutInputBoundary interactor = new LogoutInteractor(successPresenter);
        interactor.logout("Paul");
    }

    @Test
    void failTest(){
        LogoutOutputBoundary failPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                fail("fail test should not call prepareSuccessView");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Username is empty", errorMessage);
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(failPresenter);
        interactor.logout("");
    }

    @Test
    void failTest_NullUsername() {
        LogoutOutputBoundary failPresenter = new LogoutOutputBoundary() {
            @Override
            public void prepareSuccessView(LogoutOutputData user) {
                fail("Null test should not call prepareSuccessView");
            }

            @Override
            public void prepareFailView(String errorMessage) {
                assertEquals("Username is empty", errorMessage);
            }
        };

        LogoutInputBoundary interactor = new LogoutInteractor(failPresenter);
        interactor.logout(null);
    }
}
