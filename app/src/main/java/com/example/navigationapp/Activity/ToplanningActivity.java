package com.example.navigationapp.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.navigationapp.R;

public class ToplanningActivity extends AppCompatActivity {
    private RadioGroup yy_renqun;
    private RadioButton yy_qingshaonian;
    private RadioButton yy_qingnian;
    private RadioButton yy_jiating;
    private RadioButton yy_laoren;
    private RadioButton yy_pengyoutuan;
    private RadioButton yy_qinglv;
    private RadioGroup yy_pianhao;
    private RadioButton yy_yule;
    private RadioButton yy_anjing;
    private RadioButton yy_ziran;
    private RadioButton yy_lishi;
    private RadioButton yy_renao;
    private RadioButton yy_ciji;
    private RadioButton yy_yinbi;
    private RadioGroup yy_shijian;
    private RadioButton yy_yitian;
    private RadioButton yy_liangtian;
    private RadioButton yy_santian;
    private RadioButton yy_yizhou;
    private String myrenqun;
    private String mypianhao;
    private String myshiian;
    private Button yy_chaxun;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.toplanning_activity);
        yy_renqun=(RadioGroup)findViewById(R.id.yy_renqun);
        yy_qingshaonian=(RadioButton)findViewById(R.id.yy_qingshaonian);
        yy_jiating=(RadioButton)findViewById(R.id.yy_jiating);
        yy_laoren=(RadioButton)findViewById(R.id.yy_laoren);
        yy_pengyoutuan=(RadioButton)findViewById(R.id.yy_pengyoutuan);
        yy_qinglv=(RadioButton)findViewById(R.id.yy_qinglv);
        yy_pianhao=(RadioGroup)findViewById(R.id.yy_pianhao);
        yy_yule=(RadioButton)findViewById(R.id.yy_yule);
        yy_anjing=(RadioButton)findViewById(R.id.yy_anjing);
        yy_ziran=(RadioButton)findViewById(R.id.yy_ziran);
        yy_lishi=(RadioButton)findViewById(R.id.yy_lishi);
        yy_renao=(RadioButton)findViewById(R.id.yy_renao);
        yy_ciji=(RadioButton)findViewById(R.id.yy_ciji);
        yy_yinbi=(RadioButton)findViewById(R.id.yy_yinbi);
        yy_shijian=(RadioGroup)findViewById(R.id.yy_shijian);
        yy_yitian=(RadioButton)findViewById(R.id.yy_yitian);
        yy_liangtian=(RadioButton)findViewById(R.id.yy_liangtian);
        yy_santian=(RadioButton)findViewById(R.id.yy_santian);
        yy_yizhou=(RadioButton)findViewById(R.id.yy_yizhou);
        yy_chaxun=(Button)findViewById(R.id.yy_chaxun);
        yy_renqun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yy_qingshaonian:
                         myrenqun="teens";
                         break;
                    case R.id.yy_qingnian:
                        myrenqun="white";
                        break;
                    case R.id.yy_jiating:
                        myrenqun="familys";
                        break;
                    case R.id.yy_laoren:
                        myrenqun="oldman";
                        break;
                    case R.id.yy_pengyoutuan:
                        myrenqun="friend";
                        break;
                    case R.id.yy_qinglv:
                        myrenqun="couple";
                        break;

                }
            }
        });
        yy_pianhao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yy_yule:
                        mypianhao="enjoy";
                        break;
                    case R.id.yy_anjing:
                        mypianhao="quiet";
                        break;
                    case R.id.yy_ziran:
                        mypianhao="natural";
                        break;
                    case R.id.yy_lishi:
                        mypianhao="lishi";
                        break;
                    case R.id.yy_renao:
                        mypianhao="lively";
                        break;
                    case R.id.yy_ciji:
                        mypianhao="stimulate";
                        break;
                    case R.id.yy_yinbi:
                        mypianhao="hidden";
                        break;
                }
            }
        });
        yy_shijian.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.yy_yitian:
                        myshiian="8";
                        break;
                    case R.id.yy_liangtian:
                        myshiian="16";
                        break;
                    case R.id.yy_shijian:
                        myshiian="24";
                        break;
                    case R.id.yy_yizhou:
                        myshiian="40";
                        break;
                }
            }
        });
        yy_chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(ToplanningActivity.this,myrenqun+","+mypianhao+","+myshiian,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(ToplanningActivity.this,PlanningActivity.class);
                intent.putExtra("myrenqun",myrenqun);
                intent.putExtra("mypianhao",mypianhao);
                intent.putExtra("myshijian",myshiian);
                startActivity(intent);
            }
        });
    }
}
