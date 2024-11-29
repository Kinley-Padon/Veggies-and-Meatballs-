package data_access;

import entities.Recipes;
import use_case.recipe_add.RecipeAddDataAccessException;
import use_case.recipe_add.RecipeAddDataAccessInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class InMemoryRecipeDataAccess implements RecipeAddDataAccessInterface {
    private final Set<Integer> recipeIds = new HashSet<>();
    private final List<Recipes> recipes = new ArrayList<>();

    @Override
    public Set<Integer> fetchExistingRecipeIds() {
        return new HashSet<>(recipeIds);
    }

    @Override
    public void saveRecipe(Recipes recipe) {
        if (recipeIds.contains(recipe.getID())) {
            throw new RecipeAddDataAccessException("Recipe ID already exists.");
        }
        recipeIds.add(recipe.getID());
        recipes.add(recipe);
    }
}

