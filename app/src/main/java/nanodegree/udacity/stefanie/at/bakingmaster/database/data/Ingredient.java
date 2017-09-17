package nanodegree.udacity.stefanie.at.bakingmaster.database.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
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
@Entity(indices = {
            @Index("recipeId"),
            @Index(value = {"recipeId", "quantity", "measure", "ingredient"},
                unique = true)},
        foreignKeys = @ForeignKey(
        entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId")
    )
public class Ingredient implements Parcelable {
    private double quantity;
    private String measure;
    private String ingredient;
    @Ignore
    private boolean checked;
    private int recipeId;

    @PrimaryKey(autoGenerate = true)
    private int ingredientId;


    public Ingredient() {

    }

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

    public int getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
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

    @Ignore
    public boolean isChecked() {
        return checked;
    }

    @Ignore
    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return quantity + " " + measure + " " + ingredient;
    }

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }
}
