package app;

import data_access.DB

public class MainRecipeApplication {
    // create the data access and inject it into our builder!

    public static void main(String[] args) {
        final RecipeDataAccessInterface RecipeDataAccess = new DBRecipeDataAccessObject();

        final RecipeAppBuilder builder = new RecipeAppBuilder();
        builder.addRecipeDAO(noteDataAccess)
                .addRecipeView()
                .addRecipeUseCase().build().setVisible(true);
    }
}