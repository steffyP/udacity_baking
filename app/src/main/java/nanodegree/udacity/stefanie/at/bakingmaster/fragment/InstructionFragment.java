package nanodegree.udacity.stefanie.at.bakingmaster.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.IngredientAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.StepAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;
import static nanodegree.udacity.stefanie.at.bakingmaster.StepDetailsActivity.EXTRA_STEP;

/**
 * Created by steffy on 02/09/2017.
 */

public class InstructionFragment extends Fragment implements IngredientAdapter.IngredientCheckCallback, StepAdapter.StepOnClickCallback {

    public interface StepSelectedCallback {
        void onStepSelected(int pos);
    }

    private Recipe recipe;
    private RecyclerView ingredientsRecyclerview;
    private RecyclerView stepsRecyclerView;
    private View ingredientView;
    private ImageView imageViewArrow;
    private StepSelectedCallback callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof StepSelectedCallback) {
            callback = (StepSelectedCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);


        if (getArguments() != null) {
            recipe = getArguments().getParcelable(EXTRA_RECIPE);
        }
        int servings = recipe.getServings();
        if (servings > 0) {
            ((TextView) view.findViewById(R.id.servings)).setText(getString(R.string.for_servings, servings));
        }

        ingredientsRecyclerview = (RecyclerView) view.findViewById(R.id.recycler_view_ingredients);
        stepsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_steps);

        ingredientsRecyclerview.setHasFixedSize(true);
        stepsRecyclerView.setHasFixedSize(true);

        ingredientsRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ingredientsRecyclerview.setAdapter(new IngredientAdapter(getActivity(), recipe.getIngredients(), this));

        stepsRecyclerView.setAdapter(new StepAdapter(getActivity(), recipe.getSteps(), this));

        ingredientView = view.findViewById(R.id.details_ingredients);
        imageViewArrow = (ImageView) view.findViewById(R.id.iv_arrow);

        ingredientView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ingredientsRecyclerview.getVisibility() == View.VISIBLE) {
                    imageViewArrow.setImageResource(R.drawable.ic_arrow_down);
                    ingredientsRecyclerview.setVisibility(View.GONE);
                } else {
                    imageViewArrow.setImageResource(R.drawable.ic_arrow_up);
                    ingredientsRecyclerview.setVisibility(View.VISIBLE);
                }
            }
        });

        testAllIngredientsAreChecked();

        return view;
    }

    @Override
    public void ingredientChecked(int position, boolean checked) {
        recipe.getIngredients().get(position).setChecked(checked);
        testAllIngredientsAreChecked();
    }

    private void testAllIngredientsAreChecked() {
        boolean allChecked = true;
        for (Ingredient i : recipe.getIngredients()) {
            allChecked &= i.isChecked();
        }
        if (allChecked) {
            ingredientsRecyclerview.setVisibility(View.GONE);
            imageViewArrow.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    @Override
    public void onStepClicked(int position) {
        if (callback != null) callback.onStepSelected(position);
    }
}
