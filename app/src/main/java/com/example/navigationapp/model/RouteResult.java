package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/4/14.
 */

public class RouteResult {
    private String mypicture;
    private String route;
    private String routeid;
    private int zhishu;
    private double time;
    private double price;
    public RouteResult(){

    }
    public RouteResult(String mypicture,String route,String routeid,int zhishu,double time,double price){
        this.route=route;
        this.routeid=routeid;
        this.zhishu=zhishu;
        this.time=time;
        this.price=price;
        this.mypicture=mypicture;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public String getRoute() {
        return route;
    }
    public void setRoute(String route) {
        this.route = route;
    }
    public String getRouteid() {
        return routeid;
    }
    public void setRouteid(String routeid) {
        this.routeid = routeid;
    }
    public int getZhishu() {
        return zhishu;
    }
    public void setZhishu(int zhishu) {
        this.zhishu = zhishu;
    }
    public double getTime() {
        return time;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public String getMypicture() {
        return mypicture;
    }
    public void setMypicture(String mypicture) {
        this.mypicture = mypicture;
    }
}
