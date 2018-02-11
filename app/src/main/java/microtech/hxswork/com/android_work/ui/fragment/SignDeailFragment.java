package microtech.hxswork.com.android_work.ui.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import org.bson.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.SignFragmentgItemAdapter;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.SignItemBean;
import microtech.hxswork.com.android_work.contract.SignDeailContract;
import microtech.hxswork.com.android_work.model.SignDeailModelImpl;
import microtech.hxswork.com.android_work.mpandroid.LineChartManager;
import microtech.hxswork.com.android_work.presenter.SignDeailPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.Util.TimeUtils.stampToDate;
import static microtech.hxswork.com.android_work.mpandroid.LineChartManager.xAxis;
import static microtech.hxswork.com.android_work.mpandroid.LineChartManager.yAxisLeft;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_array_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getDateToString;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getDateToString1;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.userA;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.userB;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.userC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;

/**
 * Created by microtech on 2017/9/25.
 */

public class SignDeailFragment extends BaseFragment<SignDeailPresenterImpl,SignDeailModelImpl>implements SignDeailContract.View,RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    @Bind(R.id.chart)
    LineChart chart;
    @Bind(R.id.mRadiogroup)
    RadioGroup mRadiogroup;
    @Bind(R.id.rb1)
    RadioButton mRb1;
    @Bind(R.id.rb2)
    RadioButton mRb2;

    @Bind(R.id.sign_z_nums)
    TextView sign_z_nums;//正常次数

    @Bind(R.id.sign_g_nums)
    TextView sign_g_nums;//偏高次数

    @Bind(R.id.sign_d_nums)
    TextView sign_d_nums;//偏低次数

    @Bind(R.id.sign_recycel)
    RecyclerView sign_recycel;//表格布局

    @Bind(R.id.sign_add)
    LinearLayout sign_add;//手动输入

    @Bind(R.id.sign_title)
    TextView sign_title;

    @Bind(R.id.sign_back_linear)
    LinearLayout sign_back_linear;

    HomeActivity mActivity;
    List<SignItemBean> list;
    SignFragmentgItemAdapter signadapter;

    OkHttpClientManager ok;
    String home_url="https://doc.newmicrotech.cn/otsmobile/app/physicalInfo?";//体征数据详情
    Handler handler;
    final String[] SignDeail = {null};
    String sing_title1[];
   public static String flgae_user;
    String user_ida="";

    Document document;
    Document document_obj;
    Document document_line;
    Document document_List;

    List<Document> list_line;
    List<Document> list_data;//列表的数据
    List<Document> list_status;//状态次数的数据
    ArrayList<String> xValues;
    @Override
    protected int getLayoutResource() {
        return R.layout.signdeail_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.GONE);
        sing_title1= new String[2];
        sing_title1 = getArguments().getStringArray("data");
        sign_title.setText(sing_title1[0]+"数据统计");

        flgae_user =userA+""+userB+""+userC;

        System.out.println("flgae_user***********"+flgae_user);
        System.out.println("box_array_flage***********"+box_array_flage);

        sign_recycel.setLayoutManager(new LinearLayoutManager(getContext()));

        sign_back_linear.setOnClickListener(this);
        sign_add.setOnClickListener(this);
        mRadiogroup.setOnCheckedChangeListener(this);
        mRb1.setChecked(true);
        //ChartUtils.initChart(chart);

        handler= new MyHander();

        if(box_array_flage==0)
        {
            user_ida = user_id;
            thread(user_id, "");
        }
        else {
            if (flgae_user.equals("100")) { //判断是那个用户的体征数据详情
                user_ida = user_idA;
                thread(user_idA, "");
            } else if (flgae_user.equals("010"))//用户B的数据
            {
                user_ida = user_idB;
                thread(user_idB, "");
            } else if (flgae_user.equals("001"))//用户C的数据
            {
                user_ida = user_idC;
                thread(user_idC, "");
            }
        }
    }
    private ArrayList<Entry> getData( List<Document> list_line,int flage) {
        ArrayList<Entry> values = null;

        if (flage == 1) {
            //标识血压
            values = new ArrayList<>();
            for (int i = 0; i < list_line.size(); i++) {
                float dia_value=0.0f;
                if(list_line.get(i).get("pressure",Document.class)!= null)
                {
                    if(list_line.get(i).get("pressure",Document.class).getDouble("dia_value")!=null) {
                        dia_value = Float.parseFloat(list_line.get(i).get("pressure", Document.class).getDouble("dia_value") + "");
                    }
                }
                values.add(new Entry(i,dia_value));
            }
            LineChartManager.setLineName1("舒张压");
        }
        else if(flage == 0)
        {
            values = new ArrayList<>();
            for (int i = 0; i < list_line.size(); i++) {
                float dia_value=0.0f;
                if(list_line.get(i).get("sugar",Document.class)!= null)
                {
                    if(list_line.get(i).get("sugar",Document.class).getDouble("value")!=null) {
                        dia_value = Float.parseFloat(list_line.get(i).get("sugar", Document.class).getDouble("value") + "");//取出血糖
                    }
                }
                values.add(new Entry(i,dia_value));
            }
            LineChartManager.setLineName("血糖");
        }

        else if(flage == 2)//体温
        {
            values = new ArrayList<>();
            LineChartManager.setLineName("体温");
        }else if(flage == 3)//心率
        {
            values = new ArrayList<>();
            for (int i = 0; i < list_line.size(); i++) {
                float dia_value=0.0f;
                if(list_line.get(i).get("heart",Document.class) != null)
                {
                    if(list_line.get(i).get("heart",Document.class).getDouble("value")!=null) {
                    dia_value = Float.parseFloat(list_line.get(i).get("heart", Document.class).getDouble("value") + "");//取出血糖
                }
                }
                values.add(new Entry(i,dia_value));
            }
            LineChartManager.setLineName("心率");
        }else if(flage == 4){//血氧
            values = new ArrayList<>();
            for (int i = 0; i < list_line.size(); i++) {
                float dia_value=0.0f;
                if(list_line.get(i).get("oxygen",Document.class)!= null) {
                    if (list_line.get(i).get("oxygen", Document.class).getDouble("value") != null)
                    {
                        dia_value = Float.parseFloat(list_line.get(i).get("oxygen",Document.class).getDouble("value")+"");//取出血糖
                    }
                }
                values.add(new Entry(i,dia_value));
            }
            LineChartManager.setLineName("血氧");
        }
        return values;
    }

    private ArrayList<Entry> getData_Zero() {
        ArrayList<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 0));
        values.add(new Entry(1, 0));
        values.add(new Entry(2, 0));
        values.add(new Entry(3, 0));
        values.add(new Entry(4, 0));
        values.add(new Entry(5, 0));
        values.add(new Entry(6, 0));
        LineChartManager.setLineName1("舒张压");
        return values;
    }
    private ArrayList<Entry> getData1(   List<Document> list_line) {

        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < list_line.size(); i++) {
            float dia_value=0.0f;
            if(list_line.get(i).get("pressure",Document.class)!= null)
            {
                if(list_line.get(i).get("pressure",Document.class).getDouble("sys_value")!=null) {
                    dia_value = Float.parseFloat(list_line.get(i).get("pressure", Document.class).getDouble("sys_value") + "");
                }
            }
            values.add(new Entry(i,dia_value));
        }
        LineChartManager.setLineName("收缩压");
        return values;
    }
    @Override
    public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
        mRb1.setBackgroundResource(R.drawable.sign_shape_off);
        mRb1.setTextColor(getResources().getColor(R.color.black_off));
        mRb2.setBackgroundResource(R.drawable.sign_shape_off);
        mRb2.setTextColor(getResources().getColor(R.color.black_off));
        switch (i) {
        case  R.id.rb1:
            chart.setVisibility(View.VISIBLE);
            chart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.x_anim));//加载动画
            sign_recycel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.x_anim_off));//加载动画
            sign_recycel.setVisibility(View.GONE);

            mRb1.setBackgroundResource(R.drawable.sign_shape);
            mRb1.setTextColor(getResources().getColor(R.color.white));
            break;
        case  R.id.rb2:
            mRb2.setBackgroundResource(R.drawable.sign_shape);
            mRb2.setTextColor(getResources().getColor(R.color.white));
            chart.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.x_anim_off));//加载动画
            chart.setVisibility(View.GONE);
            sign_recycel.setVisibility(View.VISIBLE);
            sign_recycel.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.x_anim));//加载动画
            break;
        }
    }

    @Override
    public void onClick(View view) {
            switch (view.getId())
            {
                case R.id.sign_back_linear:
                    mActivity.onBackPressed();
                    break;
                case R.id.sign_add:
                    if(box_id == null)
                    {
                        SAToast.makeText(getContext(),"请先绑定盒子").show();
                    }else {
                        String a[]=  new String[3];
                        a[0]=sing_title1[0];
                        a[1]=user_ida;
                        a[2]=sing_title1[1];//type标识
                        Bundle bundle = new Bundle();
                        bundle.putStringArray("data",a);
                        mActivity.pushFragment(new SignItemNextFragment(),true,bundle);
                    }
                    break;
            }
    }
    public class MyMarkerView extends MarkerView{

        private TextView tvContent;

        public MyMarkerView(Context context, int layoutResource) {
            super(context, layoutResource);
            tvContent  =  findViewById(R.id.marker_hou);
        }

        @Override
        public void refreshContent(Entry e, Highlight highlight) {

            if (e instanceof CandleEntry) {

                CandleEntry ce = (CandleEntry) e;

                tvContent.setText("" + Utils.formatNumber(ce.getHigh(), 0, true));
            } else {

                tvContent.setText("" + Utils.formatNumber(e.getY(), 0, true));
            }

            super.refreshContent(e, highlight);
        }
        @Override
        public MPPointF getOffset() {
            return new MPPointF( - (getWidth() / 2), getHeight());
        }
    }





    public void thread(final String user_id , final String phone) {
//数据加载
        LoadingDialog.showDialogForLoading(mActivity, "获取数据中..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ok = new OkHttpClientManager();

                    final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                            //主界面
                            new OkHttpClientManager.Param("p_id", user_id),
                            new OkHttpClientManager.Param("timeType","0"),//时间
                            new OkHttpClientManager.Param("type", sing_title1[1]),
                            new OkHttpClientManager.Param("next_start", "")
                    };
                    System.out.println("user_id*********"+user_id);
                    SignDeail[0] = ok.postOneHeadAsString(home_url,device_no, params_home);//体征数据
                    System.out.println("体征数据详情加载成功!" + SignDeail[0]);
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = SignDeail[0];
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    class MyHander extends Handler{
        //跟新数据handler
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://代表首次获取
                    list_line = new ArrayList<>();
                        String data= (String) msg.obj;
                    if ((data.charAt(0) + "").equals("<")) {
                        SAToast.makeText(getContext(),"服务器异常").show();
                        LoadingDialog.cancelDialogForLoading();
                        return;
                    }
                    document = Document.parse(data);
                    document_obj = document.get("obj",Document.class);

                    if(document.getInteger("code")==200)
                    {
                        list_line = document_obj.get("line",List.class);//得到折线图的数据
                        list_data = document_obj.get("list",List.class);//得到列表的数据
                        list_status =  document_obj.get("status",List.class);//得到列表的数据

                        for(int i  =0 ;  i < list_status.size() ; i++)
                        {
                            if(list_status.get(i).getInteger("_id")!=null) {
                                if (list_status.get(i).getInteger("_id") == -1) {//偏低次数
                                    sign_d_nums.setText(list_status.get(i).getInteger("count")+"");
                                } else if (list_status.get(i).getInteger("_id") == 0) {//正常次数
                                    sign_z_nums.setText(list_status.get(i).getInteger("count")+"");
                                }else {//偏高次数
                                    sign_g_nums.setText(list_status.get(i).getInteger("count")+"");
                                }
                            }
                        }
                        xValues = new ArrayList<>();
                        for (int i = 0; i < list_line.size(); i++) {
                            xValues.add("" + list_line.get(i).getString("times").substring(5,list_line.get(i).getString("times").length()));//获取到X轴坐标的数据 日期坐标
                            System.out.println("****string*******"+xValues.get(i));
                        }
                        if(sing_title1[1].equals("1")) {  //血压数据
                            //血压模块
                            if (box_id == null) {
                                SAToast.makeText(getContext(), "请及时绑定盒子！").show();
                                LineChartManager.initDoubleLineChart(getContext(),chart,xValues,getData_Zero(),getData_Zero());
                                xAxis.setLabelCount(10);     //设置显示10个标签
                                nomber();
                            }else {
                                System.out.println("****血压的数据*******"+list_line);
                                LineChartManager.initDoubleLineChart(getContext(),chart,xValues,getData(list_line,1),getData1(list_line));
                                if(xValues.size() > 1) {
                                    chart.getXAxis().setLabelCount(xValues.size() - 1);     //设置显示10个标签
                                }else {
                                    chart.getXAxis().setLabelCount(xValues.size());
                                }
                                LimitLine llx = new LimitLine(xAxis.getLabelCount(), "");
                                llx.setLineWidth(1f);
                                llx.setLineColor(Color.parseColor("#2FDBF4"));
                                xAxis.addLimitLine(llx);
                                per();
                            }
                        }else if(sing_title1[1].equals("0")){
                            if (box_id == null) {
                                SAToast.makeText(getContext(), "请及时绑定盒子！").show();
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData_Zero());
                                chart.getXAxis().setLabelCount(10);     //设置显示10个标签
                                nomber();
                            }else {
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData(list_line,0));//单数据
                                if(xValues.size() > 1) {
                                    chart.getXAxis().setLabelCount(xValues.size() - 1);     //设置显示10个标签
                                }else {
                                    chart.getXAxis().setLabelCount(xValues.size());
                                }
                                LimitLine llx = new LimitLine(xAxis.getLabelCount(), "");
                                llx.setLineWidth(2f);
                                llx.setLineColor(Color.parseColor("#2FDBF4"));
                                xAxis.addLimitLine(llx);
                                tan();
                            }
                        }else if(sing_title1[1].equals("2")){
                            //体温
                            if (box_id == null) {
                                SAToast.makeText(getContext(), "请及时绑定盒子！").show();
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData_Zero());
                                chart.getXAxis().setLabelCount(10);     //设置显示10个标签
                                nomber();
                            }else {
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData_Zero());
                                if(xValues.size() > 1) {
                                    chart.getXAxis().setLabelCount(xValues.size() - 1);     //设置显示10个标签
                                }else {
                                    chart.getXAxis().setLabelCount(xValues.size());
                                }
                                LimitLine llx = new LimitLine(xAxis.getLabelCount(), "");
                                llx.setLineWidth(1f);
                                llx.setLineColor(Color.parseColor("#2FDBF4"));
                                xAxis.addLimitLine(llx);
                                nomber();
                            }
                        }else if(sing_title1[1].equals("3")){
                            //心率
                            if (box_id == null) {
                                SAToast.makeText(getContext(), "请及时绑定盒子！").show();
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData_Zero());
                                chart.getXAxis().setLabelCount(10);     //设置显示10个标签
                                nomber();
                            }else {
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData(list_line,3));//单数据
                                if(xValues.size() > 1) {
                                    chart.getXAxis().setLabelCount(xValues.size() - 1);     //设置显示10个标签
                                }else {
                                    chart.getXAxis().setLabelCount(xValues.size());
                                }
                                LimitLine llx = new LimitLine(xAxis.getLabelCount(), "");
                                llx.setLineWidth(1f);
                                llx.setLineColor(Color.parseColor("#2FDBF4"));
                                xAxis.addLimitLine(llx);
                                heart();
                            }
                        }else if(sing_title1[1].equals("4")){
                            //血氧
                            if (box_id == null) {
                                SAToast.makeText(getContext(), "请及时绑定盒子！").show();
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData_Zero());
                                chart.getXAxis().setLabelCount(10);     //设置显示10个标签
                                nomber();
                            }else {

                                System.out.println("*********血氧的数据*****"+list_line);
                                LineChartManager.initSingleLineChart(getContext(),chart,xValues,getData(list_line,4));//单数据
                                if(xValues.size() > 1) {
                                    chart.getXAxis().setLabelCount(xValues.size() - 1);     //设置显示10个标签
                                }else {
                                    chart.getXAxis().setLabelCount(xValues.size());
                                }
                              /*  LimitLine llx = new LimitLine(xAxis.getLabelCount(), "");
                                llx.setLineWidth(1f);
                                llx.setLineColor(Color.parseColor("#2FDBF4"));
                                xAxis.addLimitLine(llx);*/
                                oxy();
                            }
                        }
                        MyMarkerView mv = new MyMarkerView(getContext(), R.layout.markerlayout);
                        mv.setChartView(chart);
                        chart.setMarker(mv);
                        MyXFormatter myXFormatter = new MyXFormatter(xValues);
                        chart.getXAxis().setValueFormatter(myXFormatter);
                        if(list_data.size()>0) {
                             list_list(list_data, sing_title1[1]);
                        }
                    }else {
                        SAToast.makeText(getContext(),"体征数据加载失败").show();
                    }
                    LoadingDialog.cancelDialogForLoading();
                    break;
                case 1://代表刷新获取

                    break;

            }
        }
    }


    private void heart(){//心率
        LimitLine ll1 = new LimitLine(100f, "偏高预警线");
        ll1.setLineColor(Color.parseColor("#D1DDE6"));
        ll1.setTextColor(Color.parseColor("#8FACC8"));
        ll1.setTextSize(12);
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        LimitLine ll2 = new LimitLine(55f, "偏低预警线");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setTextColor(Color.parseColor("#8FACC8"));
        ll2.setTextSize(12);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll2.setLineColor(Color.parseColor("#D1DDE6"));


        LimitLine ll3 = new LimitLine(75f, "");//正常线
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#D1DDE6"));

        LimitLine ll4 = new LimitLine(200f, "");//顶部的线
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#D1DDE6"));
        //偏高预警线
        yAxisLeft.setAxisMaximum(200f);
        //偏低预警线
        yAxisLeft.addLimitLine(ll1);
        yAxisLeft.addLimitLine(ll2);
        yAxisLeft.addLimitLine(ll3);
        yAxisLeft.addLimitLine(ll4);
        yAxisLeft.setAxisMinimum(0);
    }


    private void per(){//血压
        LimitLine ll1 = new LimitLine(140f, "偏高预警线");
        ll1.setLineColor(Color.parseColor("#D1DDE6"));
        ll1.setTextColor(Color.parseColor("#8FACC8"));
        ll1.setTextSize(12);
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        LimitLine ll2 = new LimitLine(90f, "偏低预警线");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setTextColor(Color.parseColor("#8FACC8"));
        ll2.setTextSize(12);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll2.setLineColor(Color.parseColor("#D1DDE6"));


        LimitLine ll3 = new LimitLine(115f, "");//正常线
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#D1DDE6"));

        LimitLine ll4 = new LimitLine(200f, "");//顶部的线
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#D1DDE6"));
        //偏高预警线
        yAxisLeft.setAxisMaximum(200f);
        //偏低预警线
        yAxisLeft.addLimitLine(ll1);
        yAxisLeft.addLimitLine(ll2);
        yAxisLeft.addLimitLine(ll3);
        yAxisLeft.addLimitLine(ll4);
        yAxisLeft.setAxisMinimum(0);
    }
    private void oxy(){
        LimitLine ll1 = new LimitLine(97f, "偏高预警线");
        ll1.setLineColor(Color.parseColor("#D1DDE6"));
        ll1.setTextColor(Color.parseColor("#8FACC8"));
        ll1.setTextSize(12);
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        LimitLine ll2 = new LimitLine(90f, "偏低预警线");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setTextColor(Color.parseColor("#8FACC8"));
        ll2.setTextSize(12);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll2.setLineColor(Color.parseColor("#D1DDE6"));


        LimitLine ll3 = new LimitLine(93f, "");//正常线
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#D1DDE6"));

        LimitLine ll4 = new LimitLine(100f, "");//顶部的线
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#D1DDE6"));
        //偏高预警线
        yAxisLeft.setAxisMaximum(100f);
        //偏低预警线
        yAxisLeft.addLimitLine(ll1);
        yAxisLeft.addLimitLine(ll2);
        yAxisLeft.addLimitLine(ll3);
        yAxisLeft.addLimitLine(ll4);
        yAxisLeft.setAxisMinimum(70);
    }


    private void tan(){
        //血糖
        LimitLine ll1 = new LimitLine(11.10f, "偏高预警线");
        ll1.setLineColor(Color.parseColor("#D1DDE6"));
        ll1.setTextColor(Color.parseColor("#8FACC8"));
        ll1.setTextSize(12);
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        LimitLine ll2 = new LimitLine(3.9f, "偏低预警线");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setTextColor(Color.parseColor("#8FACC8"));
        ll2.setTextSize(12);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll2.setLineColor(Color.parseColor("#D1DDE6"));


        LimitLine ll3 = new LimitLine(7.5f, "");//正常线
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#D1DDE6"));

        LimitLine ll4 = new LimitLine(20.0f, "");//顶部的线
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#D1DDE6"));
        //偏高预警线
        yAxisLeft.setAxisMaximum(20.0f);
        //偏低预警线
        yAxisLeft.addLimitLine(ll1);
        yAxisLeft.addLimitLine(ll2);
        yAxisLeft.addLimitLine(ll3);
        yAxisLeft.addLimitLine(ll4);
        yAxisLeft.setAxisMinimum(0.0f);
    }

    private void nomber()
    {
        LimitLine ll1 = new LimitLine(140f, "偏高预警线");
        ll1.setLineColor(Color.parseColor("#D1DDE6"));
        ll1.setTextColor(Color.parseColor("#8FACC8"));
        ll1.setTextSize(12);
        ll1.setLineWidth(1f);
        ll1.enableDashedLine(10f, 10f, 0f);
        ll1.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_TOP);

        LimitLine ll2 = new LimitLine(60f, "偏低预警线");
        ll2.setLineWidth(1f);
        ll2.enableDashedLine(10f, 10f, 0f);
        ll2.setTextColor(Color.parseColor("#8FACC8"));
        ll2.setTextSize(12);
        ll2.setLabelPosition(LimitLine.LimitLabelPosition.LEFT_BOTTOM);
        ll2.setLineColor(Color.parseColor("#D1DDE6"));


        LimitLine ll3 = new LimitLine(90f, "");
        ll3.setLineWidth(1f);
        ll3.setLineColor(Color.parseColor("#D1DDE6"));

        LimitLine ll4 = new LimitLine(200f, "");
        ll4.setLineWidth(1f);
        ll4.setLineColor(Color.parseColor("#D1DDE6"));
        yAxisLeft.setAxisMaximum(200f);
        //偏高预警线
        yAxisLeft.addLimitLine(ll1);
        //偏低预警线
        yAxisLeft.addLimitLine(ll2);
        yAxisLeft.addLimitLine(ll3);
        yAxisLeft.addLimitLine(ll4);
    }
    public class MyXFormatter implements IAxisValueFormatter {
        List<String> mValues;
        public MyXFormatter(List<String> values) {
            this.mValues = values;
        }
        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            int i = (int) value % mValues.size();
            return mValues.get(i);
        }
    }

    private void list_list(List<Document> list_data,String z)
    {
        list = new ArrayList<>();
        //int type, String sign_time, String number, String zhuantai, String time) {

        int number=0;
        int flage=0;
        System.out.println("***********size************"+list_data.size());
        System.out.println("***********getLong************"+list_data.get(0).getLong("timed"));
        int zon =0 ;
        for(int i =0 ; i < list_data.size();i++)
        {
                               if (list.size() != 0) {
                                   for (int k = 0; k < list.size(); k++) {
                                       if (list.get(k).getSign_time().equals(stampToDate(list_data.get(i).getLong("timed") + "", "yyyy-MM-dd"))) {
                                           flage = 1;
                                           if (z.equals("1"))//标记是血压
                                           {
                                               SignItemBean s2 = new SignItemBean(1, "", list_data.get(i).getDouble("dia_value") + "/" + list_data.get(i).getDouble("sys_value") + "mmHg", list_data.get(i).getInteger("status") + "",
                                                       stampToDate(list_data.get(i).getLong("timed") + "", "HH:mm"));
                                               list.add(k + 1, s2);
                                           } else {
                                               SignItemBean s2 = new SignItemBean(1, "", list_data.get(i).getDouble("value") + "", list_data.get(i).getInteger("status") + "",
                                                       stampToDate(list_data.get(i).getLong("timed") + "", "HH:mm"));
                                               list.add(k + 1, s2);
                                           }
                                       }
                                   }
                               }
                               if (flage == 0) {

                                   SignItemBean s1 = new SignItemBean(0, stampToDate(list_data.get(i).getLong("timed") + "", "yyyy-MM-dd"), "", "", "");
                                   list.add(s1);

                                   if (z.equals("1"))//标记是血压
                                   {
                                       SignItemBean s2 = new SignItemBean(1, "", list_data.get(i).getDouble("dia_value") + "/" + list_data.get(i).getDouble("sys_value") + "mmHg", list_data.get(i).getInteger("status") + "",
                                               stampToDate(list_data.get(i).getLong("timed") + "", "HH:mm"));
                                       list.add(s2);
                                   } else {
                                       SignItemBean s2 = new SignItemBean(1, "", list_data.get(i).getDouble("value") + "", list_data.get(i).getInteger("status") + "",
                                               stampToDate(list_data.get(i).getLong("timed") + "", "HH:mm"));
                                       list.add(s2);
                                   }
                                 }
        }
        System.out.println("***********size1************"+list.size());
        signadapter = new SignFragmentgItemAdapter(getContext(),list);
        sign_recycel.setAdapter(signadapter);
    }
}
