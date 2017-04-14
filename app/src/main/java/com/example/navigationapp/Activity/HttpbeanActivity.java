package com.example.navigationapp.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.example.navigationapp.R;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

public class HttpbeanActivity extends AppCompatActivity {
    private static final int SHOW_RESPONSE=0;

    private Button http_button;
    private EditText http_text;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response =(String)msg.obj;
                    http_text.setText(response);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.http_bean);
        http_text=(EditText)findViewById(R.id.http_text);
        http_button=(Button)findViewById(R.id.http_button);
        http_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendHttp();
            }
        });
    }
    private void sendHttp(){
        HttpUtil.sendHttpRequest("https://www.baidu.com", new HttpCallbackListener() {
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
