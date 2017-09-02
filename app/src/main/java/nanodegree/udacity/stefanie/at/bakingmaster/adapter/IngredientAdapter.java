package nanodegree.udacity.stefanie.at.bakingmaster.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Ingredient;

/**
 * Created by steffy on 20/08/2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {
    public interface IngredientCheckCallback {
        void ingredientChecked(int position, boolean checked);
    }

    private IngredientCheckCallback callback;
    private final Context context;
    private final ArrayList<Ingredient> ingredients;

    public IngredientAdapter(Context context,
                             ArrayList<Ingredient> ingredients,
                             IngredientCheckCallback callback) {
        this.context = context;
        this.ingredients = ingredients;
        this.callback = callback;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_ingredient, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ingredient i = ingredients.get(position);
        String ingredient = i.getQuantity() + " " + i.getMeasure() + " " + i.getIngredient();
        holder.textView.setText(ingredient);

        if (i.isChecked()) {
            setIngredientChecked(holder.checkbox, holder.textView);
        } else {
            setIngredientUnchecked(holder.checkbox, holder.textView);
        }
    }

    @Override
    public int getItemCount() {
        if (ingredients == null) return 0;
        return ingredients.size();
    }

    private void setIngredientChecked(CheckBox checkbox, TextView textView) {
        checkbox.setChecked(true);
        textView.setTextColor(context.getResources().getColor(R.color.grey));
    }

    private void setIngredientUnchecked(CheckBox checkbox, TextView textView) {
        checkbox.setChecked(false);
        textView.setTextColor(context.getResources().getColor(R.color.black));
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private CheckBox checkbox;
        private TextView textView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.checkbox = itemView.findViewById(R.id.checkbox_ingredient);
            this.textView = itemView.findViewById(R.id.ingredient);

            itemView.setOnClickListener(this);
            checkbox.setClickable(false);
        }

        @Override
        public void onClick(View view) {
            if (checkbox.isChecked()) {
                setIngredientUnchecked(checkbox, textView);
                if (callback != null) callback.ingredientChecked(getAdapterPosition(), false);
            } else {
                setIngredientChecked(checkbox, textView);
                if (callback != null) callback.ingredientChecked(getAdapterPosition(), true);


            }

        }

    }
}
