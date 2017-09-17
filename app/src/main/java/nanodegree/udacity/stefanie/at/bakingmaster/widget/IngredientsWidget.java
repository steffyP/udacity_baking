package nanodegree.udacity.stefanie.at.bakingmaster.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;

import nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity;
import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.database.AppDatabase;
import nanodegree.udacity.stefanie.at.bakingmaster.database.DatabaseUtil;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link IngredientsWidgetConfigureActivity IngredientsWidgetConfigureActivity}
 */
public class IngredientsWidget extends AppWidgetProvider {

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";

    static void updateAppWidget(final Context context, final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            //updateAppWidget(context, appWidgetManager, appWidgetId);
            updateRemoteViewAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    static void updateRemoteViewAppWidget(final Context context, final AppWidgetManager appWidgetManager, final int appWidgetId) {
        // Set up the intent that starts the StackViewService, which will
        // provide the views for this collection.

        int id = IngredientsWidgetConfigureActivity.loadRecipeId(context, appWidgetId);
        if (id > 0) {
            Recipe recipe = DatabaseUtil.getRecipeForId(context, id);

            Intent intent = new Intent(context, IngredientRemoteViewService.class);
            // Add the app widget ID to the intent extras.
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            intent.putExtra(EXTRA_RECIPE_ID, recipe.getId());


            // Instantiate the RemoteViews object for the app widget layout.
            final RemoteViews rv = new RemoteViews(context.getPackageName(), R.layout.ingredients_widget);
            rv.setTextViewText(R.id.appwidget_title, recipe.getName());
            // Set up the RemoteViews object to use a RemoteViews adapter.
            // This adapter connects
            // to a RemoteViewsService  through the specified intent.
            // This is how you populate the data.
            Intent startActivity = new Intent(context, InstructionActivity.class);
            Bundle b = new Bundle();
            b.putParcelable(EXTRA_RECIPE, recipe);
            startActivity.putExtras(b);
            rv.setRemoteAdapter(appWidgetId, R.id.appwidget_content, intent);

            rv.setEmptyView(R.id.listview_recipe, R.id.no_view);


            PendingIntent pending = PendingIntent.getActivity(context, appWidgetId, startActivity,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            rv.setOnClickPendingIntent(R.id.container, pending);

            appWidgetManager.updateAppWidget(appWidgetId, rv);
        }


    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            IngredientsWidgetConfigureActivity.deleteIngredients(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

