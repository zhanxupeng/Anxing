package com.example.navigationapp.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.navigationapp.R;
import com.example.navigationapp.model.MyIp;
import com.example.navigationapp.util.HttpCallbackListener;
import com.example.navigationapp.util.HttpUtil;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

public class BigDataActivity extends AppCompatActivity {
    private final static int SHOW_DATA=1;
    private LineChartView lineChart;
    private Button mybutton;
    private EditText data_route;
    private ImageView data_time;
    String[] date = {"原点","0点","1点","2点","3点","4点","5点","6点","7点","8点","9点","10点","11点","12点",
            "13点","14点","15点","16点","17点","18点","19点","20点","21点","22点","23点"};//X轴的标注
    //int[] score= {0,42,90,33,10,74,22,18,79,20,23,43,53,25,52,52,62,62,72,12,41,51,63,25,62};//图表的数据点
    int[] score= new int[25];//图表的数据点
    private List<PointValue> mPointValues = new ArrayList<PointValue>();
    private List<AxisValue> mAxisXValues = new ArrayList<AxisValue>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.big_data);
        mybutton=(Button)findViewById(R.id.mybutton);
        lineChart = (LineChartView)findViewById(R.id.line_chart);
        data_route=(EditText)findViewById(R.id.data_route);
        //getAxisXLables();//获取x轴的标注
        //getAxisPoints();//获取坐标点
        //initLineChart();//初始化
        mybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=data_route.getText().toString().trim();
                if("".equals(s)){
                    Toast.makeText(BigDataActivity.this,"请输入地点",Toast.LENGTH_SHORT).show();
                }else {
                    sendRequest(s);
                }
            }
        });
        data_time=(ImageView)findViewById(R.id.data_time);
        data_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BigDataActivity.this,traffic_activity.class);
                startActivity(intent);
            }
        });
    }
    private void sendRequest(String route){
        String coinUrl="http://"+ MyIp.ip+":8080/CarSafe/CardataServlet?route="+route;
        HttpUtil.sendHttpRequest(coinUrl, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Message message=new Message();
                message.what=SHOW_DATA;
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
              case SHOW_DATA:
                  String response=(String)msg.obj;
                  //Toast.makeText(BigDataActivity.this, response, Toast.LENGTH_SHORT).show();
                  //对response进行解析
                  analysisData(response);
                  getAxisXLables();//获取x轴的标注
                  getAxisPoints();//获取坐标点
                  initLineChart();//初始化
          }
      }
    };
    private void analysisData(String data){
        String[] num=data.split(",");
        for(int i=0;i<num.length;i++){
            score[i]=1000/Integer.parseInt(num[i]);
        }
    }
    private void getAxisXLables(){
        for (int i = 0; i < date.length; i++) {
            mAxisXValues.add(new AxisValue(i).setLabel(date[i]));
        }
    }
    /**
     * 图表的每个点的显示
     */
    private void getAxisPoints() {
        for (int i = 0; i < score.length; i++) {
            mPointValues.add(new PointValue(i, score[i]));
        }
    }
    private void initLineChart(){
        Line line = new Line(mPointValues).setColor(Color.parseColor("#0000ff"));  //折线的颜色（橙色）
        List<Line> lines = new ArrayList<Line>();
        line.setShape(ValueShape.CIRCLE);//折线图上每个数据点的形状  这里是圆形 （有三种 ：ValueShape.SQUARE  ValueShape.CIRCLE  ValueShape.DIAMOND）
        line.setCubic(false);//曲线是否平滑，即是曲线还是折线
        line.setFilled(false);//是否填充曲线的面积
        line.setHasLabels(true);//曲线的数据坐标是否加上备注
//      line.setHasLabelsOnlyForSelected(true);//点击数据坐标提示数据（设置了这个line.setHasLabels(true);就无效）
        line.setHasLines(true);//是否用线显示。如果为false 则没有曲线只有点显示
        line.setHasPoints(true);//是否显示圆点 如果为false 则没有原点只有点显示（每个数据点都是个大的圆点）
        lines.add(line);
        LineChartData data = new LineChartData();
        data.setLines(lines);

        //坐标轴
        Axis axisX = new Axis(); //X轴
        axisX.setHasTiltedLabels(false);  //X坐标轴字体是斜的显示还是直的，true是斜的显示
        axisX.setTextColor(Color.BLACK);  //设置字体颜色
        //axisX.setName("date");  //表格名称
        axisX.setTextSize(15);//设置字体大小
        axisX.setMaxLabelChars(2); //最多几个X轴坐标，意思就是你的缩放让X轴上数据的个数7<=x<=mAxisXValues.length
        axisX.setValues(mAxisXValues);  //填充X轴的坐标名称
        data.setAxisXBottom(axisX); //x 轴在底部
        //data.setAxisXTop(axisX);  //x 轴在顶部
        axisX.setHasLines(true); //x 轴分割线

        // Y轴是根据数据的大小自动设置Y轴上限(在下面我会给出固定Y轴数据个数的解决方案)
        Axis axisY = new Axis();  //Y轴
        axisY.setName("");//y轴标注
        axisY.setTextSize(10);//设置字体大小
        axisY.setTextColor(Color.RED);
        data.setAxisYLeft(axisY);  //Y轴设置在左边
        //data.setAxisYRight(axisY);  //y轴设置在右边


        //设置行为属性，支持缩放、滑动以及平移
        lineChart.setInteractive(true);
        lineChart.setZoomType(ZoomType.HORIZONTAL);
        lineChart.setMaxZoom((float)3);//最大方法比例
        lineChart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        lineChart.setLineChartData(data);
        lineChart.setVisibility(View.VISIBLE);
        /**注：下面的7，10只是代表一个数字去类比而已
         * 当时是为了解决X轴固定数据个数。见（http://forum.xda-developers.com/tools/programming/library-hellocharts-charting-library-t2904456/page2）;
         */
        Viewport v = new Viewport(lineChart.getMaximumViewport());
        v.left = 0;
        v.right= 8;
        lineChart.setCurrentViewport(v);
    }
}
