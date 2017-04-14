package com.example.navigationapp.Activity;

import android.content.Intent;

import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;



import android.os.SystemClock;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.Mycoin;
import com.example.navigationapp.model.Person;
import com.example.navigationapp.model.StopStory;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FirstPage extends AppCompatActivity {

    private ViewPager mViewPager;
    //图片都存放在这里
    private List<ImageView> imageViewList;
    private ImageView iv;
    private TextView imgDes;
    //线程开关，当activity 销毁后，线程也应该停止运行
    private boolean isStop=false;
    private int previousPoint=0;
    //存放小点的布局文件
    private LinearLayout layoutPGroup;
    private String[] imageDescription = { "淮左名都，竹西佳处，解鞍少驻初程。", "过春风十里。尽荠麦青青。",
            "自胡马窥江去后，废池乔木，犹厌言兵。", "渐黄昏，清角吹寒。都在空城。", "杜郎俊赏，算而今、重到须惊。" };
    private final static int SHOW_RESPONSE=1;
    private final static int SHOW_COIN=2;
    private final static int SHOW_UPDATE=3;
    private ImageView a_start;
    private ImageView icons_one;
    private ImageView icons_two;
    private ImageView icons_three;
    private ImageView icons_four;
    private ImageView icons_five;
    private ImageView icons_six;
    private ImageView a_car;
    private ImageView a_shop;
    private ImageView a_mymessage;
    private TextView play_card;//打卡按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.first_page);
        init();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!isStop){
                    SystemClock.sleep(5000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
                        }
                    });
                }
            }
        }).start();
        //如果当前用户已经登录了，那么开启安行币系统
        if(Person.car_id!=0&&Person.coinflag==false){
            //输入安行币类的值（由服务器得到）
            sendToGetCoin(Person.car_id);
            Person.coinflag=true;
        }
        //上传现在的位置，用于检测是否进行雾霾检测
        if(Person.car_id==0) {
            sendWeatherRequest(1);
        }
        //打卡
        play_card=(TextView)findViewById(R.id.play_card);
        play_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(Person.car_id>0) {
                        if(Mycoin.member==calculate()){
                           Toast.makeText(FirstPage.this,"您今天已经签到过了哦，明天再来",Toast.LENGTH_SHORT).show();
                        }else {
                            //签到成功
                            sendCoinRequest();
                            //Intent intent = new Intent(FirstPage.this, ToTalkActivity.class);
                            //startActivity(intent);
                        }
                    }else {
                        Toast.makeText(FirstPage.this,"要先登录的哦",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        //获取id
        icons_one=(ImageView)findViewById(R.id.icons_one);
        //单一景点查询
        icons_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstPage.this,AttractionsActivity.class);
                startActivity(intent);
            }
        });
        //路线规划
        icons_two=(ImageView)findViewById(R.id.icons_two);
        icons_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstPage.this,ToplanningActivity.class);
                startActivity(intent);
            }
        });
        icons_three=(ImageView) findViewById(R.id.icons_three);
        icons_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //进入博客空间的链接
                Intent intent=new Intent(FirstPage.this,BlogListActivity.class);
                startActivity(intent);
            }
        });
        //改为商城
        icons_four=(ImageView) findViewById(R.id.icons_four);
        icons_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstPage.this,ShopActivity.class);
                startActivity(intent);
            }
        });
        icons_five=(ImageView) findViewById(R.id.icons_five);
        icons_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstPage.this,BigDataActivity.class);
                startActivity(intent);
            }
        });
        //改功能仅对登录用户，并且改天是第一次做题的用户方可进入
        icons_six=(ImageView) findViewById(R.id.icons_six);
        icons_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Person.flag) {
                    if(Mycoin.mydate!=calculate()) {
                        Intent intent = new Intent(FirstPage.this, EnjoyActivity.class);
                        startActivity(intent);
                    }else{
                        Toast.makeText(FirstPage.this,"抱歉，您今天已经做过题了",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(FirstPage.this,"该功能仅对登录用户开放，请先登录！",Toast.LENGTH_LONG).show();
                }
            }
        });
        //行车
        a_car=(ImageView)findViewById(R.id.a_car);
        //商店
        a_shop=(ImageView)findViewById(R.id.a_shop);
        a_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent=new Intent(FirstPage.this,KipActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //我的信息页面
        a_mymessage=(ImageView)findViewById(R.id.a_mymessage);
        a_mymessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FirstPage.this,MymessageActivity.class);
                startActivity(intent);
                finish();
            }
        });
            a_car.setImageResource(R.mipmap.che_two);
    }
    private void init(){
        mViewPager=(ViewPager)this.findViewById(R.id.viewpager);
        layoutPGroup=(LinearLayout)this.findViewById(R.id.show_pointer);
        imgDes=(TextView)this.findViewById(R.id.image_description);
        imageViewList=new ArrayList<ImageView>();
        int[] ivIDs=new int[]{
                R.mipmap.a,R.mipmap.b,R.mipmap.c,R.mipmap.d,R.mipmap.e
        };
        for(int id:ivIDs){
            iv=new ImageView(this);
            iv.setBackgroundResource(id);
            imageViewList.add(iv);
            View v=new View(this);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(8,8);
            params.leftMargin=12;
            v.setLayoutParams(params);
            v.setEnabled(false);
            v.setBackgroundResource(R.drawable.pointer_selector);
            layoutPGroup.addView(v);
        }
        int index=Integer.MAX_VALUE/2-3;
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());
        mViewPager.setCurrentItem(index);

    }
    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener{
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //开始
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //正在进行时
        }

        @Override
        public void onPageSelected(int position) {
            //结束
            position=position%imageViewList.size();
            imgDes.setText(imageDescription[position]);
            layoutPGroup.getChildAt(previousPoint).setEnabled(false);
            layoutPGroup.getChildAt(position).setEnabled(true);
            previousPoint=position;
        }
    }
    private class MyPagerAdapter extends PagerAdapter{
        @Override
        public int getCount(){
            return Integer.MAX_VALUE;
        }
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViewList.get(position%imageViewList.size()));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViewList.get(position%imageViewList.size()));
            return imageViewList.get(position%imageViewList.size());
        }
    }
    @Override
    protected void onDestroy(){
        isStop=true;
        super.onDestroy();
    }
    private void sendWeatherRequest(int id){
        String url="http://"+ MyIp.ip+":8080/CarSafe/WeatherServlet?id="+id;
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
    private void sendToGetCoin(int car_id){
        String coinUrl="http://"+ MyIp.ip+":8080/CarSafe/IntegrationServlet?car_id="+car_id;
        HttpUtil.sendHttpRequest(coinUrl, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
              Message message=new Message();
                message.what=SHOW_COIN;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
   private Handler handler=new Handler(){
     public void handleMessage(Message msg){
         switch (msg.what){
             case SHOW_RESPONSE:
                 String response=(String) msg.obj;
                 if(Integer.parseInt(response)>=5&&Person.car_id==0) {
                     Person.car_id=-1;
                     Toast.makeText(FirstPage.this, "当前空间能见度低，已经开始预警模式", Toast.LENGTH_SHORT).show();
                 }
                 StopStory.flag=Integer.parseInt(response);
                 break;
             case SHOW_COIN:
                 String coinResponse=(String)msg.obj;
                 //Toast.makeText(FirstPage.this,coinResponse,Toast.LENGTH_SHORT).show();
                 //处理数据
                 parseXMLcoin(coinResponse);
                 break;
             case SHOW_UPDATE:
                 String myresponse=(String)msg.obj;
                 Toast.makeText(FirstPage.this,myresponse,Toast.LENGTH_SHORT).show();
                 break;
         }
     }
   };
   private void parseXMLcoin(String xmlData){
       try{
           XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
           XmlPullParser xmlPullParser=factory.newPullParser();
           xmlPullParser.setInput(new StringReader(xmlData));
           int eventType=xmlPullParser.getEventType();
           while (eventType!=XmlPullParser.END_DOCUMENT){
               String nodeName=xmlPullParser.getName();
               switch (eventType){
                   case XmlPullParser.START_TAG:{
                       if("car_id".equals(nodeName)){
                           Mycoin.car_id=Integer.parseInt(xmlPullParser.nextText());
                       }else if("coin".equals(nodeName)){
                           Mycoin.coin=Integer.parseInt(xmlPullParser.nextText());
                       }else if("member".equals(nodeName)){
                           Mycoin.member=Integer.parseInt(xmlPullParser.nextText());
                       }else if("mydate".equals(nodeName)){
                           Mycoin.mydate=Integer.parseInt(xmlPullParser.nextText());
                       }
                       break;
                   }
                   default:
                       break;
               }
               eventType=xmlPullParser.next();
           }
       }catch (Exception e){
           e.printStackTrace();
       }
   }
    private int calculate(){
        int s=0;
        Calendar calendar=Calendar.getInstance();
        s=100*calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
        return s;
    }
    //用于签到的数据
    private int calculates(){
        int s=0;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        s=100*calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
        return s;
    }
    private void sendCoinRequest(){
        Mycoin.member=calculates();
        Mycoin.coin=Mycoin.coin+200;
        String str="http://"+ MyIp.ip+":8080/CarSafe/InsertQianServlet?car_id="+ Mycoin.car_id+"&coin="+Mycoin.coin+"&member="+Mycoin.member+"&flag=1";
        HttpUtil.sendHttpRequest(str, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_UPDATE;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
