package interface_adapter.recipe_add;

import interface_adapter.ViewManagerModel;
import use_case.recipe_add.RecipeAddOutputBoundary;
import use_case.recipe_add.RecipeAddOutputData;

public class RecipeAddPresenter implements RecipeAddOutputBoundary {

    private final RecipeAddViewModel recipeAddViewModel;

    public RecipeAddPresenter(RecipeAddViewModel recipeAddViewModel) {
        this.recipeAddViewModel = recipeAddViewModel;
    }

    @Override
    public void displayAddedRecipe(RecipeAddOutputData outputData) {
        // Get the current state from the view model
        RecipeAddState recipeAddState = recipeAddViewModel.getState();

        // Set the new recipe data in the state
        recipeAddState.setRecipeName(outputData.getRecipeName());
        recipeAddState.setDescription(outputData.getDescription());
        recipeAddState.setIngredients(outputData.getIngredients());
        recipeAddState.setRecipeID(outputData.getId());  // Use the 'id' from RecipeAddOutputData for the recipe ID

        // Update the view model with the new state
        recipeAddViewModel.setState(recipeAddState);
        System.out.println("viewModel in interactor: " + recipeAddViewModel);
        recipeAddViewModel.firePropertyChanged();

        System.out.println("Presenter setting Recipe ID: " + outputData.getId());
    }


    @Override
    public void displayError(String errorMessage) {
        final RecipeAddState recipeAddState = recipeAddViewModel.getState();
        recipeAddState.setErrorMessage(errorMessage);
        recipeAddViewModel.setState(recipeAddState);
        recipeAddViewModel.firePropertyChanged();
    }
}
