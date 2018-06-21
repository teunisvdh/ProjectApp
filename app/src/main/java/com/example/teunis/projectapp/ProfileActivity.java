package com.example.teunis.projectapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class ProfileActivity extends AppCompatActivity {

    ArrayList<String> moods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences preferences = getSharedPreferences("sharedMoods", Context.MODE_PRIVATE);
        String stringMoods = preferences.getString("moods", "No moods defined.");
        if (stringMoods != "No moods defined.") {
            moods = new ArrayList<>(Arrays.asList(stringMoods.split(", ")));
        }
        else {
            moods = new ArrayList<>();
        }
//        GlobalVariables global = GlobalVariables.getInstance();
//        moods = global.getMoods();

//        if (savedInstanceState == null) {
//            moods = new ArrayList<>();
//            TextView moodsText = findViewById(R.id.moodsView);
//            String moodsString = TextUtils.join(", ", moods);
//            moodsText.setText(moodsString);
//        }
        TextView moodsText = findViewById(R.id.moodsView);
//        String moodsString = TextUtils.join(", ", moods);
//        moodsText.setText(moodsString);
        moodsText.setText(stringMoods);
    }

//    @Override
////    protected void onSaveInstanceState(Bundle outState) {
////        super.onSaveInstanceState(outState);
////
////        outState.putStringArrayList("moodsRetrieved", moods);
////    }
////
////    @Override
////    protected void onRestoreInstanceState(Bundle savedInstanceState) {
////        super.onRestoreInstanceState(savedInstanceState);
////
////        moods = savedInstanceState.getStringArrayList("moodsRetrieved");
////        TextView moodsText = findViewById(R.id.moodsView);
////        String moodsString = TextUtils.join(", ", moods);
////        moodsText.setText(moodsString);
////    }

//    public void addDelete(View view) {
//        EditText moodAddDelete = findViewById(R.id.editText);
//        String thisMood = moodAddDelete.getText().toString();
//        if (moods.contains(thisMood)) {
//            moods.remove(thisMood);
//        }
//        else {
//            moods.add(thisMood);
//        }
//        TextView moodsText = findViewById(R.id.moodsView);
//        String moodsString = TextUtils.join(", ", moods);
//        moodsText.setText(moodsString);
//        GlobalVariables global = GlobalVariables.getInstance();
//        global.setMoods(moods);
//    }

    public void addDelete(final View view) {
        EditText moodAddDelete = findViewById(R.id.editText);
        String thisMood = moodAddDelete.getText().toString();
        Log.d("addDelete", "moods: " + moods);
        if (moods.contains(thisMood)) {
            moods.remove(thisMood);
            if (moods.size() == 0) {
                moods.add("No moods defined.");
            }
            view.setBackgroundColor(0xFFFF0000);
        }
        else if (moods.contains("No moods defined.")){
            moods.remove("No moods defined.");
            moods.add(thisMood);
        }
        else {
            moods.add(thisMood);
        }
        Log.d("addDelete", "moods after: " + moods);
        TextView moodsText = findViewById(R.id.moodsView);
        String moodsString = TextUtils.join(", ", moods);
        moodsText.setText(moodsString);
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
        }, 250);
//        GlobalVariables global = GlobalVariables.getInstance();
//        global.setMoods(moods);
    }
}
