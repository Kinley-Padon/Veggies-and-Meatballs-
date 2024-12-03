package use_case.recipe_add;

import entities.Recipes;

import java.util.Set;

public interface RecipeAddDataAccessInterface {
    void saveRecipe(Recipes recipe) throws RecipeAddDataAccessException;
}

