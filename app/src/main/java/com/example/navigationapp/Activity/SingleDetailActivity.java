package com.example.navigationapp.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.ForAttractions;
import com.example.navigationapp.util.SingleRouteAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class SingleDetailActivity extends AppCompatActivity {
    private ForAttractions forAttractions;
    private ImageView xx_exterior;
    private TextView xx_name;
    private TextView xx_detail;
    private TextView xx_name_local;
    private ImageView xx_forcrowd;
    private ImageView xx_forview;
    private ImageView xx_forweather;
    private TextView xx_price;
    private ImageView xx_routepicture;
    private TextView xx_time;
    private TextView xx_name_price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singledetail_activity);
        Intent intent=getIntent();
        forAttractions=(ForAttractions)intent.getSerializableExtra("myattractions");
        xx_exterior=(ImageView)findViewById(R.id.xx_exterior);
        xx_name=(TextView)findViewById(R.id.xx_name);
        xx_detail=(TextView)findViewById(R.id.xx_detail);
        xx_name_local=(TextView)findViewById(R.id.xx_name_local);
        xx_forcrowd=(ImageView)findViewById(R.id.xx_forcrowd);
        xx_forview=(ImageView)findViewById(R.id.xx_forview);
        xx_forweather=(ImageView)findViewById(R.id.xx_forweather);
        xx_price=(TextView) findViewById(R.id.xx_price);
        xx_routepicture=(ImageView)findViewById(R.id.xx_routepicture);
        xx_time=(TextView)findViewById(R.id.xx_time);
        xx_name_price=(TextView)findViewById(R.id.xx_name_price);

        xx_name.setText(forAttractions.getName());
        xx_detail.setText(forAttractions.getDetail());
        xx_name_local.setText(forAttractions.getName());
        insertstar(forAttractions.getForcrowds(),xx_forcrowd);
        insertstar(forAttractions.getForviewpreference(),xx_forview);
        insertstar(forAttractions.getForweatherindex(),xx_forweather);
        xx_price.setText("￥"+forAttractions.getPrice());
        xx_time.setText("   预计"+String.format("%.0f",forAttractions.getTime())+"小时");
        xx_name_price.setText("   "+forAttractions.getName()+"成人票：");

        Bitmap bitmap=null;
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"anxin/"+forAttractions.getExterior().trim());
            InputStream in = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(bitmap!=null) {
            xx_exterior.setImageBitmap(bitmap);
            bitmap=null;
        }
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"anxin/"+forAttractions.getRoutepicture().trim());
            InputStream in = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        if(bitmap!=null){
            xx_routepicture.setImageBitmap(bitmap);
        }

    }
    private void insertstar(int num,ImageView forImage){
        if(num==5){
            forImage.setImageResource(R.mipmap.fivestar);
        }else if(num==4){
            forImage.setImageResource(R.mipmap.fourstar);
        }else if(num==3){
            forImage.setImageResource(R.mipmap.threestar);
        }else if(num==2){
            forImage.setImageResource(R.mipmap.twostar);
        }else if(num==1){
            forImage.setImageResource(R.mipmap.onestar);
        }else {
            forImage.setImageResource(R.mipmap.nostar);
        }












    }
}
