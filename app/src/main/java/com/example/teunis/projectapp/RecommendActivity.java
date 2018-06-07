package com.example.teunis.projectapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

public class RecommendActivity extends AppCompatActivity implements ChannelRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);
    }

    @Override
    public void gotChannels(ArrayList<String> channels) {

    }

    @Override
    public void gotChannelsError(String message) {

    }
}
