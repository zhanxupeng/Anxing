package com.example.navigationapp.model;

import android.util.Log;

import java.util.Iterator;
import java.util.List;

/**
 * Created by 占大帅 on 2017/1/20.
 */

public class Pileup {
    public boolean checkSafe(int car_id,List<Nowcar> cars){
        boolean result=false;
        Nowcar car1=new Nowcar();
        for(Nowcar car:cars){
            if(car.getCar_id()==car_id){
                System.out.println(car.getCar_id());
                car1.setCar_id(car.getCar_id());
                car1.setXx(car.getXx());
                car1.setYy(car.getYy());
                car1.setFlag(car.getFlag());
                car1.setSpeed(car.getSpeed());
                car1.setDirection(car.getDirection());
            }
        }
        System.out.println(car1.getCar_id()+","+car1.getXx());
        if(car1.getFlag()==1){
            for(Nowcar car:cars){
                if(car.getCar_id()!=car1.getCar_id()&&(car.getFlag()==1)){
                    if(((car1.getDirection()==1)&&(car.getDirection()==1)&&
                            ((car.getXx()-car1.getXx())>=0)&&((car.getXx()-car1.getXx())<=200))
                            ||
                            ((car1.getDirection()==2)&&(car.getDirection()==2)&&
                                    ((car.getXx()-car1.getXx())<=0)&&((car.getXx()-car1.getXx())>=-200)))
                    {
                        //处理的逻辑，双向
                        double x=Math.abs(car1.getXx()-car.getXx())-2;
                        double va=car1.getSpeed();
                        double vb=car.getSpeed();
                        result=gudge(x,va,vb);
                        if(result){
                            Log.d("car_id is...........",car1.getCar_id()+","+car1.getXx()+"and"+car.getCar_id());
                            break;
                        }
                        System.out.println("有车次为："+car.getCar_id());
                    }
                }
            }
        }
        if(car1.getFlag()==2||car1.getFlag()==3){
            for(Nowcar car:cars){
                if((car.getCar_id()!=car1.getCar_id())&&(car.getFlag()==car1.getFlag())&&(car.getDirection()==car1.getDirection())){
                    if(
                            (
                                    (car1.getYy()<=car.getYy())
                                            &&
                                            ((car1.getYy()+200)>=car.getYy())
                            )
                                    ||
                                    (
                                            (car1.getYy()>=car.getYy())
                                                    &&
                                                    ((car1.getYy()-200)<=car.getYy())
                                    )
                            ){
                        double x1=Math.abs(car1.getYy()-car.getYy())-2;
                        double va1=car1.getSpeed();
                        double vb1=car.getSpeed();
                        result=gudge(x1,va1,vb1);
                        if(result){
                            System.out.println("来自2或3路线！");
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }
    public boolean corner(int car_id,List<Nowcar> cars){
        boolean result=false;
        Nowcar car1=new Nowcar();
        for(Nowcar car:cars){
            if(car.getCar_id()==car_id){
                System.out.println(car.getCar_id());
                car1.setCar_id(car.getCar_id());
                car1.setXx(car.getXx());
                car1.setYy(car.getYy());
                car1.setFlag(car.getFlag());
                car1.setSpeed(car.getSpeed());
                car1.setDirection(car.getDirection());
            }
        }
        if((car1.getFlag()==1)&&(car1.getXx()>=880)&&(car1.getDirection()==1)){
            for(Nowcar car:cars){
                if(car.getCar_id()!=car_id){
                    if((car.getFlag()==3)&&(car.getYy()<=200)&&(car.getDirection()==2)){
                        result=true;
                        break;
                    }
                }
            }
        }
        if((car1.getFlag()==3)&&(car1.getYy()<=200)&&(car1.getDirection()==2)){
            for(Nowcar car:cars){
                if(car.getCar_id()!=car_id){
                    if((car.getFlag()==1)&&(car.getXx()>=880)&&(car.getDirection()==1)){
                        result=true;
                        break;
                    }
                }
            }
        }
        return result;
    }
    public int crosssafe(int car_id,List<Nowcar> cars){
        int result=0;
        Nowcar car1=new Nowcar();
        for(Nowcar car:cars){
            if(car.getCar_id()==car_id){
                System.out.println(car.getCar_id());
                car1.setCar_id(car.getCar_id());
                car1.setXx(car.getXx());
                car1.setYy(car.getYy());
                car1.setFlag(car.getFlag());
                car1.setSpeed(car.getSpeed());
                car1.setDirection(car.getDirection());
            }
        }
        if((car1.getFlag()==2)&&(car1.getDirection()==2)&&(car1.getYy()<=200)){
            for(Nowcar car:cars){
                if(car.getCar_id()!=car_id){
                    if((car.getFlag()==1)&&(car.getXx()>=300)&&(car.getXx()<=500)&&(car.getSpeed()>=10)&&(car.getDirection()==1)){
                        System.out.println("前方左转弯口有高速车辆驶来，请小心驾驶！");
                        if(result==2){
                            result=3;
                        }else{
                            result=1;
                        }
                    }
                    if((car.getFlag()==1)&&(car.getXx()>=400)&&(car.getXx()<=600)&&(car.getSpeed()>=10)&&(car.getDirection()==2)){
                        System.out.println("前方右转弯口有高速车辆驶来，请小心驾驶！");
                        if(result==1){
                            result=3;
                        }else{
                            result=2;
                        }
                    }
                }
            }
        }
        return result;
    }
    public boolean gudge(double x,double va,double vb){
        boolean result=false;
        double gen=Math.sqrt(Math.pow((2-vb/10),2)-0.2*(vb*vb/20-2*vb-x));
        double va1=(vb/10-2+gen)/0.1;
        System.out.println(va1);
        if(va1<=va){
            result=true;
        }
        return result;
    }
    public int PianYi( Nowcar old, Nowcar now ){
        int flag=0;
        double x1 = old.getXx();double y1 = old.getYy();
        double x2 = now.getXx();double y2 = now.getYy();
        if(now.getSpeed()>=10){
            if( old.getFlag()==now.getFlag() )	//未改变行驶道路
                flag = old.getFlag();
            else return 0;						//若行驶道路改变，本次测量结束

            if( old.getDirection()!= now.getDirection() )	//行驶方向改变
                return -2;									//本次测量结束(调头)

            if( flag==1 ){											//1号道路并且朝同一个方向行驶
                if( Math.abs( (y2-y1)/(x2-x1) )  >  1.732/3 ){		//偏离超过30°
                    return -1;
                }
                else return 1;
            }

            else if( flag==2 || flag==3 ){							//2号、3号道路并且朝同一个方向行驶
                if( Math.abs( (x2-x1)/(y2-y1) )  >  1.732/3 ){		//偏离超过30°
                    return -1;
                }
                else return 1;
            }
            return 1;
        }
        return 0;
    }
    //找出特定id的车辆；
    public Nowcar selectcar(List<Nowcar> cars,int car_id){
        Nowcar car=null;
        for(Nowcar car1:cars){
            if(car1.getCar_id()==car_id){
                car=car1;
            }
        }
        return car;
    }
    public boolean SpeedWan(List<Nowcar> cars,int car_id){
        boolean result=false;
        Nowcar car=selectcar(cars,car_id);
        if(car.getSpeed()>=50){
            if(car.getFlag()==1&&car.getDirection()==1){
                if((car.getXx()>=300&&car.getXx()<=400)||(car.getXx()>=880)){
                    result=true;
                }
            }
            if(car.getFlag()==1&&car.getDirection()==2){
                if(car.getXx()>=500&&car.getXx()<=600){
                    result=true;
                }
            }
            if(car.getDirection()==2){
                if(car.getYy()>=100||car.getYy()<=200){
                    result=true;
                }
            }
        }
        return result;
    }
    public int Danger( List< Nowcar > _data, int car_id, double s ){
        double x=0,y=0;
        int flag = 0;
        double dirt=0;

        Iterator< Nowcar > iter2 = _data.iterator();
        while( iter2.hasNext() ){			//找出我的信息
            Nowcar info = iter2.next();
            if(info.getCar_id()==car_id){
                x = info.getXx();
                y = info.getYy();
                flag = info.getFlag();
                dirt = info.getDirection();
                break;
            }
        }
        //打印我的车辆信息
        System.out.println(x+" "+y+" "+flag+" "+dirt);

        Iterator< Nowcar > iter = _data.iterator();
        while( iter.hasNext() ){			//对ArrayList进行遍历
            Nowcar info = iter.next();
            if(info.getCar_id()==car_id)
                continue;
            else{
                if( flag == info.getFlag() ){	//两辆车在同一条道路行驶
                    if(flag == 1){				//1号横向道路
                        if( Math.abs(x - info.getXx())<s && dirt==info.getDirection()){
                            if( (dirt==1 && info.getXx()-x<s && info.getXx()>x ) ||
                                    (dirt==2 && x-info.getXx()<s && info.getXx()<x )){
                                if(info.getSpeed()>40)
                                    return 11;
                                else
                                    return 1;
                            }
                            else{}
                        }
                        else if( Math.abs( x - info.getXx() )<s && dirt!=info.getDirection() ){
                            if( (dirt==1 && info.getDirection()==2 && info.getXx()-x<s && info.getXx()>x )
                                    ||(dirt==2 && info.getDirection()==1 && x-info.getXx()<s ) && info.getXx()<x ){
                                if(info.getSpeed()>40)
                                    return 22;
                                else
                                    return 2;
                            }
                            else{}
                        }
                    }
                    else{						//2、3号竖向道路
                        if( Math.abs(y - info.getYy())<s && dirt==info.getDirection() ){
                            if( (dirt==1 && y-info.getYy()<s && y>info.getYy())
                                    || (dirt==2 && info.getYy()-y<s) && y<info.getYy() ){
                                if(info.getSpeed()>40)
                                    return 11;
                                else
                                    return 1;
                            }
                            else{}
                        }
                        else if( Math.abs( y - info.getYy() )<s && dirt!=info.getDirection() ){
                            if( (dirt==1 && info.getDirection()==2 && y-info.getYy()<s && y>info.getYy())
                                    ||(dirt==2 && info.getDirection()==1 && info.getYy()-y<s && y<info.getYy()) ){
                                if(info.getSpeed()>40)
                                    return 22;
                                else
                                    return 2;
                            }
                            else{}
                        }
                    }
                }
                else{							//两辆车在不同道路行驶，可能在路口交汇
                    if(Math.abs(x-info.getXx())<s || Math.abs(y-info.getYy())<30 ){
                        if(info.getSpeed()>40)
                            return 33;
                        else
                            return 3;
                    }
                }
            }
        }
        return 0;
    }
}
