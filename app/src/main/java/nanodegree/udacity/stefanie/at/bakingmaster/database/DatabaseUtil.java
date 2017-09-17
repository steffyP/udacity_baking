package nanodegree.udacity.stefanie.at.bakingmaster.database;

import android.content.Context;
import android.os.AsyncTask;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;

/**
 * Created by steffy on 03/09/2017.
 */

public class DatabaseUtil {

    public static void insertRecipeInBackground(final Context context, final Recipe recipe) {

        AppDatabase db = AppDatabase.getInstance(context);
        db.recipeDao().insertRecipe(recipe);
        if (recipe.getIngredients() != null) {
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.setRecipeId(recipe.getId());
            }
            db.ingredientDao().insertIngredients(recipe.getIngredients());
        }

        if (recipe.getSteps() != null) {
            for (Step s : recipe.getSteps()) {
                s.setRecipeId(recipe.getId());
            }
            db.stepDao().insertSteps(recipe.getSteps());

        }
    }


    public static Recipe getRecipeForId(Context context, int id) {
        AppDatabase db = AppDatabase.getInstance(context);
        Recipe recipe = db.recipeDao().getRecipeForId(id);
        if (recipe != null) {
            recipe.setIngredients(db.ingredientDao().getIngredientsForRecipe(recipe.getId()));
            recipe.setSteps(db.stepDao().getStepsForRecipe(recipe.getId()));
        }
        return recipe;
    }
}
