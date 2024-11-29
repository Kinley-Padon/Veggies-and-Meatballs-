package interface_adapter.recipe_add;

import entities.Recipes;
import interface_adapter.ViewManagerModel;
import use_case.recipe_add.RecipeAddOutputBoundary;

public class RecipeAddPresenter implements RecipeAddOutputBoundary {

    private final RecipeAddViewModel recipeAddViewModel;
    private final ViewManagerModel viewManagerModel;

    public RecipeAddPresenter(ViewManagerModel viewManagerModel, RecipeAddViewModel recipeAddViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.recipeAddViewModel = recipeAddViewModel;
    }

    @Override
    public void displayAddedRecipe(Recipes recipe) {
        final RecipeAddState recipeAddState = recipeAddViewModel.getState();
        recipeAddState.setRecipeName(recipe.getName());
        recipeAddState.setDescription(recipe.getDescription());
        recipeAddState.setIngredients(recipe.getIngredients());
        recipeAddState.setImageUrl(recipe.getImage());

        recipeAddViewModel.setState(recipeAddState);
        recipeAddViewModel.firePropertyChanged();

        viewManagerModel.setState("recipeList");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void displayError(String errorMessage) {
        final RecipeAddState recipeAddState = recipeAddViewModel.getState();
        recipeAddState.setErrorMessage(errorMessage);
        recipeAddViewModel.setState(recipeAddState);
        recipeAddViewModel.firePropertyChanged();
    }

    public void switchToRecipeListView() {
        viewManagerModel.setState("recipeList");
        viewManagerModel.firePropertyChanged();
    }
}
