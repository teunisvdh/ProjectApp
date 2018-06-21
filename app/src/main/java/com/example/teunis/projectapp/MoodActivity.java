package com.example.teunis.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
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
        String minutesString = String.format("%.00f", minutes);

        SharedPreferences preferences = getSharedPreferences("sharedMoods", Context.MODE_PRIVATE);
        String stringMoods = preferences.getString("moods", "No moods defined.");
        List<String> listMoods = new ArrayList<>(Arrays.asList(stringMoods.split(", ")));

        TextView minutesMood = findViewById(R.id.minutesMood);
        minutesMood.setText("Minutes available: " + minutesString);

        LinearLayout allMoods = (LinearLayout) findViewById(R.id.linearLayout);

        for (int i = 0; i < listMoods.size(); i++) {
            String thisMood = listMoods.get(i);
            CheckBox check = new CheckBox(this);
            check.setText(thisMood);
            check.setTextSize(18);
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean check = ((CheckBox) view).isChecked();
                    String text = (String) ((CheckBox) view).getText();
                    if (check) {
                        selectedMoods.add(text);
                    } else if (selectedMoods.contains(text)) {
                        selectedMoods.remove(text);
                    }
                }
            });
            allMoods.addView(check);
        }
    }

//    public void moodClicked(View view) {
////        boolean check = ((CheckBox) view).isChecked();
////        String text = (String) ((CheckBox) view).getText();
////        if (check) {
////            selectedMoods.add(text);
////        }
////        else if (selectedMoods.contains(text)){
////            selectedMoods.remove(text);
////        }
////    }

    public void moodsGiven(View view) {
        Intent intent = new Intent(MoodActivity.this, RecommendActivity.class);
        intent.putStringArrayListExtra("moods", selectedMoods);
        intent.putExtra("timeInput", minutes);
        startActivity(intent);
    }
}
