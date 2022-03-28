package com.example.mygame;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

public class MyAnimation extends Animation{

    private Context context;
    MyAnimation(Context context)
    {
        this.context = context;
    }

    public static void zoomAnimation(View view, float zoom, float resultZoom, int duration) {
        ValueAnimator animSelectMode = ValueAnimator.ofFloat(zoom, resultZoom);
        animSelectMode.setDuration(duration).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setScaleX((float) animation.getAnimatedValue());
                view.setScaleY((float) animation.getAnimatedValue());
            }
        });
        animSelectMode.start();
    }
    public static void translateXAnimation (View view, int start, int finish, int duration) {
        ValueAnimator animTranslateX = ValueAnimator.ofFloat(start, finish);
        animTranslateX.setDuration(duration).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationX((float) animation.getAnimatedValue());
                view.setTranslationY((float) animation.getAnimatedValue());
            }
        });
        animTranslateX.start();
    }
    public static void translateYAnimation (View view, int start, int finish, int duration) {
        ValueAnimator animTranslateX = ValueAnimator.ofFloat(start, finish);
        animTranslateX.setDuration(duration).addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                view.setTranslationY((float) animation.getAnimatedValue());
            }
        });
        animTranslateX.start();
    }

    public void up(View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.up);
        view.startAnimation(animation);
    }

    public void up2(View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.up2);
        view.startAnimation(animation);
    }

    public void down(View view) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down);
        view.startAnimation(animation);
    }

    public void down2(View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down2);
        view.startAnimation(animation);
    }
    public void left(View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.left);
        view.startAnimation(animation);
    }
    public void right (View view){
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.right);
        view.startAnimation(animation);
    }

    public  void  rotate (View view, int duration, float start, float finish){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotation", start, finish);
        animation.setDuration(duration);
        animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.start();
    }
    public  void  rotateX (View view, int duration, float start, float finish){
        ObjectAnimator animation = ObjectAnimator.ofFloat(view, "rotationY", start, finish);
        animation.setDuration(duration);
//        animation.setRepeatCount(ObjectAnimator.INFINITE);
//        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();
    }


    public void setStartOffset(int i) {

    }
}
