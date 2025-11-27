package app;

import javax.swing.*;

public class StartFromDash {
    public static void main(String[] args) {
        AppBuilder appBuilder = new AppBuilder();
        JFrame application = appBuilder
                .addDashboardView()
                .addProfileView()
                .addDashboardUseCase()
                .addProfileUseCase()
                .addLogMealsUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
