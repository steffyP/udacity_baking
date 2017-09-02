package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import nanodegree.udacity.stefanie.at.bakingmaster.adapter.IngredientAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.StepAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.InstructionFragment;

import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_STEP;

/**
 * Created by steffy on 20/08/2017.
 */

public class InstructionActivity extends AppCompatActivity implements InstructionFragment.StepSelectedCallback {


    private static final String TAG = InstructionActivity.class.getSimpleName();
    public static final String EXTRA_RECIPE = "extra_recipe";

    private RecyclerView ingredientsRecyclerview;
    private RecyclerView stepsRecyclerView;
    private Recipe recipe;
    private View ingredientView;
    private ImageView imageViewArrow;
    private boolean twoPane;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruction);
        recipe = getIntent().getParcelableExtra(EXTRA_RECIPE);
        getSupportActionBar().setTitle(recipe.getName());

        Fragment fragment = new InstructionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(EXTRA_RECIPE, recipe);
        fragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        twoPane = false;
        
    }

    @Override
    public void onStepSelected(int pos) {
        if (!twoPane) {
            Intent i = new Intent(this, StepDetailsActivity.class);
            i.putExtra(EXTRA_RECIPE, recipe);
            i.putExtra(EXTRA_STEP, pos);
            startActivity(i);
        } else {

        }
    }
}
