package nanodegree.udacity.stefanie.at.bakingmaster.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity;
import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.RecipeAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.loader.RecipeLoader;

import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;

/**
 * Created by steffy on 13/08/2017.
 */

public class RecipeListFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<Recipe>>,
        RecipeAdapter.CallbackClickListener {


    private static final String TAG = RecipeListFragment.class.getSimpleName();
    private static final int RECEIPT_LOADER = 112233;
    private View progressBar;
    private RecyclerView recyclerView;
    private View errorView;
    private RecipeAdapter recipeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        progressBar = view.findViewById(R.id.progress);
        errorView = view.findViewById(R.id.error_view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        if (getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT) {

            recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), getResources().getInteger(R.integer.grid_span_count_portrait)));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), getResources().getInteger(R.integer.grid_span_count_landscape)));

        }
        recipeAdapter = new RecipeAdapter(getContext(), this);
        recyclerView.setAdapter(recipeAdapter);

        errorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().restartLoader(RECEIPT_LOADER, null, RecipeListFragment.this);
            }
        });

        getLoaderManager().initLoader(RECEIPT_LOADER, null, this);
        return view;

    }


    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, Bundle args) {
        return new RecipeLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Recipe>> loader, List<Recipe> recipeList) {
        if (recipeList == null || recipeList.isEmpty()) {
            showConnectionUnsuccessful();
        } else {
            updateRecipeList(recipeList);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Recipe>> loader) {
        recipeAdapter.updateData(null);
    }


    private void updateRecipeList(List<Recipe> recipeList) {
        recipeAdapter.updateData(recipeList);
        progressBar.setVisibility(View.GONE);
        recyclerView.setVisibility(View.VISIBLE);
        errorView.setVisibility(View.GONE);
    }

    private void showConnectionUnsuccessful() {
        progressBar.setVisibility(View.GONE);
        errorView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onClick(Recipe recipe) {
        if (recipe == null) return;

        Intent i = new Intent(this.getActivity(), InstructionActivity.class);
        i.putExtra(EXTRA_RECIPE, recipe);
        startActivity(i);

    }
}
