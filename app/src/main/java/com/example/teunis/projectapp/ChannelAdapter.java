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

public class ChannelAdapter extends ArrayAdapter<ChannelItem> {
    public ChannelAdapter(@NonNull Context context, int resource, @NonNull ArrayList<ChannelItem> objects) {
        super(context, resource, objects);
        items = objects;
    }

    ArrayList<ChannelItem> items;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_channel, parent, false);
        }

        ChannelItem channel = items.get(position);

        ImageView imageChannel = convertView.findViewById(R.id.imageChannel);
        TextView titleChannel = convertView.findViewById(R.id.titleChannel);

        titleChannel.setText(channel.name);

        Picasso.get().load(channel.imageUrl).into(imageChannel);

        return convertView;
    }
}
