package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/3/4.
 */

public class Mycrowded {
    private int id;
    private int myflag;
    private int nextflag;
    private double speed;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public int getNextflag() {
        return nextflag;
    }

    public void setNextflag(int nextflag) {
        this.nextflag = nextflag;
    }

    public int getMyflag() {
        return myflag;
    }

    public void setMyflag(int myflag) {
        this.myflag = myflag;
    }
}
