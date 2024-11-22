package app;

import data_access.DBRecipeDataAccessObject;
import use_case.recipe_search.RecipeDataAccessInterface;

public class MainRecipeApplication {
    // create the data access and inject it into our builder!

    public static void main(String[] args) {
        final RecipeDataAccessInterface recipeDataAccess = new DBRecipeDataAccessObject();

        final RecipeAppBuilder builder = new RecipeAppBuilder();
        builder.addRecipeDAO(recipeDataAccess)
                .addRecipeView()
                .addRecipeUseCase().build().setVisible(true);
    }
}