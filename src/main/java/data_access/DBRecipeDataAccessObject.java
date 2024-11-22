package data_access;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.recipe_search.RecipeDataAccessException;
import use_case.recipe_search.RecipeDataAccessInterface;

import entities.Recipes;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class DBRecipeDataAccessObject implements RecipeDataAccessInterface {
    private static final String API_URL = "https://api.spoonacular.com/recipes/complexSearch";
    private String apiKey = "36b1d188a2b8448283c5d01c1ff30c5e";
    private final OkHttpClient client = new OkHttpClient();


    public void RecipeDataAccessObject(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Searches for recipes based on a query string.
     *
     * @param userInput The recipe name or keyword to search for.
     * @return A list of Recipe objects that match the query.
     * @throws Exception if there is an error during the API call or response parsing.
     */



    @Override
    public List<Recipes> searchRecipe(String userInput) throws RecipeDataAccessException {
        // Build the request URL
        String url = API_URL + "?query=" + userInput + "&apiKey=" + apiKey;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();


        // Execute the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RecipeDataAccessException("Failed to fetch recipes. HTTP code: " + response.code());
            }


            // Parse JSON response
            String responseBody = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray resultsArray = jsonResponse.getJSONArray("results");


            // Map JSON response to Recipe objects
            List<Recipes> recipes = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject recipeJson = resultsArray.getJSONObject(i);
                int id = recipeJson.getInt("id");
                String name = recipeJson.getString("title");


                recipes.add(new Recipes(id, name));
            }
            return recipes;
        }

        catch (IOException | JSONException e) {
            throw new RecipeDataAccessException("Error while processing the API response: " + e.getMessage());
        }
    }
}