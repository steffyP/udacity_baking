package nanodegree.udacity.stefanie.at.bakingmaster.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.database.DatabaseUtil;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;
import static nanodegree.udacity.stefanie.at.bakingmaster.widget.IngredientsWidget.EXTRA_RECIPE_ID;

/**
 * Created by steffy on 08/09/2017.
 */

public class IngredientRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d("factory", "ongetviewfactory called");
        return new ListRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private final Context context;
    private final int appWidgetId;
    private final int recipeId;
    private Recipe recipe;

    public ListRemoteViewsFactory(Context context, Intent intent) {
        this.context = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        recipeId = intent.getIntExtra(EXTRA_RECIPE_ID, 0);

    }

    @Override
    public void onCreate() {
        recipe = DatabaseUtil.getRecipeForId(context, recipeId);
       /* final int id = IngredientsWidgetConfigureActivity.loadRecipeId(context, appWidgetId);
        new AsyncTask<Void, Void, Recipe>() {

            @Override
            protected Recipe doInBackground(Void... voids) {
                recipe = DatabaseUtil.getRecipeForId(context, id);
                return recipe;
            }

            @Override
            protected void onPostExecute(Recipe recipe) {
                onDataSetChanged();
            }*/
     //   }.execute();
    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipe == null ? 0 : recipe.getIngredients().size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.widget_item);
        Ingredient ingredient = recipe.getIngredients().get(i);
        rv.setTextViewText(R.id.measure, ingredient.getQuantity() + " " + ingredient.getMeasure());
        rv.setTextViewText(R.id.ingredient, ingredient.getIngredient());

        return rv;
    }


    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return recipe.getIngredients().get(i).getIngredientId();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


}
