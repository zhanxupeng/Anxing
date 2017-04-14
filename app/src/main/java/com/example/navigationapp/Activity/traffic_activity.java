package com.example.navigationapp.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Forecast;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.Mycoin;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

public class traffic_activity extends AppCompatActivity {
    private final static int SHOW_SELECT=1;
    private Forecast forecast=new Forecast();
    private EditText traffic_route;
    private EditText traffic_time;
    private Button traffic_select;

    private ImageView averagea;
    private ImageView averageb;
    private ImageView averagec;
    private ImageView averaged;
    private ImageView averagee;
    private ImageView daoa;
    private ImageView daob;
    private ImageView daoc;
    private ImageView daod;
    private ImageView daoe;
    private ImageView yonga;
    private ImageView yongb;
    private ImageView yongc;
    private ImageView yongd;
    private ImageView yonge;
    private ImageView gonga;
    private ImageView gongb;
    private ImageView gongc;
    private ImageView gongd;
    private ImageView gonge;

    private TextView tra_ave;
    private TextView tra_dao;
    private TextView tra_yong;
    private TextView tra_gong;
    private ImageView traffic_return;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_SELECT:
                    String response=(String) msg.obj;
                    //Toast.makeText(traffic_activity.this,response,Toast.LENGTH_SHORT).show();
                    //解析数据，并且放入各种类型中
                    parseXMLWithPull(response);
                    //然后改变各种星星的状态
                    changeState();
            }
        }
    };
    private void changeState(){
        //通过 forecast 来改变各种星星的状态
        int ave=(forecast.getRrank()+forecast.getTrank()+forecast.getHrank())/3;

        tra_ave.setText("Average Rating "+ave+".0");
        tra_dao.setText(String.format("%d",forecast.getRrank())+".0");
        tra_yong.setText(String.format("%d",forecast.getTrank())+".0");
        tra_gong.setText(String.format("%d",forecast.getHrank())+".0");
        if(ave>0){
            averagea.setImageResource(R.mipmap.starone);
        }
        if(ave>1){
            averageb.setImageResource(R.mipmap.starone);
        }
        if(ave>2){
            averagec.setImageResource(R.mipmap.starone);
        }
        if(ave>3){
            averaged.setImageResource(R.mipmap.starone);
        }
        if(ave>4){
            averagee.setImageResource(R.mipmap.starone);
        }
        int dao=forecast.getRrank();
        if(dao>0){
            daoa.setImageResource(R.mipmap.starfour);
        }
        if(dao>1){
            daob.setImageResource(R.mipmap.starfour);
        }
        if(dao>2){
            daoc.setImageResource(R.mipmap.starfour);
        }
        if(dao>3){
            daod.setImageResource(R.mipmap.starfour);
        }
        if(dao>4){
            daoe.setImageResource(R.mipmap.starfour);
        }
        int yong =forecast.getTrank();
        if(yong>0){
            yonga.setImageResource(R.mipmap.starfour);
        }
        if(yong>1){
            yongb.setImageResource(R.mipmap.starfour);
        }
        if(yong>2){
            yongc.setImageResource(R.mipmap.starfour);
        }
        if(yong>3){
            yongd.setImageResource(R.mipmap.starfour);
        }
        if(yong>4){
            yonge.setImageResource(R.mipmap.starfour);
        }
        int gong=forecast.getHrank();
        if(gong>0){
            gonga.setImageResource(R.mipmap.starfour);
        }
        if(gong>1){
            gongb.setImageResource(R.mipmap.starfour);
        }
        if(gong>2){
            gongc.setImageResource(R.mipmap.starfour);
        }
        if(gong>3){
            gongd.setImageResource(R.mipmap.starfour);
        }
        if(gong>4){
            gonge.setImageResource(R.mipmap.starfour);
        }

    }
    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            forecast.setId(Integer.parseInt(xmlPullParser.nextText()));
                        }else if("route".equals(nodeName)){
                            forecast.setRoute(xmlPullParser.nextText());
                        }else if("time".equals(nodeName)){
                            forecast.setTime(Integer.parseInt(xmlPullParser.nextText()));
                        }else if("rrank".equals(nodeName)){
                            forecast.setRrank(Integer.parseInt(xmlPullParser.nextText()));
                        }else if("trank".equals(nodeName)){
                            forecast.setTrank(Integer.parseInt(xmlPullParser.nextText()));
                        }else if("hrank".equals(nodeName)){
                            forecast.setHrank(Integer.parseInt(xmlPullParser.nextText()));
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.traffic_activity);
        traffic_route=(EditText)findViewById(R.id.traffic_route);
        traffic_time=(EditText)findViewById(R.id.traffic_time);
        traffic_select=(Button)findViewById(R.id.traffic_select);


        tra_ave=(TextView)findViewById(R.id.tra_ave);
        tra_dao=(TextView)findViewById(R.id.tra_dao);
        tra_yong=(TextView)findViewById(R.id.tra_yong);
        tra_gong=(TextView)findViewById(R.id.tra_gong);

        averagea=(ImageView)findViewById(R.id.averagea);
        averageb=(ImageView)findViewById(R.id.averageb);
        averagec=(ImageView)findViewById(R.id.averagec);
        averaged=(ImageView)findViewById(R.id.averaged);
        averagee=(ImageView)findViewById(R.id.averagee);
        daoa=(ImageView)findViewById(R.id.daoa);
        daob=(ImageView)findViewById(R.id.daob);
        daoc=(ImageView)findViewById(R.id.daoc);
        daod=(ImageView)findViewById(R.id.daod);
        daoe=(ImageView)findViewById(R.id.daoe);
        yonga=(ImageView)findViewById(R.id.yonga);
        yongb=(ImageView)findViewById(R.id.yongb);
        yongc=(ImageView)findViewById(R.id.yongc);
        yongd=(ImageView)findViewById(R.id.yongd);
        yonge=(ImageView)findViewById(R.id.yonge);
        gonga=(ImageView)findViewById(R.id.gonga);
        gongb=(ImageView)findViewById(R.id.gongb);
        gongc=(ImageView)findViewById(R.id.gongc);
        gongd=(ImageView)findViewById(R.id.gongd);
        gonge=(ImageView)findViewById(R.id.gonge);
        traffic_return=(ImageView)findViewById(R.id.traffic_return);
        traffic_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //连接网络进行查询
                String route=traffic_route.getText().toString().trim();
                String time=traffic_time.getText().toString().trim();
                if(!("".equals(route)||"".equals(time))){
                    sendRequest(route,time);
                }else{
                    Toast.makeText(traffic_activity.this,"道路或者时间不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
        traffic_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void sendRequest(final String myroute,final String mytime){
        String str="http://"+ MyIp.ip+":8080/CarSafe/ForecastServlet?route="+myroute+"&time="+mytime;
        HttpUtil.sendHttpRequest(str, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_SELECT;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
