package com.example.navigationapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.example.navigationapp.R;

public class Before_first extends AppCompatActivity {

    private final static int SHOW_RESPONSE=1;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    Intent intent=new Intent(Before_first.this,FirstPage.class);
                    startActivity(intent);
                    finish();
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.before_first);
        sendRequest();
    }
    private void sendRequest(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(300);
                }catch (Exception e){
                    e.printStackTrace();
                }
                Message message=new Message();
                message.what=SHOW_RESPONSE;
                handler.sendMessage(message);
            }
        }).start();
    }
}
