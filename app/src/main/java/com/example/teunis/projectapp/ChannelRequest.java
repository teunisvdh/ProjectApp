package com.example.teunis.projectapp;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.ArrayList;

public class ChannelRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;

    public interface Callback {
        void gotChannels(ArrayList<String> channels);
        void gotChannelsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorResponse = error.getMessage();
        activity.gotChannelsError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {

    }

    public void getChannels(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
//        queue.add(jsonObjectRequest);

    }
}
