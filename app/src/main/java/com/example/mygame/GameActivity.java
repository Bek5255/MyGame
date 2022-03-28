package com.example.mygame;

import static com.example.mygame.MainActivity.COLOR;
import static com.example.mygame.MainActivity.modeCount;
import static com.example.mygame.MainActivity.mySettings;
import static com.example.mygame.MyAnimation.translateYAnimation;
import static com.example.mygame.YouWin.SCORE;
import static com.example.mygame.YouWin.TIME;

import static java.util.Collections.shuffle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mygame.model.Cordinate;
import com.example.mygame.model.Model;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity implements Contract.View {
    private static final String TAG = "GameActivity";
    private Presenter presenter;
    private Model model;
    private Button[][] buttons;
    private ImageView icPause;
    static boolean youWin = false;
    private TextView score;
    private Chronometer chronometer;
    private ImageView min;
    private ImageView hour;
    private ImageView sek;
    long time;
    public MyAnimation gameAnimation = new MyAnimation(this);
    LinearLayout linearLayoutGame;
    private ImageView fonGame;
    LinearLayout statusLinear;
    ConstraintLayout constraintGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        fullScreen();
        min = findViewById(R.id.min);
        hour = findViewById(R.id.hour);
        sek = findViewById(R.id.sek);
        linearLayoutGame = findViewById(R.id.linearLayoutGame);
        statusLinear = findViewById(R.id.statusLinear);
        model = new Model(modeCount);
        buttons = new Button[5][5];
        chronometer = new Chronometer(this);
        loadViews();
        presenter = new Presenter(this, model);
        constraintGame = findViewById(R.id.constraintGame);
        fonGame = findViewById(R.id.fonGame);
        MainActivity.setSettings(constraintGame, fonGame);
        translateYAnimation(statusLinear, 150, 0, 750);
    }

    private void loadViews() {
        chronometer = findViewById(R.id.chronometer);
        score = findViewById(R.id.score);
        ImageButton finish = findViewById(R.id.finish);
        finish.setOnClickListener(view -> presenter.finish());

        ImageButton pause = findViewById(R.id.pause);
        pause.setOnClickListener(view -> pause());
        icPause = findViewById(R.id.icPause);
        icPause.setOnClickListener(v -> {
            icPause.setVisibility(View.INVISIBLE);
            resumeTimer();
        });
        icPause.setVisibility(View.INVISIBLE);

        ImageButton restart = findViewById(R.id.restart);
        restart.setOnClickListener(view -> restartGame());

        for (int i = 0; i < 5; i++) {
            LinearLayout linearLayout = (LinearLayout) linearLayoutGame.getChildAt(i);
            for (int j = 0; j < 5; j++) {
                buttons[i][j] = (Button) linearLayout.getChildAt(j);
                buttons[i][j].setTextColor(MyColors.getMyColor(mySettings.getInt(COLOR, 23)));
                buttons[i][j].setOnClickListener(view -> presenter.click((Cordinate) view.getTag()));
                if (j >= modeCount) {
                    buttons[i][j].setVisibility(View.GONE);
                }
            }
            if (i >= modeCount) {
                linearLayoutGame.getChildAt(i).setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showWin() {
        youWin = true;
        chronometer.stop();
        Intent intentWinner = new Intent(this, YouWin.class);
        chronometer.getScrollBarFadeDuration();
        intentWinner.putExtra(TIME, chronometer.getText());
        intentWinner.putExtra(SCORE, score.getText());
        startActivity(intentWinner);
        gameAnimation.rotate(sek, 10000, 0, 0);
        gameAnimation.rotate(hour, 10000, 0, 0);
        gameAnimation.rotate(min, 10000, 0, 0);
    }

    @Override
    public void loadData() {
        ArrayList data = model.getData();
        int counter = 0;
        for (int i = 0; i < modeCount; i++) {
            for (int j = 0; j < modeCount; j++, counter++) {
                buttons[i][j].setText(data.get(counter).toString());
                buttons[i][j].setVisibility(View.VISIBLE);
                buttons[i][j].setTag(new Cordinate(i, j));
                if (data.get(counter).equals(0)) {
                    buttons[i][j].setVisibility(View.INVISIBLE);
                    Cordinate space = new Cordinate(i, j);
                }
            }
        }
    }

    @Override
    public void setElementText(Cordinate coordinate, String s) {
        buttons[coordinate.getX()][coordinate.getY()].setText(s);
    }

    @Override
    public void setScore(int score) {
        this.score.setText(score + "");
    }

    @Override
    public void startTimer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        gameAnimation.rotate(sek, 5000, 0, 90);
        gameAnimation.rotate(hour, 1000, 360, 330);
        gameAnimation.rotate(min, 10000, 0, 360);
    }

    public void pauseTimer() {
        chronometer.stop();
        time = SystemClock.elapsedRealtime() - chronometer.getBase();
    }

    public void resumeTimer() {
        chronometer.setBase(SystemClock.elapsedRealtime() - time);
        chronometer.start();
    }

    public void pause() {
        pauseTimer();
        icPause.setVisibility(View.VISIBLE);
    }

    @Override
    public void setBackground(Cordinate coordinate) {
        buttons[coordinate.getX()][coordinate.getY()].setVisibility(View.VISIBLE);
    }

    @Override
    public void clearBackground(Cordinate coordinate) {
        buttons[coordinate.getX()][coordinate.getY()].setVisibility(View.INVISIBLE);
    }

    @Override
    public void setAnimation(Cordinate coordinate, int g) {
        switch (g) {
            case 1:
                gameAnimation.left(buttons[coordinate.getX()][coordinate.getY()]);
                break;
            case 2:
                gameAnimation.right(buttons[coordinate.getX()][coordinate.getY()]);
                break;
            case 3:
                gameAnimation.up(buttons[coordinate.getX()][coordinate.getY()]);
                gameAnimation.up2(buttons[coordinate.getX() + 1][coordinate.getY()]);
                break;
            case 4:
                gameAnimation.down(buttons[coordinate.getX()][coordinate.getY()]);//
                gameAnimation.down2(buttons[coordinate.getX() - 1][coordinate.getY()]);//
                break;
        }
    }

    @Override
    public String getElementText(Cordinate coordinate) {
        return buttons[coordinate.getX()][coordinate.getY()].getText().toString();
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

    public void restartGame() {
        model = new Model(modeCount);
        presenter = new Presenter(this, model);
        presenter.restart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onStart() {
        super.onStart();
        resumeTimer();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseTimer();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }
}


