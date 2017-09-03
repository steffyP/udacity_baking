package nanodegree.udacity.stefanie.at.bakingmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Step;
import nanodegree.udacity.stefanie.at.bakingmaster.fragment.DetailsFragment;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;

/**
 * Created by steffy on 01/09/2017.
 */

public class StepDetailsActivity extends AppCompatActivity  {
    public static final String EXTRA_STEP = "extra_step";
    public static final String DETAILS_FRAGMENT = "DETAILS_FRAGMENT";

    private Recipe recipe;
    private int stepPos;

    private DetailsFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);

        if (savedInstanceState != null) {
            stepPos = savedInstanceState.getInt(EXTRA_STEP);
        } else {
            stepPos = getIntent().getExtras().getInt(EXTRA_STEP);
        }
        recipe = getIntent().getExtras().getParcelable(EXTRA_RECIPE);

        if(savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putInt(EXTRA_STEP, stepPos);
            bundle.putParcelable(EXTRA_RECIPE, recipe);
            fragment = new DetailsFragment();
            fragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment, DETAILS_FRAGMENT).commit();
        } else {
            fragment = (DetailsFragment) getSupportFragmentManager().findFragmentByTag(DETAILS_FRAGMENT);
        }


    }


    @Override
    public Intent getParentActivityIntent() {
        Intent intent = super.getParentActivityIntent();
        intent.putExtra(EXTRA_RECIPE, recipe);
        return intent;
    }



}
