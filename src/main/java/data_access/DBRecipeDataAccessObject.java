package data_access;

import entities.Recipes;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.recipe_search.RecipeDataAccessException;
import use_case.recipe_search.RecipeDataAccessInterface;

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
    public List<Recipes> searchRecipe(final String userInput) throws RecipeDataAccessException {
        // Build the request URL
        String url = API_URL + "?query=" + userInput + "&apiKey=" + this.apiKey;
        // Execute the request
        return getRecipesFromApi(new Request.Builder()
                .url(url)
                .get()
                .build());
    }

    public List<Recipes> searchRecipe(final String recipe, final String allergen) throws RecipeDataAccessException {
        // Build the request URL
        String url = String.format("%s?query=%s&apiKey=%s&excludeIngredients=%s", API_URL, recipe, apiKey, allergen);
        return getRecipesFromApi(new Request.Builder()
                .url(url)
                .get()
                .build());
    }

    @NotNull
    private List<Recipes> getRecipesFromApi(final Request request) throws RecipeDataAccessException {
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
                String image = recipeJson.getString("image");

                Recipes newRecipe = new Recipes(id, name, image);
                recipes.add(newRecipe);
            }
            return recipes;
        } catch (IOException | JSONException e) {
            throw new RecipeDataAccessException("Error while processing the API response: " + e.getMessage());
        }
    }

    @Override
    public String getInstructions(int recipeId) throws RecipeDataAccessException {
        String url = String.format("https://api.spoonacular.com/recipes/%d/analyzedInstructions?apiKey=%s", recipeId, apiKey);
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new RecipeDataAccessException("Failed to fetch recipe instructions. HTTP code: " + response.code());
            }

            String responseBody = response.body().string();
            JSONArray instructionsArray = new JSONArray(responseBody);

            StringBuilder instructionsBuilder = new StringBuilder();
            for (int i = 0; i < instructionsArray.length(); i++) {
                JSONObject instructionObject = instructionsArray.getJSONObject(i);
                JSONArray stepsArray = instructionObject.getJSONArray("steps");

                for (int j = 0; j < stepsArray.length(); j++) {
                    JSONObject stepObject = stepsArray.getJSONObject(j);
                    int stepNumber = stepObject.getInt("number");
                    String stepDescription = stepObject.getString("step");

                    instructionsBuilder.append(stepNumber)
                            .append(". ")
                            .append(stepDescription)
                            .append("\n");
                }
            }

            return instructionsBuilder.toString().trim();

        } catch (IOException | JSONException e) {
            throw new RecipeDataAccessException("Error while processing the API response: " + e.getMessage());
        }
    }

}