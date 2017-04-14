package com.example.navigationapp.model;

import java.io.Serializable;

/**
 * Created by 占大帅 on 2017/4/13.
 */

public class ForAttractions implements Serializable{
    private int id;
    private String name;
    private String detail;
    private String local;
    private String exterior;
    private String routepicture;
    private double time;
    private double price;
    private int forcrowds;
    private int forviewpreference;
    private int forweatherindex;
    private double distance;
    public ForAttractions(){

    }
    public ForAttractions(String name,double time, double price,int forcrowds,int forviewpreference,int forweatherindex,String exterior){
        this.name=name;
        this.time=time;
        this.price=price;
        this.forcrowds=forcrowds;
        this.forviewpreference=forviewpreference;
        this.forweatherindex=forweatherindex;
        this.exterior=exterior;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }
    public String getLocal() {
        return local;
    }
    public void setLocal(String local) {
        this.local = local;
    }
    public String getExterior() {
        return exterior;
    }
    public void setExterior(String exterior) {
        this.exterior = exterior;
    }
    public String getRoutepicture() {
        return routepicture;
    }
    public void setRoutepicture(String routepicture) {
        this.routepicture = routepicture;
    }
    public double getTime() {
        return time;
    }
    public void setTime(double time) {
        this.time = time;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getForcrowds() {
        return forcrowds;
    }
    public void setForcrowds(int forcrowds) {
        this.forcrowds = forcrowds;
    }
    public int getForviewpreference() {
        return forviewpreference;
    }
    public void setForviewpreference(int forviewpreference) {
        this.forviewpreference = forviewpreference;
    }
    public int getForweatherindex() {
        return forweatherindex;
    }
    public void setForweatherindex(int forweatherindex) {
        this.forweatherindex = forweatherindex;
    }
    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
}
