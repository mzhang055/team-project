package interface_adapter.LogMeals;

import entities.NutritionalInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.LogMeals.NutritionApiInterface;

import java.io.IOException;
import java.util.Optional;

/**
 * CalorieNinjas API client implementation.
 * Fetches nutritional information from the CalorieNinjas API.
 */
public class CalorieNinjasApiClient implements NutritionApiInterface {

    private static final String API_URL = "https://api.calorieninjas.com/v1/nutrition?query=";
    private final String apiKey;
    private final OkHttpClient client;

    /**
     * Constructs a CalorieNinjas API client with the given API key.
     *
     * @param apiKey the CalorieNinjas API key
     */
    public CalorieNinjasApiClient(String apiKey) {
        this.apiKey = apiKey;
        this.client = new OkHttpClient();
    }

    @Override
    public Optional<NutritionalInfo> getNutritionalInfo(String foodName) {
        if (foodName == null || foodName.trim().isEmpty()) {
            return Optional.empty();
        }

        try {
            String url = API_URL + foodName.trim().replace(" ", "%20");

            Request request = new Request.Builder()
                    .url(url)
                    .addHeader("X-Api-Key", apiKey)
                    .build();

            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful() || response.body() == null) {
                    return Optional.empty();
                }

                String responseBody = response.body().string();
                JSONObject jsonResponse = new JSONObject(responseBody);
                JSONArray items = jsonResponse.getJSONArray("items");

                if (items.length() == 0) {
                    return Optional.empty();
                }

                JSONObject firstItem = items.getJSONObject(0);

                NutritionalInfo nutritionalInfo = NutritionalInfo.builder()
                        .calories(firstItem.optDouble("calories", 0.0))
                        .protein(firstItem.optDouble("protein_g", 0.0))
                        .carbohydrates(firstItem.optDouble("carbohydrates_total_g", 0.0))
                        .fat(firstItem.optDouble("fat_total_g", 0.0))
                        .fiber(firstItem.optDouble("fiber_g", 0.0))
                        .sugar(firstItem.optDouble("sugar_g", 0.0))
                        .build();

                return Optional.of(nutritionalInfo);
            }
        } catch (IOException | org.json.JSONException e) {
            System.err.println("Error fetching nutrition data: " + e.getMessage());
            return Optional.empty();
        }
    }
}
