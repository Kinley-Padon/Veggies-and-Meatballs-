package entities;


public class Ingredient {
    private final String name;
    private final String image;
    private final double quantity;
    private final String unit;

    public Ingredient(String name, String image, double quantity, String unit) {
        this.name = name;
        this.image = image;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }
}
