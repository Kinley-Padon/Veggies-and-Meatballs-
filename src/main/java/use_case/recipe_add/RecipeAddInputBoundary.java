package use_case.recipe_add;

import entities.Ingredient;

import java.util.List;

public interface RecipeAddInputBoundary {
    void addRecipe(String name, List<Ingredient> ingredients, String description);
}
