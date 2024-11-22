package entities;

/**
 * Represents a recipe with its ID, name and image.
 */

public class Recipes{

    private final int id;
    private final String name;
    private String description;
    private String ingredients;
    private String image;


    public Recipes(int ID, String Name, String image){
        this.id = ID;
        this.name = Name;
        this.image = image;
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
        return String.format("id [%d] name [%s]", id, name);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }
}
