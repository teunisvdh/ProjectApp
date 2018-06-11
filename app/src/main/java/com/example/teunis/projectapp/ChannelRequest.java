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

    public ChannelRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotChannels(ArrayList<String> channels);
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
        ArrayList<String> channels = new ArrayList<>();
        try {
//            String user = response.getString("author_name");
//            Log.d("try", user);
//            channels.add(user);
            JSONArray channelArray = response.getJSONArray("items");
            Log.d("JSONArray", "JSONArray: " + channelArray);

            for (int i = 0; i < channelArray.length(); i++) {
                JSONObject thisChannel = channelArray.getJSONObject(i);
                String name = thisChannel.getString("channelTitle");
                channels.add(name);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }
        activity.gotChannels(channels);
    }

    public void getChannels(Callback activity) {
        Log.d("getChannels", "getChannels");
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=eESLfJgYYHE&format=json", null, this, this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=5&q=surfing&type=channel&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);

        queue.add(jsonObjectRequest);


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
