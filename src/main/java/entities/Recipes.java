package entities;

import java.util.HashMap;

/**
 * Represents a recipe with its ID, name and image.
 */

public class Recipes{

    private int ID;
    private String Name;
    private HashMap<String, Integer> recipeDetails;

    // Constructor that accepts a HashMap
    public Recipes(HashMap<String, Integer> recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    // Getter and setter for recipeDetails
    public HashMap<String, Integer> getRecipeDetails() {
        return recipeDetails;
    }

    public void setRecipeDetails(HashMap<String, Integer> recipeDetails) {
        this.recipeDetails = recipeDetails;
    }

    public Recipes() {
    }

    public Recipes(int ID, String Name){
        this.ID = ID;
        this.Name = Name;
    }

    public String getName(){
        return Name;
    }

    public int getID(){
        return ID;
    }

}
