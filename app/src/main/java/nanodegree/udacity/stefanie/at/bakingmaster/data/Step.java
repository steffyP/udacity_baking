package nanodegree.udacity.stefanie.at.bakingmaster.data;

/**
 * Created by steffy on 13/08/2017.
 */

import org.json.JSONObject;

/**
 * "id": 0,
 * "shortDescription": "Recipe Introduction",
 * "description": "Recipe Introduction",
 * "videoURL": "https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4",
 * "thumbnailURL": ""
 */
public class Step {

    private final int id;
    private final String shortDescription;
    private final String videoURL;
    private final String thumnailURL;

    public Step(JSONObject jsonObject){
        id = jsonObject.optInt("id");
        shortDescription = jsonObject.optString("shortDescription");
        videoURL = jsonObject.optString("videoURL");
        thumnailURL = jsonObject.optString("thumbnailURL");
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

    public String getThumnailURL() {
        return thumnailURL;
    }
}
