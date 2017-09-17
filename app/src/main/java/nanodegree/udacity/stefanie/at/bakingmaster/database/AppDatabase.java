package nanodegree.udacity.stefanie.at.bakingmaster.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import nanodegree.udacity.stefanie.at.bakingmaster.adapter.RecipeAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.database.dao.IngredientDao;
import nanodegree.udacity.stefanie.at.bakingmaster.database.dao.RecipeDao;
import nanodegree.udacity.stefanie.at.bakingmaster.database.dao.StepDao;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;

/**
 * Created by steffy on 03/09/2017.
 */

@Database(entities = {
        Recipe.class,
        Ingredient.class,
        Step.class
},
        version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract IngredientDao ingredientDao();

    public abstract StepDao stepDao();

    public abstract RecipeDao recipeDao();


    public synchronized static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context,
                    AppDatabase.class, "recipe.db").allowMainThreadQueries().build();
        }
        return instance;
    }

}