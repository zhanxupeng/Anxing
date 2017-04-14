package com.example.navigationapp.Activity;


import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.Mycoin;
import com.example.navigationapp.model.Person;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.Date;

public class ToTalkActivity extends AppCompatActivity {
    private Button talk_return;
    private Button talk_submit;
    private String mysign;
    private EditText my_idea;
    private final static int SEND_SIGN=1;
    private final static int SHOW_UPDATE=2;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SEND_SIGN:
                    String response=(String)msg.obj;
                    Toast.makeText(ToTalkActivity.this,response,Toast.LENGTH_SHORT).show();
                    //成功后先获得金币，然后才页面跳转
                    sendCoinRequest();
                    break;
                case SHOW_UPDATE:
                    String myresponse=(String)msg.obj;
                    Toast.makeText(ToTalkActivity.this,myresponse,Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(ToTalkActivity.this,BlogListActivity.class);
                    startActivity(intent);
                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.to_talk);
        my_idea=(EditText)findViewById(R.id.my_ideas);
        talk_submit=(Button)findViewById(R.id.talk_submit);
        talk_return=(Button)findViewById(R.id.talk_return);
        talk_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        talk_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发表动态
                mysign=my_idea.getText().toString().trim();
                if(mysign.equals("")){
                    Toast.makeText(ToTalkActivity.this,"一定要输入点东西的哦",Toast.LENGTH_SHORT).show();
                }else{
                    //发送请求
                    //Toast.makeText(ToTalkActivity.this,"正在发表，请等待...",Toast.LENGTH_SHORT).show();
                    sendRequest(mysign);
                }
            }
        });
    }
    private void sendRequest(final String sign){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url="http://"+ MyIp.ip+":8080/CarSafe/InsertBlogServlet";
                HttpURLConnection connection=null;
                try{
                    URL posturl=new URL(url);
                    connection=(HttpURLConnection)posturl.openConnection();
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    connection.setRequestMethod("POST");
                    connection.setUseCaches(false);
                    connection.setInstanceFollowRedirects(true);
                    connection.connect();
                    DataOutputStream out=new DataOutputStream(connection.getOutputStream());
                    String content="car_id="+ Person.car_id;
                    String content1="&username="+URLEncoder.encode(Person.usename,"UTF-8");
                    String content2="&mysign="+URLEncoder.encode(sign,"UTF-8");
                    Log.d("content", Person.usename);
                    out.writeBytes(content);
                    out.writeBytes(content1);
                    out.writeBytes(content2);
                    out.flush();
                    out.close();
                    BufferedReader reader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuffer response=new StringBuffer();
                    while((line=reader.readLine())!=null){
                        response.append(line);
                    }
                    reader.close();
                    Message message=new Message();
                    message.what=SEND_SIGN;
                    message.obj=response.toString();
                    handler.sendMessage(message);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(connection!=null){
                        connection.disconnect();
                    }
                }
            }
        }).start();

    }
    private int calculate(){
        int s=0;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        s=100*calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
        return s;
    }
    private void sendCoinRequest(){
        Mycoin.member=calculate();
        Mycoin.coin=Mycoin.coin+20;
        String str="http://"+ MyIp.ip+":8080/CarSafe/InsertQianServlet?car_id="+ Mycoin.car_id+"&coin="+Mycoin.coin+"&member="+Mycoin.member;
        HttpUtil.sendHttpRequest(str, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_UPDATE;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
