package com.example.shakeball;

public class BallObject {
    private String name;
    private int ImageResourceId;

    private AdviceObject[] AdviceList;

    BallObject(AdviceObject[] AdviceList_){
        AdviceList = AdviceList_;
    }

    public int getImageResourceId() {
        return ImageResourceId;
    }
    public String getName() {
        return name;
    }

}
