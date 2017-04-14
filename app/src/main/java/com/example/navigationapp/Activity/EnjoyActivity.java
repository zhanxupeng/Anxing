package com.example.navigationapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.Examination;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.model.Mycoin;
import com.example.navigationapp.model.Person;
import com.example.navigationapp.model.StopStory;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.Calendar;
import java.util.Date;

public class EnjoyActivity extends AppCompatActivity{
    private TextView question;
    private RadioButton answera;
    private RadioButton answerb;
    private RadioButton answerc;
    private RadioButton answerd;
    private Button exam_button;
    private RadioGroup group;
    private int myanswer=0;
    private Examination examination=new Examination();
    private final static int SHOW_PESPONSE=1;
    private final static int SHOW_UPDATE=2;
    private int mycoin;
    private Handler handler=new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case SHOW_PESPONSE:
                  String response=(String)msg.obj;
                  parseXMLWithPull(response);
                  //Toast.makeText(EnjoyActivity.this,examination.getId()+","+examination.getQuestion(),Toast.LENGTH_LONG).show();
                  //放值
                  setMyText();
                  break;
              case SHOW_UPDATE:
                String coinResponse=(String)msg.obj;
                  Toast.makeText(EnjoyActivity.this,coinResponse,Toast.LENGTH_SHORT).show();
                  break;
          }
      }
    };
    private void setMyText(){
        question.setText(examination.getQuestion());
        answera.setText(examination.getAnswera());
        answerb.setText(examination.getAnswerb());
        answerc.setText(examination.getAnswerc());
        answerd.setText(examination.getAnswerd());
    }
    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            while(eventType!=XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            examination.setId(Integer.parseInt(xmlPullParser.nextText().trim()));
                        }else if("question".equals(nodeName)){
                            examination.setQuestion(xmlPullParser.nextText().trim());
                        }else if("answera".equals(nodeName)){
                            examination.setAnswera(xmlPullParser.nextText());
                        }else if("answerb".equals(nodeName)){
                            examination.setAnswerb(xmlPullParser.nextText());
                        }else if("answerc".equals(nodeName)){
                            examination.setAnswerc(xmlPullParser.nextText());
                        }else if("answerd".equals(nodeName)){
                            examination.setAnswerd(xmlPullParser.nextText());
                        }else if("answer".equals(nodeName)){
                            examination.setAnswer(Integer.parseInt(xmlPullParser.nextText()));
                        }
                    }
                    case XmlPullParser.END_TAG:{
                        if("answer".equals(nodeName)){
                            break;
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enjoy_activity);
        question=(TextView)findViewById(R.id.question);
        answera=(RadioButton)findViewById(R.id.answer_a);
        answerb=(RadioButton)findViewById(R.id.answer_b);
        answerc=(RadioButton)findViewById(R.id.answer_c);
        answerd=(RadioButton)findViewById(R.id.answer_d);
        exam_button=(Button)findViewById(R.id.exam_button);
        group=(RadioGroup)findViewById(R.id.rg);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
               if(checkedId==R.id.answer_a){
                   myanswer=1;
               }else if(checkedId==R.id.answer_b){
                   myanswer=2;
               }else if(checkedId==R.id.answer_c){
                   myanswer=3;
               }else if(checkedId==R.id.answer_d){
                   myanswer=4;
               }
            }
        });
        //开始连接网络，选择题目和答案
        sendRequest();
        exam_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myanswer!=0){
                    if(myanswer==examination.getAnswer()){
                        Toast.makeText(EnjoyActivity.this,"恭喜你，回答正确",Toast.LENGTH_SHORT).show();
                        //发送请求
                        mycoin=(int)(Math.random()*100+200);
                        showSuccessDialog();//提示框
                        Mycoin.coin=mycoin+Mycoin.coin;
                        Mycoin.mydate=calculate();
                        sendCoinRequest();
                    }else{
                        Toast.makeText(EnjoyActivity.this,"抱歉，回答错误",Toast.LENGTH_SHORT).show();
                        mycoin=(int)(Math.random()*100);
                        showAddDialog();//提示框
                        Mycoin.coin=mycoin+Mycoin.coin;
                        Mycoin.mydate=calculate();
                        //发送请求
                        sendCoinRequest();
                    }
                }else{
                    Toast.makeText(EnjoyActivity.this,"请先选择答案",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendRequest(){
        String str="http://"+ MyIp.ip+":8080/CarSafe/ExamServlet";
        HttpUtil.sendHttpRequest(str, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_PESPONSE;
                message.obj=response;
                handler.sendMessage(message);
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
    private void sendCoinRequest(){
        String str="http://"+ MyIp.ip+":8080/CarSafe/UpdateCoinServlet?car_id="+Mycoin.car_id+"&coin="+Mycoin.coin+"&mydate="+Mycoin.mydate;
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
    private int calculate(){
        int s=0;
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(new Date());
        s=100*calendar.get(Calendar.MONTH)+calendar.get(Calendar.DAY_OF_MONTH);
        return s;
    }
    //success_coin
    //回答正确显示的对话框
    private void showSuccessDialog(){
        LayoutInflater factory=LayoutInflater.from(EnjoyActivity.this);//主活动
        final View view=factory.inflate(R.layout.success_exam,null);//对话框的xml文件
        final TextView default_coin=(TextView) view.findViewById(R.id.success_coin);//对话框的xml文件中的id
        default_coin.setText("恭喜你，回答正确，仅仅获得"+mycoin+"枚安行币");//设置值
        AlertDialog.Builder ad1=new AlertDialog.Builder(EnjoyActivity.this);
        ad1.setTitle("温馨提示：");//标题
        ad1.setCancelable(false);//必须做出选择，按返回键无法退出
        ad1.setView(view);
        //积极按键
        ad1.setPositiveButton("去积分商城逛逛", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //关闭当前页面，页面跳转
                Intent intent=new Intent(EnjoyActivity.this,CoinShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //消极按键
        ad1.setNegativeButton("先不去了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //仅仅关闭当前页面
                finish();
            }
        });
        //显示
        ad1.show();
    }
    private void showAddDialog(){
        LayoutInflater factory=LayoutInflater.from(EnjoyActivity.this);//主活动
        final View view=factory.inflate(R.layout.defaut_title,null);//对话框的xml文件
        final TextView default_coin=(TextView) view.findViewById(R.id.default_coin);//对话框的xml文件中的id
        default_coin.setText("非常抱歉，你的回答有误，仅仅获得"+mycoin+"枚安行币");//设置值
        AlertDialog.Builder ad1=new AlertDialog.Builder(EnjoyActivity.this);
        ad1.setTitle("温馨提示：");//标题
        ad1.setCancelable(false);//必须做出选择，按返回键无法退出
        ad1.setView(view);
        //积极按键
        ad1.setPositiveButton("去积分商城逛逛", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //关闭当前页面，页面跳转
                Intent intent=new Intent(EnjoyActivity.this,CoinShopActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //消极按键
        ad1.setNegativeButton("先不去了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                //仅仅关闭当前页面
                finish();
            }
        });
        //显示
        ad1.show();
    }
}
