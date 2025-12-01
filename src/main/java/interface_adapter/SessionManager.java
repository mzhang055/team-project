package interface_adapter;

import entities.Meal;

/**
 * Manages the current user session and temporary session data.
 * This keeps track of who is logged in and any pending actions.
 */
public class SessionManager {
    private String currentUsername = null;
    private Meal pendingMeal; // Temporarily stores a fetched meal before it's saved

    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

    public String getCurrentUsername() {
        return currentUsername;
    }

    public boolean isLoggedIn() {
        return currentUsername != null && !currentUsername.isEmpty();
    }

    public void clearSession() {
        currentUsername = null;
        pendingMeal = null;
    }

    /**
     * Stores a meal temporarily after fetching nutrition data.
     * The meal is not saved to the database yet.
     *
     * @param meal the meal to store temporarily
     */
    public void setPendingMeal(Meal meal) {
        this.pendingMeal = meal;
    }

    /**
     * Gets the pending meal that was fetched but not yet saved.
     *
     * @return the pending meal, or null if none
     */
    public Meal getPendingMeal() {
        return pendingMeal;
    }

    /**
     * Clears the pending meal after it has been saved or cancelled.
     */
    public void clearPendingMeal() {
        this.pendingMeal = null;
    }

    /**
     * Checks if there is a pending meal ready to be saved.
     *
     * @return true if a meal is pending, false otherwise
     */
    public boolean hasPendingMeal() {
        return pendingMeal != null;
    }
}
