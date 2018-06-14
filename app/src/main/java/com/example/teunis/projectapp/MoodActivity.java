package com.example.teunis.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MoodActivity extends AppCompatActivity {

    ArrayList<String> selectedMoods = new ArrayList<String>();
    float minutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        Intent intent = getIntent();
        minutes = (float) intent.getSerializableExtra("timeInput");
        TextView minutesMood = findViewById(R.id.minutesMood);
        minutesMood.setText("Minutes available: " + minutes);
    }

    public void moodClicked(View view) {
        boolean check = ((CheckBox) view).isChecked();
        String text = (String) ((CheckBox) view).getText();
        if (check) {
            selectedMoods.add(text);
        }
        else if (selectedMoods.contains(text)){
            selectedMoods.remove(text);
        }
    }

    public void moodsGiven(View view) {
        Intent intent = new Intent(MoodActivity.this, RecommendActivity.class);
        intent.putStringArrayListExtra("moods", selectedMoods);
        intent.putExtra("timeInput", minutes);
        startActivity(intent);
    }
}
