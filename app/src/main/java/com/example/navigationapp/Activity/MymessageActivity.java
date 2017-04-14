package com.example.navigationapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.Person;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import org.w3c.dom.Text;

public class MymessageActivity extends AppCompatActivity {
    private ImageView my_share;
    private Button a_register;
    private Button a_login;
    private ImageView b_che;
    private ImageView b_shang;
    private ImageView b_wo;
    private TextView d_usename;
    private TextView d_car_id;
    private LinearLayout d_before_layout;
    private LinearLayout d_after_layout;
    private static final int SHOW_RESPONSE=1;
    private TextView mycoin_shop;
    private TextView d_cencel;
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_RESPONSE:
                    String response=(String)msg.obj;
                    if(response!=null){
                        Person.usename=response;
                        Person.flag=true;
                        setmessage();
                    }else {
                        Toast.makeText(MymessageActivity.this,"用户名或密码错误，请重新登录！", Toast.LENGTH_SHORT).show();
                    }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.mymessage_activity);
        d_usename=(TextView) findViewById(R.id.d_usename);
        d_car_id=(TextView) findViewById(R.id.d_car_id);
        a_register=(Button)findViewById(R.id.a_register);
        d_before_layout=(LinearLayout) findViewById(R.id.d_before_layout);
        d_after_layout=(LinearLayout) findViewById(R.id.d_after_layout);
        my_share=(ImageView)findViewById(R.id.my_share);//分享键

        //积分商城
        mycoin_shop=(TextView)findViewById(R.id.mycoin_shop);
        mycoin_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MymessageActivity.this,CoinShopActivity.class);
                startActivity(intent);
            }
        });
        //检测一下当前是否登录
        setmessage();
        d_cencel=(TextView)findViewById(R.id.d_cancel);
        d_cencel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person.flag=false;
                setmessage();
            }
        });
        a_login=(Button)findViewById(R.id.a_login);
        a_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog();
            }
        });
        a_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MymessageActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
        b_che=(ImageView)findViewById(R.id.a_car);
        b_che.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MymessageActivity.this,FirstPage.class);
                startActivity(intent);
                finish();
            }
        });
        b_shang=(ImageView)findViewById(R.id.a_shop);
        b_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MymessageActivity.this,KipActivity.class);
                startActivity(intent);
                finish();
            }
        });
        b_wo=(ImageView)findViewById(R.id.a_mymessage);
        b_wo.setImageResource(R.mipmap.wo_two);
    }
    public void showAddDialog(){
        LayoutInflater factory=LayoutInflater.from(MymessageActivity.this);
        final View view=factory.inflate(R.layout.login_title,null);
        final EditText car_id=(EditText)view.findViewById(R.id.c_usename);
        final EditText password=(EditText)view.findViewById(R.id.c_password);
        AlertDialog.Builder ad1=new AlertDialog.Builder(MymessageActivity.this);
        ad1.setTitle("用户登录：");
        ad1.setView(view);
        ad1.setPositiveButton("登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                Person.car_id=Integer.parseInt(car_id.getText().toString());
                Person.password=password.getText().toString();
                sendrequest(Person.car_id,Person.password);
            }
        });
        ad1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

            }
        });
        ad1.show();
    }
    private void sendrequest(int car_id,String password){
        String url="http://"+ MyIp.ip+":8080/CarSafe/LoginsServlet?car_id="+car_id+"&password="+password;
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
    private void setmessage(){
        if(Person.flag){
            d_usename.setText(Person.usename);
            d_car_id.setText(String.format("%d",Person.car_id));
            d_before_layout.setVisibility(View.GONE);
            d_after_layout.setVisibility(View.VISIBLE);
        }else{
            d_before_layout.setVisibility(View.VISIBLE);
            d_after_layout.setVisibility(View.GONE);
        }
    }

}
