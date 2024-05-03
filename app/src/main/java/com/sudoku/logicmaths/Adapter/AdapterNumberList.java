package com.sudoku.logicmaths.Adapter;

import android.content.Context;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudoku.logicmaths.Activity.ActivityLevelContent;
import com.sudoku.logicmaths.R;


import java.util.ArrayList;
import java.util.Date;

public class AdapterNumberList extends RecyclerView.Adapter<AdapterNumberList.ViewHolder> {

    Context context;
    ArrayList<Integer> modelHomeChildList;

    View view;
    char firstlatter_name;
    String date="";
    Date formatedDate=new Date();


    public AdapterNumberList(Context context, ArrayList<Integer> modelHomeChildList) {
        this.context = context;
        this.modelHomeChildList = modelHomeChildList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.item_numberlist, parent, false);
        ViewHolder holder = new ViewHolder(view);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        int width = display.getWidth(); // ((display.getWidth()*20)/100)
//        int height =(750*30)/100;// ((display.getHeight()*30)/100)
        int newwidth = width/6+5;

        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(newwidth, newwidth);
        holder.rl_main.setLayoutParams(parms);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.tv_text.setText(String.valueOf(modelHomeChildList.get(position)));

        holder.rl_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value=modelHomeChildList.get(position);
                ActivityLevelContent.getInstance().et_answer.requestFocus();
                ActivityLevelContent.getInstance().arrayList_answer.add(value);
                ActivityLevelContent.getInstance().getanswer();

            }
        });

    }

    @Override
    public int getItemCount() {
        return modelHomeChildList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_text;
        RelativeLayout rl_main;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_text = itemView.findViewById(R.id.tv_text);
            rl_main = itemView.findViewById(R.id.rl_main);
        }
    }


}
