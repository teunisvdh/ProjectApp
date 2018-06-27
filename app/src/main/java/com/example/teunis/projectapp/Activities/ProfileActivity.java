package com.example.teunis.projectapp.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teunis.projectapp.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> moods;
    Map<String, Integer> mapMoments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = getSharedPreferences("sharedMoods", Context.MODE_PRIVATE);
        String stringMoods = preferences.getString("moods", "");
        if (!stringMoods.equals("")) {
            moods = new ArrayList<>(Arrays.asList(stringMoods.split(", ")));
        }
        else {
            moods = new ArrayList<>();
        }

        SharedPreferences preferencesMoments = getSharedPreferences("sharedMoments", Context.MODE_PRIVATE);
        String stringMoments = preferencesMoments.getString("moments", "");
        if (!stringMoments.equals("")) {
            mapMoments = new HashMap<>();
            ArrayList<String> listMoments = new ArrayList<>(Arrays.asList(stringMoments.split(", ")));
            for (int i = 0; i < listMoments.size(); i++) {
                String moment = listMoments.get(i);
                String[] splitted = moment.split("=");
                Integer duration = Integer.parseInt(splitted[1]);
                mapMoments.put(splitted[0], duration);
            }
        }
        else {
            mapMoments = new HashMap<>();
        }

        TextView moodsText = findViewById(R.id.moodsView);

        if (stringMoods.equals("")) {
            moodsText.setText("No moods defined.");
        }
        else {
            moodsText.setText(stringMoods);
        }
        TextView momentsText = findViewById(R.id.momentsView);
        if (stringMoments.equals("")) {
            momentsText.setText("No moments defined.");
        }
        else {
            String momentsNotation = stringMoments.replace("=", ": ");
            momentsText.setText(momentsNotation);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);
    }

    public void addDelete(final View view) {
        EditText moodAddDelete = findViewById(R.id.editText);
        String thisMood = moodAddDelete.getText().toString();
        if (moods.contains(thisMood)) {
            moods.remove(thisMood);
            view.setBackgroundColor(0xFFFF0000);
        }
        else if (thisMood.equals("")) {
            Toast toast = Toast.makeText(this, "Fill in a mood.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            moods.add(thisMood);
            view.setBackgroundColor(0xFF00FF00);
        }
        TextView moodsText = findViewById(R.id.moodsView);
        String moodsString = TextUtils.join(", ", moods);
        if (moodsString.equals("")) {
            moodsText.setText("No moods defined.");
        }
        else {
            moodsText.setText(moodsString);
        }
        SharedPreferences.Editor editor = getSharedPreferences("sharedMoods", MODE_PRIVATE).edit();
        editor.putString("moods", moodsString);
        editor.apply();
        moodAddDelete.setText("");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundColor(0xFFFFFFFF);
            }
        }, 150);
    }

    public void addDeleteMoment(final View view) {
        EditText momentAddDelete = findViewById(R.id.editMoment);
        EditText momentTimeAddDelete = findViewById(R.id.editMomentTime);
        String thisMoment = momentAddDelete.getText().toString();
        String timeMomentString = momentTimeAddDelete.getText().toString();
        Integer timeMoment = 0;
        if (!timeMomentString.equals("")) {
            timeMoment = Integer.parseInt(timeMomentString);
        }
        if (mapMoments.containsKey(thisMoment)) {
            mapMoments.remove(thisMoment);
            view.setBackgroundColor(0xFFFF0000);
        } else if (thisMoment.equals("") || timeMomentString.equals("")) {
            Toast toast = Toast.makeText(this, "Fill in a moment and time.", Toast.LENGTH_SHORT);
            toast.show();
        } else if (!timeMomentString.equals("") && timeMoment < 10) {
            Toast toast = Toast.makeText(this, "Time input must be 10 minutes or higher.", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            mapMoments.put(thisMoment, timeMoment);
            view.setBackgroundColor(0xFF00FF00);
        }
        TextView momentsText = findViewById(R.id.momentsView);
        String momentsString2 = mapMoments.toString();
        String momentsString1 = momentsString2.replace("{", "");
        String momentsString = momentsString1.replace("}", "");
        if (momentsString.equals("")) {
            momentsText.setText("No moments defined.");
        }
        else {
            momentsText.setText(momentsString);
        }
        SharedPreferences.Editor editor = getSharedPreferences("sharedMoments", MODE_PRIVATE).edit();
        editor.putString("moments", momentsString);
        editor.apply();
        momentAddDelete.setText("");
        momentTimeAddDelete.setText("");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundColor(0xFFFFFFFF);
            }
        }, 150);
    }
}
