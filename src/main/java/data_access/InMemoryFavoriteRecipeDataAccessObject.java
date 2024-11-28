package data_access;

import entities.Recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class InMemoryFavoriteRecipeDataAccessObject implements FavoriteRecipeDataAccessObject {
    private final List<Recipes> favoriteRecipes = new ArrayList<>();

    @Override
    public void addToFavorites(Recipes recipe) {
        if (!favoriteRecipes.contains(recipe)) {
            favoriteRecipes.add(recipe);
        }
    }

    @Override
    public Set<Recipes> getFavoriteRecipes() {
        return (Set<Recipes>) new ArrayList<>(favoriteRecipes);
    }
}