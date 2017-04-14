package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/1/17.
 */

public class Nowcar {
    private int car_id;
    private double xx;
    private double yy;
    private double speed;
    private int flag;
    private double direction;

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public int getFlag() {

        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public double getSpeed() {

        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getYy() {

        return yy;
    }

    public void setYy(double yy) {
        this.yy = yy;
    }

    public double getXx() {

        return xx;
    }

    public void setXx(double xx) {
        this.xx = xx;
    }

    public int getCar_id() {

        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }
}
