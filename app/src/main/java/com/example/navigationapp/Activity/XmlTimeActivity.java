package com.example.navigationapp.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Nowcar;
import com.example.navigationapp.model.Timecar;
import com.example.navigationapp.util.ContentHandler;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;
import com.example.navigationapp.util.NowcarHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class XmlTimeActivity extends AppCompatActivity {
    private static final int SHOW_PESPONSE=0;
    private static final int SHOW_NOWCAR=1;

    private Button start_times;
    private Button start_car;
    private EditText start_text;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_PESPONSE:
                    String response=(String) msg.obj;
                    parseXMLWithSAX(response);
                    break;
                case SHOW_NOWCAR:
                    String response1=(String)msg.obj;
                    parseXMLNowCar(response1);
                    break;
            }
        }
    };
    private void parseXMLNowCar(String xmlData){
        List<Nowcar> cars=new ArrayList<Nowcar>();
        try{
            SAXParserFactory factory1=SAXParserFactory.newInstance();
            XMLReader xmlReader1=factory1.newSAXParser().getXMLReader();
            NowcarHandler handler1=new NowcarHandler();
            xmlReader1.setContentHandler(handler1);
            xmlReader1.parse(new InputSource(new StringReader(xmlData)));
            cars=handler1.getTimes();
            Toast.makeText(XmlTimeActivity.this, "一共有"+cars.size()+"条数据！", Toast.LENGTH_SHORT).show();
            StringBuilder str=new StringBuilder();
            for(Nowcar car:cars){
                str.append(car.getCar_id()+","+car.getXx()+","+car.getYy()+"\n");
            }
            start_text.setText(str.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    private void parseXMLWithSAX(String xmlData){
        List<Timecar> times=new ArrayList<Timecar>();
            try {
                SAXParserFactory factory=SAXParserFactory.newInstance();
                XMLReader xmlReader=factory.newSAXParser().getXMLReader();
                ContentHandler handler=new ContentHandler();
                xmlReader.setContentHandler(handler);
                xmlReader.parse(new InputSource(new StringReader(xmlData)));
                times=handler.getTimes();
                Toast.makeText(XmlTimeActivity.this,"一共有"+times.size()+"条数据！", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.xml_times);
        start_times=(Button)findViewById(R.id.start_times);
        start_times.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithHttpURLConnection(2017);
            }
        });
        start_text=(EditText)findViewById(R.id.start_text);
        start_car=(Button)findViewById(R.id.start_car);
        start_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestNowcar();
            }
        });
    }
    private void sendRequestNowcar(){
        HttpUtil.sendHttpRequest("http://192.168.1.103:8080/CarSafe/NowcarServlet?car_id=2017&&flag=1&&xx=10&&yy=10&&speed=10&&direction=10", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_NOWCAR;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void sendRequestWithHttpURLConnection(final int car_id){
        HttpUtil.sendHttpRequest("http://192.168.1.103:8080/CarSafe/TimecarServlet?car_id=2017", new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_PESPONSE;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }
}
