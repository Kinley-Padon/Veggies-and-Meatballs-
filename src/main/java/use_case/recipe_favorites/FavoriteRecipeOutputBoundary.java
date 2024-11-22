package use_case.recipe_favorites;
import entities.Recipes;
import java.util.Set;

public interface FavoriteRecipeOutputBoundary {
    void presentFavoriteRecipes(Set<Recipes> recipes);
    void presentFavoriteError(String errorMessage);
}
