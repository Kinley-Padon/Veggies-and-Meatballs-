package use_case.recipe_add;

import entities.Ingredient;
import entities.Recipes;
import interface_adapter.recipe_add.RecipeAddState;
import interface_adapter.recipe_add.RecipeAddViewModel;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RecipeAddInteractor implements RecipeAddInputBoundary {
    private final RecipeAddOutputBoundary outputBoundary;
    private final RecipeAddDataAccessInterface dataAccess;
    private final RecipeAddViewModel viewModel;

    public RecipeAddInteractor(RecipeAddOutputBoundary outputBoundary, RecipeAddDataAccessInterface dataAccess, RecipeAddViewModel viewModel) {
        this.outputBoundary = outputBoundary;
        this.dataAccess = dataAccess;
        this.viewModel = viewModel;
    }

    @Override
    public void addRecipe(String name, List<Ingredient> ingredients, String description) {
        try {
            int generatedId = generateRecipeId();
            Recipes newRecipe = new Recipes(generatedId, name, ingredients, description);
            dataAccess.saveRecipe(newRecipe);
            RecipeAddOutputData outputData = new RecipeAddOutputData(
                    generatedId,
                    name,
                    ingredients,
                    description
            );

            RecipeAddState newState = new RecipeAddState();
            newState.setRecipeID(generatedId);
            newState.setRecipeName(name);
            newState.setDescription(description);
            newState.setIngredients(ingredients);
            outputBoundary.displayAddedRecipe(outputData);
            viewModel.setState(newState);
        } catch (Exception e) {
            outputBoundary.displayError("Failed to add recipe: " + e.getMessage());
        }
    }


    /**
     * Generate a unique recipe ID.
     * This method ensures the generated ID does not already exist in the data store.
     */
    /**
     * Generate a 7-digit random recipe ID.
     * This method generates a random number in the range [1000000, 9999999].
     */
    public int generateRecipeId() {
        int generatedId = ThreadLocalRandom.current().nextInt(1000000, 10000000); // Generate a 7-digit number
        System.out.println("Generated Recipe ID: " + generatedId); // Log the generated ID
        return generatedId;
    }
}
