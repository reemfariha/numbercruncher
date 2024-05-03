package com.sudoku.logicmaths.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sudoku.logicmaths.R;
import com.sudoku.logicmaths.Utils.Constance;
import com.sudoku.logicmaths.Utils.SharedPrefrenceConfig;

public class ActivityHome extends AppCompatActivity {

    Context context;

    public static ActivityHome instance = null;

    public ActivityHome() {
        instance = ActivityHome.this;
    }

    public static synchronized ActivityHome getInstance() {
        if (instance == null) {
            instance = new ActivityHome();
        }
        return instance;
    }

    boolean soundon=true;

    String sound="";

    SharedPrefrenceConfig sharedPrefrenceConfig;
    LinearLayout ll_play,ll_level,ll_followus,ll_soundoff,ll_setting,ll_exit;
    ImageView iv_soundoff,iv_soundon;
    TextView tv_virsion,tv_sound;
    String levelid="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        context=ActivityHome.this;

        sharedPrefrenceConfig=new SharedPrefrenceConfig(context);
        onbindview();

        if(SharedPrefrenceConfig.getPrefString(context,Constance.keylevelid1,"")!=null &&
                !SharedPrefrenceConfig.getPrefString(context,Constance.keylevelid1,"").equals("")){
            levelid=SharedPrefrenceConfig.getPrefString(context,Constance.keylevelid1,"");
            Log.e("levelid","level : "+levelid);
        }else {
            levelid="1";
           SharedPrefrenceConfig.saveStringPreferance(context,Constance.keylevelid1,levelid);
            Log.e("levelid","level : "+levelid);
        }

        if(SharedPrefrenceConfig.getPrefString(context,Constance.soundonkey,"")!=null &&
                !SharedPrefrenceConfig.getPrefString(context,Constance.soundonkey,"").equals("")){
            sound=SharedPrefrenceConfig.getPrefString(context,Constance.soundonkey,"");
        }else {
            sound="true";
            SharedPrefrenceConfig.saveStringPreferance(context,Constance.soundonkey,sound);
        }

        ll_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ActivityLevelContent.class);
                intent.putExtra("leveln", levelid);
                startActivity(intent);
            }
        });

        ll_level.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ActivityListofLevel.class);
                startActivity(intent);
            }
        });

        ll_followus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openinstagram();
                    }
                });

        ll_soundoff.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(sound.equals("true")){
                            sound="false";
                            getsound();
                        }else {
                            sound="true";
                            getsound();
                        }

                    }
                });


        ll_setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getsettingpopup();
                    }
                });

        ll_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        getexitpopup();
                    }
                });
    }

    public void onbindview(){
        ll_play=findViewById(R.id.ll_play);
        ll_level=findViewById(R.id.ll_level);
        ll_followus=findViewById(R.id.ll_followus);
        ll_soundoff=findViewById(R.id.ll_soundoff);
        ll_setting=findViewById(R.id.ll_setting);
        ll_exit=findViewById(R.id.ll_exit);
        iv_soundoff=findViewById(R.id.iv_soundoff);
        iv_soundon=findViewById(R.id.iv_soundon);
        tv_virsion=findViewById(R.id.tv_virsion);
        tv_sound=findViewById(R.id.tv_sound);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(SharedPrefrenceConfig.getPrefString(context,Constance.keylevelid1,"")!=null &&
                !SharedPrefrenceConfig.getPrefString(context,Constance.keylevelid1,"").equals("")){
            levelid=SharedPrefrenceConfig.getPrefString(context,Constance.keylevelid1,"");
            Log.e("levelid","level : "+levelid);
        }else {
            levelid="1";
            SharedPrefrenceConfig.saveStringPreferance(context,Constance.keylevelid1,levelid);
            Log.e("levelid","level : "+levelid);
        }

        if(SharedPrefrenceConfig.getPrefString(context,Constance.soundonkey,"")!=null &&
                !SharedPrefrenceConfig.getPrefString(context,Constance.soundonkey,"").equals("")){
            sound=SharedPrefrenceConfig.getPrefString(context,Constance.soundonkey,"");
            getsound();
        }else {
            sound="true";
            SharedPrefrenceConfig.saveStringPreferance(context,Constance.soundonkey,"true");
            getsound();
        }

    }

    public void openinstagram(){
        Uri uri = Uri.parse(getResources().getString(R.string.instagram_url));
        Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

        likeIng.setPackage("com.instagram.android");

        try {
            startActivity(likeIng);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    uri));
        }
    }

    public void getsound(){
        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if(sound.equals("true")){
            iv_soundoff.setVisibility(View.GONE);
            iv_soundon.setVisibility(View.VISIBLE);
            tv_sound.setText("SOUND ON");
            SharedPrefrenceConfig.saveStringPreferance(context,Constance.soundonkey,"true");
//            UnMuteAudio();
        }else {
            iv_soundoff.setVisibility(View.VISIBLE);
            iv_soundon.setVisibility(View.GONE);
            tv_sound.setText("SOUND OFF");
            SharedPrefrenceConfig.saveStringPreferance(context,Constance.soundonkey,"false");
//            MuteAudio();
        }
    }

    public void MuteAudio(){
        AudioManager mAlramMAnager = (AudioManager) getApplicationContext().getSystemService(context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_MUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_MUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_MUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_MUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_MUTE, PendingIntent.FLAG_IMMUTABLE);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, true);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, true);
        }
    }

    public void UnMuteAudio(){
        AudioManager mAlramMAnager = (AudioManager) getApplicationContext().getSystemService(context.AUDIO_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_NOTIFICATION, AudioManager.ADJUST_UNMUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_ALARM, AudioManager.ADJUST_UNMUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_UNMUTE,PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_RING, AudioManager.ADJUST_UNMUTE, PendingIntent.FLAG_IMMUTABLE);
            mAlramMAnager.adjustStreamVolume(AudioManager.STREAM_SYSTEM, AudioManager.ADJUST_UNMUTE, PendingIntent.FLAG_IMMUTABLE);
        } else {
            mAlramMAnager.setStreamMute(AudioManager.STREAM_NOTIFICATION, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_ALARM, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_MUSIC, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_RING, false);
            mAlramMAnager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
        }
    }

    private void playAlarm() {
        MediaPlayer mp = MediaPlayer.create(this,
                R.raw.test_audio);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void getsettingpopup(){

        LinearLayout  ll_restart,ll_adsetting;
        TextView tv_privacypolicy,tv_close;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_setting,null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        ll_restart=layout.findViewById(R.id.ll_restart);
        ll_adsetting=layout.findViewById(R.id.ll_adsetting);
        tv_privacypolicy=layout.findViewById(R.id.tv_privacypolicy);
        tv_close=layout.findViewById(R.id.tv_close);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ll_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                getrestartdata_popup();
            }
        });

        ll_adsetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                getadsettingpopup();
            }
        });
        tv_privacypolicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(R.string.privacy_policy)));
                startActivity(browserIntent);
            }
        });
    }

    public void getexitpopup(){

        TextView  tv_cancel,tv_exit;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_exitapp,null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        tv_cancel=layout.findViewById(R.id.tv_cancel);
        tv_exit=layout.findViewById(R.id.tv_exit);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tv_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });

    }

    public void getadsettingpopup(){
        LinearLayout  ll_personlize,ll_nonpersonlise;
        TextView tv_close,tv_nonpersonlise,tv_personlise;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_adsetting,null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        ll_personlize=layout.findViewById(R.id.ll_personlize);
        ll_nonpersonlise=layout.findViewById(R.id.ll_nonpersonlise);
        tv_close=layout.findViewById(R.id.tv_close);
        tv_nonpersonlise=layout.findViewById(R.id.tv_nonpersonlise);
        tv_personlise=layout.findViewById(R.id.tv_personlise);

        ll_personlize.setBackground(getResources().getDrawable(R.drawable.box_radi_solid));
        ll_nonpersonlise.setBackground(null);
        tv_nonpersonlise.setTextColor(getResources().getColor(R.color.colortheme_black));
        tv_personlise.setTextColor(getResources().getColor(R.color.colortheme_white));

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ll_personlize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_personlize.setBackground(getResources().getDrawable(R.drawable.box_radi_solid));
                ll_nonpersonlise.setBackground(null);
                tv_nonpersonlise.setTextColor(getResources().getColor(R.color.colortheme_black));
                tv_personlise.setTextColor(getResources().getColor(R.color.colortheme_white));
            }
        });
        ll_nonpersonlise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_nonpersonlise.setBackground(getResources().getDrawable(R.drawable.box_radi_solid));
                ll_personlize.setBackground(null);
                tv_nonpersonlise.setTextColor(getResources().getColor(R.color.colortheme_white));
                tv_personlise.setTextColor(getResources().getColor(R.color.colortheme_black));
            }
        });
    }

    public void getrestartdata_popup(){

        LinearLayout  ll_cancel,ll_clear;
        TextView tv_cancel,tv_clear;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_restart,null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        ll_cancel=layout.findViewById(R.id.ll_cancel);
        ll_clear=layout.findViewById(R.id.ll_clear);
        tv_cancel=layout.findViewById(R.id.tv_cancel);
        tv_clear=layout.findViewById(R.id.tv_clear);

        ll_clear.setBackground(getResources().getDrawable(R.drawable.box_radi_solid));
        ll_cancel.setBackground(null);
        tv_cancel.setTextColor(getResources().getColor(R.color.colortheme_black));
        tv_clear.setTextColor(getResources().getColor(R.color.colortheme_white));

        ll_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ll_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ll_cancel.setBackground(getResources().getDrawable(R.drawable.box_radi_solid));
                ll_clear.setBackground(null);
                tv_cancel.setTextColor(getResources().getColor(R.color.colortheme_white));
                tv_clear.setTextColor(getResources().getColor(R.color.colortheme_black));

                levelid="1";
                SharedPrefrenceConfig.saveStringPreferance(context,Constance.keylevelid1,"1");
                popupWindow.dismiss();

                getafter_restartpopup();
            }
        });

    }

    public void getafter_restartpopup(){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_after_restart,null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                popupWindow.dismiss();
            }
        },2000);

    }


    @Override
    public void onBackPressed() {
        getexitpopup();
    }
}