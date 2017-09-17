package nanodegree.udacity.stefanie.at.bakingmaster.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Ingredient;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by steffy on 03/09/2017.
 */

@Dao
public interface IngredientDao {

    @Insert(onConflict = REPLACE)
    void insertIngredients(List<Ingredient> ingredientList);


    @Insert(onConflict = REPLACE)
    void insertIngredient(Ingredient ingredient);

    @Query("SELECT * FROM Ingredient WHERE recipeId = :recipeId")
    List<Ingredient> getIngredientsForRecipe(int recipeId);

    @Query("DELETE FROM Ingredient where recipeId = :recipeId")
    void deleteIngredientsForRecipe(int recipeId);
}
