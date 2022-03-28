package com.example.mygame;

import static com.example.mygame.MainActivity.modeCount;
import static com.example.mygame.MainActivity.mySettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class YouWin extends AppCompatActivity {
    ConstraintLayout constraintYouWin;
//    Button rest;
    TextView textScore;
    TextView theBestResult;
    ImageView fonWin;
    static final String SCORE = "winScore";
    static final String TIME = "winTime";
    String saveTime = TIME + modeCount;
    String saveScore = SCORE + modeCount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_you_win);
        constraintYouWin = findViewById(R.id.constraintYouWin);
        fonWin = findViewById(R.id.fonWin);
        fullScreen();

        MainActivity.setSettings(constraintYouWin, fonWin);
        theBestResult = findViewById(R.id.theBestResult);
//        rest = findViewById(R.id.rest);
//        rest.setOnClickListener(view -> GameActivity.restartGame);
        textScore = findViewById(R.id.textScore);

        String winScore = (String) getIntent().getSerializableExtra(SCORE);
        String winTime = (String) getIntent().getSerializableExtra(TIME);
        textScore.setText(String.format("You Win\nTime:  %s\n Clicks: %s", winTime, winScore));
        if (theBestResult()) {
            SharedPreferences.Editor mySettingsEditor = mySettings.edit();
            mySettingsEditor.putString(saveScore, (String) getIntent().getSerializableExtra(SCORE));
            mySettingsEditor.putString(saveTime, (String) getIntent().getSerializableExtra(TIME));
            mySettingsEditor.apply();
        }

        theBestResult.setText(String.format("The best result! \n Time:  %s\n Clicks: %s", mySettings.getString(saveTime, ""), mySettings.getString(saveScore, "")));
    }

    public void fullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        Window window = getWindow();
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

    public Boolean theBestResult() {
        String oldBestResult = mySettings.getString(saveTime, "10:00");
        String newBestResult = (String) getIntent().getSerializableExtra(TIME);
        if (timeToInt(oldBestResult) < timeToInt(newBestResult)) {
            return false;
        }
        return true;
    }

    public int timeToInt(String time) {
        int sec = Integer.parseInt(time.substring(3));
        int min = 60 * Integer.parseInt(time.substring(0, 2));
        return sec + min;
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        GameActivity.youWin = false;
//    }

}