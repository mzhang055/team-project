package app;

import javax.swing.*;

public class StartFromDash {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addLoginView()
                .addCreateAccountView()
                .addDashboardView()
                .addProfileView()
                .addAddFriendView()
                .addLoginUseCase()
                .addCreateAccountUseCase()
                .addDashboardUseCase()
                .addProfileUseCase()
                .addAddFriendUseCase()
                .addLogMealsUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
