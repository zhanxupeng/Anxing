package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/3/13.
 */

public class Forecast {
    private int id;
    private String route;
    private int time;
    private int rrank;
    private int trank;
    private int hrank;

    public int getHrank() {
        return hrank;
    }

    public void setHrank(int hrank) {
        this.hrank = hrank;
    }

    public int getTrank() {
        return trank;
    }

    public void setTrank(int trank) {
        this.trank = trank;
    }

    public int getRrank() {
        return rrank;
    }

    public void setRrank(int rrank) {
        this.rrank = rrank;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
