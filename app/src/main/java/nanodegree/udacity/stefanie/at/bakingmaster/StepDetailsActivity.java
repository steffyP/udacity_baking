package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.DetailsFragment;

import static android.content.res.Configuration.ORIENTATION_LANDSCAPE;
import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;

/**
 * Created by steffy on 01/09/2017.
 */

public class StepDetailsActivity extends AppCompatActivity  {
    public static final String EXTRA_POSITION = "extra_step";
    public static final String DETAILS_FRAGMENT = "DETAILS_FRAGMENT";

    private Recipe recipe;
    private int stepPos;

    private DetailsFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(getResources().getConfiguration().orientation == ORIENTATION_LANDSCAPE){
            this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getSupportActionBar().hide();
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_step);

        if (savedInstanceState != null) {
            stepPos = savedInstanceState.getInt(EXTRA_POSITION);
        } else {
            stepPos = getIntent().getExtras().getInt(EXTRA_POSITION);
        }
        recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);


        if(savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(EXTRA_POSITION, stepPos);
            bundle.putParcelable(EXTRA_RECIPE, recipe);
            fragment = new DetailsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, DETAILS_FRAGMENT).commit();
        } else {
            fragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT);
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
