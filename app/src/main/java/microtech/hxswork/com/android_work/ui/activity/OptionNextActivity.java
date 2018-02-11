package microtech.hxswork.com.android_work.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.OptionNextAdapter;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.OptionNextBean;
import microtech.hxswork.com.android_work.ui.fragment.PersonalOptionFragment;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_name;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.phone;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;

/**
 * Created by microtech on 2017/9/27.
 */

public class OptionNextActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    SwipeRefreshLayout swipeLayout;
    RecyclerView recyclerview;
    ImageView optionnext_back;
    List<OptionNextBean> list;
    OptionNextAdapter adapter;

    OkHttpClientManager ok;
    Handler handler;
    Document document;
    String optionlist_url="https://doc.newmicrotech.cn/otsmobile/app/feedbackList?";
    String our_Data[]={null};
    int next_id=0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_next);
        initview();
    }

    private void initview() {
        ok = new OkHttpClientManager();
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeLayout);
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        optionnext_back= (ImageView) findViewById(R.id.optionnext_back);
        optionnext_back.setOnClickListener(this);
        swipeLayout.setOnRefreshListener(this);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        list =new ArrayList<>();
        handler  =new MyHander();
        thread_noimgae();
    }

    private void thread_noimgae()
    {
        LoadingDialog.showDialogForLoading(this, "上传中..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("user_id",user_id),
                        new OkHttpClientManager.Param("next_start",""),
                        new OkHttpClientManager.Param("item_count", "10")
                };
                try {
                    our_Data[0] = ok.postOneHeadAsString(optionlist_url, device_no, params_home);//反馈意见的提交
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("意见列表数据:" + our_Data[0]);
                Message message =new Message();
                message.what=0;//表示加载
                message.obj=our_Data[0];
                handler.sendMessage(message);
            }
        }).start();
    }
    class MyHander extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what)
            {
                case 0:
                    String data = (String) msg.obj;
                   // document = Document.parse(data);
                    //测试数据
                    OptionNextBean b1 = new OptionNextBean("修改绑定设备后APP体征数据没有及时更新。","2017/11/15 16:48","您好，感谢你的反馈，我们已经关注此问题，会尽快解决，对此给你造成的不便，我们深表歉意。感谢你的理解与配合。","2017/11/15 16:49");
                    OptionNextBean b2 = new OptionNextBean("修改绑定设备后APP体征数据没有及时更新。","2017/11/11 16:48","感谢你的理解与配合。","2017/11/11 16:49");
                    OptionNextBean b3 = new OptionNextBean("心率测试设备无法上传到米可云盒子？网络正常！。","2017/11/11 16:48","","2017/11/11 16:49");
                    list.add(b1);
                    list.add(b2);
                    list.add(b3);
                    adapter = new OptionNextAdapter(OptionNextActivity.this,list);
                    recyclerview.setAdapter(adapter);
                    LoadingDialog.cancelDialogForLoading();
                    break;
                case 1:
                    SAToast.makeText(OptionNextActivity.this,"反馈成功!").show();//刷新操作
                    String data1 = (String) msg.obj;
                    //document = Document.parse(data1);
                    //测试数据
                    OptionNextBean b11 = new OptionNextBean("修改绑定设备后AP。","2017/11/15 16:48","您好，感谢你的反馈，我们已经关注。","2017/11/15 16:49");
                    OptionNextBean b21 = new OptionNextBean("修改绑定设备后AP。","2017/11/11 16:48","配合。","2017/11/11 16:49");
                    OptionNextBean b31 = new OptionNextBean("心率测试设备无法上传到米可云盒子？。","2017/11/11 16:48","","2017/11/11 16:49");
                    list.add(b11);
                    list.add(b21);
                    list.add(b31);
                    adapter.notifyDataSetChanged();
                    if(swipeLayout!=null) {
                        swipeLayout.setRefreshing(false);
                    }
                    break;
            }
        }
    }
    @Override
    public void onClick(View view) {
            finish();
    }

    @Override
    public void onRefresh() {
        swipeLayout.setRefreshing(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ok = new OkHttpClientManager();
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("user_id",user_id),
                        new OkHttpClientManager.Param("next_start",next_id+""),
                        new OkHttpClientManager.Param("item_count", "10")
                };
                System.out.println("user_id*********"+user_id);
                try {
                    our_Data[0] = ok.postOneHeadAsString(optionlist_url,device_no, params_home);//体征数据
                    System.out.println("******home_data********"+our_Data[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Message msg = new Message();
                msg.what = 1;//表示刷新操作
                msg.obj = our_Data[0];
                handler.sendMessage(msg);
            }
        }).start();
    }
}
