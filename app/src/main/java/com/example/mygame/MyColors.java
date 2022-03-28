package com.example.mygame;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.content.Context;
import android.graphics.Typeface;
import java.lang.reflect.Field;

public class MyColors {

    public static int getMyColor(int i) {
        return myColor[i];
    }
    public static int getMyGrad(int i) {
        return myGrad[i];
    }

    public static void overrideFont(Context context, String defaultFontNameToOverride, String customFontFileNameInAssets) {
        try {
            final Typeface customFontTypeface = Typeface.createFromAsset(context.getAssets(), customFontFileNameInAssets);
            final Field defaultFontTypefaceField = Typeface.class.getDeclaredField(defaultFontNameToOverride);
            defaultFontTypefaceField.setAccessible(true);
            defaultFontTypefaceField.set(null, customFontTypeface);
        } catch (Exception e) {}
    }


    private static final int[] myGrad = {
            R.xml.grad_1,
            R.xml.grad_2,
            R.xml.grad_3,
            R.xml.grad_4,
            R.xml.grad_5,
            R.xml.grad_6,
            R.xml.grad_7,
            R.xml.grad_8,
            R.xml.grad_9,
            R.xml.grad_10};


    private static int[] myColor = {

            Color.parseColor("#FFDE00"),//0
            Color.parseColor("#162EAE"),//1
            Color.parseColor("#FFDE00"),//2
            Color.parseColor("#FB000D"),//3
            Color.parseColor("#41DB00"),//4
            Color.parseColor("#7109AA"),//5
            Color.parseColor("#FF9500"),//6
            Color.parseColor("#078600"),
            Color.parseColor("#7109AA"),
            Color.parseColor("#0776A0"),
            Color.parseColor("#FFEB3B"),
            Color.parseColor("#CDDC39"),
            Color.parseColor("#8BC04A"),
            Color.parseColor("#4CAF50"),
            Color.parseColor("#009688"),
            Color.parseColor("#00BCD4"),
            Color.parseColor("#03A9F4"),//
            Color.parseColor("#2196F3"),
            Color.parseColor("#3F51B5"),
            Color.parseColor("#673AB7"),
            Color.parseColor("#9C27B0"),
            Color.parseColor("#E91E63"),
            Color.parseColor("#F44336"),
            Color.parseColor("#FFC107"),
            Color.parseColor("#ffffff")};
}
