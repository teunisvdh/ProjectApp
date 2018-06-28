/* ChannelRequest is a request class for retrieving YouTube channels. For each mood it starts a
 * JSON query for retrieving a maximum of 10 channels associated with the mood. For each channel
 * it created a ChannelItem.
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

public class ChannelRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    Context context;
    Callback activity;
    ArrayList<ChannelItem> channels = new ArrayList<>();
    int moodsSize;
    int count = 0;

    public ChannelRequest(Context context) {
        this.context = context;
    }

    public interface Callback {
        void gotChannels(ArrayList<ChannelItem> channels);
        void gotChannelsError(String message);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        String errorResponse = error.getMessage();
        activity.gotChannelsError(errorResponse);
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            JSONArray channelArray = response.getJSONArray("items");

            for (int i = 0; i < channelArray.length(); i++) {
                JSONObject thisChannel = channelArray.getJSONObject(i);
                JSONObject snippet = thisChannel.getJSONObject("snippet");
                JSONObject thumbnails = snippet.getJSONObject("thumbnails");
                JSONObject modeImage = thumbnails.getJSONObject("default");
                String name = snippet.getString("channelTitle");
                String description = snippet.getString("description");
                String imageUrl = modeImage.getString("url");
                String url = snippet.getString("channelId");
                ChannelItem channelItem = new ChannelItem(name, description, imageUrl, url);
                channels.add(channelItem);
            }
        } catch (JSONException e) {
            Log.d("JSONException", "JSONException");
        }
        count = count + 1;
        if (count == moodsSize) {
            activity.gotChannels(channels);
        }
    }

    public void getChannels(Callback activity, ArrayList moods) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        moodsSize = moods.size();

        for (int i = 0; i < moodsSize; i++) {
            String mood = moods.get(i).toString();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://www.googleapis.com/youtube/v3/search?part=snippet&maxResults=10&order=relevance&q="
                    + mood + "&relevanceLanguage=en&type=channel&key=AIzaSyBAd7Nkwxa8G5J4cdB6jy2gh6iI-goGpX4", null, this, this);
            queue.add(jsonObjectRequest);
        }
    }
}
