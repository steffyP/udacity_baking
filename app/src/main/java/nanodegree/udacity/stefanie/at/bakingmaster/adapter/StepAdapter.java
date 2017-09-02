package nanodegree.udacity.stefanie.at.bakingmaster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Step;

/**
 * Created by steffy on 20/08/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    public interface StepOnClickCallback{
        void onStepClicked(int position);
    }

    private StepOnClickCallback callback;
    private final ArrayList<Step> steps;
    private final Context context;

    public StepAdapter(Context context, ArrayList<Step> steps, StepOnClickCallback callback) {
        this.context = context;
        this.steps = steps;
        this.callback = callback;
    }

    @Override
    public StepAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_step, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(StepAdapter.ViewHolder holder, int position) {
        Step step = steps.get(position);

        holder.textView.setText(step.getShortDescription());

    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;

        return steps.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.step_short);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.d("test", "clicked step");
            if(callback != null) callback.onStepClicked(getAdapterPosition());
        }
    }
}
