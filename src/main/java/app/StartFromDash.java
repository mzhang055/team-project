package app;

import data_access.FileUserDataAccessObject;
import data_access.InMemoryMealDataAccessObject;
import data_access.MealDataAccessInterface;
import data_access.PersistentUserDataAccessObject;
import data_access.RemoteAuthGateway;
import data_access.UserDataAccessInterface;

import javax.swing.*;

public class StartFromDash {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StartFromDash::startApp);
    }
    private static void startApp() {
        UserDataAccessInterface localUserDataAccess = new FileUserDataAccessObject("users.json");
        RemoteAuthGateway remoteAuthGateway = new RemoteAuthGateway();
        UserDataAccessInterface userDataAccess = new PersistentUserDataAccessObject(localUserDataAccess, remoteAuthGateway);
        MealDataAccessInterface mealDataAccess = new InMemoryMealDataAccessObject();

        AppBuilder appBuilder = new AppBuilder(userDataAccess, mealDataAccess);
        JFrame application = appBuilder
                .addLoginView()
                .addCreateAccountView()
                .addDashboardView()
                .addProfileView()
                .addUpdateProfileView()
                .addAddFriendView()
                .addLeaderboardView()
                .addLoginUseCase()
                .addCreateAccountUseCase()
                .addDashboardUseCase()
                .addProfileUseCase()
                .addUpdateProfileUseCase()
                .addAddFriendUseCase()
                .addLogMealsUseCase()
                .addRecipeUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
