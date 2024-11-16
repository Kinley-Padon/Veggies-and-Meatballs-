package interface_adapter.recipe_search;

public class RecipeState {
    private String recipeDetails;
    private String errorMessage;

    public String getRecipeDetails() {
        return recipeDetails;
    }

    public void setRecipeDetails(String recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}