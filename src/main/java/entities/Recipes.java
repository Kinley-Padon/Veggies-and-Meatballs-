package entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

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
    private List<Review> reviews;

    public Recipes(int ID, String Name, String image){
        this.id = ID;
        this.name = Name;
        this.image = image;
        this.reviews = new ArrayList<>();
    }

    @JsonCreator
    public Recipes(@JsonProperty("id") int id, @JsonProperty("name") String name, @JsonProperty("ingredients") List<Ingredient> ingredients,
                   @JsonProperty("description") String description) {
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

    public List<Review> getReviews() {
        return reviews;
    }
}
