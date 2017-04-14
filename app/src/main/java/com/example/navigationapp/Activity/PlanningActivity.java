package com.example.navigationapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.RouteResult;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;
import com.example.navigationapp.util.RouteResultHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class PlanningActivity extends AppCompatActivity {
    private final static int SHOW_RESPONSE=1;
    private String myrenqun;
    private String mypianhao;
    private String myshijian;
    private List<RouteResult> list;
    private Handler handler=new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    //Toast.makeText(PlanningActivity.this,response,Toast.LENGTH_SHORT).show();
                    /*
                    解析数据
                     */
                    parseXML(response);
                    if(list!=null){
                        Toast.makeText(PlanningActivity.this,"共"+list.size(),Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    private void parseXML(String xmlData){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            RouteResultHandler handler=new RouteResultHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            list=handler.getList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planning_activity);
        Intent intent=getIntent();
        myrenqun=intent.getStringExtra("myrenqun");
        mypianhao=intent.getStringExtra("mypianhao");
        myshijian=intent.getStringExtra("myshijian");
        //Toast.makeText(PlanningActivity.this,myrenqun+"_"+mypianhao+"_"+myshijian,Toast.LENGTH_SHORT).show();
        sendCoinRequest(myrenqun,mypianhao,myshijian);
    }
    private void sendCoinRequest(final String myrenqun,final String mypianhao,final String myshijian){
        String str="http://"+ MyIp.ip+":8080/CarSafe/ByRouteSevlet?myrenqun="+myrenqun+"&mypianhao="+mypianhao+"&myshijian="+myshijian;
        HttpUtil.sendHttpRequest(str, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_RESPONSE;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
