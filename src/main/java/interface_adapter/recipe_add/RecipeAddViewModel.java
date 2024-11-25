package interface_adapter.recipe_add;

import entities.Ingredient;
import interface_adapter.ViewModel;
import use_case.recipe_add.RecipeAddInputBoundary;

import java.util.List;

public class RecipeAddViewModel extends ViewModel<RecipeAddState> {

    public static final String TITLE_LABEL = "Add a New Recipe";
    public static final String RECIPE_NAME_LABEL = "Enter Recipe Name";
    public static final String RECIPE_DESCRIPTION_LABEL = "Enter Recipe Description";
    public static final String INGREDIENTS_LABEL = "Enter Ingredients (comma-separated)";
    public static final String IMAGE_URL_LABEL = "Enter Image URL";

    public static final String ADD_BUTTON_LABEL = "Add Recipe";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    public static final String TO_RECIPE_LIST_BUTTON_LABEL = "Back to Recipe List";
    private RecipeAddInputBoundary recipeAddInputBoundary;

    public RecipeAddViewModel() {
        super("add recipe");
        setState(new RecipeAddState());
    }

    public void setRecipeID(int id) {
        getState().setRecipeID(id);
    }

    public void setRecipeName(String name) {
        getState().setRecipeName(name);
    }

    public void setRecipeDescription(String description) {
        getState().setDescription(description);
    }

    public void setIngredients(List<Ingredient> ingredients) {
        getState().setIngredients(ingredients);
    }

    public void setImageUrl(String imageUrl) {
        getState().setImageUrl(imageUrl);
    }

    public void submitRecipe(List<Ingredient> ingredients, String steps) {
        String recipeName = "Recipe Name";
        recipeAddInputBoundary.addRecipe(recipeName, ingredients, steps);
    }

    public void setAddRecipeInputBoundary(RecipeAddInputBoundary addRecipeInputBoundary) {
        this.recipeAddInputBoundary = addRecipeInputBoundary;
    }
}
