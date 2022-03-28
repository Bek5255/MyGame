package com.example.mygame;

import static com.example.mygame.MainActivity.modeCount;
import static java.lang.Math.abs;
import static java.util.Collections.shuffle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;

import com.example.mygame.model.Cordinate;

import java.util.ArrayList;

public class Presenter implements Contract.Presenter {
    private Contract.Model model;
    private Contract.View view;
    private Cordinate space;
    private int score;


    public Presenter(Contract.View view, Contract.Model model) {
        this.model = model;
        this.view = view;
        restart();
    }

    @Override
    public void restart() {
        score = 0;
        view.setScore(score);
        view.startTimer();

        int b = 0;
        for (int i = 0; i < modeCount; i++) {
            for (int j = 0; j < modeCount; j++, b++) {
                if (model.getData().get(b).equals(0)) {
                    space = new Cordinate(i, j);
                }
            }
        }
        view.loadData();
    }

    @Override
    public void finish() {
        view.finish();

    }

    @Override
    public void click(Cordinate cordinate) {
        int x = abs(cordinate.getX() - space.getX());
        int y = abs(cordinate.getY() - space.getY());
        if (x + y == 1) {
            int g = 0;
            view.setScore(++score);
            view.setElementText(space, view.getElementText(cordinate));
            view.setBackground(space);
            view.clearBackground(cordinate);
            if (cordinate.getY() > space.getY()) {
                g = 1;
            } else if (cordinate.getY() < space.getY()) {
                g = 2;
            } else if (cordinate.getX() > space.getX()) {
                g = 3;
            } else if (cordinate.getX() < space.getX()) {
                g = 4;
            }
            view.setAnimation(space, g);
//            view.setElementText(cordinate, "");
            space = cordinate;
            if (isWin()) {
                view.showWin();
            }
        }
    }

    @Override
    public void pause() {


    }

    private boolean isWin() {

        if (!(space.getY() == modeCount - 1 && space.getX() == modeCount - 1 || space.getY() == 0 && space.getX() == 0)) {
            return false;
        }
        for (int i = 0; i < modeCount * modeCount - 1; i++) {
            if (!(view.getElementText(new Cordinate(i / modeCount, i % modeCount)).equals(String.valueOf(i + 1)) || view.getElementText(new Cordinate((i + 1) / modeCount, (i + 1) % modeCount)).equals(String.valueOf(i + 1)))) {
                return false;
            }
        }
        return true;
    }
}