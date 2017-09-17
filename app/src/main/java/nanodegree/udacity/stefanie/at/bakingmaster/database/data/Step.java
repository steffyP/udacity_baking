package nanodegree.udacity.stefanie.at.bakingmaster.database.data;

/**
 * Created by steffy on 13/08/2017.
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

/**
 * "id": 0,
 * "shortDescription": "Recipe Introduction",
 * "description": "Recipe Introduction",
 * "videoURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
 * "thumbnailURL": ""
 */
@Entity(indices = @Index("recipeId"),
        foreignKeys = @ForeignKey(
        entity = Recipe.class,
        parentColumns = "id",
        childColumns = "recipeId"))
public class Step implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;
    private  int id;
    private  String shortDescription;
    private  String videoURL;
    private  String thumbnailURL;
    private  String description;
    private int recipeId;

    public Step(){

    }
    public Step(JSONObject jsonObject) {
        id = jsonObject.optInt("id");
        shortDescription = jsonObject.optString("shortDescription");
        description = jsonObject.optString("description");
        videoURL = jsonObject.optString("videoURL");
        thumbnailURL = jsonObject.optString("thumbnailURL");
    }

    public Step(Parcel in) {
        id = in.readInt();
        shortDescription = in.readString();
        videoURL = in.readString();
        thumbnailURL = in.readString();
        description = in.readString();
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getVideoURL() {
        return videoURL;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public String getDescription() {
        return description;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }


    @Ignore
    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(shortDescription);
        out.writeString(videoURL);
        out.writeString(thumbnailURL);
        out.writeString(description);
    }

    @Ignore
    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setVideoURL(String videoURL) {
        this.videoURL = videoURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
