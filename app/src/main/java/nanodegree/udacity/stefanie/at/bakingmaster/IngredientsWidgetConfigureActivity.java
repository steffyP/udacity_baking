package nanodegree.udacity.stefanie.at.bakingmaster;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.loader.RecipeLoader;

/**
 * The configuration screen for the {@link IngredientsWidget IngredientsWidget} AppWidget.
 */
public class IngredientsWidgetConfigureActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>> {

    private static final String PREFS_NAME = "nanodegree.udacity.stefanie.at.bakingmaster.IngredientsWidget";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    private static final String PREF_INGREDIENT = "ingredient_";
    private static final String PREF_RECIPE_TITLE = "recipe_";
    private static final int RECEIPT_LOADER_WIDGET = 2222;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private ListView listView;

    private ArrayAdapter<String> recipeAdapter;
    private View noTextView;

    public IngredientsWidgetConfigureActivity() {
        super();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveIngredients(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + PREF_INGREDIENT + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadIngredients(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + PREF_INGREDIENT + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.recipe_not_found);
        }
    }

    static void deleteIngredients(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + PREF_INGREDIENT + appWidgetId);
        prefs.apply();
    }

    // Write the prefix to the SharedPreferences object for this widget
    static void saveRecipe(Context context, int appWidgetId, String text) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + PREF_RECIPE_TITLE + appWidgetId, text);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadRecipe(Context context, int appWidgetId) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + PREF_RECIPE_TITLE + appWidgetId, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return context.getString(R.string.recipe_not_found);
        }
    }

    static void deleteRecipe(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + PREF_RECIPE_TITLE + appWidgetId);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.ingredients_widget_configure);

        listView = findViewById(R.id.listview_recipe);
        noTextView = findViewById(R.id.no_recipe);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }
        getSupportLoaderManager().initLoader(RECEIPT_LOADER_WIDGET, null, this);

        //   mAppWidgetText.setText(loadIngredients(IngredientsWidgetConfigureActivity.this, mAppWidgetId));
    }


    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, final List<Recipe> data) {
        if (data == null || data.isEmpty()) {
            listView.setVisibility(View.GONE);
            noTextView.setVisibility(View.VISIBLE);
        } else {
            List<String> recipeTitle = new ArrayList<>();
            for (Recipe r : data) {
                recipeTitle.add(r.getName());
            }
            recipeAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeTitle);
            listView.setAdapter(recipeAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                    Recipe recipe = data.get(pos);
                    final Context context = IngredientsWidgetConfigureActivity.this;

                    saveRecipe(context, mAppWidgetId, recipe.getName());
                    saveIngredients(context, mAppWidgetId, getIngredientsFromReceipt(recipe));
                    // It is the responsibility of the configuration activity to update the app widget
                    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                    IngredientsWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);

                    // Make sure we pass back the original appWidgetId
                    Intent resultValue = new Intent();
                    resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                    setResult(RESULT_OK, resultValue);
                    finish();
                }
            });
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
    }


    private String getIngredientsFromReceipt(Recipe recipe) {

        String ingredients = "";
        for (Ingredient i : recipe.getIngredients()) {
            ingredients += "- " + i.toString() + "\n";
        }
        if (ingredients.length() > 0) {
            ingredients = ingredients.substring(0, ingredients.length() - 1);
        }
        return ingredients;
    }
}

