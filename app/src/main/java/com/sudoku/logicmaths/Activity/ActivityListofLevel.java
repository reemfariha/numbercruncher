package com.sudoku.logicmaths.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sudoku.logicmaths.Adapter.AdapterLevelList;
import com.sudoku.logicmaths.R;
import com.sudoku.logicmaths.Utils.SharedPrefrenceConfig;

import java.util.ArrayList;

public class ActivityListofLevel extends AppCompatActivity {

    Context context;

    public static ActivityListofLevel instance = null;

    public ActivityListofLevel() {
        instance = ActivityListofLevel.this;
    }

    public static synchronized ActivityListofLevel getInstance() {
        if (instance == null) {
            instance = new ActivityListofLevel();
        }
        return instance;
    }

    SharedPrefrenceConfig sharedPrefrenceConfig;

    ImageView iv_back;
    public RecyclerView rv_levelList;

    ArrayList<Integer> arrayList_listofLevel=new ArrayList<>();
    AdapterLevelList adapterLevelList;

    GridLayoutManager gridLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context= ActivityListofLevel.this;
        setContentView(R.layout.activity_listoflevel);
        sharedPrefrenceConfig=new SharedPrefrenceConfig(context);
        bindview();

        gridLayoutManager=new GridLayoutManager(context,5);
        rv_levelList.setLayoutManager(gridLayoutManager);

        getListLevel();

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void bindview(){
        rv_levelList=findViewById(R.id.rv_levelList);
        iv_back=findViewById(R.id.iv_back);
    }


    public void getListLevel(){
        arrayList_listofLevel.clear();
        for(int i=1;i<101;i++){
            arrayList_listofLevel.add(i);
        }

        adapterLevelList=new AdapterLevelList(context,arrayList_listofLevel);
        rv_levelList.setAdapter(adapterLevelList);
        adapterLevelList.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(adapterLevelList!=null){
            adapterLevelList.notifyDataSetChanged();
        }
    }

}