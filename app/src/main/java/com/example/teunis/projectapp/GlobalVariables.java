package com.example.teunis.projectapp;

import java.util.ArrayList;

public class GlobalVariables {
    public static GlobalVariables instance;

    ArrayList<String> moods = new ArrayList<>();

    public GlobalVariables() {}

    public ArrayList<String> getMoods() {
        return moods;
    }

    public void setMoods(ArrayList<String> moods) {
        this.moods = moods;
    }

    public static synchronized GlobalVariables getInstance(){
        if(instance == null) {
            instance = new GlobalVariables();
        }
        return instance;
    }
}


