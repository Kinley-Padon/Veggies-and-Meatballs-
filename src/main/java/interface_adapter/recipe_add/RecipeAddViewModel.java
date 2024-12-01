package interface_adapter.recipe_add;

import interface_adapter.ViewModel;

public class RecipeAddViewModel extends ViewModel<RecipeAddState> {
    public RecipeAddViewModel() {
        super("Add New Recipe");
        setState(new RecipeAddState());
    }
}