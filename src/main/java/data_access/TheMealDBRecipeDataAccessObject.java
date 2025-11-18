package data_access;

import entities.Ingredient;
import entities.Recipe;
import use_case.recipe_search.RecipeSearchDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TheMealDBRecipeDataAccessObject implements RecipeSearchDataAccessInterface {

    private static final String API_URL_TEMPLATE =
            "https://www.themealdb.com/api/json/v1/1/search.php?s=%s";

    @Override
    public List<Recipe> searchRecipesByName(String name) throws IOException {
        String encoded = URLEncoder.encode(name, StandardCharsets.UTF_8.name());
        String urlStr = String.format(API_URL_TEMPLATE, encoded);
        String body = httpGet(urlStr);
        return parseRecipes(body);
    }

    private String httpGet(String urlStr) throws IOException {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int status = conn.getResponseCode();
            InputStream is;
            if (status >= 200 && status < 300) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
                if (is == null) {
                    throw new IOException("HTTP error: " + status);
                }
            }

            try (BufferedReader reader =
                         new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();
            }
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private List<Recipe> parseRecipes(String json) {
        if (json == null || json.isEmpty()) {
            return Collections.emptyList();
        }
        if (json.contains("\"meals\":null")) {
            return Collections.emptyList();
        }

        int mealsIndex = json.indexOf("\"meals\"");
        if (mealsIndex < 0) {
            return Collections.emptyList();
        }

        int arrayStart = json.indexOf('[', mealsIndex);
        if (arrayStart < 0) {
            return Collections.emptyList();
        }

        int arrayEnd = findMatchingBracket(json, arrayStart, '[', ']');
        if (arrayEnd < 0) {
            return Collections.emptyList();
        }

        List<Recipe> result = new ArrayList<>();
        int pos = arrayStart;

        while (true) {
            int objStart = json.indexOf('{', pos);
            if (objStart < 0 || objStart > arrayEnd) {
                break;
            }
            int objEnd = findMatchingBracket(json, objStart, '{', '}');
            if (objEnd < 0 || objEnd > arrayEnd) {
                break;
            }

            String mealJson = json.substring(objStart, objEnd + 1);
            Recipe recipe = parseSingleMeal(mealJson);
            if (recipe != null) {
                result.add(recipe);
            }
            pos = objEnd + 1;
        }

        return result;
    }

    private Recipe parseSingleMeal(String mealJson) {
        String id = getJsonStringField(mealJson, "idMeal");
        String name = getJsonStringField(mealJson, "strMeal");
        String category = getJsonStringField(mealJson, "strCategory");
        String area = getJsonStringField(mealJson, "strArea");
        String instructions = getJsonStringField(mealJson, "strInstructions");
        String imageUrl = getJsonStringField(mealJson, "strMealThumb");

        if (name == null) {
            return null;
        }

        List<Ingredient> ingredients = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String ingName = getJsonStringField(mealJson, "strIngredient" + i);
            String measure = getJsonStringField(mealJson, "strMeasure" + i);

            if (ingName != null && !ingName.isBlank()) {
                if (measure == null) {
                    measure = "";
                }
                ingredients.add(new Ingredient(ingName.trim(), measure.trim()));
            }
        }

        return new Recipe(id, name, category, area, instructions, imageUrl, ingredients);
    }

    private int findMatchingBracket(String text, int openIndex, char openChar, char closeChar) {
        int depth = 0;
        for (int i = openIndex; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == openChar) {
                depth++;
            } else if (c == closeChar) {
                depth--;
                if (depth == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    private String getJsonStringField(String json, String key) {
        String pattern = "\"" + key + "\"";
        int keyIndex = json.indexOf(pattern);
        if (keyIndex < 0) {
            return null;
        }

        int colonIndex = json.indexOf(':', keyIndex + pattern.length());
        if (colonIndex < 0) {
            return null;
        }

        int valueStart = colonIndex + 1;
        while (valueStart < json.length()
                && Character.isWhitespace(json.charAt(valueStart))) {
            valueStart++;
        }

        if (valueStart + 4 <= json.length()
                && json.startsWith("null", valueStart)) {
            return null;
        }

        if (valueStart >= json.length() || json.charAt(valueStart) != '"') {
            return null;
        }

        int firstQuote = valueStart;
        StringBuilder sb = new StringBuilder();
        boolean escaped = false;

        for (int i = firstQuote + 1; i < json.length(); i++) {
            char c = json.charAt(i);
            if (escaped) {
                sb.append(c);
                escaped = false;
            } else if (c == '\\') {
                escaped = true;
            } else if (c == '"') {
                break;
            } else {
                sb.append(c);
            }
        }

        String value = sb.toString().trim();
        return value.isEmpty() ? null : value;
    }
}