package com.example.navigationapp.Activity;

import android.content.Intent;
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
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.Person;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

public class RegisterActivity extends AppCompatActivity {

    private EditText e_usename;
    private EditText e_car_id;
    private EditText e_password;
    private Button e_button;
    public static final int SHOW_RESPONSE=1;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    //处理
                    boolean result=Boolean.parseBoolean(response);
                    if(result){
                        Person.flag=true;
                        Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(RegisterActivity.this,MymessageActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(RegisterActivity.this,"用户名重复，请重新注册",Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_activity);
        e_usename=(EditText)findViewById(R.id.e_usename);
        e_car_id=(EditText)findViewById(R.id.e_car_id);
        e_password=(EditText)findViewById(R.id.e_password);
        e_button=(Button) findViewById(R.id.e_button);
        e_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usename=e_usename.getText().toString();
                String car_id=e_car_id.getText().toString();
                String password=e_password.getText().toString();
                Person.car_id=Integer.parseInt(car_id);
                Person.usename=usename;
                Person.password=password;
                sendrequest(usename,car_id,password);
            }
        });
    }
    private void sendrequest(String usename,String car_id,String password){
        String url="http://"+ MyIp.ip+":8080/CarSafe/InsertPersonServlet?car_id="+car_id+"&usename="+usename+"&password="+password;
        HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {
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
