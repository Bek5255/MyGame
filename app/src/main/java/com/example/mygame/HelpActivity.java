package com.example.mygame;

import static android.view.View.INVISIBLE;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.RelativeLayout;

public class HelpActivity extends AppCompatActivity {
    Button[][] numbers = new Button[4][4];
    GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
//        gridLayout = findViewById(R.id.gridLayout);

//        int j = 0;
//        for (int i = 0; i < 4; i++) {
//            for (int k = 0; k < 4; k++) {
//                numbers[i][k] = findViewById(gridLayout.getChildAt(j).getId());
//                numbers[i][k].setTag(j);
//
//                if (j == 15) {
//                    numbers[i][k].setVisibility(INVISIBLE);
//                } else {
//                    numbers[i][k].setText(Integer.toString(j + 1));
//                }
//                numbers[i][k].setEnabled(false);
//                j++;
//            }
//        }

    }
}