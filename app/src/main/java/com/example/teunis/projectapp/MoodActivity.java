package com.example.teunis.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        Intent intent = getIntent();
        int minutes = (Integer) intent.getSerializableExtra("timeInput");
        TextView minutesMood = findViewById(R.id.minutesMood);
        minutesMood.setText("Minutes available: " + minutes);
    }

    public void moodsGiven(View view) {
        // or ChannelRequest instead of RecommendActivity????
        Intent intent = new Intent(MoodActivity.this, RecommendActivity.class);
        intent.putExtra("timeInput", minutesInt);
        startActivity(intent);
    }
}
