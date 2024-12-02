package interface_adapter.recipe_search;

import interface_adapter.ViewModel;

public class RecipeViewModel extends ViewModel<RecipeState> {
    public RecipeViewModel() {
        super("note");
        setState(new RecipeState());
    }
}
