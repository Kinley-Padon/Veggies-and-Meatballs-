package app;

import interface_adapter.recipe_search.RecipeController;
import interface_adapter.recipe_search.RecipePresenter;
import interface_adapter.recipe_search.RecipeViewModel;
import use_case.recipe_search.RecipeDataAccessInterface;
import use_case.recipe_search.RecipeInteractor;
import use_case.recipe_search.RecipeOutputBoundary;
import view.RecipeView;

import javax.swing.*;
/**
 * Builder for the Recipe Application.
 */

public class RecipeAppBuilder {
    public static final int HEIGHT = 300;
    public static final int WIDTH = 400;
    private RecipeDataAccessInterface recipeDAO;
    private RecipeViewModel recipeViewModel = new RecipeViewModel();
    private RecipeView recipeView;
    private RecipeInteractor recipeInteractor;

    /**
     * Sets the RecipeDAO to be used in this application.
     * @param recipeDataAccess the DAO to use
     * @return this builder
     */
    public RecipeAppBuilder addRecipeDAO(RecipeDataAccessInterface recipeDataAccess) {
        recipeDAO = recipeDataAccess;
        return this;
    }

    /**
     * Creates the objects for the Recipe Use Case and connects the RecipeView to its
     * controller.
     * <p>This method must be called after addRecipeView!</p>
     * @return this builder
     * @throws RuntimeException if this method is called before addRecipeView
     */
    public RecipeAppBuilder addRecipeUseCase() {
        final RecipeOutputBoundary recipeOutputBoundary = new RecipePresenter(recipeViewModel);
        recipeInteractor = new RecipeInteractor(recipeDAO, recipeOutputBoundary);

        final RecipeController controller = new RecipeController(recipeInteractor);
        if (recipeView == null) {
            throw new RuntimeException("addRecipeView must be called before addRecipeUseCase");
        }
        recipeView.setRecipeController(controller);
        return this;
    }

    /**
     * Creates the RecipeView and underlying RecipeViewModel.
     * @return this builder
     */
    public RecipeAppBuilder addRecipeView() {
        recipeViewModel = new RecipeViewModel();
        recipeView = new RecipeView(recipeViewModel);
        return this;
    }

    /**
     * Builds the application.
     * @return the JFrame for the application
     */
    public JFrame build() {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setTitle("Recipe Explorer");
        frame.setSize(WIDTH, HEIGHT);

        frame.add(recipeView);

        return frame;
    }

}


