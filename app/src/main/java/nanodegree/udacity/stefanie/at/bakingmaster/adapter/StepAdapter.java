package nanodegree.udacity.stefanie.at.bakingmaster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.database.data.Step;

/**
 * Created by steffy on 20/08/2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.ViewHolder> {
    private static final int TYPE_INGREDIENTS = 0;
    private static final int TYPE_STEP = 1;

    public interface StepOnClickCallback {
        void onStepClicked(int position);
    }

    private StepOnClickCallback callback;
    private final List<Step> steps;
    private final Context context;

    public StepAdapter(Context context, List<Step> steps, StepOnClickCallback callback) {
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
        if (getItemViewType(position) == TYPE_INGREDIENTS) {
            holder.textView.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            holder.textView.setText(context.getString(R.string.ingredients));
        } else if (getItemViewType(position) == TYPE_STEP) {
            holder.textView.setTextColor(context.getResources().getColor(R.color.grey));

            Step step = steps.get(position - 1);
            holder.textView.setText(step.getShortDescription());
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return TYPE_INGREDIENTS;
        return TYPE_STEP;
    }

    @Override
    public int getItemCount() {
        if (steps == null) return 0;

        return steps.size() + 1;
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
            if (callback != null) callback.onStepClicked(getAdapterPosition());
        }
    }
}
