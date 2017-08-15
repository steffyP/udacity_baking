package nanodegree.udacity.stefanie.at.bakingmaster;

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

import nanodegree.udacity.stefanie.at.bakingmaster.data.Receipt;

/**
 * Created by steffy on 13/08/2017.
 */

class ReceiptAdapter extends RecyclerView.Adapter<ReceiptAdapter.ViewHolder> {
    private static final String TAG = ReceiptAdapter.class.getSimpleName();
    private final Context context;
    private List<Receipt> receiptList;

    public ReceiptAdapter(Context context) {
        this.receiptList = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card_main, parent, false);
        ReceiptAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Receipt receipt = receiptList.get(position);
        holder.textReceiptName.setText(receipt.getName());
        String image = receipt.getImage();
        if (!image.isEmpty())
            Picasso.with(context).load(image).error(R.drawable.default_img).into(holder.imageView);

        String details = "";
        if (receipt.getServings() != 0){
            details += context.getString(R.string.servings, receipt.getServings());
            details += "\n";
        }

        details += context.getString(R.string.steps, receipt.getSteps().size());

        holder.textDetails.setText(details);

    }


    @Override
    public int getItemCount() {
        if (receiptList == null) return 0;
        else return receiptList.size();
    }

    public void updateData(List<Receipt> receiptList) {
        this.receiptList.clear();
        this.receiptList.addAll(receiptList);
        this.notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
