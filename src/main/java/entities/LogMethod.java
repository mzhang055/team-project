package entities;

/**
 * Enum representing the method used to log a meal.
 */
public enum LogMethod {
    BARCODE("Scan Barcode"),
    MANUAL("Manual Entry");

    private final String displayName;

    LogMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
