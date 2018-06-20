package com.example.teunis.projectapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
        lengthVideo.setText(Float.toString(video.duration));

        Picasso.get().load(video.image).into(imageVideo);

        return convertView;
    }
}

