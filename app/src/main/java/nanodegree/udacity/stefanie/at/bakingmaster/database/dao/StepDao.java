package nanodegree.udacity.stefanie.at.bakingmaster.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

/**
 * Created by steffy on 03/09/2017.
 */

@Dao
public interface StepDao {

    @Insert(onConflict = IGNORE)
    void insertSteps(List<Step> stepList);

    @Insert(onConflict = IGNORE)
    void insertStep(Step step);

    @Query("SELECT * FROM Step where recipeId = :recipeId")
    List<Step> getStepsForRecipe(int recipeId);

    @Query("DELETE FROM Step where recipeId = :recipeId")
    void deleteStepsForRecipe(int recipeId);
}
