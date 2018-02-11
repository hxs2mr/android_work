package microtech.hxswork.com.android_work.ui.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.media.RingtoneManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;

import org.bson.Document;

import java.io.IOException;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.AuthBean;
import microtech.hxswork.com.android_work.bean.LoginBean;
import microtech.hxswork.com.android_work.bean.NIMBean;
import microtech.hxswork.com.android_work.bean.TokenBean;
import microtech.hxswork.com.android_work.bean.XGBean;
import microtech.hxswork.com.android_work.receiver.MessageReceiver;
import microtech.hxswork.com.commom.commonutils.JacksonUtil;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.commom.commonutils.FormatUtil.isMobileNO;

/**
 * Created by microtech on 2017/8/22.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editText_username;
    EditText editText_password;
    TextView login_resgist;//注册
    TextView login_forpassword;//忘记密码
    TextView login;
    TextView login_mikeserver;
    ImageView login_username_delete;//去除用户名
    ImageView login_password_look;//查看密码
    int look_flage=0;
    LinearLayout main;
    LinearLayout end_login_linear;
    ImageView head_image;
    LinearLayout end_linear;
    PopupWindow server_pop;
    String token_url ="https://doc.newmicrotech.cn/otsmobile/app/init?";//"http://doc.newmicrotech.cn:8080/otsmobile/app/init?";//获取token
    String access_token_url = "https://doc.newmicrotech.cn/otsmobile/app/access_token?";//授权token
    String login_url = "https://doc.newmicrotech.cn/otsmobile/app/login?";//登录
    public  static String phone_id = "米可phone";//手机型号
    public  static String os_id = "米可1.0";//系统版本
    public  static String uniqueId = "00000000-55fa-eefb-0033-c5870033c587";//设备号

    public  static  int phone_width;//获取屏幕的宽度
    public  static  int phone_heigh;//获取屏幕的高度
    public static  String mac;////获取手机MAC地址
    String tmDevice, tmSerial, tmPhone, androidId;
    TelephonyManager tm;
    private String locationProvider;//获取经纬度
    LocationManager locationManager;
    Location location;
    OkHttpClientManager ok;
    public static  String user;
    public  static    double d_j = 0.0;
    public  static    double d_w = 0.0;
    private static final String KICK_OUT = "KICK_OUT";
    final int perssion_location = 1;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    ActivityCompat ac;
    final String[] data = {null};
    final String[] accc_token = {null};
    final String[] login_token = {null};
    final String[] login_token1 = {null};

    public static   AuthBean AccBean;
    OkHttpClientManager.Param[]  params_token;
    String register[] = {null};
    Document document ;//= Document.parse(login_token1[0]);
    Document document1;// =  document.get("obj",Document.class);//obj目录极数据
    Document document2;
    Document document3; //取出boxArray中的值
    Document document4; //取出boxArray中user的值
    Document document5; //取出boxArray中user中的brthday值
    Document document_box;
    Document document_im;
    Document document_doctor;
//医生模块的信息

    public static  int doctor_status = -1;
    public static Document document_native;

    public  static  String proviceId;
    public  static  String cityId;
    public  static  String areaId;

         public  static  int box_array_flage = 1;//监听boxarray是否为空

        public  static  int box_userA_flage = 0 ;//判断boxArray中存不存在绑定家人


        public  static String start_head_image="";
        public  static String start_name="";
        public static  int start_sex=0;
        public static  Long start_birthday=1l;
        public static String start_address="";
        public  static  String start_id_card="";
        public static String start_native="";//详细地址

        public static  String box_id ="";
        public  static int relation = -1;
        public static  int  relationA = -1;//关系
        public static  String user_idA="";//家庭成员的用户id
        public static  String nameA="";//姓名
        public static  String phoneA="";//电话
        public static  String birthdayA="";//生日



    public  static  int box_userB_flage = 0 ;
    public static  int  relationB = -1;//关系
    public static  String user_idB="";//家庭成员的用户id
    public static  String nameB="";//姓名
    public static  String phoneB="";//电话
    public static  String birthdayB="";//生日


    public  static  int box_userC_flage = 0 ;
    public static  int  relationC = -1;//关系
    public static  String user_idC="";//家庭成员的用户id
    public static  String nameC="";//姓名
    public static  String phoneC="";//电话
    public static  String birthdayC="";//生日

    //盒子的信息
    public  static  String famile_id="";
    public  static  String box_name="";
    public  static  String box_model="";
    public  static  String box_sn="";
    public  static  String box_time="";


    //im的accid
    public  static  String accid="";
    public  static  String token="";

    public  static int home_thread_flage=0;//Yongl
    public static  String password="";
    public static int reflash=0;


    public static int update_flage=0;//查看跟新的标志位
    public static String push_token="";
    public  static int version_code=0;
    //信鸽推送
    private MessageReceiver updateListViewReceiver;

    public static int user_break_flage =0 ;
    public  static  List<XGBean> list_XG;//信鸽实时的信息
    public static  List<NIMBean> list_NIM;//云信实时的信息
    public static  int infomation_save_flage= 0 ;
    public static  int flage_NIM =0  ;
    public static  int user_NIM=0;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_login_activity);
        if(LoadZero().equals("0"))
        {
            startActivity(new Intent(this,ZeroActivity.class));
            finish();
        }
        init_xinge();
     if(!Load_PushToken().equals("0"))
        {
            push_token=Load_PushToken();
        }
        initview();
        System.out.println("******uuid*****"+uniqueId);
        System.out.println("******push_token*****"+push_token);
    }
    private void initview() {
        list_XG=  new ArrayList<>();
        list_NIM = new ArrayList<>();
        box_array_flage = 1;
        box_userA_flage = 0 ;
        box_userB_flage = 0 ;
        box_userC_flage = 0 ;
        tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        version_code  = app_vierson();
        main = (LinearLayout) findViewById(R.id.main);
        end_login_linear = (LinearLayout) findViewById(R.id.end_login_linear);
        head_image = (ImageView) findViewById(R.id.head_image);
        end_linear = (LinearLayout) findViewById(R.id.end_linear);
        editText_username = (EditText) findViewById(R.id.login_username);
        editText_password = (EditText) findViewById(R.id.login_userpassword);
        login_resgist = (TextView) findViewById(R.id.login_resgist);//注册
        login_forpassword = (TextView) findViewById(R.id.login_forpassword);//忘记密码
        login = (TextView) findViewById(R.id.login);//登录
        login_mikeserver = (TextView) findViewById(R.id.login_mikeserver);//服务条款
        login_username_delete = (ImageView) findViewById(R.id.login_username_delete);//去除用户名
        login_password_look = (ImageView) findViewById(R.id.login_password_look);//查看密码
        login.setOnClickListener(this);
        login_username_delete.setOnClickListener(this);
        login_password_look.setOnClickListener(this);
        login_resgist.setOnClickListener(this);
        login_forpassword.setOnClickListener(this);
        editText_username.setOnClickListener(this);
        editText_password.setOnClickListener(this);
        login_mikeserver.setOnClickListener(this);
        editText_username.addTextChangedListener(new Edittext_change_username());//监听edittext的输入
        editText_password.addTextChangedListener(new Edittext_change_userpassword());
        editText_username.getText().toString().trim();
        mac = getMac(getBaseContext());//获取手机MAC地址
        System.out.println("mac *************! is a:" + mac);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        phone_width = wm.getDefaultDisplay().getWidth(); //获取屏幕的宽度
        phone_heigh = wm.getDefaultDisplay().getHeight();//获取屏幕的高度
        addLayoutListener(main,end_login_linear);

        onDevice();
        params_token = new OkHttpClientManager.Param[]{//获取token
                new OkHttpClientManager.Param("skin",uniqueId),new OkHttpClientManager.Param("procedure","null")};
        inindevice_no();//获取device_no

         register = getIntent().getStringArrayExtra("register");//获取注册成功之后的用户返回的用户名和密码

        SharedPreferences share = getSharedPreferences("read_user", MODE_PRIVATE);
        String user_name = share.getString("user", "0");
        String user_passowrd =  share.getString("password", "0");
        if(register!=null)
        {
            editText_username.setText(register[0]);
            editText_password.setText(register[1]);
        }
        if(!user_name.equals("0"))
        {
            editText_username.setText(user_name);
            editText_password.setText(user_passowrd);
        }

        System.out.println(" **** d_j  :" + d_j + "*****d_w :" + d_w);
        System.out.println(" **** phone_id  :" + phone_id + "*****os_id :" + os_id);
    }

    private void onDevice() {
        //动态配置6.0后的动态获取权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                // 申请一个（或多个）权限，并提供用于回调返回的获取码（用户定义）
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, BAIDU_READ_PHONE_STATE);

            } else {  //获取权限后的
                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                    tmDevice = "" + tm.getDeviceSoftwareVersion();
                    tmSerial = "" + tm.getSimSerialNumber();
                    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
                    phone_id = Build.MODEL;//手机型号
                    os_id = Build.VERSION.RELEASE;//系统版本
                    uniqueId = deviceUuid.toString();//设备号
                    System.out.println(" **** phone_id  :" + phone_id + "*****os_id :" + os_id);
                }

                if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    //获取地理位置管理器
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //获取所有可用的位置提供器
                    List<String> providers = locationManager.getProviders(true);
                    if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                        //如果是GPS
                        locationProvider = LocationManager.NETWORK_PROVIDER;
                        location = locationManager.getLastKnownLocation(locationProvider);
                        if(location != null) {
                            d_j = location.getLongitude();
                            d_w = location.getLatitude();
                        }
                    } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                        //如果是Network
                        locationProvider = LocationManager.GPS_PROVIDER;
                        location = locationManager.getLastKnownLocation(locationProvider);
                        if(location != null) {
                            d_j = location.getLongitude();
                            d_w = location.getLatitude();
                        }
                    } else {
                        Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        } else {
            tmDevice = "" + tm.getDeviceSoftwareVersion();
            tmSerial = "" + tm.getSimSerialNumber();
            androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
            UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
            phone_id = Build.MODEL;//手机型号
            os_id = Build.VERSION.RELEASE;//系统版本
            uniqueId = deviceUuid.toString();//设备号
            //获取地理位置管理器
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            //获取所有可用的位置提供器
            List<String> providers = locationManager.getProviders(true);
            if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                //如果是GPS
                locationProvider = LocationManager.NETWORK_PROVIDER;
                location = locationManager.getLastKnownLocation(locationProvider);
                if(location != null) {
                    d_j = location.getLongitude();
                    d_w = location.getLatitude();
                }
            } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                //如果是Network
                locationProvider = LocationManager.GPS_PROVIDER;
                location = locationManager.getLastKnownLocation(locationProvider);
                if(location != null) {
                    d_j = location.getLongitude();
                    d_w = location.getLatitude();
                }
            } else {
                Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
            }
        }
    }
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case BAIDU_READ_PHONE_STATE:
                System.out.println("************" + grantResults.length);
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 获取到权限，作相应处理（调用定位SDK应当确保相关权限均被授权，否则可能引起定位失败）
                    tmDevice = "" + tm.getDeviceSoftwareVersion();
                    tmSerial = "" + tm.getSimSerialNumber();
                    androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
                    UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
                    phone_id = Build.MODEL;//手机型号
                    os_id = Build.VERSION.RELEASE;//系统版本
                    uniqueId = deviceUuid.toString();//设备号
                    System.out.println(" **** phone_id  :" + phone_id + "*****os_id :" + os_id);
                } else
                if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    //获取地理位置管理器
                    locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                    //获取所有可用的位置提供器
                    List<String> providers = locationManager.getProviders(true);
                    if (providers.contains(LocationManager.NETWORK_PROVIDER)) {
                        //如果是GPS
                        locationProvider = LocationManager.NETWORK_PROVIDER;
                    } else if (providers.contains(LocationManager.GPS_PROVIDER)) {
                        //如果是Network
                        locationProvider = LocationManager.GPS_PROVIDER;
                    } else {
                        Toast.makeText(this, "没有可用的位置提供器", Toast.LENGTH_SHORT).show();
                    }
                    if(location != null) {
                        d_j = location.getLongitude();
                        d_w = location.getLatitude();
                        System.out.println(" **** d_w  :" + d_w + "*****d_j :" + d_j);
                    }
                }else {
                    // 没有获取到权限，做特殊处理
                    Toast.makeText(getApplicationContext(), "获取位置权限失败，请手动开启", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login: //用户登录模块
                hideSoftInputView();

                ok = new OkHttpClientManager();
/*
                 startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
                NimUIKit.startP2PSession(LoginActivity.this, "18385655626");*/
               // doLogin();
                if (!editText_username.getText().toString().equals("") && !editText_password.getText().toString().equals("")) {
                    if (isMobileNO(editText_username.getText().toString().trim().replace(" ",""))) {
                    if (isConnectingToInternet()) {
                        //判断网络是否链接
                        LoadingDialog.showDialogForLoading(this, "登录中..", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    if (!LoadData(editText_username.getText().toString().trim().replace(" ", "")).equals("0")) {    //读取文件中是否存在device_on设备凭证
                                        System.out.println("带的有device_on");
                                        final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{   //登录token
                                                new OkHttpClientManager.Param("os", "android"),//时间
                                                new OkHttpClientManager.Param("user_name", editText_username.getText().toString().trim().replace(" ", "")),
                                                new OkHttpClientManager.Param("password", MD5(editText_password.getText().toString().trim()))};
                                        System.out.println("new ********" + LoadData(editText_username.getText().toString().trim().replace(" ", "")));

                                        login_token1[0] = ok.postHeadAsString(login_url, LoadData(editText_username.getText().toString().trim().replace(" ", "")), "login", params_login);

                                        System.out.println("*******new login*******;" + login_token1[0]);

//                                        Document document = JacksonUtil.readJson(login_token1[0], Document.class);

                                        if ((login_token1[0].charAt(0) + "").equals("<")) {
                                            toast("服务器异常");
                                            LoadingDialog.cancelDialogForLoading();
                                            return;
                                        }
                                        document = Document.parse(login_token1[0]);

                                        if (document.get("obj") == "") {
                                            toast("用户名或密码错误");
                                            LoadingDialog.cancelDialogForLoading();
                                            return;
                                        }
                                        if (document.getInteger("code") == 500) {
                                            toast("操作异常");
                                            LoadingDialog.cancelDialogForLoading();
                                            return;
                                        }
                                        document1 = document.get("obj", Document.class);//obj目录极数据
                                        if (document1== null) {
                                            toast("请求参数有误  请稍后再试!");
                                            LoadingDialog.cancelDialogForLoading();
                                            return;
                                        }
                                        document2 = document1.get("user", Document.class);//用户表数据
                                        document3 = document1.get("boxArray", Document.class);//boxArray数据
                                        document_im = document2.get("im_account", Document.class);
                                        if (document_im != null) {
                                            accid = document_im.getString("accid");
                                            token = document_im.getString("token");
                                        }
                                        System.err.println("********" + document1.toJson());
                                        System.out.println("*******document*******" + document.getInteger("code"));
                                        System.out.println("*******document*******" + document.getString("msg"));
                                        System.out.println("*******document***********************" + document.get("obj"));
                                        System.out.println("*******document1*******" + document1.get("boxArray"));

                                        if (document2.getInteger("relation") != null) {
                                            relation = document2.getInteger("relation");//获取当前用户的关系
                                            System.out.println("******relation " + relation);
                                        }
                                        box_id = document2.getString("box_id");//盒子id
                                        famile_id = document2.getString("family_id");
                                       // document_doctor = document2.get("doctor",Document.class);
                                        //医生的申请状态
                                        doctor_status = document2.getInteger("status");
                                  /*      if(document_doctor.getString("doctor_id")!=null)
                                        {  doctor_id = document_doctor.getString("doctor_id");

                                        }
                                        hospital_name = document_doctor.getString("hospital_name");
                                        if (document_doctor.getString("doctor_name") !=null) {
                                            doctor_name = document_doctor.getString("doctor_name");
                                        }
                                        if( document_doctor.get("im_account",Document.class)!=null)
                                        {
                                            doctor_accid = document_doctor.getString("accid");
                                            doctor_token = document_doctor.getString("token");
                                        }*/
                                        if (box_id != null) {
                                            document_box = document2.get("box", Document.class);
                                            box_name = document_box.getString("name");
                                            box_model = document_box.getString("model");
                                            box_sn = document_box.getString("sn");
                                            box_time = document_box.getLong("times") + "";
                                        }
                                        start_head_image = document2.getString("avatar");//盒子id
                                        if (start_head_image == null) {
                                            start_head_image = "";
                                        }

                                        start_name = document2.getString("name");//盒子id
                                        start_sex = document2.getInteger("sex");
                                        if( document2.getLong("birthday") != null)
                                        {
                                            start_birthday = document2.getLong("birthday");
                                        }
                                        if (document2.getString("address") != null) {
                                            start_address = document2.getString("address");
                                        }
                                        if (document2.getString("id_card") != null) {
                                            start_id_card = document2.getString("id_card");
                                        }
                                        if (document2.get("native", Document.class) != null) {
                                            document_native = document2.get("native", Document.class);
                                            proviceId = document_native.getString("proviceId");
                                            cityId = document_native.getString("cityId");
                                            areaId = document_native.getString("areaId");
                                            start_native = document2.get("native", Document.class) + "";
                                        }
                                        System.out.println("*****start_head_image*****" + start_head_image);
                                        System.out.println("*****start_name*****" + start_name);

                                        System.out.println("*****box_id*****" + box_id);
                                        System.out.println("*****boxArray*****" + document1.get("boxArray"));


                                        if (document1.get("boxArray") == null) {
                                            box_array_flage = 0;
                                        } else {
                                            System.out.println("*****netx*****");
                                            if (document3.get("UserA") != null && !document3.get("UserA").equals(""))//判断UserA用户是否存在
                                            {
                                                System.out.println("*****netx2*****");
                                                box_userA_flage = 1;
                                                document4 = document3.get("UserA", Document.class);//取出UserA中的值
                                                relationA = document4.getInteger("relation");
                                                user_idA = document4.getString("user_id");
                                                nameA = document4.getString("name");
                                                phoneA = document4.getString("phone");
                                                birthdayA = document4.get("birthday") + "";

                                            }

                                            if (document3.get("UserB") != null && !document3.get("UserB").equals(""))//判断UserB用户是否存在
                                            {
                                                box_userB_flage = 1;
                                                document4 = document3.get("UserB", Document.class);//取出UserA中的值
                                                relationB = document4.getInteger("relation");
                                                user_idB = document4.getString("user_id");
                                                nameB = document4.getString("name");
                                                phoneB = document4.getString("phone");
                                                birthdayB = document4.get("birthday") + "";
                                            }

                                            if (document3.get("UserC") != null && !document3.get("UserC").equals(""))//判断UserC用户是否存在
                                            {
                                                box_userC_flage = 1;
                                                document4 = document3.get("UserC", Document.class);//取出UserA中的值
                                                relationC = document4.getInteger("relation");
                                                user_idC = document4.getString("user_id");
                                                nameC = document4.getString("name");
                                                phoneC = document4.getString("phone");
                                                birthdayC = document4.get("birthday") + "";
                                            }

                                            System.out.println("*******box_userA_flage  box_userB_flage  box_userC_flage*******" + box_userA_flage + "" + box_userB_flage + "" + box_userC_flage);
                                        }

                                        System.out.println("*******document1_id*******" + document2.get("_id"));

                                        switch (document.getInteger("code")) {
                                            case 200:
                                                password = editText_password.getText().toString().trim();
                                                Save_User(editText_username.getText().toString().trim(),password);//保存用户名和密码
                                                user = editText_username.getText().toString().trim().replace(" ", "");
                                                SaveData(AccBean.getObj(), editText_username.getText().toString().trim().replace(" ", ""), document2.get("_id")+"");//保存文件
                                                doLogin();
                                                break;
                                            case 201:
                                                LoadingDialog.cancelDialogForLoading();
                                                toast("用户名或密码错误");
                                                break;
                                            case 202:
                                                LoadingDialog.cancelDialogForLoading();
                                                toast("用户名或密码错误");
                                                break;
                                            case 203:
                                                LoadingDialog.cancelDialogForLoading();
                                                toast("登录授权不通过");
                                                break;
                                            case 404:
                                                LoadingDialog.cancelDialogForLoading();
                                                toast("请求参数错误");
                                                break;
                                            case 500:
                                                LoadingDialog.cancelDialogForLoading();
                                                toast("服务器异常");
                                                break;
                                            default:
                                                LoadingDialog.cancelDialogForLoading();
                                                toast("未知错误");
                                                break;
                                        }

                                    } else {

                                        System.out.println("没有带的有device_on");

                                        if (AccBean.getMsg() == "123") {
                                            toast("服务器异常");
                                            LoadingDialog.cancelDialogForLoading();
                                            return;
                                        }
                                        System.out.println("AccBean getCode**********" + AccBean.getObj());
                                        if (AccBean.getCode() == 200)//登录步骤
                                        {
                                            final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{   //登录token
                                                    new OkHttpClientManager.Param("os", "android"),//时间
                                                    new OkHttpClientManager.Param("user_name", editText_username.getText().toString().trim().replace(" ", "")),
                                                    new OkHttpClientManager.Param("password", MD5(editText_password.getText().toString()))};
                                            login_token[0] = ok.postHeadAsString(login_url, AccBean.getObj(), "login", params_login);

                                            System.out.println("*******new login*******;" + login_token[0]);

                                            if ((login_token[0].charAt(0) + "").equals("<")) {
                                                toast("服务器异常");
                                                LoadingDialog.cancelDialogForLoading();
                                                return;
                                            }
                                            document = Document.parse(login_token[0]);


                                            if (document.get("obj") == "") {
                                                toast("用户名或密码错误");
                                                LoadingDialog.cancelDialogForLoading();
                                                return;
                                            }
                                            if (document.getInteger("code") == 500) {
                                                toast("操作异常");
                                                LoadingDialog.cancelDialogForLoading();
                                                return;
                                            }
                                            document1 = document.get("obj", Document.class);//obj目录极数据
                                            if (document1== null) {
                                                toast("请求参数有误  请稍后再试!");
                                                LoadingDialog.cancelDialogForLoading();
                                                return;
                                            }
                                            document2 = document1.get("user", Document.class);//用户表数据
                                            document3 = document1.get("boxArray", Document.class);//boxArray数据

                                            document_im = document2.get("im_account", Document.class);
                                            if (document_im != null) {
                                                accid = document_im.getString("accid");
                                                token = document_im.getString("token");
                                            }
                                            System.err.println("********" + document1.toJson());
                                            System.out.println("*******document*******" + document.getInteger("code"));
                                            System.out.println("*******document*******" + document.getString("msg"));
                                            System.out.println("*******document***********************" + document.get("obj"));
                                            System.out.println("*******document1*******" + document1.get("boxArray"));

                                            if (document2.getInteger("relation") != null) {
                                                relation = document2.getInteger("relation");//获取当前用户的关系
                                                System.out.println("******relation " + relation);
                                            }

                                            box_id = document2.getString("box_id");//盒子id
                                            famile_id = document2.getString("family_id");

                                            //document_doctor = document2.get("doctor",Document.class);
                                            //医生的申请状态

                                            doctor_status = document2.getInteger("status");
                                           /* if(document_doctor.getString("doctor_id")!=null)
                                            {  doctor_id = document_doctor.getString("doctor_id");
                                            }
                                            hospital_name = document_doctor.getString("hospital_name");
                                            if (document_doctor.getString("doctor_name") !=null) {
                                                doctor_name = document_doctor.getString("doctor_name");
                                            }
                                            if( document_doctor.get("im_account",Document.class)!=null)
                                            {
                                                doctor_accid = document_doctor.getString("accid");
                                                doctor_token = document_doctor.getString("token");
                                            }
*/
                                            if (box_id != null) {
                                                document_box = document2.get("box", Document.class);
                                                box_name = document_box.getString("name");
                                                box_model = document_box.getString("model");
                                                box_sn = document_box.getString("sn");
                                                box_time = document_box.getLong("times") + "";
                                            }
                                            start_head_image = document2.getString("avatar");//盒子id
                                            start_name = document2.getString("name");//盒子id
                                            start_sex = document2.getInteger("sex");

                                            if( document2.getLong("birthday") != null)
                                            {
                                                start_birthday = document2.getLong("birthday");
                                            }

                                            if (document2.getString("address") != null) {
                                                start_address = document2.getString("address");//地址
                                            }
                                            if (document2.getString("id_card") != null) {
                                                start_id_card = document2.getString("id_card");//身份证号
                                            }
                                            if (document2.get("native", Document.class) != null) {
                                                document_native = document2.get("native", Document.class);
                                                proviceId = document_native.getString("proviceId");
                                                cityId = document_native.getString("cityId");
                                                areaId = document_native.getString("areaId");
                                                start_native = document2.get("native", Document.class) + "";
                                            }
                                            if (document1.get("boxArray") == null) {
                                                box_array_flage = 0;
                                            } else {

                                                if (document3.get("UserA") != null && !document3.get("UserA").equals(""))//判断UserA用户是否存在
                                                {
                                                    box_userA_flage = 1;
                                                    document4 = document3.get("UserA", Document.class);//取出UserA中的值
                                                    relationA = document4.getInteger("relation");
                                                    user_idA = document4.getString("user_id");
                                                    nameA = document4.getString("name");
                                                    phoneA = document4.getString("phone");
                                                    birthdayA = document4.get("birthday") + "";

                                                }

                                                if (document3.get("UserB") != null && !document3.get("UserB").equals(""))//判断UserB用户是否存在
                                                {
                                                    box_userB_flage = 1;
                                                    document4 = document3.get("UserB", Document.class);//取出UserA中的值
                                                    relationB = document4.getInteger("relation");
                                                    user_idB = document4.getString("user_id");
                                                    nameB = document4.getString("name");
                                                    phoneB = document4.getString("phone");
                                                    birthdayB = document4.get("birthday") + "";
                                                }

                                                if (document3.get("UserC") != null && !document3.get("UserC").equals(""))//判断UserC用户是否存在
                                                {
                                                    box_userC_flage = 1;
                                                    document4 = document3.get("UserC", Document.class);//取出UserA中的值
                                                    relationC = document4.getInteger("relation");
                                                    user_idC = document4.getString("user_id");
                                                    nameC = document4.getString("name");
                                                    phoneC = document4.getString("phone");
                                                    birthdayC = document4.get("birthday")+"";
                                                }

                                                System.out.println("*******box_userA_flage  box_userB_flage  box_userC_flage*******" + box_userA_flage + "" + box_userB_flage + "" + box_userC_flage);

                                            }

                                            // toast("登录成功!"+login_token[0]);
                                            System.out.println("*******acc*******;" + AccBean.getObj());
                                            System.out.println("*******load acc*******;" + LoadData(editText_username.getText().toString().trim().replace(" ", "")));
                                            switch (document.getInteger("code")) {
                                                case 200:
                                                    password = editText_password.getText().toString().trim();
                                                    Save_User(editText_username.getText().toString().trim(),password);//保存用户名和密码
                                                    user = editText_username.getText().toString().trim().replace(" ", "");
                                                    SaveData(AccBean.getObj(), editText_username.getText().toString().trim().replace(" ", ""), document2.get("_id")+"");//保存文件
                                                    doLogin();
                                                    break;
                                                case 201:
                                                    LoadingDialog.cancelDialogForLoading();
                                                    toast("用户名或密码错误");
                                                    break;
                                                case 202:
                                                    LoadingDialog.cancelDialogForLoading();
                                                    toast("用户名不存在");
                                                    break;
                                                case 203:
                                                    LoadingDialog.cancelDialogForLoading();
                                                    toast("登录授权不通过");
                                                    break;
                                                case 404:
                                                    LoadingDialog.cancelDialogForLoading();
                                                    toast("请求参数错误");
                                                    break;
                                                case 500:
                                                    LoadingDialog.cancelDialogForLoading();
                                                    toast("服务器异常");
                                                    break;
                                                default:
                                                    LoadingDialog.cancelDialogForLoading();
                                                    toast("未知错误");
                                                    break;

                                            }
                                        } else {
                                            LoadingDialog.cancelDialogForLoading();
                                            toast("授权token失败!");
                                            return;
                                        }
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    } else {
                        SAToast.makeText(getApplicationContext(), "网络未链接").show();
                    }
                }else {
                    SAToast.makeText(getApplicationContext(), "手机格式不对").show();
                }
                }else
                {
                    SAToast.makeText(getApplicationContext(), "用户和密码不能为空").show();
                }
                break;
            case R.id.login_username_delete:
                if (!editText_username.getText().toString().equals("")) {
                    editText_username.setText("");
                }
                break;
            case R.id.login_password_look:
                    if(look_flage == 0) {
                        editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        look_flage = 1;
                    }else {
                        editText_password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        look_flage =0;
                    }

                break;
            case R.id.login_resgist:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                //show_restiger_pop();
                break;
            case R.id.login_forpassword:
                Intent intent1 = new Intent(LoginActivity.this,ForgePassword.class);
                startActivity(intent1);
                break;
            case R.id.login_username:
                break;
            case R.id.login_userpassword:
                break;
            case R.id.login_mikeserver:
                show_cookie_pop();
                break;

        }
    }

    class Edittext_change_username implements TextWatcher{//设置edittext的监听时间
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            login_username_delete.setVisibility(View.INVISIBLE);

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            login_username_delete.setVisibility(View.VISIBLE);
            if(!editText_password.getText().toString().equals(""))
            {
                login.setAlpha(1.0f);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String username = editText_username.getText().toString();
            if (username.equals("")) {
                login_username_delete.setVisibility(View.INVISIBLE);
                login.setAlpha(0.2f);
            } else
            {
                login_username_delete.setVisibility(View.VISIBLE);
            }

        }
    }
    class  Edittext_change_userpassword implements TextWatcher{
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            login_password_look.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            login_password_look.setVisibility(View.VISIBLE);
            if(!editText_username.getText().toString().equals(""))
            {
                login.setAlpha(1.0f);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String password = editText_password.getText().toString();
            if (password.equals("")) {
                login_password_look.setVisibility(View.INVISIBLE);
                login.setAlpha(0.2f);
                editText_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else
            {
                login_password_look.setVisibility(View.VISIBLE);
            }
        }
    }


    public  boolean isConnectingToInternet() {
        ConnectivityManager connectivity = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
        }
        return false;
    }
    private void toast(final String msg) {
      runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String getMac(Context context){//获取手机的mac地址
        try
        {
            for (Enumeration e = NetworkInterface.getNetworkInterfaces();e.hasMoreElements();)
            {
                NetworkInterface item = (NetworkInterface) e.nextElement();
                byte[] mac = item.getHardwareAddress();
                if(mac != null && mac.length > 0){
                    return byte2hex(mac);
                }
            }
        } catch (Exception e)
        {
        }
        return "";
    }

    public static  String byte2hex(byte[] b) {
        StringBuffer hs = new StringBuffer(b.length);
        String stmp = "";
        int len = b.length;
        for (int n = 0; n < len; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            if (stmp.length() == 1)
                hs = hs.append("0").append(stmp);
            else {
                hs = hs.append(stmp);
            }
        }
        return String.valueOf(hs);
    }

    public static String MD5(String plainText ) {
        StringBuffer buf = new StringBuffer("");
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if(i<0) i+= 256;
                if(i<16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }

            System.out.println("result: " + buf.toString());//32位的加密

            System.out.println("result: " + buf.toString().substring(8,24));//16位的加密

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return   buf.toString();
    }

    public  void  hideSoftInputView(){
        InputMethodManager manager = ((InputMethodManager)getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getWindow().getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    public void SaveData(String device_no,String phone,String user_id) {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences(phone+"123", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("phone", phone);         //根据键值对添加数据
        edit.putString("device_no",device_no);
        edit.putString("user_id",user_id);
        edit.commit();  //保存数据信息
    }

    public void Save_User(String user,String password) {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("read_user", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("user", user);         //根据键值对添加数据
        edit.putString("password",password);
        edit.commit();  //保存数据信息
    }

    public String LoadData(String phone1) {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences(phone1+"123", MODE_PRIVATE);
        String device_no = share.getString("device_no", "0");
        String phone =  share.getString("phone", "0");
        return device_no;
    }
public static long getStringToDate(String time) {  //转为时间戳
        SimpleDateFormat sf = null;
sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date = new Date(0);
try{
date = (Date) sf.parse(time);
} catch(ParseException e) {
// TODO Auto-generated catch block
e.printStackTrace();
} catch (java.text.ParseException e) {
    e.printStackTrace();
}
    return date.getTime();
}

    public static long getStringToDate1(String time) {  //转为时间戳
        SimpleDateFormat sf = null;
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(0);
        try{
            date = (Date) sf.parse(time);
        } catch(ParseException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        return date.getTime();
    }

public static String getDateToString(long time) {
        SimpleDateFormat sf = null;
        sf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd");
        return sf.format(d);
    }
    public static String getDateToString1(long time) {
        SimpleDateFormat sf = null;
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }


public void doLogin()
{

    LoginInfo info = new LoginInfo(accid,token);

    RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
        @Override
        public void onSuccess(LoginInfo param) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            LoadingDialog.cancelDialogForLoading();
            toast("登录成功!");
            finish();
        }

        @Override
        public void onFailed(int code) {

        }

        @Override
        public void onException(Throwable exception) {

        }
    };
    NIMClient.getService(AuthService.class).login(info).setCallback(callback);

}

    public void addLayoutListener(final View main, final View scroll) {
        main.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                Rect rect = new Rect();
                main.getWindowVisibleDisplayFrame(rect);
                int mainInvisibleHeight = main.getRootView().getHeight() - rect.bottom;
                if (mainInvisibleHeight > 200) {
                    int[] location = new int[2];
                    scroll.getLocationInWindow(location);
                    int srollHeight = (location[1] + scroll.getHeight()) - rect.bottom;
                    main.scrollTo(0, srollHeight);
                } else {
                    main.scrollTo(0, 0);
                }
            }
        });
    }


    private void inindevice_no() {
        if (isConnectingToInternet()) {                 //判断网络是否链接

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        data[0] = ok.postAsString(token_url, params_token);//获取tonken
                        Gson gson1 = new Gson();
                        System.out.println("********data accc***" + data[0]);
                        System.out.println("********data[0].length***" +data[0].length());
                        if(data[0].length() > 500||data[0].length()==0)
                        {
                            Toast.makeText(LoginActivity.this,"服务器异常1",Toast.LENGTH_SHORT).show();
                            AccBean=new AuthBean(1,"123","");
                            return;
                        }
                        TokenBean tokenBean = gson1.fromJson(data[0], TokenBean.class);

                        System.out.println("********uniqueId***" + uniqueId);
                        if (tokenBean.getCode() == 200) {
                            //授权token
                            final OkHttpClientManager.Param[] params_access_token = new OkHttpClientManager.Param[]{//授权token
                                    new OkHttpClientManager.Param("token", tokenBean.getObj()),
                                    new OkHttpClientManager.Param("version", app_vierson()+".0"),
                                    new OkHttpClientManager.Param("device_id",uniqueId),
                                    new OkHttpClientManager.Param("push_token", push_token.trim()),
                                    new OkHttpClientManager.Param("model", phone_id),
                                    new OkHttpClientManager.Param("environment", "Android"),
                                    new OkHttpClientManager.Param("os", "android"),
                                    new OkHttpClientManager.Param("mac", mac),
                                    new OkHttpClientManager.Param("screen", phone_width + "*" + phone_heigh),
                                    new OkHttpClientManager.Param("lng", d_j + ""),
                                    new OkHttpClientManager.Param("lat", d_w + "")
                            };
                            accc_token[0] = ok.postAsString(access_token_url, params_access_token);
                            System.out.println("********accc***" + accc_token[0]);
                            if((accc_token[0].charAt(0)+"").equals("<"))
                            {
                                toast("服务器异常2");
                                LoadingDialog.cancelDialogForLoading();
                                return;
                            }
                            Gson gson2 = new Gson();
                            AccBean = gson2.fromJson(accc_token[0], AuthBean.class);//获取device_no授权失败!
                        } else {
                            toast("授权token失败!");
                            return;
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        else{
            SAToast.makeText(getApplicationContext(), "网络未链接!").show();
        }

    }

    public String LoadZero() {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("Zero", MODE_PRIVATE);
        String device_no = share.getString("flage", "0");
        return device_no;
    }

    public String Load_PushToken() {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("PushToken", MODE_PRIVATE);
        String device_no = share.getString("PushToken", "0");
        return device_no;
    }

    public void Save_PushToken(String pushtoken) {
        //指定操作的文件名称
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("PushToken", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("PushToken", pushtoken);
        edit.commit();
    }

    private void show_cookie_pop() {
        final View view = LayoutInflater.from(this).inflate(R.layout.mark_servier_pop, null);
        ImageView back = view.findViewById(R.id.optionnext_back);
        server_pop = new PopupWindow(view);
        server_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        server_pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        //server_pop.setAnimationStyle(R.style.LoginPopWindowAnim);//设置进入和出场动画
        server_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        server_pop.setOutsideTouchable(true);
        server_pop.setFocusable(true);
        server_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        server_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                server_pop.dismiss();
            }
        });
    }
    private void init_xinge() {

        updateListViewReceiver = new MessageReceiver();
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("microtech.hxswork.com.android_work.receiver.UPDATE_LISTVIEW");
        Context context = getApplicationContext();
        XGPushConfig.enableDebug(this, true);//线上不能使用
        //信鸽注册代码
        XGPushManager.registerPush(this,new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                System.out.println("******信鸽注册成功*****"+data);

                push_token = (String) data;
                Save_PushToken((String) data);//保存token
                //initCustomPushNotificationBuilder(getApplicationContext());
                registerReceiver(updateListViewReceiver, intentFilter);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                System.out.println("******信鸽注册失败*****错误码"+ errCode + ",错误信息：" + msg);

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);// 必须要调用这句
    }

    private  int app_vierson()
    {
        PackageManager manager;

        PackageInfo info = null;

        manager = this.getPackageManager();

        try {

            info = manager.getPackageInfo(this.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {

            // TODO Auto-generated catch block

            e.printStackTrace();

        }

        return info.versionCode;

      /*  info.versionName;

        info.packageName;

        info.signatures;*/
    }
    private String getMyUUID(){
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(),android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }


}
