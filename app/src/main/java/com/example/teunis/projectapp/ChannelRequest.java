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

public class ChannelRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    ArrayList<ChannelItem> channels = new ArrayList<>();

    public ChannelRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotChannels(ArrayList<ChannelItem> channels);
        void gotChannelsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("onErrorResponse", "onErrorResponse");
        String errorResponse = error.getMessage();
        activity.gotChannelsError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("onResponse", "onResponse" + response);
//        ArrayList<String> channels = new ArrayList<>();
        try {
            JSONArray channelArray = response.getJSONArray("items");
            Log.d("JSONArray", "JSONArray: " + channelArray);

            for (int i = 0; i < channelArray.length(); i++) {
                Log.d("loop begin", "begin loop");
                JSONObject thisChannel = channelArray.getJSONObject(i);
                JSONObject snippet = thisChannel.getJSONObject("snippet");
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                JSONObject modeImage = thumbnails.getJSONObject("default");
//                Log.d("snippet", "snippet" + snippet);
                Log.d("loop second", "begin second" + snippet);
                String name = snippet.getString("channelTitle");
                String description = snippet.getString("description");
                String imageUrl = modeImage.getString("url");
                String url = snippet.getString("channelId");
                Log.d("loop third", "begin third");
                ChannelItem channelItem = new ChannelItem(name, description, imageUrl, url);
                channels.add(channelItem);
                Log.d("channels", "channels"+ channels);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }
        activity.gotChannels(channels);
    }

    public void getChannels(Callback activity, ArrayList moods) {
        Log.d("getChannels", "getChannels");
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);

        for (int i = 0; i < moods.size(); i++) {
            String mood = moods.get(i).toString();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&order=relevance&q=" + mood + "&relevanceLanguage=en&type=channel&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);
            queue.add(jsonObjectRequest);
        }


//        try {
//            HashMap<String, String> parameters = new HashMap<>();
//            parameters.put("part", "snippet");
//            parameters.put("maxResults", "25");
//            parameters.put("q", "surfing");
//            parameters.put("type", "");
//
//            YouTube.Search.List searchListByKeywordRequest = youtube.search().list(parameters.get("part").toString());
//            if (parameters.containsKey("maxResults")) {
//                searchListByKeywordRequest.setMaxResults(Long.parseLong(parameters.get("maxResults").toString()));
//            }
//
//            if (parameters.containsKey("q") && parameters.get("q") != "") {
//                searchListByKeywordRequest.setQ(parameters.get("q").toString());
//            }
//
//            if (parameters.containsKey("type") && parameters.get("type") != "") {
//                searchListByKeywordRequest.setType(parameters.get("type").toString());
//            }
//
//            SearchListResponse response = searchListByKeywordRequest.execute();
//            System.out.println(response);
//        } catch (IOException e) {
//            Log.d("IOException", "IOException");
//        }
    }
}
