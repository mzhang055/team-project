package interface_adapter.LogMeals;

import entities.Meal;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * View Model for the Log Meals use case.
 * Holds the state and notifies observers of changes.
 */
public class LogMealsViewModel {

    private final PropertyChangeSupport support;
    private String successMessage;
    private String errorMessage;
    private Meal meal;

    public LogMealsViewModel() {
        this.support = new PropertyChangeSupport(this);
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
        this.errorMessage = null;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        this.successMessage = null;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Meal getMeal() {
        return meal;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this);
    }
}
