package com.example.navigationapp.util;

import android.util.Log;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Blog;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 占大帅 on 2017/3/9.
 */

public class BlogHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder value;
    private Blog blog;
    private List<Blog> list=new ArrayList<Blog>();

    public List<Blog> getList() {
        return list;
    }

    public void setList(List<Blog> list) {
        this.list = list;
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("blog".equals(localName)){
            list.add(0,blog);
            blog=new Blog();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        value.append(ch,start,length);
        if(!"".equals(value.toString().trim())) {
            if ("id".equals(nodeName)) {
                // Log.d("id",value.toString().trim());
                //cars.setId(Integer.parseInt(value.toString().trim()));
                blog.setId(Integer.parseInt(value.toString().trim()));
                //blog.setId(R.mipmap.first_head);
                Log.d("BlogHandler",value.toString().trim());
            }else if("car_id".equals(nodeName)){
                blog.setCar_id(Integer.parseInt(value.toString().trim()));
                Log.d("BlogHandler",value.toString().trim());
            }else if("username".equals(nodeName)){
                blog.setUsername(value.toString().trim());
                Log.d("BlogHandler",value.toString().trim());
            }else if("mysign".equals(nodeName)){
                blog.setMysign(value.toString().trim());
                Log.d("BlogHandler",value.toString().trim());
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
        blog=new Blog();
    }
}
