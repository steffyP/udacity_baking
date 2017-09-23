package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.DetailsFragment;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.InstructionFragment;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static android.content.res.Configuration.ORIENTATION_PORTRAIT;
import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_POSITION;

/**
 * Created by steffy on 20/08/2017.
 */

public class InstructionActivity extends AppCompatActivity implements InstructionFragment.StepSelectedCallback {


    private static final String TAG = InstructionActivity.class.getSimpleName();
    public static final String EXTRA_RECIPE = "extra_recipe";


    private Recipe recipe;
    private boolean twoPane;
    private Step step;
    private int stepPos;
    private DetailsFragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        getIntent().setExtrasClassLoader(Recipe.class.getClassLoader());

        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);

        Fragment fragment = new InstructionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RECIPE, recipe);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();


        twoPane = getResources().getInteger(R.integer.sw_600) == 1;
        if(twoPane && getResources().getConfiguration().orientation == ORIENTATION_PORTRAIT){
            twoPane = false;
        }



        if (twoPane) {
            if (savedInstanceState == null) {
                stepPos = 0;
            } else {
                stepPos = savedInstanceState.getInt(EXTRA_POSITION);
            }
            step = recipe.getSteps().get(stepPos);
            setContentTwoPane();
        } else {
            getSupportActionBar().setTitle(recipe.getName());

        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_POSITION, stepPos);
    }

    private void setContentTwoPane() {
        fragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_POSITION, stepPos);
        bundle.putParcelable(EXTRA_RECIPE, recipe);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container_details, fragment)
                .commit();

        getSupportActionBar().setTitle(step.getShortDescription());
    }

    @Override
    public void onStepSelected(int pos) {
        if (!twoPane) {
            Intent i = new Intent(this, StepDetailsActivity.class);
            i.putExtra(EXTRA_RECIPE, recipe);
            i.putExtra(EXTRA_POSITION, pos);
            startActivity(i);
        } else {
            stepPos = 0;
            fragment.updateContent(pos);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
