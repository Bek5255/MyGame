package com.example.mygame;

import static com.example.mygame.MyColors.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mygame.model.Cordinate;

import java.util.logging.LogRecord;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ConstraintLayout constraintLayout;
    LinearLayout linearLayoutMain;
    ImageView fon;
    Button start;
    ImageButton exit;
    ImageButton settings;
    ImageView help;
    Button mode;
    ImageButton[][] numbers = new ImageButton[5][5];
    ImageView imageMode;
    MyAnimation myAnimation = new MyAnimation(this);
    public static int modeCount = 5;
    final String SETTINGS = "SETTINGS";
    static final String COLOR = "colors";
    static SharedPreferences mySettings;
    private boolean visibleSettings = false;

    Handler handler;
    Handler.Callback hc = new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                myAnimation.rotateX(linearLayoutMain, 300, 90, 180);
                selectMode();
            } else {
                selectMode();
                selectColor();
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySettings = getSharedPreferences(SETTINGS, Context.MODE_PRIVATE);
        fullScreen();
        constraintLayout = findViewById(R.id.container);
        linearLayoutMain = findViewById(R.id.linearLayoutMain);
        fon = findViewById(R.id.fon);
        start = findViewById(R.id.start);
        exit = findViewById(R.id.exit);
        settings = findViewById(R.id.settings);
        help = findViewById(R.id.help);
        mode = findViewById(R.id.mode);
        imageMode = findViewById(R.id.imageMode);

        exit.setOnClickListener(this);
        settings.setOnClickListener(this);
        start.setOnClickListener(this);
        help.setOnClickListener(this);
        mode.setOnClickListener(this);

        setSettings(constraintLayout, fon);
        handler = new Handler(hc);

        start.setTextColor(MyColors.getMyColor(mySettings.getInt(COLOR, 1)));
        mode.setTextColor(MyColors.getMyColor(mySettings.getInt(COLOR, 1)));
        int counter = 0;
        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = (LinearLayout) linearLayoutMain.getChildAt(i);
            for (int j = 0; j < 5; j++) {
                numbers[i][j] = (ImageButton) linearLayout.getChildAt(j);
                numbers[i][j].setOnClickListener(this);
                numbers[i][j].setTag(counter);
                numbers[i][j].setEnabled(false);
                counter++;
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.exit:
                finish();
                break;

            case R.id.settings:
                modeCount = 5;
                if (visibleSettings) {
                    visibleSettings = false;
                    for (int i = 0; i < 5; i++) {
                        for (int k = 0; k < 5; k++) {
                            if (i < 2) {
                                numbers[i][k].setImageResource(R.drawable.my_btn);
                            } else {
                                numbers[i][k].setBackground(getDrawable(R.drawable.my_btn));
                            }
                            numbers[i][k].setEnabled(false);
                        }
                    }
                    myAnimation.rotateX(linearLayoutMain, 300, 0, 90);
                    handler.sendEmptyMessageDelayed(1, 300);
                    imageMode.setImageResource(R.drawable.puzzle_24);
                    mode.setEnabled(true);
                    start.setEnabled(true);
                    break;
                }
                mode.setEnabled(false);
                start.setEnabled(false);
                myAnimation.rotateX(linearLayoutMain, 300, 0, 90);
                handler.sendEmptyMessageDelayed(2, 300);
                imageMode.setImageResource(R.drawable.choose_color);
                break;

            case R.id.start:
                start.setEnabled(false);
                Intent intentGame = new Intent(this, GameActivity.class);
                intentGame.putExtra("modeCount", modeCount);
                startActivity(intentGame);
                break;

            case R.id.mode:
                if (modeCount == 5) {
                    modeCount = 4;
                    myAnimation.rotateX(linearLayoutMain, 300, 0, 90);
                    handler.sendEmptyMessageDelayed(1, 300);
                    imageMode.setImageResource(R.drawable.puzzle_15);
                } else if (modeCount == 4) {
                    modeCount = 3;
                    myAnimation.rotateX(linearLayoutMain, 300, 0, 90);
                    handler.sendEmptyMessageDelayed(1, 300);
                    imageMode.setImageResource(R.drawable.puzzle_8);
                } else {
                    modeCount = 5;
                    myAnimation.rotateX(linearLayoutMain, 300, 0, 90);
                    handler.sendEmptyMessageDelayed(1, 300);
                    imageMode.setImageResource(R.drawable.puzzle_24);
                }
                break;
            case R.id.help:
                Intent intent = new Intent(this, HelpActivity.class);
                startActivity(intent);
                break;

            default:
                constraintLayout.setBackgroundColor(getMyColor((Integer) v.getTag()));
                if ((Integer) v.getTag() < 10) {
                    fon.setVisibility(View.VISIBLE);
                    fon.setImageResource(MyColors.getMyGrad((Integer) v.getTag()));
                } else {
                    fon.setVisibility(View.INVISIBLE);
                }
                start.setTextColor(getMyColor((Integer) v.getTag()));
                mode.setTextColor(getMyColor((Integer) v.getTag()));
                SharedPreferences.Editor mySettingsEditor = mySettings.edit();
                mySettingsEditor.putInt(COLOR, (Integer) v.getTag());
                mySettingsEditor.apply();
                mode.setEnabled(true);
                start.setEnabled(true);
                visibleSettings = false;
                int c = mySettings.getInt(COLOR, 1);
                for (int i = 0; i < 5; i++) {
                    for (int k = 0; k < 5; k++) {
                        if (i < 2) {
                            numbers[i][k].setImageResource(R.drawable.my_btn);
                        } else {
                            numbers[i][k].setBackground(getDrawable(R.drawable.my_btn));
                        }
                        numbers[i][k].setEnabled(false);
                    }
                }
                imageMode.setImageResource(R.drawable.puzzle_24);
        }
    }

    public void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        Window window = getWindow();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_IMMERSIVE;
        decorView.setSystemUiVisibility(uiOptions);
    }

    public static void setSettings(View view, ImageView fon) {
        if (mySettings.contains(COLOR)) {
            int c = mySettings.getInt(COLOR, 23);
            if (c < 10) {
                fon.setImageResource(MyColors.getMyGrad(c));
            } else {
                view.setBackgroundColor(getMyColor(c));
                fon.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        start.setEnabled(true);
    }

    public void selectMode() {
        for (int f = 0; f < 5; f++) {
            linearLayoutMain.getChildAt(f).setVisibility(View.VISIBLE);
        }
        for (ImageButton[] v : numbers) {
            for (ImageButton z : v) {
                z.setVisibility(View.VISIBLE);
            }
        }
        int y = 0;
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                if (k >= modeCount) {
                    numbers[i][k].setVisibility(View.GONE);
                }
                if (i >= modeCount) {
                    linearLayoutMain.getChildAt(i).setVisibility(View.GONE);
                }
                y++;
            }
        }
    }

    public void selectColor() {
        visibleSettings = true;
        modeCount = 5;
        myAnimation.rotateX(linearLayoutMain, 300, 90, 180);
        imageMode.setImageResource(R.drawable.choose_color);
        int t = 0;
        for (int i = 0; i < 5; i++) {
            for (int k = 0; k < 5; k++) {
                if (t < 10) {
                    numbers[i][k].setImageResource(MyColors.getMyGrad(t));
                } else {
                    numbers[i][k].setBackgroundColor(getMyColor(t));
                }
                numbers[i][k].setEnabled(true);
                t++;
            }
        }
    }

}