package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.DetailsFragment;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.InstructionFragment;

import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_STEP;

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

        View view = findViewById(R.id.container_details);
        if (view == null) {
            twoPane = false;
        } else {
            twoPane = true;
        }

        if (twoPane) {
            if (savedInstanceState == null) {
                stepPos = 0;
            } else {
                stepPos = savedInstanceState.getInt(EXTRA_STEP);
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
        outState.putInt(EXTRA_STEP, stepPos);
    }

    private void setContentTwoPane() {
        fragment = new DetailsFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(EXTRA_STEP, stepPos);
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
            i.putExtra(EXTRA_STEP, pos);
            startActivity(i);
        } else {
            stepPos = 0;
            fragment.updateContent(pos);
        }
    }
}
