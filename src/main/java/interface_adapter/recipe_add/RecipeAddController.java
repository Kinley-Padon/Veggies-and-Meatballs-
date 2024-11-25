package interface_adapter.recipe_add;

import entities.Ingredient;
import use_case.recipe_add.RecipeAddInputBoundary;

import java.util.List;

public class RecipeAddController {
    private RecipeAddInputBoundary recipeAddInputBoundary;

    public RecipeAddController(RecipeAddInputBoundary recipeAddInputBoundary) {
        this.recipeAddInputBoundary = recipeAddInputBoundary;
    }

    public void addRecipe(String name, List<Ingredient> ingredients, String description, String image) {
        recipeAddInputBoundary.addRecipe(name, ingredients, description);
    }
}
