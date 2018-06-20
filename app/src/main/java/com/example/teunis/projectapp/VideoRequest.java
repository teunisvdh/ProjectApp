package com.example.teunis.projectapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class VideoRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    ArrayList<String> videos = new ArrayList<>();

    public VideoRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotVideos(ArrayList<String> videos);
        void gotVideosError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("onErrorResponse", "onErrorResponse");
        String errorResponse = error.getMessage();
        activity.gotVideosError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("onResponse", "onResponse" + response);
//        ArrayList<String> channels = new ArrayList<>();
        try {
            JSONArray videoArray = response.getJSONArray("items");
            Log.d("JSONArray", "JSONArray: " + videoArray);

            for (int i = 0; i < videoArray.length(); i++) {
                Log.d("loop begin", "begin loop");
                JSONObject content = videoArray.getJSONObject(i);
                JSONObject id = content.getJSONObject("id");
                String url = id.getString("videoId");
                videos.add(url);
                Log.d("loop third", "begin third" + url);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }
        Log.d("gotVideos in request:", "gotVideos" + videos);
        activity.gotVideos(videos);
    }

    public void getVideos(Callback activity, ArrayList finalChannels) {
        Log.d("getVideos", "getVideos");
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        for (int i = 0; i < finalChannels.size(); i++) {
            ChannelItem channel = (ChannelItem) finalChannels.get(i);
            String channelId = channel.url;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/search?part=snippet&channelId=" + channelId + "&maxResults=10&order=viewCount&type=video&videoDuration=medium&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);
            queue.add(jsonObjectRequest);
        }
    }
}
