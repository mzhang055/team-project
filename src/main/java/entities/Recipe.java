package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Recipe implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String id;
    private final String name;
    private final String category;
    private final String area;
    private final String instructions;
    private final String imageUrl;
    private final List<Ingredient> ingredients;

    public Recipe(String id,
                  String name,
                  String category,
                  String area,
                  String instructions,
                  String imageUrl,
                  List<Ingredient> ingredients) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.area = area;
        this.instructions = instructions;
        this.imageUrl = imageUrl;

        if (ingredients == null) {
            this.ingredients = new ArrayList<>();
        } else {
            this.ingredients = new ArrayList<>(ingredients);
        }
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getArea() {
        return area;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<Ingredient> getIngredients() {
        return new ArrayList<>(ingredients);
    }
}