package use_case.recipe_add;

import java.sql.SQLException;

public class RecipeAddDataAccessException extends RuntimeException {
    public RecipeAddDataAccessException(String message) {
        super(message);
    }

    public RecipeAddDataAccessException(String message, SQLException cause) {
        super(message, cause);
    }
}
