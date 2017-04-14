package com.example.navigationapp.Activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.ForAttractions;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;
import com.example.navigationapp.util.SingleRouteAdapter;
import com.example.navigationapp.util.SingleRouteHandler;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

public class ResultRouteActivity extends AppCompatActivity {
    private List<ForAttractions> forAttractionsList=new ArrayList<>();
    private SingleRouteAdapter adapter;
    private ListView listView;
    private final static int SHOW_RESPONSE=1;
    private Handler handler=new Handler(){
      public void handleMessage(Message msg){
          switch (msg.what){
              case SHOW_RESPONSE:
                  String response=(String)msg.obj;
                  //Toast.makeText(ResultRouteActivity.this,response,Toast.LENGTH_SHORT).show();
                  /*
                  解析数据
                   */
                  parseXmlSax(response);
                  /*
                  显示结果
                   */
                  //Toast.makeText(ResultRouteActivity.this,"共"+forAttractionsList.size(),Toast.LENGTH_SHORT).show();
                  adapter=new SingleRouteAdapter(ResultRouteActivity.this,
                          R.layout.singleroute_item,forAttractionsList);
                  listView=(ListView)findViewById(R.id.single_list_view);
                  listView.setAdapter(adapter);
                  listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                      @Override
                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                          ForAttractions myattractions=forAttractionsList.get(position);
                          //Toast.makeText(ResultRouteActivity.this,myattractions.getName(),Toast.LENGTH_SHORT).show();
                          Intent intent=new Intent(ResultRouteActivity.this,SingleDetailActivity.class);
                          Bundle bundle=new Bundle();
                          bundle.putSerializable("myattractions",myattractions);
                          intent.putExtras(bundle);
                          startActivity(intent);
                      }
                  });
                  break;
          }
      }
    };
    private void parseXmlSax(String xmlData){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            SingleRouteHandler handler=new SingleRouteHandler();
            xmlReader.setContentHandler(handler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            forAttractionsList=handler.getList();
            //Toast.makeText(ResultRouteActivity.this,"SAX："+forAttractionsList.size(),Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.singleroute_list);
        Intent intent=getIntent();
        String mymudi=intent.getStringExtra("mymudi");
        String myrenqun=intent.getStringExtra("myrenqun");
        String mypianhao=intent.getStringExtra("mypianhao");
        String mytianqi=intent.getStringExtra("mytianqi");
        //Toast.makeText(ResultRouteActivity.this,mymudi+","+myrenqun+","+mypianhao+","+mytianqi,Toast.LENGTH_SHORT).show();
        sendCoinRequest(myrenqun,mypianhao,mytianqi);
    }

    private void sendCoinRequest(final String myrenqun,final String mypianhao,final String mytianqi){
        String str="http://"+ MyIp.ip+":8080/CarSafe/SingleRouteServlet?myrenqun="+myrenqun+"&mypianhao="+mypianhao+"&mytianqi="+mytianqi;
        HttpUtil.sendHttpRequest(str, new HttpCallbackListener() {
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
