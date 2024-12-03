package interface_adapter.recipe_add;

import entities.Ingredient;
import use_case.recipe_add.RecipeAddInputBoundary;
import use_case.recipe_add.RecipeAddInteractor;

import java.util.List;

public class RecipeAddController {
    private final RecipeAddInputBoundary recipeAddInputBoundary;
    private final RecipeAddInteractor recipeAddInteractor;

    public RecipeAddController(RecipeAddInputBoundary recipeAddInputBoundary, RecipeAddInteractor recipeAddInteractor) {
        this.recipeAddInputBoundary = recipeAddInputBoundary;
        this.recipeAddInteractor = recipeAddInteractor;
    }

    public void addRecipe(String name, List<Ingredient> ingredients, String description) {
        recipeAddInputBoundary.addRecipe(name, ingredients, description);
    }

    public int generateRecipeId() {
        return recipeAddInteractor.generateRecipeId();
    }
}
