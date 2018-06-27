package com.example.teunis.projectapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.teunis.projectapp.R;
import com.example.teunis.projectapp.Items.VideoItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class VideoAdapter extends ArrayAdapter<VideoItem> {
    public VideoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<VideoItem> objects) {
        super(context, resource, objects);
        items = objects;
    }

    ArrayList<VideoItem> items;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_playlist, parent, false);
        }

        VideoItem video = items.get(position);

        ImageView imageVideo = convertView.findViewById(R.id.imageVideo);
        TextView titleVideo = convertView.findViewById(R.id.titleVideo);
        TextView titleChannel = convertView.findViewById(R.id.titleChannel);
        TextView lengthVideo = convertView.findViewById(R.id.lengthVideo);

        titleVideo.setText(video.title);
        titleChannel.setText(video.channel);

        float durationSeconds = video.duration % 1 * 60;
        String secondsString = String.format("%.00f", durationSeconds);
        if (secondsString.length() == 1) {
            secondsString = "0" + secondsString;
        }
        float durationMinutes = video.duration - (video.duration % 1);
        String minutesString = String.format("%.00f", durationMinutes);
        if (minutesString.length() == 1) {
            minutesString = "0" + minutesString;
        }
        lengthVideo.setText(minutesString + "m " + secondsString + "s");

        Picasso.get().load(video.image).into(imageVideo);

        return convertView;
    }
}

