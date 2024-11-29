package data_access;

import entities.Recipes;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.recipe_add.RecipeAddDataAccessException;
import use_case.recipe_add.RecipeAddDataAccessInterface;

import java.io.IOException;
import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class DBRecipeAddDataAccessObject implements RecipeAddDataAccessInterface {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/recipe_database";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";

    @Override
    public Set<Integer> fetchExistingRecipeIds() throws RecipeAddDataAccessException {
        String apiKey = "89e0ffa4eb2648c2b4feb97323a4ef50";
        String apiUrl = "https://api.spoonacular.com/recipes/complexSearch";

        Set<Integer> ids = new HashSet<>();
        int offset = 0;
        int number = 50;

        try {
            while (true) {
                String url = String.format("%s?apiKey=%s&offset=%d&number=%d", apiUrl, apiKey, offset, number);

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();

                try (Response response = client.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new RecipeAddDataAccessException("Failed to fetch recipe IDs. HTTP code: " + response.code());
                    }

                    String responseBody = response.body().string();
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    JSONArray resultsArray = jsonResponse.getJSONArray("results");

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject recipe = resultsArray.getJSONObject(i);
                        ids.add(recipe.getInt("id"));
                    }

                    if (resultsArray.length() < number) {
                        break;
                    }
                }
                offset += number;
            }
        } catch (IOException | JSONException e) {
            throw new RecipeAddDataAccessException("Error while fetching recipe IDs: " + e.getMessage());
        }
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM recipes")) {
            while (rs.next()) {
                ids.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RecipeAddDataAccessException("Error fetching recipe IDs from database: " + e.getMessage());
        }

        return ids;
    }

    @Override
    public void saveRecipe(Recipes recipe) throws RecipeAddDataAccessException {
        Set<Integer> existingIds = fetchExistingRecipeIds();

        if (existingIds.contains(recipe.getID())) {
            throw new RecipeAddDataAccessException("Recipe ID already exists in the system (API or database).");
        }

        String insertSQL = "INSERT INTO recipes (id, name) VALUES (?, ?)";

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement ps = connection.prepareStatement(insertSQL)) {

            ps.setInt(1, recipe.getID());
            ps.setString(2, recipe.getName());

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new RecipeAddDataAccessException("Failed to insert the recipe.");
            }
        } catch (SQLException e) {
            throw new RecipeAddDataAccessException("Error saving recipe: " + e.getMessage(), e);
        }
    }
}
