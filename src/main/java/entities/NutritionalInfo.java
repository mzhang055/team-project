package entities;

import java.util.Objects;

/**
 * Value object representing nutritional information for a meal/food item.
 * Immutable and all measured in grams (except calories)
 */
public final class NutritionalInfo {
    private final double calories;
    private final double protein;
    private final double carbohydrates;
    private final double fat;
    private final double fiber;
    private final double sugar;

    private NutritionalInfo(Builder builder) {
        this.calories = builder.calories;
        this.protein = builder.protein;
        this.carbohydrates = builder.carbohydrates;
        this.fat = builder.fat;
        this.fiber = builder.fiber;
        this.sugar = builder.sugar;
    }

    public static Builder builder() {
        return new Builder();
    }

    public double getCalories() {
        return calories;
    }

    public double getProtein() {
        return protein;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFat() {
        return fat;
    }

    public double getFiber() {
        return fiber;
    }

    public double getSugar() {
        return sugar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NutritionalInfo that = (NutritionalInfo) o;
        return Double.compare(that.calories, calories) == 0 &&
                Double.compare(that.protein, protein) == 0 &&
                Double.compare(that.carbohydrates, carbohydrates) == 0 &&
                Double.compare(that.fat, fat) == 0 &&
                Double.compare(that.fiber, fiber) == 0 &&
                Double.compare(that.sugar, sugar) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(calories, protein, carbohydrates, fat, fiber, sugar);
    }

    @Override
    public String toString() {
        return String.format(
                "Calories: %.1f kcal | Protein: %.1fg | Carbs: %.1fg | Fat: %.1fg | Fiber: %.1fg | Sugar: %.1fg",
                calories, protein, carbohydrates, fat, fiber, sugar
        );
    }

    public static class Builder {
        private double calories = 0.0;
        private double protein = 0.0;
        private double carbohydrates = 0.0;
        private double fat = 0.0;
        private double fiber = 0.0;
        private double sugar = 0.0;

        public Builder calories(double calories) {
            if (calories < 0) {
                throw new IllegalArgumentException("Calories cannot be negative");
            }
            this.calories = calories;
            return this;
        }

        public Builder protein(double protein) {
            if (protein < 0) {
                throw new IllegalArgumentException("Protein cannot be negative");
            }
            this.protein = protein;
            return this;
        }

        public Builder carbohydrates(double carbohydrates) {
            if (carbohydrates < 0) {
                throw new IllegalArgumentException("Carbohydrates cannot be negative");
            }
            this.carbohydrates = carbohydrates;
            return this;
        }

        public Builder fat(double fat) {
            if (fat < 0) {
                throw new IllegalArgumentException("Fat cannot be negative");
            }
            this.fat = fat;
            return this;
        }

        public Builder fiber(double fiber) {
            if (fiber < 0) {
                throw new IllegalArgumentException("Fiber cannot be negative");
            }
            this.fiber = fiber;
            return this;
        }

        public Builder sugar(double sugar) {
            if (sugar < 0) {
                throw new IllegalArgumentException("Sugar cannot be negative");
            }
            this.sugar = sugar;
            return this;
        }

        public NutritionalInfo build() {
            return new NutritionalInfo(this);
        }
    }
}
