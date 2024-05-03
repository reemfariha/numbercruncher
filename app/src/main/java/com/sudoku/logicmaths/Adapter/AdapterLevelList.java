package com.sudoku.logicmaths.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sudoku.logicmaths.Activity.ActivityLevelContent;
import com.sudoku.logicmaths.R;
import com.sudoku.logicmaths.Utils.Constance;
import com.sudoku.logicmaths.Utils.SharedPrefrenceConfig;

import java.util.ArrayList;

public class AdapterLevelList extends RecyclerView.Adapter<AdapterLevelList.ViewHolder> {

    Context context;
    ArrayList<Integer> modelHomeChildList;
    View view;
    int pos=0;

    int level;

    public AdapterLevelList(Context context, ArrayList<Integer> modelHomeChildList) {
        this.context = context;
        this.modelHomeChildList = modelHomeChildList;
        level= Integer.valueOf(SharedPrefrenceConfig.getPrefString(context, Constance.keylevelid1,""));
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

        if(Integer.valueOf(modelHomeChildList.get(position))<=level){
            holder.rl_main.setBackgroundColor(context.getResources().getColor(R.color.green_500));

            holder.rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pos=position;
                    holder.rl_main.setBackgroundColor(context.getResources().getColor(R.color.green_500));
                    notifyDataSetChanged();
                    Intent intent=new Intent(context,ActivityLevelContent.class);
                    intent.putExtra("leveln", modelHomeChildList.get(pos).toString());
                    context.startActivity(intent);
                }
            });

        }else {
            holder.rl_main.setBackgroundColor(context.getResources().getColor(R.color.colortheme_buttonbg));

            holder.rl_main.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "First you need to complete the level "+level, Toast.LENGTH_SHORT).show();
                }
            });
        }


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
