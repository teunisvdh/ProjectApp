package com.example.teunis.projectapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeActivity extends AppCompatActivity {

    HashMap<String, Integer> mapMoments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SHA1: EA:C8:7C:59:22:F2:F1:71:E2:26:5A:AE:A7:78:8A:A9:86:EC:6C:25
        // Client ID 891303747235-fcc150kknmv0e6htv7ea7faq59p7re14.apps.googleusercontent.com
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        Log.d("TimeActivity", "TimeActivity");

        SharedPreferences preferences = getSharedPreferences("sharedMoments", Context.MODE_PRIVATE);
        String stringMoments = preferences.getString("moments", "");
        final LinearLayout allMoments = (LinearLayout) findViewById(R.id.linearLayoutMoments);

//        List<String> listMoods = new ArrayList<>(Arrays.asList(stringMoods.split(", ")));

        if (stringMoments.equals("")) {
            TextView textEmpty = new TextView(this);
            textEmpty.setText("No moments defined yet.");
            allMoments.addView(textEmpty);
        }
        else {
            mapMoments = new HashMap<>();
            ArrayList<String> listMoments = new ArrayList<>(Arrays.asList(stringMoments.split(", ")));
            for (int i = 0; i < listMoments.size(); i++) {
                String moment = listMoments.get(i);
                String[] splitted = moment.split("=");
                Integer duration = Integer.parseInt(splitted[1]);
                mapMoments.put(splitted[0], duration);
            }

            //        final LinearLayout allMoments = (LinearLayout) findViewById(R.id.linearLayoutMoments);

            for (Map.Entry<String, Integer> entry : mapMoments.entrySet()) {
                String thisMoment = entry.getKey();
                final CheckBox check = new CheckBox(this);
                check.setText(thisMoment);
                check.setTextSize(18);
                check.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean checked = ((CheckBox) view).isChecked();
                        if (checked) {
                            String text = (String) ((CheckBox) view).getText();
                            TextView time = findViewById(R.id.editTime);
                            String timeMoment = mapMoments.get(text).toString();
                            time.setText(timeMoment);
                        }
                        check.setChecked(false);
                    }
                });
                allMoments.addView(check);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(TimeActivity.this, ProfileActivity.class);
        startActivity(intent);
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        startActivity(getIntent());
    }

    public void minutesFilled(View view) {
        float minutesFloat = 0;
        TextView time = findViewById(R.id.editTime);
        CharSequence minutes = time.getText();
        String minutesString = minutes.toString();
        if (minutesString.equals("")) {
            Toast toast = Toast.makeText(this, "Fill in minutes.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            minutesFloat = Float.parseFloat(minutesString);
            if (minutesFloat > 0) {
                Intent intent = new Intent(TimeActivity.this, MoodActivity.class);
                intent.putExtra("timeInput", minutesFloat);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, "Time input must be higher than 0 minutes.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

//    public void profileClicked(View view) {
//        Intent intent = new Intent(TimeActivity.this, ProfileActivity.class);
//        startActivity(intent);
//    }
}



