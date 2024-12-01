package entities;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Ingredient {
    private final String name;
    private double quantity;
    private String unit;

    public Ingredient(String name, double quantity, String unit) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
    }

    @JsonCreator
    public Ingredient(@JsonProperty("name") String name, @JsonProperty("quantity") double quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }


    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }


}
