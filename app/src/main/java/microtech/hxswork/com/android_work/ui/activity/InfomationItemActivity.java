package microtech.hxswork.com.android_work.ui.activity;

import android.content.ContentValues;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushShowedResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.adapter.InfomationItemAdapter;
import microtech.hxswork.com.android_work.bean.InfomationItemBean;
import microtech.hxswork.com.android_work.bean.XGBean;
import microtech.hxswork.com.android_work.mysql.system_sql;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;
import xander.elasticity.ElasticityHelper;
import xander.elasticity.ORIENTATION;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.infomation_save_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.list_XG;

/**
 * Created by microtech on 2017/9/14. //消息子模块
 */

public class InfomationItemActivity extends AppCompatActivity implements View.OnClickListener { //BaseFragment<InformationItemPresenterImpl,InformationItemModelmpl>implements InformationItemContract.View {
    LinearLayout infomationitem_back_linear;
    RecyclerView infomationitem_recyclerview;
    InfomationItemAdapter infomationItemAdapter;
    List<InfomationItemBean> list;
    String title="";//服务模块的标题
    String id="";
    String content="";//服务模块的内容
    FrameLayout message_framelayout;
    LinearLayout message_linear;
    system_sql system;
    SQLiteDatabase sq;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infomationitem_fragment);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.__picker_black_41));
        initView();
        system_data();//读取是数据库
    }
    protected void initView() {
        system = new system_sql(this,"system.db",null,1);
        sq = system.getWritableDatabase();
        sq = system.getReadableDatabase();
        message_framelayout = (FrameLayout) findViewById(R.id.message_framelayout);
        message_linear = (LinearLayout) findViewById(R.id.message_linear);
        infomationitem_recyclerview = (RecyclerView) findViewById(R.id.infomationitem_recyclerview);
        infomationitem_back_linear = (LinearLayout) findViewById(R.id.infomationitem_back_linear);
        infomationitem_recyclerview.setLayoutManager(new LinearLayoutManager(this));
        ElasticityHelper.setUpOverScroll(infomationitem_recyclerview, ORIENTATION.VERTICAL);//弹性拉伸
        list = new ArrayList<>();
        infomationitem_back_linear.setOnClickListener(this);

        Cursor cursor  = sq.query("system",null,null,null,null,null,null);//本地数据库查询
        cursor.moveToLast();
        System.out.println("*****数据库表中有多少天数据********"+cursor.getCount());
        while (cursor.moveToPrevious())
        {
            InfomationItemBean bean1 = new InfomationItemBean("", cursor.getString(1),  cursor.getString(2),   cursor.getString(3));
            list.add(bean1);
        }
        if(cursor.getCount() == 0)
        {
            InfomationItemBean bean1 = new InfomationItemBean("", "系统消息","谢谢下载蜂鸟健康APP 你是最好的 最帅的 最有钱的 最美的 最霸气的 Think you!",  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
            list.add(bean1);
        }
        infomationItemAdapter = new InfomationItemAdapter(this,list);
        infomationitem_recyclerview.setAdapter(infomationItemAdapter);
        message_framelayout.setVisibility(View.VISIBLE);
        message_linear.setVisibility(View.GONE);

    }

    private void system_data()
    {
        if(list_XG.size() != 0)
        {
            for (int i = 0; i < list_XG.size(); i++) {
                InfomationItemBean bean2= new InfomationItemBean("", list_XG.get(i).getTitle(), list_XG.get(i).getContent(), "" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                list.add(bean2);
                ContentValues cv = new ContentValues();
                cv.put("title",list_XG.get(i).getTitle());
                cv.put("content",list_XG.get(i).getContent());
                cv.put("time",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()));
                cv.put("url","");
                sq.insert("system",null,cv);
            }
            infomationItemAdapter.notifyItemRangeInserted(0, list_XG.size());
            infomationitem_recyclerview.scrollToPosition(0);
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.infomationitem_back_linear:
                list_XG.clear();
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        list_XG.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        XGPushClickedResult clickedResult = XGPushManager.onActivityStarted(this);
        if(clickedResult != null){
            title = clickedResult.getTitle();
            System.out.println("*******标题*****"+title);
            id = clickedResult.getMsgId() + "";
            System.out.println("*******id*****"+id);
            content = clickedResult.getContent();
            System.out.println("*******内容*****"+content);
            System.out.println("*******内容1111111111*****"+   clickedResult.getCustomContent());//隐身船舱

        }
    }
}
