package com.example.navigationapp.Activity;

import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ResourceCursorAdapter;
import android.widget.Toast;

import com.example.navigationapp.R;

public class AttractionsActivity extends AppCompatActivity {
    private RadioGroup renqun;
    private RadioGroup pianhao;
    private RadioGroup tianqi;
    private RadioButton qingshaonian;
    private RadioButton qingnian;
    private RadioButton jiating;
    private RadioButton laoren;
    private RadioButton pengyoutuan;
    private RadioButton qinglv;
    private RadioButton yule;
    private RadioButton anjing;
    private RadioButton ziran;
    private RadioButton lishi;
    private RadioButton renao;
    private RadioButton ciji;
    private RadioButton yinbi;
    private RadioButton qing;
    private RadioButton duoyun;
    private RadioButton yutian;
    private Button singleselect;
    private EditText didian;
    private String mymudi;
    private String myrenqun;
    private String mytianqi;
    private String mypianhao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.attractions_activity);
        renqun=(RadioGroup)findViewById(R.id.renqun);
        pianhao=(RadioGroup)findViewById(R.id.pianhao);
        tianqi=(RadioGroup)findViewById(R.id.tianqi);
        qingshaonian=(RadioButton)findViewById(R.id.qingshaonian);
        qingnian=(RadioButton)findViewById(R.id.qingnian);
        jiating=(RadioButton)findViewById(R.id.jiating);
        laoren=(RadioButton)findViewById(R.id.laoren);
        pengyoutuan=(RadioButton)findViewById(R.id.pengyoutuan);
        qinglv=(RadioButton)findViewById(R.id.qinglv);
        yule=(RadioButton)findViewById(R.id.yule);
        anjing=(RadioButton)findViewById(R.id.anjing);
        ziran=(RadioButton)findViewById(R.id.ziran);
        lishi=(RadioButton)findViewById(R.id.lishi);
        renao=(RadioButton)findViewById(R.id.renao);
        ciji=(RadioButton)findViewById(R.id.ciji);
        yinbi=(RadioButton)findViewById(R.id.yinbi);
        qing=(RadioButton)findViewById(R.id.qing);
        duoyun=(RadioButton)findViewById(R.id.duoyun);
        yutian=(RadioButton)findViewById(R.id.yutian);
        singleselect=(Button)findViewById(R.id.singleselect);
        didian=(EditText)findViewById(R.id.didian);
        tianqi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.qing:
                        mytianqi="sunny";
                        break;
                    case R.id.duoyun:
                        mytianqi="cloudy";
                        break;
                    case R.id.yutian:
                        mytianqi="rainy";
                        break;
                }
            }
        });
        pianhao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.yule:
                        mypianhao="enjoy";
                        break;
                    case R.id.anjing:
                        mypianhao="quiet";
                        break;
                    case R.id.ziran:
                        mypianhao="natural";
                        break;
                    case R.id.lishi:
                        mypianhao="history";
                        break;
                    case R.id.renao:
                        mypianhao="lively";
                        break;
                    case R.id.ciji:
                        mypianhao="stimulate";
                        break;
                    case R.id.yinbi:
                        mypianhao="hidden";
                        break;
                }
            }
        });
        renqun.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.qingshaonian:
                        myrenqun="teens";
                        break;
                    case R.id.qingnian:
                        myrenqun="white";
                        break;
                    case R.id.jiating:
                        myrenqun="familys";
                        break;
                    case R.id.laoren:
                        myrenqun="oldman";
                        break;
                    case R.id.pengyoutuan:
                        myrenqun="friend";
                        break;
                    case R.id.qinglv:
                        myrenqun="couple";
                        break;
                }
            }
        });
        singleselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mymudi=didian.getText().toString();
                //Toast.makeText(AttractionsActivity.this,mymudi+","+myrenqun+","+mypianhao+","+mytianqi,Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(AttractionsActivity.this,ResultRouteActivity.class);
                intent.putExtra("mymudi",mymudi);
                intent.putExtra("myrenqun",myrenqun);
                intent.putExtra("mypianhao",mypianhao);
                intent.putExtra("mytianqi",mytianqi);
                startActivity(intent);
            }
        });
    }

}
