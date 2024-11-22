package use_case.recipe_favorites;

import entities.Recipes;
import java.util.Set;

public interface FavoriteRecipeInputBoundary {
    void addRecipeToFavorites(Recipes recipe);
    void removeRecipeFromFavorites(Recipes recipe);
    Set<Recipes> getFavoriteRecipes();
}