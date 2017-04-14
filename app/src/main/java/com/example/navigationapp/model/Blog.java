package com.example.navigationapp.model;

/**
 * Created by 占大帅 on 2017/3/9.
 */

public class Blog {
    private int id;
    private int car_id;
    private String username;
    private String mysign;
    public Blog(){

    }
    public Blog(int id,int car_id,String username,String mysign){
        this.id=id;
        this.car_id=car_id;
        this.username=username;
        this.mysign=mysign;
    }

    public String getMysign() {
        return mysign;
    }

    public void setMysign(String mysign) {
        this.mysign = mysign;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCar_id() {
        return car_id;
    }

    public void setCar_id(int car_id) {
        this.car_id = car_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
