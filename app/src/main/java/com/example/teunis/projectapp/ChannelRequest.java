package com.example.teunis.projectapp;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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
        Log.d("onResponse", "onResponse");
        ArrayList<String> channels = new ArrayList<>();
        try {
            JSONArray channelArray = response.getJSONArray("thumbnail_url");
            Log.d("JSONArray", "JSONArray: " + channelArray);
            for (int i = 0; i < channelArray.length(); i++) {
                channels.add(channelArray.getString(i));
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
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.youtube.com/oembed?url=https://www.youtube.com/watch?v=eESLfJgYYHE&format=json", null, this, this);
        queue.add(jsonObjectRequest);
    }
}
