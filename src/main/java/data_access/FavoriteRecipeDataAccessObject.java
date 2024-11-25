package data_access;

import java.util.Set;

import entities.Recipes;

public interface FavoriteRecipeDataAccessObject {
    void addToFavorites(Recipes recipe);
    Set<Recipes> getFavoriteRecipes();
}