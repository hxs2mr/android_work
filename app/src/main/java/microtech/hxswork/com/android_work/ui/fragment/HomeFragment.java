package microtech.hxswork.com.android_work.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import microtech.hxswork.com.android_work.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.tencent.android.tpush.XGPushShowedResult;
import com.youth.banner.Banner;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.InfoRes.HomeInfoRes;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.HomefragmentAdapter;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.HomeBean;
import microtech.hxswork.com.android_work.bean.Home_Bean1;
import microtech.hxswork.com.android_work.bean.NIMBean;
import microtech.hxswork.com.android_work.bean.XGBean;
import microtech.hxswork.com.android_work.contract.HomeContract;
import microtech.hxswork.com.android_work.model.HomeModelImpl;
import microtech.hxswork.com.android_work.presenter.HomePresenterImpl;
import microtech.hxswork.com.android_work.receiver.MessageReceiver;
import microtech.hxswork.com.android_work.ui.activity.CaseActivity;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.InformationActivity;
import microtech.hxswork.com.android_work.update.DownLoadUtils;
import microtech.hxswork.com.android_work.update.DownloadApk;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.basebean.BaseResponse;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;
import microtech.hxswork.com.zxing.ZxingActivity;

import static android.content.Context.MODE_PRIVATE;
import static microtech.hxswork.com.android_work.ui.activity.HomeActivity.incomingMessageObserver1;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_array_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_model;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_time;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userA_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userB_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userC_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.famile_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.list_NIM;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.list_XG;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.reflash;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relation;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_head_image;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.update_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_NIM;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.flage_NIM;
/**
 * Created by HXS on 2017/8/21.主界面数据模块
 */

public class HomeFragment extends BaseFragment<HomePresenterImpl,HomeModelImpl> implements HomeContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @Bind(R.id.head2_linear)

    LinearLayout head2_linear;
    //head2_linear = head2.findViewById(R.id.head2_linear);
    @Bind(R.id.head2_banding_start)
    TextView head2_banding_start;

   // head2_banding_start = head2.findViewById(R.id.head2_banding_start);//开始绑定
    Banner home_banner;
    @Bind(R.id.homefragment_case)
    ImageView homefragment_case;//病例模块

    @Bind(R.id.homefragment_doctor)
    ImageView homefragment_doctor;//医生模块

    @Bind(R.id.homefragment_recyclerview)
    RecyclerView homefragment_recyclerview;

    @Bind(R.id.homefagment_doctor_frame)
    FrameLayout homefagment_doctor_frame;//医生互动模块

    @Bind(R.id.homefagment_case_frame)
    LinearLayout homefagment_case_frame;//病例模块

    @Bind(R.id.homefragment_doctor_linear)
    LinearLayout homefragment_doctor_linear;

    @Bind(R.id.doctor_deail_sex)
    ImageView doctor_deail_sex;

    public static int right_image_flage=0;//右上角的红点xiao
    public static int right_image_flage_NIMM=0;//右上角的红点xiao
    HomeActivity mActivity;
    View head2;
    private MessageReceiver updateListViewReceiver;
    HomefragmentAdapter homefragmentAdapter;
    private static final int REQUEST_QRCODE = 0x01;
    public static String device_no;
    public static String phone;
    String refresh_phone;
    public static  String user_id;
    OkHttpClientManager ok;
    String home_url="https://doc.newmicrotech.cn/otsmobile/app/physical?";//"http://doc.newmicrotech.cn:8080/otsmobile/app/physical?";//当前用户体征数据
    public  static  String bing_url="https://doc.newmicrotech.cn/otsmobile/app/bindBox?";
     String bing_data[]={null};
    final String[] home_data = {null};

    LinearLayout    home_user1_linear;
    LinearLayout    home_user2_linear;
    LinearLayout    home_user3_linear;

    ImageView home_user1;
    ImageView home_user2;
    ImageView home_user3;

    int flageA=0;//用了判断用户是哪一个级别 好设置图
    int flageB=0;
    int flageC=0;

    TextView home_user1_name;
    TextView home_user2_name;
    TextView home_user3_name;
    public  static  int service_flage =0  ;
    private  final  int PERSSION_CARAM = 100;
    String[] new_data;
    List<HomeInfoRes> listhomeInfoRes;//数据封装

    Document document;
    Document document_obj;
    Document document_signsData;

    Document document_sugar;
    Document document_pressure;
    Document document_heart;
    Document document_oxygen;

    Handler handler;
    private int pageNo = 1;
    private int status; // 0  正常加载更多， 1 加载完成 ， 2 加载失败。

    int flagea= 0,flageb = 0,flagec=0;
    public static  int userA=0;
    public static  int userB=0;
    public static  int userC=0;

    public static String textA="";
    public static String textB="";
    public static String textC="";

    public  static String user_nameA="";
    public  static String user_nameB="";
    public  static String user_nameC="";

    public static ArrayList<String> mDistrictBeanArrayList ;//添加关系哦

    PopupWindow bind_pop;
    Document document1;
    Document document2;
    Document document3;
    Document document4;
    String code;
    String data[];
    public static String sign_id="";
    String refresh_id="";
   public static   PopupWindow update_pop;

    String update_url;
    String update_info;
    String update_size;
    String upadte_vierson;
    int flage_a = 0 ;
    @Override
    protected int getLayoutResource() {
        return R.layout.homefragment_activity;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }
    @Override
    protected void initView() {
        updateListViewReceiver = new MessageReceiver();
        StatusBarCompat.setStatusBarColor((Activity) getContext(), ContextCompat.getColor(getContext(), R.color.main_xia));
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.VISIBLE);
        homefragment_recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        System.out.println("***box_userA_flage"+box_userA_flage+"***box_userB_flage"+box_userB_flage+"***box_userC_flage"+box_userC_flage);
        flagea = 0;
        flageb = 0;
        flagec = 0;
        System.out.println("****relationA"+relationA+"****relationB"+relationB+"****relationC"+relationC);
        mDistrictBeanArrayList = new ArrayList<>();
        handler = new MyHander();
        //homefragmentAdapter.setOnLoadMoreListener(this, homefragment_recyclerview);//加载更多
        swipeLayout.setRefreshing(false);
        swipeLayout.setOnRefreshListener(this);
        head2 = View.inflate(mActivity, R.layout.home_head2_activity, null);
        listhomeInfoRes = new ArrayList<>();
        System.out.println("***user******"+user);
        SharedPreferences share = getContext().getSharedPreferences(user+"123", MODE_PRIVATE);
        device_no = share.getString("device_no", "0");
        phone =  share.getString("phone", "0");
        user_id = share.getString("user_id","0");//获取当前用户的user_id

        System.out.println("***device_no========================="+device_no +"****user_id======"+user_id);
        inithead(head2);
            init_user();//判断当前用户是否存在家庭或者盒子中是否绑定了
        System.out.println("***app_vierson========================="+app_vierson());
        sign_id=user_id;
        refresh_phone = phone;
        if(box_id==null)
        {
            head2_linear.setVisibility(View.VISIBLE);
        }else {
            head2_linear.setVisibility(View.GONE);
        }
        new_data =  LoadData();//先进行预加载查看缓存有无上一次保存的数据
        if(new_data[16].equals("0")) {
            //如果没有历史数据开始网络请求获取数据
            thread(sign_id,refresh_phone,0);//网络请求
        }else {
            Home_Bean1 bean1 = new Home_Bean1(new_data[0], new_data[1], new_data[2], new_data[3], new_data[4], new_data[5], new_data[6], new_data[7], new_data[8], new_data[9], new_data[10], new_data[11], new_data[12], new_data[13], new_data[14],new_data[15]);//读取上一次测试的数据
            HomeInfoRes res = new HomeInfoRes(HomeInfoRes.SIGN_DATA, "", bean1);
            HomeInfoRes res1 = new HomeInfoRes(HomeInfoRes.HEALTH_DATA, new_data[17], null);
            HomeInfoRes res2 = new HomeInfoRes(HomeInfoRes.OTHER_DATA, "", null);
            listhomeInfoRes.add(HomeInfoRes.SIGN_DATA, res);
            listhomeInfoRes.add(HomeInfoRes.HEALTH_DATA, res1);
            listhomeInfoRes.add(HomeInfoRes.OTHER_DATA, res2);
            homefragmentAdapter = new HomefragmentAdapter(listhomeInfoRes, getContext(),mActivity);
            homefragmentAdapter.setEnableLoadMore(true);//允许刷新操作
            homefragmentAdapter.addHeaderView(head2);
            homefragment_recyclerview.setAdapter(homefragmentAdapter);
        }
            //ElasticityHelper.setUpOverScroll(homefragment_recyclerview, ORIENTATION.HORIZONTAL);//弹性拉伸
            //ElasticityHelper.setUpOverScroll(homefragment_recyclerview, ORIENTATION.VERTICAL);//弹性拉伸
            head2_banding_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= 23) {
                    //动态获取相机权限
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA},PERSSION_CARAM);
                    }else {
                        startActivityForResult(new Intent(mActivity, ZxingActivity.class),REQUEST_QRCODE);
                    }
                }else {
                    startActivityForResult(new Intent(mActivity, ZxingActivity.class),REQUEST_QRCODE);
                }
            }
        });

        if(load_anim().equals("0")) {
            head2.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.head2_anim));//加载动画
            save_anim();
        }
        homefagment_case_frame.setOnClickListener(this);
        homefragment_doctor_linear.setOnClickListener(this);
        //LoadingDialog.showDialogForLoading(mActivity, "数据加载中..", true);

      /* if(update_flage ==0 ) {//更新模块
            update_thread();
            update_flage = 1;
        }*/
        updateListViewReceiver = new  MessageReceiver();

        if(user_NIM == 0) {
            inint_NIMM();
            user_NIM = 1;
        }
        XGPushManager.setNotifactionCallback(new XGPushNotifactionCallback() {

            @Override
            public void handleNotify(XGNotifaction xGNotifaction) {
                System.out.println("test处理信鸽通知："+xGNotifaction);
                // 获取标签、内容、自定义内容
                right_image_flage= 1;
                String XG_title = xGNotifaction.getTitle();
                String XG_content = xGNotifaction.getContent();
                String XG_customContent = xGNotifaction.getCustomContent();
                XGBean XG = new XGBean(XG_title,XG_content,XG_customContent);
                list_XG.add(XG);
                System.out.println("list_XG_size-------------："+list_XG.size());
                Message msg = new Message();
                msg.what=10;//代表跟新图标的状态
                handler.sendMessage(msg);
                System.out.println("test处理信鸽通知*************："+XG_title+XG_content+XG_customContent);
                // 其它的处理
                // 如果还要弹出通知，可直接调用以下代码或自己创建Notifaction，否则，本通知将不会弹出在通知栏中。
                xGNotifaction.doNotify();
                //initCustomPushNotificationBuilder(getBaseContext());
            }
        });
        StatusCode status = NIMClient.getStatus();

        System.out.println("********1在线状态********"+status);
        if(right_image_flage == 1||right_image_flage_NIMM == 1)
        {
            doctor_deail_sex.setVisibility(View.VISIBLE);
        }else {
            doctor_deail_sex.setVisibility(View.INVISIBLE);
        }
    }

    private void init_user() {
        System.out.println("***box_array_flage***"+box_array_flage);
        System.out.println("***box_userA_flage***"+box_userA_flage);
        System.out.println("***box_userB_flage***"+box_userB_flage);
        System.out.println("***box_userC_flage***"+box_userC_flage);
        if (box_array_flage == 0 )//当前用户没有家庭或者没有绑定盒子
        {
            home_user2_linear.setVisibility(View.GONE);
            home_user3_linear.setVisibility(View.GONE);
            home_user1_linear.setVisibility(View.VISIBLE);
            home_user1_name.setText("自己");
            mDistrictBeanArrayList.add("自己");
            userA = 1;
            userB = 0;
            userC = 0;
            user_idA  = user_id;
            phoneA  = phone;
        }else {
        if(box_userA_flage!= 0)//盒子中A用户存在不
        {
            switch (relationA)//查看与当前用户的关系
            {
                case 1:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user1);
                        mDistrictBeanArrayList.add("自己");
                        userA = 1;
                    }else {
                        home_user1_name.setText("父亲");
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setImageResource(R.mipmap.home_user1);
                        mDistrictBeanArrayList.add("父亲");
                        home_user1.setAlpha(0.5f);
                        userA = 0;
                    }
                    break;
                case 2:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user2);
                        mDistrictBeanArrayList.add("自己");
                        userA = 1;
                    }else {
                        home_user1_name.setText("母亲");
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setImageResource(R.mipmap.home_user2);
                        home_user1.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("母亲");
                        userA = 0;
                    }
                    break;
                case 3:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user3);
                        mDistrictBeanArrayList.add("自己");
                        userA = 1;
                    }else {
                        home_user1_name.setText("祖父");
                        home_user1.setImageResource(R.mipmap.home_user3);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("祖父");
                        userA = 0;
                    }
                    break;
                case 4:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user4);
                        mDistrictBeanArrayList.add("自己");
                        userA = 1;
                    }else {
                        home_user1_name.setText("祖母");
                        home_user1.setImageResource(R.mipmap.home_user4);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("祖母");
                        userA = 0;
                    }
                    break;
                case 5:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user3);
                        mDistrictBeanArrayList.add("自己");
                        userA = 1;
                    }else {
                        home_user1_name.setText("外祖父");
                        home_user1.setImageResource(R.mipmap.home_user3);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("外祖父");
                        userA = 0;
                    }
                    break;
                case 6:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user4);
                        userA = 1;
                    }else {
                        home_user1_name.setText("外祖母");
                        home_user1.setImageResource(R.mipmap.home_user4);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("外祖母");
                        userA = 0;
                    }
                    break;
                case 7:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user7);
                        userA = 1;
                    }else {
                        home_user1_name.setText("儿子");
                        mDistrictBeanArrayList.add("儿子");
                        home_user1.setImageResource(R.mipmap.home_user7);
                        home_user1.setAlpha(0.5f);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        userA = 0;
                    }
                    break;
                case 8:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user8);
                        userA = 1;
                    }else {
                        home_user1_name.setText("女儿");
                        home_user1.setImageResource(R.mipmap.home_user8);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("女儿");
                        userA = 0;
                    }
                    break;
                case 9:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user1);
                        userA = 1;
                    }else {
                        home_user1_name.setText("丈夫");
                        home_user1.setImageResource(R.mipmap.home_user1);
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        mDistrictBeanArrayList.add("丈夫");
                        home_user1.setAlpha(0.5f);
                        userA = 0;
                    }
                    break;
                case 10:
                    if (relationA == relation)//判断是不是当前登录的用户
                    {
                        home_user1_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user1.setImageResource(R.mipmap.home_user2);
                        userA = 1;
                    }else {
                        home_user1_name.setText("妻子");
                        mDistrictBeanArrayList.add("妻子");
                        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user1.setImageResource(R.mipmap.home_user2);
                        home_user1.setAlpha(0.5f);
                        userA = 0;
                    }
                    break;
                default:
                    home_user1_name.setText("其他");
                    mDistrictBeanArrayList.add("其他");
                    home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
                    home_user1.setImageResource(R.mipmap.home_user7);
                    home_user1.setAlpha(0.5f);
                    userA = 0;
                    break;
            }
            textA=home_user1_name.getText().toString();
        }else
        {
            home_user1_linear.setVisibility(View.GONE);
        }

        if(box_userB_flage!= 0)//盒子中B用户存在不
        {
            switch (relationB)//查看与当前用户的关系
            {
                case 1:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user1);
                        userB = 1;
                    }else {
                        home_user2_name.setText("父亲");
                        mDistrictBeanArrayList.add("父亲");
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setImageResource(R.mipmap.home_user1);
                        home_user2.setAlpha(0.5f);
                        userB = 0;
                    }
                    break;
                case 2:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user2);
                        mDistrictBeanArrayList.add("自己");

                        userB = 1;
                    }else {
                        home_user2_name.setText("母亲");
                        userB = 0;
                        mDistrictBeanArrayList.add("母亲");
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setImageResource(R.mipmap.home_user2);
                        home_user2.setAlpha(0.5f);
                    }
                    break;
                case 3:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user3);
                        mDistrictBeanArrayList.add("自己");
                        userB = 1;
                    }else {
                        home_user2_name.setText("祖父");
                        home_user2.setImageResource(R.mipmap.home_user3);
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("祖父");
                        userB = 0;
                    }
                    break;
                case 4:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user4);
                        mDistrictBeanArrayList.add("mipmap");
                        userB = 1;
                    }else {
                        home_user2_name.setText("祖母");
                        home_user2.setImageResource(R.mipmap.home_user4);
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setAlpha(0.5f);
                        mDistrictBeanArrayList.add("祖母");
                        userB = 0;
                    }
                    break;
                case 5:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user3);
                        userB = 1;
                    }else {
                        home_user2_name.setText("外祖父");
                        home_user2.setImageResource(R.mipmap.home_user3);
                        home_user2.setAlpha(0.5f);
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        mDistrictBeanArrayList.add("外祖父");
                        userB = 0;
                    }
                    break;
                case 6:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user4);
                        userB = 1;
                    }else {
                        home_user2_name.setText("外祖母");
                        home_user2.setImageResource(R.mipmap.home_user4);
                        home_user2.setAlpha(0.5f);
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        mDistrictBeanArrayList.add("外祖母");
                        userB = 0;
                    }
                    break;
                case 7:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user7);
                        userB = 1;
                    }else {
                        home_user2_name.setText("儿子");
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        mDistrictBeanArrayList.add("儿子");
                        home_user2.setImageResource(R.mipmap.home_user7);
                        home_user2.setAlpha(0.5f);
                        userB = 0;
                    }
                    break;
                case 8:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user8);
                        userB = 1;
                    }else {
                        home_user2_name.setText("女儿");
                        mDistrictBeanArrayList.add("女儿");
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setImageResource(R.mipmap.home_user8);
                        home_user2.setAlpha(0.5f);
                        userB = 0;
                    }
                    break;
                case 9:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user1);
                        userB = 1;
                    }else {
                        home_user2_name.setText("丈夫");
                        mDistrictBeanArrayList.add("丈夫");
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setImageResource(R.mipmap.home_user1);
                        home_user2.setAlpha(0.5f);
                        userB = 0;
                    }
                    break;
                case 10:
                    if (relationB == relation)//判断是不是当前登录的用户
                    {
                        home_user2_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user2.setImageResource(R.mipmap.home_user2);

                        userB = 1;
                    }else {
                        home_user2_name.setText("妻子");
                        mDistrictBeanArrayList.add("妻子");
                        home_user2.setImageResource(R.mipmap.home_user2);
                        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user2.setAlpha(0.5f);
                        userB = 0;
                    }
                    break;
                default:
                    home_user2_name.setText("其他");
                    mDistrictBeanArrayList.add("其他");
                    home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
                    home_user2.setImageResource(R.mipmap.home_user7);
                    home_user2.setAlpha(0.5f);
                    userB = 0;
                    break;

            }
            textB=home_user2_name.getText().toString();
        }else {
            home_user2_linear.setVisibility(View.GONE);
        }
        if(box_userC_flage!= 0)////盒子中C用户存在不
        {
            switch (relationC)//查看与当前用户的关系
            {
                case 1:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user1);
                        userC=1;
                    }else {
                        home_user3_name.setText("父亲");
                        mDistrictBeanArrayList.add("父亲");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setImageResource(R.mipmap.home_user1);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 2:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user2);
                        userC=1;
                    }else {
                        home_user3_name.setText("母亲");
                        mDistrictBeanArrayList.add("母亲");
                        home_user3.setImageResource(R.mipmap.home_user2);
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 3:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user3);
                        userC=1;
                    }else {
                        home_user3_name.setText("祖父");
                        mDistrictBeanArrayList.add("祖父");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setImageResource(R.mipmap.home_user3);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 4:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user4);
                        userC=1;
                    }else {
                        home_user3_name.setText("祖母");
                        mDistrictBeanArrayList.add("祖母");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setImageResource(R.mipmap.home_user4);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 5:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user3);
                        userC=1;
                    }else {
                        home_user3_name.setText("外祖父");
                        mDistrictBeanArrayList.add("外祖父");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setImageResource(R.mipmap.home_user3);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 6:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user4);
                        userC=1;
                    }else {
                        home_user3_name.setText("外祖母");
                        mDistrictBeanArrayList.add("外祖母");
                        home_user3.setImageResource(R.mipmap.home_user4);
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 7:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user7);
                        userC=1;
                    }else {
                        home_user3_name.setText("儿子");
                        mDistrictBeanArrayList.add("儿子");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setImageResource(R.mipmap.home_user7);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 8:
                    if (relationC== relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user8);
                        userC=1;
                    }else {
                        home_user3_name.setText("女儿");
                        mDistrictBeanArrayList.add("女儿");
                        home_user3.setImageResource(R.mipmap.home_user8);
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 9:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user1);
                        userC=1;
                    }else {
                        home_user3_name.setText("丈夫");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        mDistrictBeanArrayList.add("丈夫");
                        home_user3.setImageResource(R.mipmap.home_user1);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                case 10:
                    if (relationC == relation)//判断是不是当前登录的用户
                    {
                        home_user3_name.setText("自己");
                        mDistrictBeanArrayList.add("自己");
                        home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                        home_user3.setImageResource(R.mipmap.home_user2);
                        userC=1;
                    }else {
                        home_user3_name.setText("妻子");
                        mDistrictBeanArrayList.add("妻子");
                        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                        home_user3.setImageResource(R.mipmap.home_user2);
                        home_user3.setAlpha(0.5f);
                        userC=0;
                    }
                    break;
                default:
                    home_user3_name.setText("其他");
                    mDistrictBeanArrayList.add("其他");
                    home_user3.setImageResource(R.mipmap.home_user7);
                    home_user3.setAlpha(0.5f);
                    home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
                    userC=0;
                    break;
            }
            textC=home_user3_name.getText().toString();
        }else {
            home_user3_linear.setVisibility(View.GONE);
        }

        }
    }

    private void inithead(View head2) {

        home_user1_linear= head2.findViewById(R.id.home_user1_linear);
        home_user2_linear= head2.findViewById(R.id.home_user2_linear);
        home_user3_linear= head2.findViewById(R.id.home_user3_linear);

        home_user1 = head2.findViewById(R.id.home_user1);
        home_user2 = head2.findViewById(R.id.home_user2);
        home_user3 = head2.findViewById(R.id.home_user3);

        home_user1_name = head2.findViewById(R.id.home_user1_name);
        home_user2_name = head2.findViewById(R.id.home_user2_name);
        home_user3_name = head2.findViewById(R.id.home_user3_name);

        home_user1_linear.setOnClickListener(this);
        home_user2_linear.setOnClickListener(this);
        home_user3_linear.setOnClickListener(this);

    }

    @Override
    public void returnToView(BaseResponse<HomeBean> response) {

    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.homefragment_doctor_linear:
                mActivity.startActivity(InformationActivity.class);
                    doctor_deail_sex.setVisibility(View.INVISIBLE);
                right_image_flage_NIMM  = 0 ;
                right_image_flage =0 ;
                NIMClient.getService(MsgServiceObserve.class)
                        .observeReceiveMessage(incomingMessageObserver1, false);
                //mActivity.pushFragment(new InformationActivity(),true);
              /*  Intent intent = new Intent(mActivity, MessageListActivity.class);
                mActivity.startActivity(intent);*/
                break;
            case R.id.homefagment_case_frame:
                Intent intent1 = new Intent(mActivity, CaseActivity.class);
                mActivity.startActivity(intent1);
                break;
            case R.id.home_user1_linear://代表家人1
                if(flagea == 0) {
                    userA = 1;
                    userB = 0;
                    userC = 0;
                    initheadimgae();
                    //home_user1.setImageResource(R.mipmap.home_user1);
                    home_user1.setAlpha(1.0f);
                    home_user1_name.setTextColor(Color.parseColor("#56E9FF"));
                    home_user1_linear.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.head2_linear_anim));//加载动画
                    sign_id = user_idA;
                    refresh_phone = phoneA;
                    thread(user_idA, phoneA, 5);//网络请求 5//代表的是点击事件
                    SaveData_anim();
                    flagea = 1;
                    flageb = 0;
                    flagec = 0;
                    home_user2_linear.clearAnimation();
                    home_user3_linear.clearAnimation();
                }
                break;
            case R.id.home_user2_linear://永远是代表当前用户
                if(flageb == 0) {
                    userA = 0;
                    userB = 1;
                    userC = 0;
                    initheadimgae();
                    //home_user2.setImageResource(R.mipmap.home_user2);
                    home_user2.setAlpha(1.0f);
                    home_user2_name.setTextColor(Color.parseColor("#56E9FF"));
                    home_user2_linear.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.head2_linear_anim));//加载动画
                    sign_id = user_idB;
                    refresh_phone = phoneB;
                    thread(user_idB, phoneB, 5);//网络请求

                    SaveData_anim();
                    flagea = 0;
                    flageb = 1;
                    flagec = 0;
                    home_user3_linear.clearAnimation();
                    home_user1_linear.clearAnimation();
                }
                //局部刷新
                break;
            case R.id.home_user3_linear://代表家人3
                if(flagec == 0)
                {
                userA = 0;
                userB = 0;
                userC = 1;
                initheadimgae();
                //home_user3.setImageResource(R.mipmap.home_user3);
                home_user3.setAlpha(1.0f);
                home_user3_name.setTextColor(Color.parseColor("#56E9FF"));
                home_user3_linear.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.head2_linear_anim));//加载动画
                sign_id=user_idC;
                refresh_phone = phoneC;
                thread(user_idC,phoneC,5);//网络请求
                SaveData_anim();
                //局部刷新
                    flagea = 0;
                    flageb = 0;
                    flagec = 1;
                    home_user2_linear.clearAnimation();
                    home_user1_linear.clearAnimation();
                }
                break;

        }
    }

    private void initheadimgae() {

   /*     home_user1.setImageResource(R.mipmap.home_user1_off);
        home_user2.setImageResource(R.mipmap.home_user2_off);
        home_user3.setImageResource(R.mipmap.home_user3_off);*/

        home_user1.setAlpha(0.3f);
        home_user2.setAlpha(0.3f);
        home_user3.setAlpha(0.3f);

        home_user1_name.setTextColor(Color.parseColor("#8FACC8"));
        home_user2_name.setTextColor(Color.parseColor("#8FACC8"));
        home_user3_name.setTextColor(Color.parseColor("#8FACC8"));
    }

    private void toast(final String msg) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_QRCODE:
                if (resultCode == Activity.RESULT_OK) {
                    //扫描后的业务逻辑
                    code = data.getStringExtra("SCAN_RESULT");
                    if (code.contains("http") || code.contains("https")) {
                        //打开链接
                        /*Intent intent = new Intent(this, AdBrowserActivity.class);
                        intent.putExtra(AdBrowserActivity.KEY_URL, code);
                        startActivity(intent);*/
                        Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                       // homefragmentAdapter.notifyItemChanged(0);
                    }
                    else {
                        //盒子的绑定
                        thread_binddevice(user_id);
                    }
                }else if(requestCode ==Activity.RESULT_FIRST_USER)
                {
                    code =data.getStringExtra("EDIT_SN");
                    System.out.println("code***********"+code);
                    if(!code.equals("1"))
                    {
                        thread_binddevice(user_id);
                    }
                }
                else if(resultCode == 300){
                    //从本地相册扫描后的业务逻辑
                    code = data.getStringExtra("LOCAL_PHOTO_RESULT");
                    Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                    //添加网络请求
                }
                break;
        }
    }

    private void thread_binddevice(final String user_id) {
        //绑定盒子线程
        LoadingDialog.showDialogForLoading(mActivity, "正在绑定..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("sn", code.trim()),
                        new OkHttpClientManager.Param("model", ""),
                        new OkHttpClientManager.Param("name", ""),
                        new OkHttpClientManager.Param("user_id", user_id),
                        new OkHttpClientManager.Param("type", "0")
                };
                try {

                    System.out.println("***sn ***user_id***:" +  code.trim()+"    "+user_id+"  "+device_no);
                    bing_data[0] = ok.postOneHeadAsString(bing_url, device_no, params_home);//体征数据
                    System.out.println("绑定盒子返回的数据:" + bing_data[0]);
                    document = Document.parse(bing_data[0]);//解析主界面绑定的盒子数据
                    if(document.getInteger("code")==205)
                    {
                        Message message= new Message();
                        message.what = document.getInteger("code");
                        handler.sendMessage(message);
                        LoadingDialog.cancelDialogForLoading();
                        return;
                    }
                    if(document.getInteger("code")==202)
                    {
                        toast("该盒子信息不存在");
                        LoadingDialog.cancelDialogForLoading();
                        return;
                    }
                    else if(document.getInteger("code")==200)
                    {
                        document1 =  document.get("obj",Document.class);//obj目录极数据
                        document2 = document1.get("boxInfo",Document.class);
                        document3 = document2.get("keys",Document.class);

                        famile_id = document1.getString("family_id");
                        box_model = document1.getString("model");
                        box_time = document2.getLong("times")+"";
                        box_id  = document2.getString("box_id");
                        init_user_bind();
                        String a[] = new String[3];
                        a[0]=code;
                        a[1]=box_model;
                        a[2]="1";//标识当前数是也绑定设备的状态
                        Message message= new Message();
                        message.obj = a;
                        message.what = document.getInteger("code");
                        handler.sendMessage(message);
                    }else {
                        toast("操作异常");
                        LoadingDialog.cancelDialogForLoading();
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    public void save_anim()
    {
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences("anim_flage", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("anim_flage", "flase");         //根据键值对添加数据
        edit.commit();  //保存数据信息
    }

    public String load_anim()
    {
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences("anim_flage", MODE_PRIVATE);
        String anim_flage=  share.getString("anim_flage","0");
        return  anim_flage;
    }

    public void SaveData_anim() {
        //更新动画状态并更新数据
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences("anim", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("anim", "0");         //根据键值对添加数据
        edit.commit();//保存数据信息
    }

    public void SaveData(String[] a) {
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences(sign_id+"home_fragment", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("xueya_1", a[0]);         //根据键值对添加数据
        edit.putString("xueya_2",a[1]);
        edit.putString("xueya_z",a[2]);
        edit.putString("xueya_time",a[3]);

        edit.putString("xinlu", a[4]);         //根据键值对添加数据
        edit.putString("xinlu_z",a[5]);
        edit.putString("xinlu_time",a[6]);

        edit.putString("xueyan", a[7]);         //根据键值对添加数据
        edit.putString("xueyan_z",a[8]);
        edit.putString("xueyan_time",a[9]);

        edit.putString("xuetan", a[10]);         //根据键值对添加数据
        edit.putString("xuetan_z",a[11]);
        edit.putString("xuetan_time",a[12]);

        edit.putString("tiwen", a[13]);         //根据键值对添加数据
        edit.putString("tiwen_z",a[14]);
        edit.putString("tiwen_time",a[15]);
        edit.putString("flage", a[16]);
        edit.putString("sys", a[17]);
        edit.commit();  //保存数据信息

    }


    public String[] LoadData() {
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences(sign_id+"home_fragment", MODE_PRIVATE);
        String a[] = new String[18];

        a[0] = share.getString("xueya_1", "0");
        a[1] =  share.getString("xueya_2", "0");
        a[2] =  share.getString("xueya_z", "0");
        a[3] = share.getString("xueya_time", "0");

        a[4] =  share.getString("xinlu", "0");
        a[5]  = share.getString("xinlu_z", "0");
        a[6] = share.getString("xinlu_time", "0");

        a[7] = share.getString("xueyan", "0");
        a[8] =  share.getString("xueyan_z", "0");
        a[9] = share.getString("xueyan_time", "0");

        a[10] =  share.getString("xuetan", "0");
        a[11] =  share.getString("xuetan_z", "0");
        a[12] =  share.getString("xuetan_time", "0");

        a[13] =  share.getString("tiwen", "0");
        a[14] =  share.getString("tiwen_z", "0");
        a[15] =  share.getString("tiwen_time", "0");

        a[16] =  share.getString("flage", "0");
        a[17] =  share.getString("sys", "0");
        return a;
    }
/*
    @Override
    public void onRefresh() {
        homefragmentAdapter.setEnableLoadMore(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pageNo = 1;
                homefragmentAdapter.setNewData(listhomeInfoRes);
                if(swipeLayout!=null) {
                    swipeLayout.setRefreshing(false);
                }
                homefragmentAdapter.setEnableLoadMore(true);
            }
        }, 200);
    }*/
    @Override
    public void onRefresh() {
        SaveData_anim();
        if(swipeLayout!=null) {
            swipeLayout.setRefreshing(true);
        }
        homefragmentAdapter.setEnableLoadMore(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                ok = new OkHttpClientManager();
                homefragmentAdapter.setEnableLoadMore(true);
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("user_id", sign_id),
                        new OkHttpClientManager.Param("time_str",""+getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("phone",  refresh_phone),
                        new OkHttpClientManager.Param("type", "")
                };

                System.out.println("user_id*********"+user_id);
                try {
                    home_data[0] = ok.postOneHeadAsString(home_url,device_no, params_home);//体征数据
                    System.out.println("******home_data********"+home_data[0]);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Message msg = new Message();
                msg.what = 1;
                msg.obj = home_data[0];
                handler.sendMessage(msg);
            }
        }).start();
    }

public void thread(final String user_id , final String phone, final int i) {
//数据加载
    LoadingDialog.showDialogForLoading(mActivity, "获取数据中..", true);
    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                ok = new OkHttpClientManager();

                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("user_id", user_id),
                        new OkHttpClientManager.Param("time_str",""+getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("phone", phone),
                        new OkHttpClientManager.Param("type", "")
                };

                System.out.println("user_id** phone*******"+user_id+"   "+phone) ;
                home_data[0] = ok.postOneHeadAsString(home_url,device_no, params_home);//体征数据
                System.out.println(device_no +  "    " + phone +"    "+user_id);
                LoadingDialog.cancelDialogForLoading();
                System.out.println("数据加载成功!" + home_data[0]);
                if(i ==5)
                {
                    Message msg = new Message();
                    msg.what = 2;//点击事件
                    msg.obj = home_data[0];
                    handler.sendMessage(msg);
                }else {
                    Message msg = new Message();
                    msg.what = 0;
                    msg.obj = home_data[0];
                    handler.sendMessage(msg);
                }

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
            case 9:
                flage_a = 0;
                flage_NIM++;
                save_NIM_numvbers();
                right_image_flage_NIMM = 1;
                if (doctor_deail_sex != null) {
                    if(doctor_deail_sex.getVisibility()==View.INVISIBLE) {
                        doctor_deail_sex.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 10:
                right_image_flage = 1;
                if (doctor_deail_sex != null) {
                    if(doctor_deail_sex.getVisibility()==View.INVISIBLE) {
                        doctor_deail_sex.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 1://刷新操作
                String json = (String) msg.obj;
                if((json.charAt(0)+"").equals("<"))
                {
                    toast("服务器异常");
                    LoadingDialog.cancelDialogForLoading();

                    if(swipeLayout!=null) {
                        swipeLayout.setRefreshing(false);
                    }
                    //**********添加过渡动画
                    //************
                    return;
                }

                document = Document.parse(json);//解析主界面刷新的体征数据
                document_obj = document.get("obj",Document.class);
                if(document_obj !=null) {
                document_signsData = (document.get("obj",Document.class)).get("signsData",Document.class);

                document_heart = document_signsData.get("heart",Document.class);//心率的数据

                document_oxygen = document_signsData.get("oxygen",Document.class);//血氧的数据

                document_pressure = document_signsData.get("pressure",Document.class);//血压的数据

                document_sugar = document_signsData.get("sugar",Document.class);//血糖的数据

                if(document_obj.getString("analysis")==null)
                {
                    //3代表为空
                    Home_Bean1 bean4 = new Home_Bean1("","","3","", "","3","","","3","","","3","","","3","");
                    homefragmentAdapter.getData().get(0).setHome_bean1(bean4);
                    homefragmentAdapter.getData().get(1).setHealthBeen("绑定设备才有签约医生为您定制化健康分析");
                    homefragmentAdapter.notifyItemChanged(1);
                    homefragmentAdapter.notifyItemChanged(2);
                }else {
                        inint_sign_data(1);//体征数据的初始化
                        SaveData(data);//保存第一次进来之后的数据
                }
                }else {
                    SAToast.makeText(getContext(),"操作异常").show();
                }
                //**********
                //************
                if(swipeLayout!=null) {
                    swipeLayout.setRefreshing(false);
                }
                break;

            case 0:
                String refresh = (String) msg.obj;
                if((refresh.charAt(0)+"").equals("<"))
                {
                    toast("服务器异常");
                    LoadingDialog.cancelDialogForLoading();

                    //**********添加过度界面
                    //************
                    return;
                }

                    document = Document.parse(refresh);//解析主界面刷新的体征数据
                    document_obj = document.get("obj", Document.class);
                if(document_obj !=null) {
                    document_signsData = (document.get("obj", Document.class)).get("signsData", Document.class);

                    document_heart = document_signsData.get("heart", Document.class);//心率的数据

                    document_oxygen = document_signsData.get("oxygen", Document.class);//血氧的数据

                    document_pressure = document_signsData.get("pressure", Document.class);//血压的数据

                    document_sugar = document_signsData.get("sugar", Document.class);//血糖的数据

                    if (document_obj.getString("analysis") == null) {
                        //3代表为空
                        data = new String[18];
                        data[0] = "";
                        data[1] = "";
                        data[2] = "3";
                        data[3] = "";
                        data[4] = "";
                        data[5] = "3";
                        data[6] = "";
                        data[7] = "";
                        data[8] = "3";
                        data[9] = "";
                        data[10] = "";
                        data[11] = "3";
                        data[12] = "";
                        data[13] = "";
                        data[14] = "3";
                        data[15] = "";
                        data[16] = "1";
                        Home_Bean1 bean4 = new Home_Bean1(data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15]);
                        HomeInfoRes res11 = new HomeInfoRes(HomeInfoRes.SIGN_DATA, "", bean4);
                        HomeInfoRes res22 = new HomeInfoRes(HomeInfoRes.HEALTH_DATA, "绑定设备才有签约医生为您定制化健康分析", null);
                        HomeInfoRes res33 = new HomeInfoRes(HomeInfoRes.OTHER_DATA, "", null);
                        listhomeInfoRes.add(HomeInfoRes.SIGN_DATA, res11);
                        listhomeInfoRes.add(HomeInfoRes.HEALTH_DATA, res22);
                        listhomeInfoRes.add(HomeInfoRes.OTHER_DATA, res33);
                        homefragmentAdapter = new HomefragmentAdapter(listhomeInfoRes, getContext(), mActivity);
                        homefragmentAdapter.setEnableLoadMore(true);//允许刷新操作
                        homefragmentAdapter.addHeaderView(head2);
                        if(homefragment_recyclerview!=null) {
                            homefragment_recyclerview.setAdapter(homefragmentAdapter);
                        }
                        SaveData(data);//保存第一次进来之后的数据*/
                    } else {
                        inint_sign_data(0);//体征数据的初始化
                        SaveData(data);//保存第一次进来之后的数据
                    }
                }else {
                    SAToast.makeText(getContext(),"操作异常").show();
                }
                //**********
                //************
                break;
            case 205:
                bind_file_pop();
                break;
            case 2://点击事件
                String refresh1 = (String) msg.obj;
                if((refresh1.charAt(0)+"").equals("<"))
                {
                    toast("服务器异常");
                    LoadingDialog.cancelDialogForLoading();
                    //**********添加过度界面
                    //************
                    return;
                }

                document = Document.parse(refresh1);//解析主界面刷新的体征数据

                document_obj = document.get("obj",Document.class);
                if(document_obj !=null) {

                    document_signsData = (document.get("obj", Document.class)).get("signsData", Document.class);

                    document_heart = document_signsData.get("heart", Document.class);//心率的数据

                    document_oxygen = document_signsData.get("oxygen", Document.class);//血氧的数据

                    document_pressure = document_signsData.get("pressure", Document.class);//血压的数据

                    document_sugar = document_signsData.get("sugar", Document.class);//血糖的数据
                    if (document_obj.getString("analysis") == null) {
                        //3代表为空
                        Home_Bean1 bean4 = new Home_Bean1("", "", "3", "", "", "3", "", "", "3", "", "", "3", "", "", "3", "");
                        homefragmentAdapter.getData().get(0).setHome_bean1(bean4);
                        homefragmentAdapter.getData().get(1).setHealthBeen("绑定设备才有签约医生为您定制化健康分析");
                        homefragmentAdapter.notifyItemChanged(1);
                        homefragmentAdapter.notifyItemChanged(2);
                    } else {
                        inint_sign_data(1);//体征数据的初始化
                    }
                }else {
                    SAToast.makeText(getContext(),"操作异常").show();
                }
                break;
            case 200:
                String a[] = (String[]) msg.obj;
                head2_linear.setVisibility(View.GONE);
                //    ***********
                Bundle bundle = new Bundle();
                bundle.putStringArray("data",a);
                LoadingDialog.cancelDialogForLoading();
                mActivity.pushFragment(new PersonalMydeviceFragment(),true,bundle);
                break;
            case 11://表示版本有更新
                dialog_photo(mActivity);//
                break;
            case 12://表示版本没有更新
                break;
        }
    }
}
    private void inint_sign_data(int i) {
        data = new String[18];
        if (document_pressure == null)
        {
            data[0] ="";
            data[1] ="";
            data[2] ="3";
            data[3] ="";
        }else {
            data[0] =document_pressure.getDouble("dia_value")+"";
            data[1] =document_pressure.getDouble("sys_value")+"";
            data[2] =document_pressure.getInteger("status")+"";
            data[3] = String.valueOf(document_pressure.getLong("times"));

        }
        if (document_heart == null)
        {
            data[4] ="";
            data[5] ="3";
            data[6] ="";
        }else {
            data[4] =document_heart.getDouble("value")+"";
            data[5] =document_heart.getInteger("status")+"";
            data[6] =String.valueOf(document_heart.getLong("times"));
        }
        if (document_oxygen == null)
        {
            data[7] ="";
            data[8] ="3";
            data[9] ="";
        }else {
            data[7] =document_oxygen.getDouble("value")+"";
            data[8] =document_oxygen.getInteger("status")+"";
            data[9] =String.valueOf(document_oxygen.getLong("times"));
        }
        if (document_sugar == null)
        {
            data[10] ="";
            data[11] ="3";
            data[12] ="";
        }else {
            data[10] =document_sugar.getDouble("value")+"";
            data[11] =document_sugar.getInteger("status")+"";
            data[12] =String.valueOf(document_sugar.getLong("times"));
        }

        //体温模块   没有数据过来
        data[13] ="";
        data[14] ="3";
        data[15] ="";
        data[16] ="1";//用了标识是否又历史数据
        data[17] =document_obj.getString("analysis");
        Home_Bean1 bean4 = new Home_Bean1(data[0],data[1],data[2],data[3],data[4],data[5],data[6],data[7],data[8],data[9],data[10],data[11],data[12],data[13],data[14],data[15]);

        if(i == 0)
        {
            HomeInfoRes res11 = new HomeInfoRes(HomeInfoRes.SIGN_DATA, "", bean4);
            HomeInfoRes res22 = new HomeInfoRes(HomeInfoRes.HEALTH_DATA, document_obj.getString("analysis"), null);
            HomeInfoRes res33 = new HomeInfoRes(HomeInfoRes.OTHER_DATA, "", null);
            listhomeInfoRes.add(HomeInfoRes.SIGN_DATA, res11);
            listhomeInfoRes.add(HomeInfoRes.HEALTH_DATA, res22);
            listhomeInfoRes.add(HomeInfoRes.OTHER_DATA, res33);
            homefragmentAdapter = new HomefragmentAdapter(listhomeInfoRes, getContext(),mActivity);
            homefragmentAdapter.setEnableLoadMore(true);//允许刷新操作
            homefragmentAdapter.addHeaderView(head2);
            homefragment_recyclerview.setAdapter(homefragmentAdapter);
        }else {
            homefragmentAdapter.getData().get(0).setHome_bean1(bean4);
            homefragmentAdapter.getData().get(1).setHealthBeen(document_obj.getString("analysis"));
            homefragmentAdapter.notifyItemChanged(1);
            homefragmentAdapter.notifyItemChanged(2);
        }
    }


    private void bind_file_pop() {
        final View view = LayoutInflater.from(mActivity).inflate(R.layout.bind_file, null);
        LinearLayout bind_linear_back = view.findViewById(R.id.bind_linear_back);
        TextView textView = view.findViewById(R.id.bind_try);
        bind_pop = new PopupWindow(view);
        bind_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        bind_pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        bind_pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha=0.5f;
        mActivity.getWindow().setAttributes(lp);
        bind_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        bind_pop.setOutsideTouchable(true);
        bind_pop.setFocusable(true);
        bind_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        bind_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        bind_linear_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind_pop.dismiss();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind_pop.dismiss();
                startActivityForResult(new Intent(mActivity, ZxingActivity.class),REQUEST_QRCODE);
            }
        });
    }
    private void init_user_bind() {
        if (document3.get("UserA")!= null &&!document3.get("UserA").equals(""))//判断UserA用户是否存在
        {
            System.out.println("*****netx2*****");
            box_userA_flage = 1;
            document4 =  document3.get("UserA",Document.class);//取出UserA中的值
            relationA = document4.getInteger("relation");
            user_idA = document4.getString("user_id");
            nameA = document4.getString("name");
            phoneA = document4.getString("phone");
            birthdayA= document4.getLong("birthday")+"";
            box_array_flage = 1;
        }
        if (document3.get("UserB")!= null &&!document3.get("UserB").equals(""))//判断UserB用户是否存在
        {
            box_userB_flage = 1;
            document4 =  document3.get("UserB",Document.class);//取出UserA中的值
            relationB = document4.getInteger("relation");
            user_idB = document4.getString("user_id");
            nameB = document4.getString("name");
            phoneB = document4.getString("phone");
            birthdayB = document4.getLong("birthday")+"";
            box_array_flage = 1;

        }
        if (document3.get("UserC")!= null &&!document3.get("UserC").equals(""))//判断UserC用户是否存在
        {
            box_userC_flage = 1;
            document4 =  document3.get("UserC",Document.class);//取出UserA中的值
            relationC= document4.getInteger("relation");
            user_idC = document4.getString("user_id");
            nameC = document4.getString("name");
            phoneC = document4.getString("phone");
            birthdayC = document4.getLong("birthday")+"";
            box_array_flage = 1;
        }
    }

    public static void dialog_photo(final Activity mActivity)
    {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        final View imgEntryView = inflater.inflate(R.layout.update_pop, null);  // 加载更新的布局文件
        TextView update_version = imgEntryView.findViewById(R.id.update_version);//最新的版本
        TextView update_size = imgEntryView.findViewById(R.id.update_size);//版本大小
        TextView update_info = imgEntryView.findViewById(R.id.update_info);//版本内容
        TextView update_cancal = imgEntryView.findViewById(R.id.update_cancal);//取消
        TextView update_ok = imgEntryView.findViewById(R.id.update_ok);//确认更新

        update_pop= new PopupWindow(imgEntryView);
        update_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        update_pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha=0.5f;
        mActivity.getWindow().setAttributes(lp);
        update_pop.setBackgroundDrawable(mActivity.getResources().getDrawable(R.color.transparent));
        update_pop.setOutsideTouchable(true);
        update_pop.setFocusable(true);
        update_pop.showAtLocation(imgEntryView, Gravity.CENTER, 0, 0);

        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        update_cancal.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1.0f;
                mActivity.getWindow().setAttributes(lp);
                update_pop.dismiss();
            }
        });
        update_ok.setOnClickListener(new View.OnClickListener() {//更新操作
            @Override
            public void onClick(View view) {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1.0f;
                mActivity.getWindow().setAttributes(lp);
                //3.如果手机已经启动下载程序，执行downloadApk。否则跳转到设置界面
                if (DownLoadUtils.getInstance(mActivity).canDownload()) {
                    DownloadApk.downloadApk(mActivity, "https://www.pgyer.com/apiv2/app/install?appKey=0b60e1e49e38402f2bfe8a4fd1d52d13&_api_key=dd7e126828c4b716f9235fd9f7e18593", "蜂鸟健康", "Microtech");
                } else {
                    DownLoadUtils.getInstance(mActivity).skipToDownloadManager();
                }
                update_pop.dismiss();
            }
        });

    }

    private  int app_vierson()
    {
        PackageManager manager;

        PackageInfo info = null;

        manager = mActivity.getPackageManager();

        try {

            info = manager.getPackageInfo(mActivity.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

        // TODO Auto-generated catch block

            e.printStackTrace();

        }

        return info.versionCode;

      /*  info.versionName;

        info.packageName;

        info.signatures;*/
    }

    private  void update_thread()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("user_id", user_id),
                        new OkHttpClientManager.Param("time_str",""+getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("phone", phone),
                        new OkHttpClientManager.Param("type", "")
                };

                System.out.println("user_id** phone*******"+user_id+"   "+phone) ;
                try {
                    home_data[0] = ok.postOneHeadAsString(home_url,device_no, params_home);//体征数据
                    System.out.println(device_no +  "    " + phone +"    "+user_id);
                    LoadingDialog.cancelDialogForLoading();
                    System.out.println("数据加载成功!" + home_data[0]);
                    Message msg = new Message();
                    msg.what = 11;//检测更新
                    msg.obj = home_data[0];
                    handler.sendMessageDelayed(msg,1000);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }).start();
    }
    public String Load_PushToken() {
        //指定操作的文件名称
        SharedPreferences share = getContext().getSharedPreferences(user+"bindUser", MODE_PRIVATE);
        String device_no = share.getString("bindUser", "0");
        return device_no;
    }
    private  void save_pushtoken(){
            //指定操作的文件名称
            //指定操作的文件名称
            SharedPreferences share = getContext().getSharedPreferences(user+"bindUser", MODE_PRIVATE);
            SharedPreferences.Editor edit = share.edit(); //编辑文件
            edit.putString("bindUser", "1");
            edit.commit();
    }

    private void inint_NIMM() {
        incomingMessageObserver1 =
                new Observer<List<IMMessage>>() {
                    @Override
                    public void onEvent(List<IMMessage> messages) {
                        // 处理新收到的消息，为了上传处理方便，SDK 保证参数 messages 全部来自同一个聊天对象。
                        System.out.println("********收到新消息内容*******"+messages.get(messages.size()-1).getContent());
                        NIMBean nimBean = new NIMBean(messages.get(messages.size()-1).getContent(),new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date()));
                        if(flage_a == 0) {
                            flage_a = 1;
                            list_NIM.add(nimBean);
                            Message msg = new Message();
                            msg.what = 9;
                            msg.obj = messages;
                            handler.handleMessage(msg);
                        }
                    }
                };
        NIMClient.getService(MsgServiceObserve.class)
                .observeReceiveMessage(incomingMessageObserver1, true);
    }

    private void save_NIM_numvbers()
    {
        //指定操作的文件名称
        //指定操作的文件名称
        SharedPreferences share = mActivity.getSharedPreferences(user+"nim", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("NIM_numbers", flage_NIM+"");
        edit.commit();
    }
}
