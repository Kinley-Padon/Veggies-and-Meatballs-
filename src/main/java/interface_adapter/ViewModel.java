package interface_adapter;


import interface_adapter.note.RecipeState;

public class ViewModel {
    private RecipeState state;

    public ViewModel() {
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
