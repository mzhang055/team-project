package entities;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Entity class representing a logged meal/food item.
 * Follows Java best practices: immutable design, proper encapsulation, and validation.
 */
public final class Meal {
    private final String id;
    private final String foodName;
    private final MealType mealType;
    private final NutritionalInfo nutritionalInfo;
    private final LocalDateTime loggedAt;
    private final String userId;

    /**
     * Private constructor to enforce use of builder pattern.
     */
    private Meal(Builder builder) {
        this.id = builder.id;
        this.foodName = builder.foodName;
        this.mealType = builder.mealType;
        this.nutritionalInfo = builder.nutritionalInfo;
        this.loggedAt = builder.loggedAt;
        this.userId = builder.userId;
    }

    /**
     * Creates a new builder for constructing Meal instances.
     *
     * @param foodName the name of the food/meal (required)
     * @return a new Builder instance
     */
    public static Builder builder(String foodName) {
        return new Builder(foodName);
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getFoodName() {
        return foodName;
    }

    public MealType getMealType() {
        return mealType;
    }

    public NutritionalInfo getNutritionalInfo() {
        return nutritionalInfo;
    }

    public LocalDateTime getLoggedAt() {
        return loggedAt;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(id, meal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Meal{" +
                "id='" + id + '\'' +
                ", foodName='" + foodName + '\'' +
                ", mealType=" + mealType +
                ", nutritionalInfo=" + nutritionalInfo +
                ", loggedAt=" + loggedAt +
                ", userId='" + userId + '\'' +
                '}';
    }

    /**
     * Builder pattern for constructing Meal instances.
     * Provides validation and default values.
     */
    public static class Builder {
        private String id;
        private final String foodName;
        private MealType mealType;
        private NutritionalInfo nutritionalInfo;
        private LocalDateTime loggedAt;
        private String userId;

        private Builder(String foodName) {
            if (foodName == null || foodName.trim().isEmpty()) {
                throw new IllegalArgumentException("Food name cannot be null or empty");
            }
            this.foodName = foodName.trim();
            this.id = UUID.randomUUID().toString();
            this.loggedAt = LocalDateTime.now();
        }

        public Builder id(String id) {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalArgumentException("ID cannot be null or empty");
            }
            this.id = id;
            return this;
        }

        public Builder mealType(MealType mealType) {
            if (mealType == null) {
                throw new IllegalArgumentException("Meal type cannot be null");
            }
            this.mealType = mealType;
            return this;
        }

        public Builder nutritionalInfo(NutritionalInfo nutritionalInfo) {
            this.nutritionalInfo = nutritionalInfo;
            return this;
        }

        public Builder loggedAt(LocalDateTime loggedAt) {
            if (loggedAt == null) {
                throw new IllegalArgumentException("Logged timestamp cannot be null");
            }
            this.loggedAt = loggedAt;
            return this;
        }

        public Builder userId(String userId) {
            if (userId == null || userId.trim().isEmpty()) {
                throw new IllegalArgumentException("User ID cannot be null or empty");
            }
            this.userId = userId;
            return this;
        }

        public Meal build() {
            if (userId == null) {
                throw new IllegalStateException("User ID must be set before building Meal");
            }
            if (mealType == null) {
                throw new IllegalStateException("Meal type must be set before building Meal");
            }
            return new Meal(this);
        }
    }
}
