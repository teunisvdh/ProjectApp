/* PlaylistActivity is the activity containing a YouTube Android Player. The playlist will be loaded
 * into the player. Video's can be viewed, paused and skipped. Rotating the screen will result in a
 * full screen experience.
 */

package com.example.teunis.projectapp.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.teunis.projectapp.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

public class PlaylistActivity extends YouTubeBaseActivity {

    YouTubePlayer.OnInitializedListener initListener;
    YouTubePlayerView youtubeView;
    ArrayList<String> finalIds;
    int orientation;
    boolean first;

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
        finalIds = new ArrayList<>();

        for (int i = 0; i < finalVideos.size(); i++) {
            CharSequence thisVideo = finalVideos.get(i);
            String thisVideoId = thisVideo.toString();
            finalIds.add(thisVideoId);
        }

        youtubeView = findViewById(R.id.youtubePlayerView);
        initListener = new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                if (first) {
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
                Toast toast = Toast.makeText(PlaylistActivity.this, "A problem occurred with initializing the YouTube player.", Toast.LENGTH_SHORT);
                toast.show();
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
        switch (item.getItemId()) {
            case R.id.profileMenu:
                Intent intent = new Intent(PlaylistActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.homeMenu:
                Intent homeIntent = new Intent(PlaylistActivity.this, TimeActivity.class);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void stopPlaylist(View view) {
        finish();
    }
}
