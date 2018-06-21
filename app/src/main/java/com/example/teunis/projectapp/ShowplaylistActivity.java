package com.example.teunis.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class ShowplaylistActivity extends AppCompatActivity implements VideoRequest.Callback, VideoInfoRequest.Callback {

    float time;
    ArrayList<VideoItem> videosForPlaylist = new ArrayList<>();
//    ArrayList<VideoItem> finalPlaylist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showplaylist);

        Intent intent = getIntent();
        time = (float) intent.getSerializableExtra("timeInput");
        Log.d("before cast", "before cast");
//        time = (float) minutesInput;
        Log.d("after cast", "after cast");
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
    public void gotVideos(ArrayList<String> videos) {
//        ArrayList<String> videoIds = new ArrayList<>();
//        for (int i = 0; i<videos.size(); i++) {
//            VideoItem thisVideo = videos.get(i);
//            String id = thisVideo.id;
//            videoIds.add(id);
//        }
//        TextView videosView = findViewById(R.id.videosView);
//        videosView.setText("Videos: " + videos);

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
        Log.d("startPlaylist", "videosForPlaylist" + videosForPlaylist);
        for (int i = 0; i < videosForPlaylist.size(); i++) {
            VideoItem thisVideo = videosForPlaylist.get(i);
            CharSequence thisVideoId = thisVideo.id;
            finalIds.add(thisVideoId);
        }
        intent.putCharSequenceArrayListExtra("finalVideos", finalIds);
//        intent.putExtra("finalVideos", "hahaha");
        startActivity(intent);
    }

    @Override
    public void gotVideoInfo(ArrayList<VideoItem> videosWithInfo) {
        Log.d("gotVideoInfo", "gotVideoInfo" + videosWithInfo);
        ArrayList<String> minutes = new ArrayList<>();
        for (int i = 0; i < videosWithInfo.size(); i++) {
            VideoItem thisVideo = videosWithInfo.get(i);
            String durationString = Float.toString(thisVideo.duration);
            minutes.add(durationString);
        }
//        TextView videosView = findViewById(R.id.videosView);
//        videosView.setText("Videos: " + minutes);
        float totalDuration = 0;
        ArrayList<VideoItem> finalPlaylist = new ArrayList<>();

        while (totalDuration <= time) {
            if (videosWithInfo.size() >= 1) {
                Log.d("begin while loop", "begin while loop");
                Random random = new Random();
                Log.d("videosWithInfo", "videosWithInfo: " + videosWithInfo + videosWithInfo.size());
                int index = random.nextInt(videosWithInfo.size());
                VideoItem thisVideo = videosWithInfo.get(index);
                float timeVideo = thisVideo.duration;
                if (videosWithInfo.size() >= 2 && totalDuration + timeVideo <= time) {
                    finalPlaylist.add(thisVideo);
                    videosWithInfo.remove(thisVideo);
                    Log.d("videoAdded", "videoAdded");
                    totalDuration = totalDuration + timeVideo;
                }
                else {
                    videosWithInfo.remove(thisVideo);
                }
            }
            else {
                Log.d("break", "break");
                break;
            }
        }

//        TextView playlistTest = findViewById(R.id.playlistTest);
//        playlistTest.setText("Videos: " + finalPlaylist);
        ArrayList<String> minutesOfVideos = new ArrayList<>();
        ArrayList<String> channelsOfVideos = new ArrayList<>();

        float totalTime = 0;
        for (int i = 0; i < finalPlaylist.size(); i++) {
            VideoItem thisVideo = finalPlaylist.get(i);
            String durationString = Float.toString(thisVideo.duration);
            minutesOfVideos.add(durationString);
            totalTime = totalTime + thisVideo.duration;
            String videoChannel = thisVideo.channel;
            channelsOfVideos.add(videoChannel);
        }
        TextView timeTestView = findViewById(R.id.timeTestView);
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
//        timeTestView.setText("Total time: " + totalDuration + minutesOfVideos + "Channels: " + channelsOfVideos);
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
