package com.sudoku.logicmaths;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

public class myapplication extends Application{
    Context context;

    private Activity currentActivity;
    @Override
    public void onCreate() {
        super.onCreate();
        context=myapplication.this;

    }

}
