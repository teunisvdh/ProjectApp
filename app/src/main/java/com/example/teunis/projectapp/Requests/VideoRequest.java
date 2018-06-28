/* VideoRequest is a request class for retrieving YouTube video-ids. For each channel it starts a
 * JSON query for retrieving a maximum of 10 videos. With the id an information request can be started.
 */

package com.example.teunis.projectapp.Requests;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teunis.projectapp.Items.ChannelItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    ArrayList<String> videos = new ArrayList<>();
    int finalChannelsSize;
    int count = 0;

    public VideoRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotVideos(ArrayList<String> videos);
        void gotVideosError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorResponse = error.getMessage();
        activity.gotVideosError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray videoArray = response.getJSONArray("items");

            for (int i = 0; i < videoArray.length(); i++) {
                JSONObject content = videoArray.getJSONObject(i);
                JSONObject id = content.getJSONObject("id");
                String url = id.getString("videoId");
                videos.add(url);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }

        count = count + 1;
        if (count == finalChannelsSize) {
            activity.gotVideos(videos);
        }
    }

    public void getVideos(Callback activity, ArrayList finalChannels) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        finalChannelsSize = finalChannels.size();

        for (int i = 0; i < finalChannelsSize; i++) {
            ChannelItem channel = (ChannelItem) finalChannels.get(i);
            String channelId = channel.url;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId="
                    + channelId + "&maxResults=10&order=viewCount&type=video&videoDuration=medium&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);
            queue.add(jsonObjectRequest);
        }
    }
}
