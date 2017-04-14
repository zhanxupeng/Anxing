package com.example.navigationapp.util;

import android.util.Log;

import com.example.navigationapp.model.ForAttractions;


import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 占大帅 on 2017/4/13.
 */

public class SingleRouteHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder value;
    private ForAttractions forAttractions;
    private List<ForAttractions> list=new ArrayList<>();
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value.append(ch,start,length);
        if(!"".equals(value.toString().trim())) {
            String mystr=value.toString().trim();
            if ("id".equals(nodeName)) {
                Log.d("idddddddddddddddddddddd",mystr);
                forAttractions.setId(Integer.parseInt(mystr));
            } else if ("name".equals(nodeName)) {
                // Log.d("car_id",value.toString().trim());
                forAttractions.setName(mystr);
            } else if ("detail".equals(nodeName)) {
                // Log.d("xx",value.toString().trim());
                forAttractions.setDetail(mystr);
            } else if ("local".equals(nodeName)) {
                //  Log.d("yy",value.toString().trim());
                forAttractions.setLocal(mystr);
            }else if("exterior".equals(nodeName)){
                forAttractions.setExterior(mystr);
            }else if("routepicture".equals(nodeName)){
                forAttractions.setRoutepicture(mystr);
            }else if("time".equals(nodeName)){
                forAttractions.setTime(Double.parseDouble(mystr));
            }else if("price".equals(nodeName)){
                forAttractions.setPrice(Double.parseDouble(mystr));
            }else if("forcrowds".equals(nodeName)){
                forAttractions.setForcrowds(Integer.parseInt(mystr));
            }else if("forviewpreference".equals(nodeName)){
                forAttractions.setForviewpreference(Integer.parseInt(mystr));
            }else if("forweatherindex".equals(nodeName)){
                forAttractions.setForweatherindex(Integer.parseInt(mystr));
            }else if("distance".equals(nodeName)){
                forAttractions.setDistance(Double.parseDouble(mystr));
            }
        }
        value.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("route".equals(localName)){
            list.add(forAttractions);
            forAttractions=new ForAttractions();
        }
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName=localName;
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
        value=new StringBuilder();
        forAttractions=new ForAttractions();
    }

    public List<ForAttractions> getList() {
        return list;
    }

    public void setList(List<ForAttractions> list) {
        this.list = list;
    }
}
