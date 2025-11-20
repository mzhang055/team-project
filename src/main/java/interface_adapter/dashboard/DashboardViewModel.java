package interface_adapter.dashboard;

import interface_adapter.ViewModel;
import use_case.dashboard.DashboardInputData;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel extends ViewModel<DashboardState> {
   public DashboardViewModel() {
       super("Dashboard");
       setState(new DashboardState());
   }
}
