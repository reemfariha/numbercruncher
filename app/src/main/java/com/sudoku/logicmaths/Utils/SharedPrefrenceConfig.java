package com.sudoku.logicmaths.Utils;

import android.content.Context;
import android.content.SharedPreferences;


public class SharedPrefrenceConfig {

    private static final String SHARED_PREF_NAME = "My_shared_pref";
    private Context context;

    public SharedPrefrenceConfig(Context context) {
        this.context = context;
    }

    public static void savebooleanPreferance(Context context, String key, boolean value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
        editor.commit();
    }

    public static boolean getPrefBoolean(Context context, String key, boolean defvalue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MP", Context.MODE_PRIVATE);
        boolean value = sharedPreferences.getBoolean(key, defvalue);
        return value;
    }

    public static void saveStringPreferance(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }



    public static String getPrefString(Context context, String key, String defvalue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MP", Context.MODE_PRIVATE);
        String value = sharedPreferences.getString(key, defvalue);
        return value;
    }


    public void logout() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("MP", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        //return sharedPreferences.getInt("id", -1) != -1;
        return sharedPreferences.getString("id", null) != null;

    }


}
