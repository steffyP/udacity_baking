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
    private final double quantity;
    private final String measure;
    private final String ingredient;
    private  boolean checked;


    public Ingredient(JSONObject jsonObject) {
        quantity = jsonObject.optDouble("quantity");
        measure = jsonObject.optString("measure");
        ingredient = jsonObject.optString("ingredient");
    }

    public Ingredient(Parcel in) {
        quantity = in.readDouble();
        measure = in.readString();
        ingredient = in.readString();
        int checkValue = in.readInt();
        checked = checkValue == 0 ? false : true;
    }


    public double getQuantity() {
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
        out.writeDouble(quantity);
        out.writeString(measure);
        out.writeString(ingredient);
        int checkedValue = checked ? 1 : 0;
        out.writeInt(checkedValue);
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return quantity + " " + measure + " " + ingredient;
    }
}
