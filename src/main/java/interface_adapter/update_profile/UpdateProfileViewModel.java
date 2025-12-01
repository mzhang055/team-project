package interface_adapter.update_profile;

import interface_adapter.ViewModel;

public class UpdateProfileViewModel extends ViewModel<UpdateProfileState>{
    public UpdateProfileViewModel() {
        super("Update Profile");
        setState(new UpdateProfileState());
    }
}
