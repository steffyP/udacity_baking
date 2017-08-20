package nanodegree.udacity.stefanie.at.bakingmaster.data;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * Created by steffy on 13/08/2017.
 */

/**
 * "quantity": 2,
 * "measure": "CUP",
 * "ingredient": "Graham Cracker crumbs"
 */
public class Ingredient implements  Parcelable{
    private final int quantity;
    private final String measure;
    private final String ingredient;


    public Ingredient(JSONObject jsonObject) {
        quantity = jsonObject.optInt("quantity");
        measure = jsonObject.optString("measure");
        ingredient = jsonObject.optString("ingredient");
    }

    public Ingredient(Parcel in) {
        quantity = in.readInt();
        measure = in.readString();
        ingredient = in.readString();
    }


    public int getQuantity() {
        return quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public static final Parcelable.Creator<Ingredient> CREATOR
            = new Parcelable.Creator<Ingredient>() {
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
    }
}
