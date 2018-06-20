package com.example.teunis.projectapp;

import android.content.Context;
import android.text.TextUtils;
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
        Log.d("onErrorResponse", "onErrorResponse");
        String errorResponse = error.getMessage();
        activity.gotVideoInfoError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("onResponse", "onResponse" + response);
//        ArrayList<String> channels = new ArrayList<>();
        try {
            JSONArray videoInfoArray = response.getJSONArray("items");
            Log.d("VideoInfoArray", "VideoInfoArray: " + videoInfoArray);

            for (int i = 0; i < videoInfoArray.length(); i++) {
                Log.d("loop begin", "begin loop");
                JSONObject content = videoInfoArray.getJSONObject(i);
                Log.d("1", "1");


                JSONObject snippet = content.getJSONObject("snippet");
                Log.d("2", "2");
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                Log.d("3", "3");
                JSONObject modeImage = thumbnails.getJSONObject("default");
                Log.d("4", "4");
                String image = modeImage.getString("url");
                Log.d("5", "5");
                String description = snippet.getString("description");
                Log.d("6", "6");
                String title = snippet.getString("title");
                Log.d("7", "7");
                String channel = snippet.getString("channelTitle");
                Log.d("8", "8");
                String id = content.getString("id");

                Log.d("HEUJJJJJ", "HEUJJJJJ begin contentDetails");
                JSONObject contentDetails = content.getJSONObject("contentDetails");
                String durationString = contentDetails.getString("duration");
                durationString = durationString.replace("PT", "");
                float minutes = 0;
                float seconds = 0;
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
                Log.d("loop third", "begin third" + videosWithInfo);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }
        Log.d("gotVideos in request:", "gotVideos" + videosWithInfo);
        activity.gotVideoInfo(videosWithInfo);
    }

    public void getVideoInfo(Callback activity, ArrayList<String> videoIds) {
        Log.d("getVideoInfo", "getVideoInfo");
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        String videoIdsString = TextUtils.join(", ", videoIds);
        Log.d("videoIdsString", "videoIdsString" + videoIds);
        // https://www.googleapis.com/youtube/v3/videos?part=snippet,%20contentDetails&id=      &key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/videos?part=snippet,%20contentDetails&id=" + videoIdsString + "&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);
        queue.add(jsonObjectRequest);
    }
}
