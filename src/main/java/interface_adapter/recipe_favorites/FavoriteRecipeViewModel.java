package interface_adapter.recipe_favorites;

import entities.Recipes;
import java.util.List;

public class FavoriteRecipeViewModel {
    private List<Recipes> favoriteRecipes;

    public List<Recipes> getFavoriteRecipes() {
        return favoriteRecipes;
    }

    public void setFavoriteRecipes(List<Recipes> favoriteRecipes) {
        this.favoriteRecipes = favoriteRecipes;
    }
}