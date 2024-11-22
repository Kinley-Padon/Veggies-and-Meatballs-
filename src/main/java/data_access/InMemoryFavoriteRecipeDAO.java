package data_access;


import entities.FavoriteRecipes;
import entities.Recipes;
import use_case.recipe_favorites.FavoriteRecipeDataAccessInterface;

import java.util.Set;

public class InMemoryFavoriteRecipeDAO implements FavoriteRecipeDataAccessInterface {
    private final FavoriteRecipes favoriteRecipes = new FavoriteRecipes();

    @Override
    public void addFavoriteRecipe(Recipes recipe) {
        favoriteRecipes.addRecipe(recipe);

    }

    @Override
    public void removeFavoriteRecipe(Recipes recipe) {
        favoriteRecipes.removeRecipe(recipe);
    }

    @Override
    public Set<Recipes> getFavoriteRecipes() {
        return favoriteRecipes.getFavoriteRecipes();
    }
}
