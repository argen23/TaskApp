package com.example.taskapp.room;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

public class Prefs {

    private SharedPreferences preferences;

    public Prefs(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);

    }
    public void delete(Context context) {
        preferences = context.getSharedPreferences("settings", Context.MODE_PRIVATE);
        preferences.edit().clear().apply();

    }

    public void saveBoardState() {
        preferences.edit().putBoolean("boardIsShown", true).apply();

    }

    public boolean isBoardShown() {
        return preferences.getBoolean("boardIsShown", false);
    }


    public void saveUserName(String name) {
        preferences.edit().putString("name", name).apply();
    }

    public String getName() {
        return preferences.getString("name", "");
    }

    public void saveNumber(String number) {
        preferences.edit().putString("number", number).apply();
    }

    public String getNumber() {
        return preferences.getString("number", "");
    }

    public void saveImageUri(String image) {
        preferences.edit().putString("avatar", image).apply();
    }

    public String getImageUri() {
        return preferences.getString("avatar", null);
    }



}