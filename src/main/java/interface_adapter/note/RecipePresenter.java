package interface_adapter.note;

public class RecipePresenter implements RecipeOutputBoundary {

    @Override
    public void prepareSuccessView(String message) {
        System.out.println("Recipe found: " + message);
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("Failed to find recipe: " + errorMessage);
    }
}
