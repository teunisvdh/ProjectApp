package com.example.teunis.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class RecommendActivity extends AppCompatActivity implements ChannelRequest.Callback {

    ArrayList<ChannelItem> finalChannels = new ArrayList<ChannelItem>();
    float minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommend);

        Log.d("RecommendActivity", "RecommendActivity");

        Intent intent = getIntent();
        minutes = (float) intent.getSerializableExtra("timeInput");
        ArrayList moods = (ArrayList) intent.getSerializableExtra("moods");
        String moodsString = TextUtils.join(", ", moods);
        TextView test = findViewById(R.id.testView);
        test.setText("Moods: " + moodsString);

        ChannelRequest whatChannel = new ChannelRequest(this);
        whatChannel.getChannels(this, moods);

//        ListView channelList = findViewById(R.id.channelList);
//        channelList.setOnItemClickListener(new RecommendActivity.listItemClickListener());
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
                Intent intent = new Intent(RecommendActivity.this, ProfileActivity.class);
                startActivity(intent);
                return true;
            case R.id.homeMenu:
                Intent homeIntent = new Intent(RecommendActivity.this, TimeActivity.class);
                startActivity(homeIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void gotChannels(ArrayList<ChannelItem> channels) {
//        Log.d("channelinfo", "channelsinfo:" + channels);
//        String channelString = TextUtils.join(", ", channels);
//        TextView channelText = findViewById(R.id.channelText);
//        channelText.setText("Channels: " + channelString);
        finalChannels = new ArrayList<ChannelItem>();
        if (channels.size() > 5) {
            Log.d("if loop", "channels size bigger than five" + channels);
            for (int i = 0; i < 5; i++) {
                Random random = new Random();
                int index = random.nextInt(channels.size());
                ChannelItem thisChannel = channels.get(index);
                channels.remove(thisChannel);
                finalChannels.add(thisChannel);
                ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.item_channel, finalChannels);
                ListView listChannel = findViewById(R.id.channelList);
                listChannel.setAdapter(channelAdapter);
            }
        }
        else {
            Log.d("else", "else" + channels);
            for (int i = 0; i < 5; i++) {
                ChannelItem thisChannel = channels.get(i);
                finalChannels.add(thisChannel);
                ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.item_channel, finalChannels);
                ListView listChannel = findViewById(R.id.channelList);
                listChannel.setAdapter(channelAdapter);
            }
//        ChannelAdapter channelAdapter = new ChannelAdapter(this, R.layout.item_channel, finalChannels);
//        ListView listChannel = findViewById(R.id.channelList);
//        listChannel.setAdapter(channelAdapter);
        }
    }

    @Override
    public void gotChannelsError(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

//    public class listItemClickListener implements AdapterView.OnItemClickListener {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            Log.d("channelItems", "channelItems" + finalChannels);
//            ChannelItem deletedChannel = (ChannelItem) parent.getItemAtPosition(position);
//            if (finalChannels.contains(deletedChannel)) {
//                Log.d("Contains", "CONTAINS CHANNEL");
//                finalChannels.remove(deletedChannel);
//            }
//            else {
//                Log.d("Contains", "DOESN'T CONTAIN CHANNEL");
//                finalChannels.add(deletedChannel);
//            }
//        }
//    }

    public void continueRecommend(View view) {
        Intent intent = new Intent(RecommendActivity.this, ShowplaylistActivity.class);
        intent.putExtra("finalChannels", finalChannels);
        intent.putExtra("timeInput", minutes);
        startActivity(intent);
    }
}
