package com.example.navigationapp.util;

import com.example.navigationapp.model.RouteResult;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 占大帅 on 2017/4/14.
 */

public class RouteResultHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder value;
    private RouteResult routeResult;
    private List<RouteResult> list=new ArrayList<>();

    public List<RouteResult> getList() {
        return list;
    }

    public void setList(List<RouteResult> list) {
        this.list = list;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("orderss".equals(localName)){
            list.add(routeResult);
            routeResult=new RouteResult();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value.append(ch,start,length);
        if(!"".equals(value.toString().trim())) {
            String str=value.toString().trim();
            if ("mypicture".equals(nodeName)) {
                routeResult.setMypicture(str);
            }else if("route".equals(nodeName)){
                routeResult.setRoute(str);
            }else if("routeid".equals(nodeName)){
                routeResult.setRouteid(str);
            }else if("zhishu".equals(nodeName)){
                routeResult.setZhishu(Integer.parseInt(str));
            }else if("time".equals(nodeName)){
                routeResult.setTime(Double.parseDouble(str));
            }else if("price".equals(nodeName)){
                routeResult.setPrice(Double.parseDouble(str));
            }
        }
        value.setLength(0);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        nodeName=localName;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }

    @Override
    public void startDocument() throws SAXException {
        value=new StringBuilder();
        routeResult=new RouteResult();
    }
}
