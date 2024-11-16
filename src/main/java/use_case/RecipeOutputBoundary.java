package use_case;
/**
 * The output boundary for the Recipe Search Use Case.
 */
public interface RecipeOutputBoundary {
    /**
     * Prepares the success view for the Note related Use Cases.
     * @param recipeName the output data
     */
    void prepareSuccessView(String recipeName);
}
