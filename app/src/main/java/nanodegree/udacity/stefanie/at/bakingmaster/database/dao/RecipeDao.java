package nanodegree.udacity.stefanie.at.bakingmaster.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * Created by steffy on 03/09/2017.
 */

@Dao
public interface RecipeDao {

    @Insert(onConflict = REPLACE)
    void insertRecipes(List<Recipe> recipeList);

    @Insert(onConflict = REPLACE)
    void insertRecipe(Recipe recipe);

    @Query("SELECT * FROM Recipe")
    List<Recipe> getAllRecipes();


    @Query("SELECT * FROM Recipe where id = :id")
    Recipe getRecipeForId(int id);

    @Delete
    void deleteRecipeForId(Recipe recipe);
}
