package interface_adapter;

import java.util.HashMap;
import java.util.Map;

public class Navigation {
    private final ViewManagerModel viewManagerModel;
    private final Map<String, Runnable> refreshMap = new HashMap<>();


    public Navigation(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
    }

    public void registerRefresh(String viewName, Runnable refreshAction) {
        refreshMap.put(viewName, refreshAction);
    }

    public void goTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.firePropertyChange();

        Runnable refresh = refreshMap.get(viewName);
        if (refresh != null) refresh.run();
    }
}
