package nanodegree.udacity.stefanie.at.bakingmaster;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nanodegree.udacity.stefanie.at.bakingmaster.adapter.IngredientAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.StepAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;

/**
 * Created by steffy on 20/08/2017.
 */

public class InstructionFragment extends Fragment {


    private static final String TAG = InstructionFragment.class.getSimpleName();

    private RecyclerView ingredientsRecyclerview;
    private RecyclerView stepsRecyclerView;
    private Recipe recipe;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_instruction, container, false);

        Bundle bundle = getArguments();
        recipe = bundle.getParcelable(RecipeListFragment.RECIPE_ARGUMENT);

        int servings = recipe.getServings();
        if(servings > 0) {
            ((TextView) view.findViewById(R.id.servings)).setText(getActivity().getString(R.string.for_servings, servings));
        }

        ingredientsRecyclerview = view.findViewById(R.id.recycler_view_ingredients);
        stepsRecyclerView = view.findViewById(R.id.recycler_view_steps);

        ingredientsRecyclerview.setHasFixedSize(true);
        stepsRecyclerView.setHasFixedSize(true);

        ingredientsRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        ingredientsRecyclerview.setAdapter(new IngredientAdapter(getContext(), recipe.getIngredients()));

        stepsRecyclerView.setAdapter(new StepAdapter(getContext(), recipe.getSteps()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(stepsRecyclerView.getContext(),
                getResources().getConfiguration().orientation);
        stepsRecyclerView.addItemDecoration(dividerItemDecoration);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(recipe.getName());
    }

}
