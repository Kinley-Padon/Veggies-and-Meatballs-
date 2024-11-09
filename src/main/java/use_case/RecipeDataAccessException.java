package main.java.use_case;

/**
 * Exception thrown when there is an error with accessing data.
 */
public class RecipeDataAccessException extends Exception {
    public RecipeDataAccessException(String string) {
        super(string);
    }
}