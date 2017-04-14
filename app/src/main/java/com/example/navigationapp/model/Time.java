package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/2/1.
 */

public class Time {
    private int flag;
    private double xx;
    private double yy;
    private double time;
    public Time(int flag,double xx,double yy,double time){
        this.flag=flag;
        this.xx=xx;
        this.yy=yy;
        this.time=time;
    }
    public Time(){

    }
    public int getFlag() {
        return flag;
    }
    public void setFlag(int flag) {
        this.flag = flag;
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
    public double getTime() {
        return time;
    }
    public void setTime(double time) {
        this.time = time;
    }
}
