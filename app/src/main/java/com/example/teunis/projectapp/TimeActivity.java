package com.example.teunis.projectapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // SHA1: EA:C8:7C:59:22:F2:F1:71:E2:26:5A:AE:A7:78:8A:A9:86:EC:6C:25
        // Client ID 891303747235-fcc150kknmv0e6htv7ea7faq59p7re14.apps.googleusercontent.com
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
    }

    public void minutesFilled(View view) {
        int minutesInt = 0;
        TextView time = findViewById(R.id.editTime);
        CharSequence minutes = time.getText();
        String minutesString = minutes.toString();
        if (minutesString.equals("")) {
            Toast toast = Toast.makeText(this, "Fill in minutes.", Toast.LENGTH_SHORT);
            toast.show();
        }
        else {
            minutesInt = Integer.parseInt(minutesString);
            if (minutesInt > 0) {
                Intent intent = new Intent(TimeActivity.this, MoodActivity.class);
                intent.putExtra("timeInput", minutesInt);
                startActivity(intent);
            } else {
                Toast toast = Toast.makeText(this, "Time input must be higher than 0 minutes.", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}



