package data_access;

import entities.Recipe;
import use_case.recipe_log.RecipeLogDataAccessInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileRecipeLogDataAccessObject implements RecipeLogDataAccessInterface {

    private final File storageFile;

    public FileRecipeLogDataAccessObject(String filePath) throws IOException {
        this.storageFile = new File(filePath);
        File parent = storageFile.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }
        if (!storageFile.exists()) {
            storageFile.createNewFile();
        }
    }

    // 读出所有 Recipe，没有就返回空列表
    @SuppressWarnings("unchecked")
    private List<Recipe> readAll() throws IOException {
        if (storageFile.length() == 0L) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(storageFile))) {
            Object obj = ois.readObject();
            List<Recipe> result = new ArrayList<>();
            if (obj instanceof List<?>) {
                for (Object o : (List<?>) obj) {
                    if (o instanceof Recipe) {
                        result.add((Recipe) o);
                    }
                }
            }
            return result;
        } catch (ClassNotFoundException e) {
            throw new IOException("Failed to read recipes: " + e.getMessage(), e);
        }
    }

    // 把所有 Recipe 写回文件
    private void writeAll(List<Recipe> recipes) throws IOException {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(storageFile))) {
            oos.writeObject(new ArrayList<>(recipes));
            oos.flush();
        }
    }

    @Override
    public synchronized void saveRecipe(Recipe recipe) throws IOException {
        List<Recipe> recipes = readAll();

        if (recipe.getId() != null) {
            boolean exists = recipes.stream()
                    .anyMatch(r -> recipe.getId().equals(r.getId()));
            if (!exists) {
                recipes.add(recipe);
            }
        } else {
            recipes.add(recipe);
        }

        writeAll(recipes);
    }

    @Override
    public synchronized List<Recipe> getAllSavedRecipes() throws IOException {
        return readAll();
    }
}
