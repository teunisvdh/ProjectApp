package com.example.teunis.projectapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.api.services.youtube.YouTube;

import java.util.ArrayList;

public class PlaylistActivity extends YouTubeBaseActivity {

    YouTubePlayer.OnInitializedListener initListener;
    YouTubePlayerView youtubeView;
    ArrayList<String> finalIds;
    int orientation;
    YouTubePlayer youTubePlayerCopy;
    boolean first;
//    boolean first = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        if (savedInstanceState == null) {
            first = true;
        }

        orientation = this.getResources().getConfiguration().orientation;

        Intent intent = getIntent();
        final ArrayList<CharSequence> finalVideos = (ArrayList<CharSequence>) intent.getSerializableExtra("finalVideos");
        Log.d("PlaylistActivity", "finalVideos" + finalVideos);
        finalIds = new ArrayList<>();

        for (int i = 0; i < finalVideos.size(); i++) {
            CharSequence thisVideo = finalVideos.get(i);
            String thisVideoId = thisVideo.toString();
            finalIds.add(thisVideoId);
        }

        Log.d("PlaylistActivity", "PlaylistActivity");
        Log.d("First", "First: " + first);

        youtubeView = (YouTubePlayerView) findViewById(R.id.youtubePlayerView);
        initListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("success", "success Video");
                if (first) {
                    Log.d("videos loaded", "videos loaded");
                    youTubePlayer.loadVideos(finalIds);
                    first = false;
                }
                youTubePlayer.play();

                if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    youTubePlayer.setFullscreen(true);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("Error Video", "Error Video");
            }
        };

        youtubeView.initialize("AIzaSyAI9dTBMA_2el8NlVSVjCLECXEoeyhdhNc", initListener);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(PlaylistActivity.this, ProfileActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    public void stopPlaylist(View view) {
        Log.d("stopPlayList", "STOP STOP STOP STOP STOP STOP STOP STOP STOP");
        finish();
//        youtubeView.initialize("AIzaSyAI9dTBMA_2el8NlVSVjCLECXEoeyhdhNc", initListener);
    }
}
