package nanodegree.udacity.stefanie.at.bakingmaster.data;

import org.json.JSONObject;

/**
 * Created by steffy on 13/08/2017.
 */

/**
 * "quantity": 2,
 * "measure": "CUP",
 * "ingredient": "Graham Cracker crumbs"
 */
public class Ingredient {
    private final int quantity;
    private final String measure;
    private final String ingredient;


    public Ingredient(JSONObject jsonObject) {
        quantity = jsonObject.optInt("quantity");
        measure = jsonObject.optString("measure");
        ingredient = jsonObject.optString("ingredient");
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

}
