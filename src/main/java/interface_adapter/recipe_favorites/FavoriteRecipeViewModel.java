package interface_adapter.recipe_favorites;

import entities.Recipes;
import interface_adapter.ViewModel;

import java.util.Set;

public class FavoriteRecipeViewModel extends ViewModel<Set<Recipes>> {
    private String errorMessage;

    public FavoriteRecipeViewModel() {
        super("FavoriteRecipes");
    }

    public void setFavoriteRecipes(Set<Recipes> recipes) {
        setState(recipes);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
