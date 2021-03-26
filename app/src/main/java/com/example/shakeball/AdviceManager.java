package com.example.shakeball;

import java.util.ArrayList;
import java.util.Random;

public class AdviceManager {
    private String adviceSetName;
    private ArrayList<BallAdvice> adviceList;
    private final Random rand = new Random();

    public void loadAdvice(){
        adviceList = new ArrayList<BallAdvice>();
        adviceList.add(new BallAdvice("play tennis"));
        adviceList.add(new BallAdvice("play basket"));
        adviceList.add(new BallAdvice("play volley"));
        adviceList.add(new BallAdvice("go to sleep"));
        adviceList.add(new BallAdvice("go to code"));
    }

    public BallAdvice getAdvice(){
        int rand_number = rand.nextInt(adviceList.size());
        return adviceList.get(rand_number);
    }
}
