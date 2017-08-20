package nanodegree.udacity.stefanie.at.bakingmaster.data;

/**
 * Created by steffy on 13/08/2017.
 */

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
public class Step implements Parcelable {

    private final int id;
    private final String shortDescription;
    private final String videoURL;
    private final String thumbnailURL;
    private final String description;

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(id);
        out.writeString(shortDescription);
        out.writeString(videoURL);
        out.writeString(thumbnailURL);
        out.writeString(description);
    }

    public static final Parcelable.Creator<Step> CREATOR
            = new Parcelable.Creator<Step>() {
        public Step createFromParcel(Parcel in) {
            return new Step(in);
        }

        public Step[] newArray(int size) {
            return new Step[size];
        }
    };
}
