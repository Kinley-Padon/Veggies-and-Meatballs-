package use_case.recipe_add;

import entities.Ingredient;
import entities.Recipes;
import interface_adapter.recipe_add.RecipeAddViewModel;

import java.util.List;
import java.util.Set;

public class RecipeAddInteractor implements RecipeAddInputBoundary {
    private final RecipeAddOutputBoundary outputBoundary;
    private final RecipeAddDataAccessInterface dataAccess;

    public RecipeAddInteractor(RecipeAddOutputBoundary outputBoundary, RecipeAddDataAccessInterface dataAccess) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
    }

    @Override
    public void addRecipe(String name, List<Ingredient> ingredients, String description) {
        try {
            int generatedId = generateRecipeId(); // Ensures unique ID

            Recipes newRecipe = new Recipes(generatedId, name, ingredients, description);

            dataAccess.saveRecipe(newRecipe);

            outputBoundary.displayAddedRecipe(newRecipe);

            RecipeAddViewModel recipeAddViewModel = (RecipeAddViewModel) outputBoundary;
            recipeAddViewModel.setRecipeID(generatedId);

        } catch (RecipeAddDataAccessException e) {
            outputBoundary.displayError("Failed to add the recipe: " + e.getMessage());
        }
    }

    public int generateRecipeId() {
        Set<Integer> existingIds = dataAccess.fetchExistingRecipeIds();
        int generatedId;

        int maxAttempts = 1000;
        int attempts = 0;

        do {
            generatedId = (int) (Math.random() * 100000000);
            attempts++;
        } while (existingIds.contains(generatedId) && attempts < maxAttempts);

        if (attempts >= maxAttempts) {
            throw new RuntimeException("Failed to generate a unique recipe ID after " + maxAttempts + " attempts.");
        }

        return generatedId;
    }
}