package com.example.navigationapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Blog;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.util.BlogAdapter;
import com.example.navigationapp.util.BlogHandler;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class BlogListActivity extends AppCompatActivity {
    private  final static int SHOW_BLOG=1;
    private ImageView insert_blog;
    private ListView listView;
    private BlogAdapter adapter;
    private List<Blog> blogList=new ArrayList<>();
    private Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case SHOW_BLOG:
                    String response=(String)msg.obj;
                    //Toast.makeText(BlogListActivity.this,response,Toast.LENGTH_SHORT).show();
                    //解析数据
                    parseXML(response);
                    //更改下头像
                    changeHeader();

                    adapter=new BlogAdapter(BlogListActivity.this,R.layout.blog_item,blogList);
                    listView=(ListView)findViewById(R.id.list_view);
                    listView.setAdapter(adapter);

                    adapter.notifyDataSetChanged();
            }
        }
    };
    private void changeHeader(){
        for(Blog blog:blogList){
            if(blog.getId()%5==0){
                blog.setId(R.mipmap.first_head);
            }else if(blog.getId()%5==1){
                blog.setId(R.mipmap.two_head);
            }else if(blog.getId()%5==2){
                blog.setId(R.mipmap.three_head);
            }else if(blog.getId()==3){
                blog.setId(R.mipmap.four_head);
            }else {
                blog.setId(R.mipmap.five_head);
            }
        }
    }
    private void parseXML(String xmlData){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            BlogHandler handler=new BlogHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            blogList=handler.getList();
            if(blogList!=null){
                Toast.makeText(BlogListActivity.this,"一共有："+blogList.size(),Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.blog_list);
        initBlog();
        adapter=new BlogAdapter(BlogListActivity.this,R.layout.blog_item,blogList);
        listView=(ListView)findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        insert_blog=(ImageView)findViewById(R.id.insert_blog);
        insert_blog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用来添加数据
                Intent intent=new Intent(BlogListActivity.this,ToTalkActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //发送网络请求
        requestXMLBlog();


    }
    private void requestXMLBlog(){
        String coinUrl="http://"+ MyIp.ip+":8080/CarSafe/SelectBlogServlet";
        HttpUtil.sendHttpRequest(coinUrl, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_BLOG;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void initBlog(){
        Blog blog=new Blog(R.mipmap.first_head,2017,"小天使","政府工作报告明确提出，要“加强国防动员和后备力量建设”“推动军民融合深度发展”。今年是军队规模结构和力量编成改革落地和磨合运行的关键一年。如何在新的历史起点上奋力推进新体制下国防动员事业创新发展？就此，全国人大代表、中央军委国防动员部部长盛斌接受了记者的采访。");
        blogList.add(blog);
        Blog blog1=new Blog(R.mipmap.first_head,2017,"小天使","政府工作报告明确提出，要“加强国防动员和后备力量建设”“推动军民融合深度发展”。今年是军队规模结构和力量编成改革落地和磨合运行的关键一年。如何在新的历史起点上奋力推进新体制下国防动员事业创新发展？就此，全国人大代表、中央军委国防动员部部长盛斌接受了记者的采访。");
        blogList.add(blog1);
        Blog blog2=new Blog(R.mipmap.first_head,2017,"小天使","政府工作报告明确提出，要“加强国防动员和后备力量建设”“推动军民融合深度发展”。今年是军队规模结构和力量编成改革落地和磨合运行的关键一年。如何在新的历史起点上奋力推进新体制下国防动员事业创新发展？就此，全国人大代表、中央军委国防动员部部长盛斌接受了记者的采访。");
        blogList.add(blog2);
        Blog blog3=new Blog(R.mipmap.first_head,2017,"小天使","政府工作报告明确提出，要“加强国防动员和后备力量建设”“推动军民融合深度发展”。今年是军队规模结构和力量编成改革落地和磨合运行的关键一年。如何在新的历史起点上奋力推进新体制下国防动员事业创新发展？就此，全国人大代表、中央军委国防动员部部长盛斌接受了记者的采访。");
        blogList.add(blog3);
    }
}
