/* ShowplaylistActivity shows the user the generated playlist. For each five channels, 10 videos will
 * be put in a list. This activity chooses random videos from this list If a video doesn't fit into
 * the time available, it will go to a random next one until the end of the list. If no other video
 * fits, the playlist is final.
 */

package com.example.teunis.projectapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teunis.projectapp.Items.ChannelItem;
import com.example.teunis.projectapp.R;
import com.example.teunis.projectapp.Adapters.VideoAdapter;
import com.example.teunis.projectapp.Requests.VideoInfoRequest;
import com.example.teunis.projectapp.Items.VideoItem;
import com.example.teunis.projectapp.Requests.VideoRequest;

import java.util.ArrayList;
import java.util.Random;

public class ShowplaylistActivity extends AppCompatActivity implements VideoRequest.Callback, VideoInfoRequest.Callback {

    float time;
    ArrayList<VideoItem> videosForPlaylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showplaylist);

        Intent intent = getIntent();
        time = (float) intent.getSerializableExtra("timeInput");
        ArrayList channels = (ArrayList) intent.getSerializableExtra("finalChannels");
        ArrayList<String> channelNames = new ArrayList<>();
        for (int i = 0; i<channels.size(); i++) {
            ChannelItem thisChannel = (ChannelItem) channels.get(i);
            String name = thisChannel.name;
            channelNames.add(name);
        }

        VideoRequest whatVideo = new VideoRequest(this);
        whatVideo.getVideos(this, channels);
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
                Intent intent = new Intent(ShowplaylistActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.homeMenu:
                Intent homeIntent = new Intent(ShowplaylistActivity.this, TimeActivity.class);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void gotVideos(ArrayList<String> videos) {
        VideoInfoRequest whatVideoInfo = new VideoInfoRequest(this);
        whatVideoInfo.getVideoInfo(this, videos);
    }

    @Override
    public void gotVideosError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    public void startPlaylist(View view) {
        ArrayList<CharSequence> finalIds = new ArrayList<>();
        Intent intent = new Intent(ShowplaylistActivity.this, PlaylistActivity.class);
        for (int i = 0; i < videosForPlaylist.size(); i++) {
            VideoItem thisVideo = videosForPlaylist.get(i);
            CharSequence thisVideoId = thisVideo.id;
            finalIds.add(thisVideoId);
        }
        intent.putCharSequenceArrayListExtra("finalVideos", finalIds);
        startActivity(intent);
    }

    @Override
    public void gotVideoInfo(ArrayList<VideoItem> videosWithInfo) {
        ArrayList<String> minutes = new ArrayList<>();
        for (int i = 0; i < videosWithInfo.size(); i++) {
            VideoItem thisVideo = videosWithInfo.get(i);
            String durationString = Float.toString(thisVideo.duration);
            minutes.add(durationString);
        }
        float totalDuration = 0;
        ArrayList<VideoItem> finalPlaylist = new ArrayList<>();

        while (totalDuration <= time) {
            if (videosWithInfo.size() >= 1) {
                Random random = new Random();
                int index = random.nextInt(videosWithInfo.size());
                VideoItem thisVideo = videosWithInfo.get(index);
                float timeVideo = thisVideo.duration;
                if (videosWithInfo.size() >= 2 && totalDuration + timeVideo <= time) {
                    finalPlaylist.add(thisVideo);
                    videosWithInfo.remove(thisVideo);
                    totalDuration = totalDuration + timeVideo;
                }
                else {
                    videosWithInfo.remove(thisVideo);
                }
            }
            else {
                break;
            }
        }

        TextView timeTestView = findViewById(R.id.timeTestView);

        // convert duration (float) to a string and show in View
        float durationSeconds = totalDuration % 1 * 60;
        String secondsString = String.format("%.00f", durationSeconds);
        if (secondsString.length() == 1) {
            secondsString = "0" + secondsString;
        }
        float durationMinutes = totalDuration - (totalDuration % 1);
        String minutesString = String.format("%.00f", durationMinutes);
        if (minutesString.length() == 1) {
            minutesString = "0" + minutesString;
        }
        timeTestView.setText("Total time:   " + minutesString + "m " + secondsString + "s");

        VideoAdapter videoAdapter = new VideoAdapter(this, R.layout.item_channel, finalPlaylist);
        ListView listPlaylistShow = findViewById(R.id.listPlaylistShow);
        listPlaylistShow.setAdapter(videoAdapter);
        videosForPlaylist = finalPlaylist;
    }

    @Override
    public void gotVideoInfoError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}
