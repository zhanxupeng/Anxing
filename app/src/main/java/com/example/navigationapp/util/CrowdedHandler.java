package com.example.navigationapp.util;

import com.example.navigationapp.model.Mycrowded;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 占大帅 on 2017/3/4.
 */

public class CrowdedHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder value;
    private Mycrowded mycrowded;
    private List<Mycrowded> list=new ArrayList<Mycrowded>();
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value.append(ch,start,length);
        if(!"".equals(value.toString().trim())) {
            if ("id".equals(nodeName)) {
                mycrowded.setId(Integer.parseInt(value.toString().trim()));
            } else if ("myflag".equals(nodeName)) {
                // Log.d("car_id",value.toString().trim());
                mycrowded.setMyflag(Integer.parseInt(value.toString().trim()));
            } else if ("nextflag".equals(nodeName)) {
                // Log.d("xx",value.toString().trim());
                mycrowded.setNextflag(Integer.parseInt(value.toString().trim()));
            } else if ("speed".equals(nodeName)) {
                //  Log.d("yy",value.toString().trim());
                mycrowded.setSpeed(Double.parseDouble(value.toString().trim()));
            }
        }
        value.setLength(0);
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("crowded".equals(localName)){
            list.add(mycrowded);
            mycrowded=new Mycrowded();
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
        mycrowded=new Mycrowded();
    }

    public List<Mycrowded> getList() {
        return list;
    }

    public void setList(List<Mycrowded> list) {
        this.list = list;
    }
}
