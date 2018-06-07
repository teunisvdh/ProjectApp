package com.example.teunis.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity implements ChannelRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Log.d("RecommendActivity", "RecommendActivity");
        ChannelRequest whatChannel = new ChannelRequest(this);
        whatChannel.getChannels(this);
    }

    @Override
    public void gotChannels(ArrayList<String> channels) {

    }

    @Override
    public void gotChannelsError(String message) {

    }

    public void continueRecommend(View view) {
        Intent intent = new Intent(RecommendActivity.this, ShowplaylistActivity.class);
        startActivity(intent);
    }
}
