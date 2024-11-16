package entities;

/**
 * Represents a recipe with its ID, name and image.
 */

public class Recipes{

    private int ID;
    private String Name;

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
