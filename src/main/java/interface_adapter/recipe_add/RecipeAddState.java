package interface_adapter.recipe_add;

import entities.Ingredient;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


/**
 * Represents the state for adding a recipe.
 */
public class RecipeAddState {
    private String recipeName;
    private String description;
    private List<Ingredient> ingredients;
    private String errorMessage;
    private int recipeID;
    private final PropertyChangeSupport support;

    public RecipeAddState() {
        this.recipeName = "";
        this.description = "";
        this.ingredients = new ArrayList<>();
        this.errorMessage = "";
        this.recipeID = 0;
        this.support = new PropertyChangeSupport(this);

    }

    public void setRecipeName(String recipeName) {
        String oldName = this.recipeName;
        this.recipeName = recipeName;
        support.firePropertyChange("recipeName", oldName, recipeName);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        String oldDescription = this.description;
        this.description = description;
        support.firePropertyChange("description", oldDescription, description);
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        List<Ingredient> oldIngredients = this.ingredients;
        this.ingredients = ingredients;
        support.firePropertyChange("ingredients", oldIngredients, ingredients);
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        String oldErrorMessage = this.errorMessage;
        this.errorMessage = errorMessage;
        support.firePropertyChange("errorMessage", oldErrorMessage, errorMessage);
    }

    public int getRecipeID() {
        return recipeID;
    }

    public void setRecipeID(int recipeID) {
        int oldRecipeID = this.recipeID;
        this.recipeID = recipeID;
        support.firePropertyChange("recipeID", oldRecipeID, recipeID);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}