import data_access.InMemoryUserDataAccessObject;
import data_access.UserDataAccessInterface;
import entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.reject_friend.RejectFriendRequestInputData;
import use_case.reject_friend.RejectFriendRequestInteractor;
import use_case.reject_friend.RejectFriendRequestOutputBoundary;
import use_case.reject_friend.RejectFriendRequestOutputData;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RejectFriendInteractorTest {
    private UserDataAccessInterface userDataAccess;
    private TestRejectPresenter presenter;
    private RejectFriendRequestInteractor interactor;

    private static class TestRejectPresenter implements RejectFriendRequestOutputBoundary {
        RejectFriendRequestOutputData successData;
        String failMessage;
        boolean successCalled = false;
        boolean failCalled = false;

        public void prepareSuccessView(RejectFriendRequestOutputData outputData) {
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
        presenter = new TestRejectPresenter();
        interactor = new RejectFriendRequestInteractor(userDataAccess, presenter);

        User Enna = new User("Enna",
                "tskr", 160.0, 50.0, "milk", false);
        User Elira = new User("Elira",
                "nice", 170.0, 60.0, "", false);
        Enna.getOutgoingFriendRequests().add("Elira");
        Elira.getIncomingFriendRequests().add("Enna");

        userDataAccess.save(Enna);
        userDataAccess.save(Elira);
    }

    @Test
    void success() {
        RejectFriendRequestInputData inputData = new RejectFriendRequestInputData(
                "Elira", "Enna");
        interactor.execute(inputData);
        assertTrue(presenter.successCalled);
        assertFalse(presenter.failCalled);

        User Enna = userDataAccess.getUser("Enna");
        User Elira = userDataAccess.getUser("Elira");
        List<String> outgoing = Enna.getOutgoingFriendRequests();
        List<String> incoming = Elira.getIncomingFriendRequests();

        assertFalse(outgoing.contains("Elira"));
        assertFalse(incoming.contains("Enna"));
        assertFalse(Enna.getFriendsUsernames().contains("Elira"));
        assertFalse(Elira.getFriendsUsernames().contains("Enna"));
    }

    @Test
    void failWhenNoSuchRequest() {
        User Elira = userDataAccess.getUser("Elira");
        Elira.getIncomingFriendRequests().clear();
        userDataAccess.save(Elira);
        RejectFriendRequestInputData inputData = new RejectFriendRequestInputData(
                "Elira", "Enna");
        interactor.execute(inputData);

        assertFalse(presenter.successCalled);
        assertTrue(presenter.failCalled);
        assertNotNull(presenter.failMessage);
    }
}
