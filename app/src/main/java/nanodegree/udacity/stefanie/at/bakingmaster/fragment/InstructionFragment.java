package nanodegree.udacity.stefanie.at.bakingmaster.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.IngredientAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.adapter.StepAdapter;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Ingredient;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Recipe;

import static nanodegree.udacity.stefanie.at.bakingmaster.InstructionActivity.EXTRA_RECIPE;

/**
 * Created by steffy on 02/09/2017.
 */

public class InstructionFragment extends Fragment implements StepAdapter.StepOnClickCallback {

    public interface StepSelectedCallback {
        void onStepSelected(int pos);
    }

    private Recipe recipe;
    private RecyclerView stepsRecyclerView;
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
        stepsRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_steps);
        stepsRecyclerView.setHasFixedSize(true);
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        stepsRecyclerView.setAdapter(new StepAdapter(getActivity(), recipe.getSteps(), this));

        return view;
    }


    @Override
    public void onStepClicked(int position) {
        if (callback != null) {
            callback.onStepSelected(position);
        }
    }
}
