package data_access;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Recipes;
import use_case.recipe_add.RecipeAddDataAccessException;
import use_case.recipe_add.RecipeAddDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DBRecipeAddDataAccessObject implements RecipeAddDataAccessInterface {

    private static final String RECIPES_FILE_PATH = "/Users/macbook/Desktop/Intellij/FizzBuzz2/file save.json"; // Path to the JSON file where recipes will be saved

    /**
     * Save a recipe to a JSON file.
     *
     * @param recipe The recipe object to save.
     * @throws RecipeAddDataAccessException If an error occurs during the save operation.
     */
    public void saveRecipe(Recipes recipe) throws RecipeAddDataAccessException {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            File file = new File(RECIPES_FILE_PATH);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            List<Recipes> recipes = new ArrayList<>();
            if (file.length() > 0) {
                recipes = objectMapper.readValue(file, objectMapper.getTypeFactory().constructCollectionType(List.class, Recipes.class));
            }

            recipes.add(recipe);

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(recipes);

            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.close();

            System.out.println("Recipe saved successfully to " + RECIPES_FILE_PATH);

        } catch (IOException e) {
            System.out.println("Error saving recipe to file: " + e.getMessage());
            e.printStackTrace();
            throw new RecipeAddDataAccessException("Error saving recipe to file: " + e.getMessage());
        }
    }
}