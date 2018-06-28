/* TimeActivity let's the user choose a maximum time for the playlist. A user can select
 * a moment or input a different time in a textbox.
 */

package com.example.teunis.projectapp.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.teunis.projectapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TimeActivity extends AppCompatActivity {

    HashMap<String, Integer> mapMoments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);

        SharedPreferences preferences = getSharedPreferences("sharedMoments", Context.MODE_PRIVATE);
        String stringMoments = preferences.getString("moments", "");
        final LinearLayout allMoments = findViewById(R.id.linearLayoutMoments);

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
                String[] split = moment.split("=");
                Integer duration = Integer.parseInt(split[1]);
                mapMoments.put(split[0], duration);
            }

            // make a clickable TextView for all moments (dynamically)
            for (Map.Entry<String, Integer> entry : mapMoments.entrySet()) {
                String thisMoment = entry.getKey();
                final TextView text = new TextView(this);
                text.setText(thisMoment);
                text.setTextSize(18);
                text.setHeight(150);
                text.setGravity(Gravity.CENTER);
                ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams
                        (RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 8, 0, 8);
                text.setLayoutParams(params);
                text.setBackgroundColor(0x4DE52C2C);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View view) {
                        String text = (String) ((TextView) view).getText();
                        TextView time = findViewById(R.id.editTime);
                        String timeMoment = mapMoments.get(text).toString();
                        time.setText(timeMoment);
                        view.setBackgroundColor(0xFFE52C2C);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                view.setBackgroundColor(0x4DE52C2C);
                            }
                        }, 150);
                    }
                });
                allMoments.addView(text);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.time_menu, menu);
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
        TextView time = findViewById(R.id.editTime);
        CharSequence minutes = time.getText();
        String minutesString = minutes.toString();
        if (minutesString.equals("")) {
            Toast toast = Toast.makeText(this, "Fill in minutes.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            float minutesFloat = Float.parseFloat(minutesString);
            if (minutesFloat >= 10 && minutesFloat <= 180) {
                Intent intent = new Intent(TimeActivity.this, MoodActivity.class);
                intent.putExtra("timeInput", minutesFloat);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, "Time input must be between 10 and 180 minutes.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}



