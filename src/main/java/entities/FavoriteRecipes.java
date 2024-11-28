package entities;

import java.util.HashSet;
import java.util.Set;

public class FavoriteRecipes {
    private final Set<Recipes> favoriteRecipes;

    public FavoriteRecipes() {
        this.favoriteRecipes = new HashSet<>();
    }

    public void addRecipe(Recipes recipe) {
        favoriteRecipes.add(recipe);
    }

    public void removeRecipe(Recipes recipe) {
        favoriteRecipes.remove(recipe);
    }

    public Set<Recipes> getFavoriteRecipes() {
        return favoriteRecipes;
    }
}