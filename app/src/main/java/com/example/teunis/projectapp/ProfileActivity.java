package com.example.teunis.projectapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> moods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        GlobalVariables global = GlobalVariables.getInstance();
        moods = global.getMoods();

//        if (savedInstanceState == null) {
//            moods = new ArrayList<>();
//            TextView moodsText = findViewById(R.id.moodsView);
//            String moodsString = TextUtils.join(", ", moods);
//            moodsText.setText(moodsString);
//        }
        TextView moodsText = findViewById(R.id.moodsView);
        String moodsString = TextUtils.join(", ", moods);
        moodsText.setText(moodsString);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("moodsRetrieved", moods);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        moods = savedInstanceState.getStringArrayList("moodsRetrieved");
        TextView moodsText = findViewById(R.id.moodsView);
        String moodsString = TextUtils.join(", ", moods);
        moodsText.setText(moodsString);
    }

    public void addDelete(View view) {
        EditText moodAddDelete = findViewById(R.id.editText);
        String thisMood = moodAddDelete.getText().toString();
        if (moods.contains(thisMood)) {
            moods.remove(thisMood);
        }
        else {
            moods.add(thisMood);
        }
        TextView moodsText = findViewById(R.id.moodsView);
        String moodsString = TextUtils.join(", ", moods);
        moodsText.setText(moodsString);
        GlobalVariables global = GlobalVariables.getInstance();
        global.setMoods(moods);
    }
}
