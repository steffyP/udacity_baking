package nanodegree.udacity.stefanie.at.bakingmaster.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import nanodegree.udacity.stefanie.at.bakingmaster.R;
import nanodegree.udacity.stefanie.at.bakingmaster.data.Recipe;

/**
 * Created by steffy on 13/08/2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {
    public interface CallbackClickListener {
        void onClick(Recipe recipe);
    }

    private static final String TAG = RecipeAdapter.class.getSimpleName();
    private final Context context;
    private List<Recipe> recipeList;
    private CallbackClickListener callbackClickListener;

    public RecipeAdapter(Context context, CallbackClickListener callback) {
        this.recipeList = new ArrayList<>();
        this.context = context;
        this.callbackClickListener = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_main, parent, false);
        RecipeAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.textReceiptName.setText(recipe.getName());
        String image = recipe.getImage();
        if (!image.isEmpty())
            Picasso.with(context).load(image).error(R.drawable.default_img).into(holder.imageView);

        String details = "";
        if (recipe.getServings() != 0){
            details += context.getString(R.string.servings, recipe.getServings());
            details += "\n";
        }

        details += context.getString(R.string.preparation_steps, recipe.getSteps().size());

        holder.textDetails.setText(details);

    }


    @Override
    public int getItemCount() {
        if (recipeList == null) return 0;
        else return recipeList.size();
    }

    public void updateData(List<Recipe> recipeList) {
        this.recipeList.clear();
        if(recipeList != null)  this.recipeList.addAll(recipeList);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView textReceiptName;
        ImageView imageView;
        TextView textDetails;
        TextView buttonShowReceipt;


        public ViewHolder(View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_view);
            textReceiptName = itemView.findViewById(R.id.txt_recipe_title);
            imageView = itemView.findViewById(R.id.img_recipe);
            textDetails = itemView.findViewById(R.id.txt_overview);
            buttonShowReceipt = itemView.findViewById(R.id.btn_show_receipt);

            buttonShowReceipt.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            if(callbackClickListener != null){
                int pos = getAdapterPosition();
                Recipe recipe = null;
                if(pos != -1){
                    recipe = recipeList.get(pos);
                }
                callbackClickListener.onClick(recipe);
            }
        }
    }
}
