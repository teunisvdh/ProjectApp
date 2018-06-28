/* VideoRequest is a request class for retrieving YouTube video information. For each retrieved id
 * it starts a JSON query for retrieving information. For each video a VideoItem will be created.
 * This item will be added to a list of videos to choose from for the playlist.
 */

package com.example.teunis.projectapp.Requests;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teunis.projectapp.Items.VideoItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoInfoRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    ArrayList<VideoItem> videosWithInfo = new ArrayList<>();

    public VideoInfoRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotVideoInfo(ArrayList<VideoItem> videosWithInfo);
        void gotVideoInfoError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorResponse = error.getMessage();
        activity.gotVideoInfoError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray videoInfoArray = response.getJSONArray("items");

            for (int i = 0; i < videoInfoArray.length(); i++) {
                JSONObject content = videoInfoArray.getJSONObject(i);
                JSONObject snippet = content.getJSONObject("snippet");
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                JSONObject modeImage = thumbnails.getJSONObject("default");
                String image = modeImage.getString("url");
                String description = snippet.getString("description");
                String title = snippet.getString("title");
                String channel = snippet.getString("channelTitle");
                String id = content.getString("id");

                // convert string time from API (structure ...M, ...S or ...M...S) to float
                JSONObject contentDetails = content.getJSONObject("contentDetails");
                String durationString = contentDetails.getString("duration");
                durationString = durationString.replace("PT", "");
                float minutes;
                float seconds;
                if (durationString.contains("S") && durationString.contains("M")) {
                    durationString = durationString.replace("S", "");
                    String[] separatedDurationString = durationString.split("M");
                    minutes = Float.valueOf(separatedDurationString[0]);
                    seconds = Float.valueOf(separatedDurationString[1]);
                }
                else if (durationString.contains("S")) {
                    durationString = durationString.replace("S", "");
                    minutes = 0;
                    seconds = Float.valueOf(durationString);
                }
                else {
                    durationString = durationString.replace("M", "");
                    minutes = Float.valueOf(durationString);
                    seconds = 0;
                }

                float duration = minutes + seconds/60;
                VideoItem thisVideo = new VideoItem(id, description, channel, title, image, duration);

                videosWithInfo.add(thisVideo);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }
        activity.gotVideoInfo(videosWithInfo);
    }

    public void getVideoInfo(Callback activity, ArrayList<String> videoIds) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        String videoIdsString = TextUtils.join(", ", videoIds);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/videos?part=snippet,%20contentDetails&id="
                + videoIdsString + "&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);
        queue.add(jsonObjectRequest);
    }
}
