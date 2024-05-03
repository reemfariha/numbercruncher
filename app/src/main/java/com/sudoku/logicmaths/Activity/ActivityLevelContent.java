package com.sudoku.logicmaths.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sudoku.logicmaths.Adapter.AdapterNumberList;
import com.sudoku.logicmaths.Model.modelanswer;
import com.sudoku.logicmaths.R;
import com.sudoku.logicmaths.Utils.Constance;
import com.sudoku.logicmaths.Utils.SharedPrefrenceConfig;

import java.util.ArrayList;


public class ActivityLevelContent extends AppCompatActivity {

    Context context;

    public static ActivityLevelContent instance = null;

    public ActivityLevelContent() {
        instance = ActivityLevelContent.this;
    }

    public static synchronized ActivityLevelContent getInstance() {
        if (instance == null) {
            instance = new ActivityLevelContent();
        }
        return instance;
    }

    SharedPrefrenceConfig sharedPrefrenceConfig;


    int headerlevel;

    MediaPlayer mp;

    ImageView iv_back, iv_eraseanswer, iv_hint, iv_image;
    TextView tv_level, tv_enter;
    public EditText et_answer;
    RecyclerView rv_number;
    public ArrayList<Integer> arrayList_answer = new ArrayList<>();

    ArrayList<Integer> arrayList_numberlist = new ArrayList<>();
    AdapterNumberList adapterNumberList;

    public String answer = "";

    public StringBuilder answertext1;

    String hintfrom = "";
    String hintimageurl = "";
    String solutionimageurl = "";

    String level_id = "";

    Activity activity;

    int nextlevel;

    String sound = "";

    int count = 0;

    RelativeLayout rl_nativead;

    int questionimageNo = 1;
    int hintNo = 1;
    int solutionNo = 1;

    ArrayList<modelanswer> arrayList_answer1 = new ArrayList<>();

    int imageResource_hint=1;
    int imageResource_solution=1;
    int imageResource_question=1;

    String answertext="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = ActivityLevelContent.this;
        activity = ActivityLevelContent.this;
        setContentView(R.layout.activity_levelcontent);
        sharedPrefrenceConfig = new SharedPrefrenceConfig(context);
        sound = SharedPrefrenceConfig.getPrefString(context, Constance.soundonkey, "");

        bindview();
        getnumberlist();

        arrayList_answer1.clear();
        getallanswer();
        answertext1 = new StringBuilder();

        if (getIntent().hasExtra("leveln")) {
            level_id = getIntent().getStringExtra("leveln");
            tv_level.setText(level_id);
        }

        levelcontent();



        iv_eraseanswer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arrayList_answer.clear();
                et_answer.setText("");
                et_answer.setHint("Answer");
            }
        });

        iv_hint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gethelp_popup();
            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tv_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<arrayList_answer1.size();i++){
                    if(arrayList_answer1.get(i).getLevelid()==Integer.parseInt(level_id)){
                        answertext=String.valueOf(arrayList_answer1.get(i).getAnswer());
                        break;
                    }
                }

                if (et_answer.getText().toString() != null && !et_answer.getText().toString().equals("")) {
                    checkanswer();
                }else {
                    Toast.makeText(context, "Please fill answer", Toast.LENGTH_SHORT).show();
                }
            }
        });

        et_answer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int position = et_answer.length();
                Editable etext = et_answer.getText();
                Selection.setSelection(etext, position);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void bindview() {
        iv_back = findViewById(R.id.iv_back);
        iv_eraseanswer = findViewById(R.id.iv_eraseanswer);
        iv_hint = findViewById(R.id.iv_hint);
        tv_level = findViewById(R.id.tv_level);
        iv_image = findViewById(R.id.iv_image);
        tv_enter = findViewById(R.id.tv_enter);
        et_answer = findViewById(R.id.et_answer);
        rv_number = findViewById(R.id.rv_number);
        rl_nativead = findViewById(R.id.rl_nativead);

        et_answer.setShowSoftInputOnFocus(false);
    }

    public void getnumberlist() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 5);
        rv_number.setLayoutManager(gridLayoutManager);

        arrayList_numberlist.clear();

        for (int i = 0; i < 10; i++) {
            arrayList_numberlist.add(i);
        }
        adapterNumberList = new AdapterNumberList(context, arrayList_numberlist);
        rv_number.setAdapter(adapterNumberList);
    }

    public void getanswer() {
        answertext1.setLength(0);
        if (arrayList_answer.size() > 0) {
            for (int i = 0; i < arrayList_answer.size(); i++) {
                answertext1.append(arrayList_answer.get(i).toString());
            }
        }

        et_answer.setText(answertext1);
    }

    public void levelcontent(){
        questionimageNo = Integer.parseInt(level_id);
        String questionimage = "que_img_" + questionimageNo;
        imageResource_question = context.getResources().getIdentifier(questionimage, "drawable", context.getPackageName());
        iv_image.setImageResource(imageResource_question);

        hintNo = Integer.parseInt(level_id);
        String hintimage = "hint_img_" + hintNo;
        imageResource_hint = context.getResources().getIdentifier(hintimage, "drawable", context.getPackageName());

        solutionNo = Integer.parseInt(level_id);
        String solutionimage = "solution_img_" + hintNo;
        imageResource_solution = context.getResources().getIdentifier(solutionimage, "drawable", context.getPackageName());

    }

    public void checkanswer(){

        if(et_answer.getText().toString().equals(answertext)){

            if (sound.equals("true")) {
                playarchive();
            }
            et_answer.setText("");
            et_answer.setHint("Answer");
            getnextlevelpopup();
        }else {
            if (sound.equals("true")) {
                playloss();
            }
            count = count + 1;
            if (count == 3) {
                count = 0;
            }
            Log.e("count", "count : " + count);
            et_answer.setText("");
            et_answer.setHint("Answer");
            Toast.makeText(ActivityLevelContent.this, "Answer is wrong please try again!!", Toast.LENGTH_SHORT).show();
        }
        arrayList_answer.clear();

    }

    private void playarchive() {
        MediaPlayer mp = MediaPlayer.create(this,
                R.raw.archiv);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    private void playloss() {
        mp = MediaPlayer.create(this,
                R.raw.loss);
        mp.start();
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.release();
            }
        });
    }

    public void getnextlevelpopup() {

        LinearLayout ll_nextlevel;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_correctanswer, null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        ll_nextlevel = layout.findViewById(R.id.ll_nextlevel);

        ll_nextlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextlevel = Integer.valueOf(level_id);
                level_id = String.valueOf(nextlevel + 1);
                tv_level.setText(level_id);
                answertext="";
                SharedPrefrenceConfig.saveStringPreferance(context, Constance.keylevelid1, level_id);
                levelcontent();
                popupWindow.dismiss();
            }
        });

    }

    public void gethelp_popup() {

        LinearLayout ll_hint1, ll_solution;
        TextView tv_close;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_needhelp, null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        ll_hint1 = layout.findViewById(R.id.ll_hint1);
        ll_solution = layout.findViewById(R.id.ll_solution);
        tv_close = layout.findViewById(R.id.tv_close);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        ll_hint1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                hintfrom = "hint";
                gethint_popup(hintfrom);

            }
        });

        ll_solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                hintfrom = "solution";
                gethint_popup("solution");

            }
        });
    }

    public void gethint_popup(String from) {

        ImageView iv_hintimage;
        TextView tv_close;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popup_hint, null);
        PopupWindow popupWindow = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        popupWindow.setFocusable(true);
        popupWindow.showAtLocation(layout, Gravity.CENTER, 0, 0);

        iv_hintimage = layout.findViewById(R.id.iv_hintimage);
        tv_close = layout.findViewById(R.id.tv_close);

        tv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        if (from.equals("hint")) {
            iv_hintimage.setImageResource(imageResource_hint);
        } else if (from.equals("solution")) {
            iv_hintimage.setImageResource(imageResource_solution);
        }
    }

    public void getallanswer() {
        arrayList_answer1.add(new modelanswer(1, 32));
        arrayList_answer1.add(new modelanswer(2, 17));
        arrayList_answer1.add(new modelanswer(3, 32));
        arrayList_answer1.add(new modelanswer(4, 225));
        arrayList_answer1.add(new modelanswer(5, 20));
        arrayList_answer1.add(new modelanswer(6, 13));
        arrayList_answer1.add(new modelanswer(7, 1));
        arrayList_answer1.add(new modelanswer(8, 15));
        arrayList_answer1.add(new modelanswer(9, 75));
        arrayList_answer1.add(new modelanswer(10, 2));
        arrayList_answer1.add(new modelanswer(11, 18));
        arrayList_answer1.add(new modelanswer(12, 5));
        arrayList_answer1.add(new modelanswer(13, 36));
        arrayList_answer1.add(new modelanswer(14, 64));
        arrayList_answer1.add(new modelanswer(15, 41));
        arrayList_answer1.add(new modelanswer(16, 54));
        arrayList_answer1.add(new modelanswer(17, 39));
        arrayList_answer1.add(new modelanswer(18, 2));
        arrayList_answer1.add(new modelanswer(19, 62));
        arrayList_answer1.add(new modelanswer(20, 9));
        arrayList_answer1.add(new modelanswer(21, 55));
        arrayList_answer1.add(new modelanswer(22, 7113));
        arrayList_answer1.add(new modelanswer(23, 26));
        arrayList_answer1.add(new modelanswer(24, 36));
        arrayList_answer1.add(new modelanswer(25, 48));
        arrayList_answer1.add(new modelanswer(26, 49));
        arrayList_answer1.add(new modelanswer(27, 500));
        arrayList_answer1.add(new modelanswer(28, 13));
        arrayList_answer1.add(new modelanswer(29, 1));
        arrayList_answer1.add(new modelanswer(30, 8));
        arrayList_answer1.add(new modelanswer(31, 90));
        arrayList_answer1.add(new modelanswer(32, 5));
        arrayList_answer1.add(new modelanswer(33, 2000));
        arrayList_answer1.add(new modelanswer(34, 240));
        arrayList_answer1.add(new modelanswer(35, 72));
        arrayList_answer1.add(new modelanswer(36, 150));
        arrayList_answer1.add(new modelanswer(37, 63));
        arrayList_answer1.add(new modelanswer(38, 21));
        arrayList_answer1.add(new modelanswer(39, 4));
        arrayList_answer1.add(new modelanswer(40, 1024));
        arrayList_answer1.add(new modelanswer(41, 7831));
        arrayList_answer1.add(new modelanswer(42, 105));
        arrayList_answer1.add(new modelanswer(43, 49));
        arrayList_answer1.add(new modelanswer(44, 200));
        arrayList_answer1.add(new modelanswer(45, 41));
        arrayList_answer1.add(new modelanswer(46, 4));
        arrayList_answer1.add(new modelanswer(47, 27));
        arrayList_answer1.add(new modelanswer(48, 3804));
        arrayList_answer1.add(new modelanswer(49, 8946));
        arrayList_answer1.add(new modelanswer(50, 44));
        arrayList_answer1.add(new modelanswer(51, 17));
        arrayList_answer1.add(new modelanswer(52, 14));
        arrayList_answer1.add(new modelanswer(53, 32));
        arrayList_answer1.add(new modelanswer(54, 49));
        arrayList_answer1.add(new modelanswer(55, 64));
        arrayList_answer1.add(new modelanswer(56, 61));
        arrayList_answer1.add(new modelanswer(57, 17));
        arrayList_answer1.add(new modelanswer(58, 10));
        arrayList_answer1.add(new modelanswer(59, 26));
        arrayList_answer1.add(new modelanswer(60, 4));
        arrayList_answer1.add(new modelanswer(61, 4));
        arrayList_answer1.add(new modelanswer(62, 2));
        arrayList_answer1.add(new modelanswer(63, 12));
        arrayList_answer1.add(new modelanswer(64, 23));
        arrayList_answer1.add(new modelanswer(65, 81));
        arrayList_answer1.add(new modelanswer(66, 16));
        arrayList_answer1.add(new modelanswer(67, 12));
        arrayList_answer1.add(new modelanswer(68, 70));
        arrayList_answer1.add(new modelanswer(69, 9));
        arrayList_answer1.add(new modelanswer(70, 48));
        arrayList_answer1.add(new modelanswer(71, 15));
        arrayList_answer1.add(new modelanswer(72, 160));
        arrayList_answer1.add(new modelanswer(73, 198));
        arrayList_answer1.add(new modelanswer(74, 7));
        arrayList_answer1.add(new modelanswer(75, 8));
        arrayList_answer1.add(new modelanswer(76, 481));
        arrayList_answer1.add(new modelanswer(77, 2401));
        arrayList_answer1.add(new modelanswer(78, 9));
        arrayList_answer1.add(new modelanswer(79, 20));
        arrayList_answer1.add(new modelanswer(80, 47));
        arrayList_answer1.add(new modelanswer(81, 564));
        arrayList_answer1.add(new modelanswer(82, 343));
        arrayList_answer1.add(new modelanswer(83, 136));
        arrayList_answer1.add(new modelanswer(84, 6));
        arrayList_answer1.add(new modelanswer(85, 26));
        arrayList_answer1.add(new modelanswer(86, 2));
        arrayList_answer1.add(new modelanswer(87, 6));
        arrayList_answer1.add(new modelanswer(88, 2842));
        arrayList_answer1.add(new modelanswer(89, 111));
        arrayList_answer1.add(new modelanswer(90, 50));
        arrayList_answer1.add(new modelanswer(91, 1));
        arrayList_answer1.add(new modelanswer(92, 75));
        arrayList_answer1.add(new modelanswer(93, 80));
        arrayList_answer1.add(new modelanswer(94, 54));
        arrayList_answer1.add(new modelanswer(95, 25));
        arrayList_answer1.add(new modelanswer(96, 23));
        arrayList_answer1.add(new modelanswer(97, 60));
        arrayList_answer1.add(new modelanswer(98, 3));
        arrayList_answer1.add(new modelanswer(99, 14));
        arrayList_answer1.add(new modelanswer(100, 24));

    }

}