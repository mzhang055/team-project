package entities;

import java.io.Serializable;

/**
 * Represents an ingredient in a recipe.
 */
public class Ingredient implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String name;
    private final String measure;

    public Ingredient(final String name, final String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    @Override
    public String toString() {
        if (measure == null || measure.isBlank()) {
            return name;
        }
        return measure.trim() + " " + name.trim();
    }
}