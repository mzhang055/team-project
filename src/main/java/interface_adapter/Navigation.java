package interface_adapter;

import java.util.HashMap;
import java.util.Map;

public class Navigation {
    private final ViewManagerModel viewManagerModel;
    private final LegacyViewManagerModel legacy;
    private final SessionManager session;
    private final Map<String, Runnable> refreshMap = new HashMap<>();


    public Navigation(ViewManagerModel viewManagerModel, LegacyViewManagerModel legacy, SessionManager session) {
        this.viewManagerModel = viewManagerModel;
        this.legacy = legacy;
        this.session = session;
    }

    public void registerRefresh(String viewName, Runnable refreshAction) {
        refreshMap.put(viewName, refreshAction);
    }

    public void goTo(String viewName) {
        if (legacy != null && legacyViewExists(viewName)) {
            legacy.showView(viewName);
            return;
        }

        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChange();

        Runnable refresh = refreshMap.get(viewName);
        if (refresh != null) refresh.run();
    }

    private boolean legacyViewExists(String name) {
        return name.equals("Login")
                || name.equals("Create Account")
                || name.equals("Add Friend");
    }

    public void setCurrentUsername(String username) {
        session.setCurrentUsername(username);
    }

    public String getCurrentUsername() {
        return session.getCurrentUsername();
    }
}
