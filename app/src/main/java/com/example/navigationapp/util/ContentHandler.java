package com.example.navigationapp.util;


import android.util.Log;

import com.example.navigationapp.model.Timecar;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 占大帅 on 2017/1/16.
 */

public class ContentHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder value;
    private Timecar cars;
    private List<Timecar> times=new ArrayList<Timecar>();

    @Override
    public void startDocument() throws SAXException {
        value=new StringBuilder();
        cars=new Timecar();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName=localName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value.append(ch,start,length);
        if(!"".equals(value.toString().trim())) {
            if ("id".equals(nodeName)) {
               // Log.d("id",value.toString().trim());
                cars.setId(Integer.parseInt(value.toString().trim()));
            } else if ("car_id".equals(nodeName)) {
               // Log.d("car_id",value.toString().trim());
                Log.d("timecar",value.toString().trim());
                cars.setCar_id(Integer.parseInt(value.toString().trim()));
            } else if ("xx".equals(nodeName)) {
               // Log.d("xx",value.toString().trim());
                cars.setXx(Double.parseDouble(value.toString().trim()));
            } else if ("yy".equals(nodeName)) {
              //  Log.d("yy",value.toString().trim());
                cars.setYy(Double.parseDouble(value.toString().trim()));
            } else if ("speed".equals(nodeName)) {
                Log.d("speed",value.toString().trim());
              //  Log.d("speed",value.toString().trim());
                cars.setSpeed(Double.parseDouble(value.toString().trim()));
            } else if ("time".equals(nodeName)) {
               // Log.d("time",value.toString().trim());
                cars.setTime(Integer.parseInt(value.toString().trim()));
            }
        }
        value.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("orderss".equals(localName)){
            times.add(cars);
            cars=new Timecar();
        }
    }

    @Override
    public void endDocument() throws SAXException {
    }

    public List<Timecar> getTimes() {
        return times;
    }

    public void setTimes(List<Timecar> times) {
        this.times = times;
    }
}
