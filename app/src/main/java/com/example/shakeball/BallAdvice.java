package com.example.shakeball;

public class BallAdvice {
    BallAdvice(String text){
        this.text = text;
    }
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
