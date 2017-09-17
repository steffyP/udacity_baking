package nanodegree.udacity.stefanie.at.bakingmaster.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;

/**
 * Created by steffy on 20/08/2017.
 */

public class RecipeLoader extends AsyncTaskLoader<List<Recipe>> {


    private static final String RECEIPT_URL = "https://go.udacity.com/android-baking-app-json";
    private static final String TAG = RecipeLoader.class.getSimpleName();
    private ArrayList<Recipe> recipeList;

    public RecipeLoader(Context context) {
        super(context);
    }

    @Override
    public List<Recipe> loadInBackground() {
        Log.d(TAG, "started loading in background...");
        URL url = null;
        try {
            url = new URL(RECEIPT_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setInstanceFollowRedirects(true);
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                InputStream is = urlConnection.getInputStream();

                Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
                String inputString = s.hasNext() ? s.next() : "";
                is.close();


                return createReceiptList(inputString);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<Recipe> createReceiptList(String inputString) {
        ArrayList<Recipe> recipeList = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(inputString);
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    recipeList.add(new Recipe(jsonArray.getJSONObject(i)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        this.recipeList = recipeList;
        return recipeList;
    }

    @Override
    protected void onStartLoading() {
        if(recipeList == null || recipeList.isEmpty())  forceLoad();
        else deliverResult(recipeList);
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
        recipeList = null;
    }

    @Override
    public void onCanceled(List<Recipe> data) {
        super.onCanceled(data);
        recipeList = null;
    }

    @Override
    protected void onReset() {
        onStopLoading();
    }
}
