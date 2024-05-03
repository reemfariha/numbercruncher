package com.sudoku.logicmaths.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import com.sudoku.logicmaths.R;

public class ActivitySplash extends AppCompatActivity {

    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        context=ActivitySplash.this;

        gonext();

    }

    public void gonext(){

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(context,ActivityHome.class);
                startActivity(intent);
                finish();
            }
        },2000);


    }

}