package com.example.mygame.model;

import static com.example.mygame.MainActivity.modeCount;
import static java.util.Collections.shuffle;


import com.example.mygame.Contract;

import java.util.ArrayList;

public class Model implements Contract.Model {
    ArrayList<Integer> data = new ArrayList();

    public Model(int mode) {
        for (int i = 0; i < mode * mode; i++) {
            data.add(i);
        }
        shuffle(data);
        while (isSolvable(data)) {
            shuffle(data);
        }
    }

    @Override
    public ArrayList getData() {
        return data;
    }

    private boolean isSolvable(ArrayList<Integer> board) {
        int inversionSum = 0;
        for (int i = 0; i < board.size(); i++) {
            if (board.get(i) == 0) {
                inversionSum += ((i / modeCount) + 1);
                continue;
            }
            int count = 0;
            for (int j = i + 1; j < board.size(); j++) {
                if (board.get(i) == 0) {
                    continue;
                } else if (board.get(i) > board.get(j)) {
                    count++;
                }
            }
            inversionSum += count;
        }
        return (inversionSum % 2) == 0;
    }
}
