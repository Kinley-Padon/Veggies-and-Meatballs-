package interface_adapter.recipe_search;

public class RecipeViewModel {
    private final RecipeState state;

    public RecipeViewModel() {
        this.state = new RecipeState();
    }

    public RecipeState getState() {
        return state;
    }

    public void updateRecipeDetails(String details) {
        state.setRecipeDetails(details);
    }

    public void updateError(String error) {
        state.setErrorMessage(error);
    }
}
