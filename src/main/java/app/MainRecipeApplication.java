package app;

import data_access.DBRecipeDataAccessObject;
import data_access.InMemoryFavoriteRecipeDAO;
import use_case.recipe_search.RecipeDataAccessInterface;

public class MainRecipeApplication {

    public static void main(String[] args) {
        final RecipeDataAccessInterface recipeDataAccess = new DBRecipeDataAccessObject();

        final RecipeAppBuilder builder = new RecipeAppBuilder();
        builder.addRecipeDAO(recipeDataAccess)
                .addRecipeView()
                .addRecipeUseCase().build().setVisible(true);
        builder.addFavoriteRecipeDAO(new InMemoryFavoriteRecipeDAO())
                .addFavoriteRecipeUseCase();
    }
}