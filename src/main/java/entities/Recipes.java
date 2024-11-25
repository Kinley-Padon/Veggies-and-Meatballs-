package entities;

import java.util.List;

/**
 * Represents a recipe with its ID, name and image.
 */

public class Recipes{

    private final int id;
    private final String name;
    private String description;
    private List<Ingredient> ingredients;
    private String image;


    public Recipes(int ID, String Name, String image){
        this.id = ID;
        this.name = Name;
        this.image = image;
    }

    public Recipes(int id, String name, List<Ingredient> ingredients, String description) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.description = description;
    }

    public String getName(){
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getID(){
        return id;
    }

    @Override
    public String toString() {
        return String.format("<%s>", name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
