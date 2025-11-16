package interface_adapter.create_account;

import use_case.create_account.CreateAccountInputBoundary;
import use_case.create_account.CreateAccountInputData;

public class CreateAccountController {
    private final CreateAccountInputBoundary interactor;
    public CreateAccountController(CreateAccountInputBoundary interactor){
        this.interactor = interactor;
    }
    public void create(String username, String password){
        CreateAccountInputData input = new CreateAccountInputData(username, password);
        interactor.execute(input);
    }
}
