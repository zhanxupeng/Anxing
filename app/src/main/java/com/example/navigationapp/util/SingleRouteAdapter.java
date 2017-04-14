package com.example.navigationapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.ForAttractions;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;


/**
 * Created by 占大帅 on 2017/4/13.
 */

public class SingleRouteAdapter extends ArrayAdapter<ForAttractions> {
    private int resourceId;
    public SingleRouteAdapter(Context context, int textViewResourceId, List<ForAttractions> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        ForAttractions forAttractions=getItem(position);
        int rank=(forAttractions.getForcrowds()+forAttractions.getForviewpreference()+forAttractions.getForweatherindex())/3;
        Bitmap bitmap=null;
        try {
            File file=new File(Environment.getExternalStorageDirectory(),"anxin/"+forAttractions.getExterior().trim());
            InputStream in = new FileInputStream(file);
            bitmap = BitmapFactory.decodeStream(in);
        }catch (Exception e){
            e.printStackTrace();
        }
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        TextView z_name=(TextView)view.findViewById(R.id.z_name);
        ImageView z_exterior=(ImageView)view.findViewById(R.id.z_exterior);
        ImageView z_rank=(ImageView)view.findViewById(R.id.z_rank);
        TextView z_time=(TextView)view.findViewById(R.id.z_time);
        TextView z_price=(TextView)view.findViewById(R.id.z_price);
        z_name.setText("   "+forAttractions.getName());
        z_time.setText("   "+"预计"+String.format("%.0f",forAttractions.getTime())+"小时");
        z_price.setText(String.format("￥"+"%.0f",forAttractions.getPrice())+"起  ");
        if(bitmap!=null) {
            z_exterior.setImageBitmap(bitmap);
        }
        if(rank==5){
            z_rank.setImageResource(R.mipmap.fivestar);
        }else if(rank==4){
            z_rank.setImageResource(R.mipmap.fourstar);
        }else if(rank==3){
            z_rank.setImageResource(R.mipmap.threestar);
        }else if(rank==2){
            z_rank.setImageResource(R.mipmap.twostar);
        }else if(rank==1){
            z_rank.setImageResource(R.mipmap.onestar);
        }else {
            z_rank.setImageResource(R.mipmap.nostar);
        }
        return view;
    }
}
