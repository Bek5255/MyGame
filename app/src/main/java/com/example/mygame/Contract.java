package com.example.mygame;
import com.example.mygame.model.Cordinate;
import java.util.ArrayList;

    public interface Contract  {
        interface Model {
            ArrayList getData();
        }
        interface View{
            void showWin();
            void loadData();
            void finish();
            void setElementText(Cordinate coordinate, String s);
            void setScore(int score);
            void startTimer();
            void setBackground(Cordinate coordinate);
            void clearBackground(Cordinate coordinate);
            void setAnimation (Cordinate cordinate, int g);
            String getElementText(Cordinate coordinate);


        }
        interface Presenter{
            void restart();
            void finish();
            void click(Cordinate cordinate);
            void pause();

        }
    }

