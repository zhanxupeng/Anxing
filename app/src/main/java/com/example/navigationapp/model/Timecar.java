package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/1/16.
 */

public class Timecar {
    private int id;
    private int car_id;
    private double xx;
    private double yy;
    private double speed;
    private int time;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public double getXx() {
        return xx;
    }

    public void setXx(double xx) {
        this.xx = xx;
    }

    public double getYy() {
        return yy;
    }

    public void setYy(double yy) {
        this.yy = yy;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
