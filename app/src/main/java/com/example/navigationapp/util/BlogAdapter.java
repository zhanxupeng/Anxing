package com.example.navigationapp.util;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Blog;

import java.util.List;

/**
 * Created by 占大帅 on 2017/3/9.
 */

public class BlogAdapter extends ArrayAdapter<Blog> {
    private int resourceId;
    public BlogAdapter(Context context, int textViewResourceId, List<Blog> objects){
        super(context,textViewResourceId,objects);
        resourceId=textViewResourceId;
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Blog blog=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resourceId,null);
        ImageView blog_image=(ImageView)view.findViewById(R.id.blog_image);
        TextView blog_name=(TextView)view.findViewById(R.id.blog_name);
        TextView blog_mysign=(TextView)view.findViewById(R.id.blog_mysign);
        blog_image.setImageResource(blog.getId());
        blog_name.setText(blog.getUsername());
        blog_mysign.setText(blog.getMysign());
        return view;
    }
}
