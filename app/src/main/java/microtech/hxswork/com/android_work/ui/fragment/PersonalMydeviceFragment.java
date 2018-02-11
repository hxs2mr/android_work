package microtech.hxswork.com.android_work.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.audiofx.Equalizer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;


import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import microtech.hxswork.com.android_work.R;

import butterknife.Bind;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.contract.PersonalMydeviceContract;
import microtech.hxswork.com.android_work.model.PersonalMydeviceModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalMydevicePresnterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.citypicker.city_20170724.DayTimerPickerView;
import microtech.hxswork.com.citypicker.city_20170724.PersonPickerView;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.zxing.ZxingActivity;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_model;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_sn;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_time;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userA_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userB_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userC_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.famile_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relation;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_array_flage;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.bing_url;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.textA;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.textB;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.textC;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;

/**
 * Created by microtech on 2017/8/31.
 */

public class PersonalMydeviceFragment extends BaseFragment<PersonalMydevicePresnterImpl,PersonalMydeviceModelImpl> implements PersonalMydeviceContract.View, View.OnClickListener {

    @Bind(R.id.personal_mydevice_back)
    ImageView personal_mydevice_back;//返回

    @Bind(R.id.personal_mydevice_back_linear)
    LinearLayout personal_mydevice_back_linear;
    @Bind(R.id.mydevice_id)
    TextView mydevice_id;//设备型号

    @Bind(R.id.mydevice_sn)
    TextView mydevice_sn;//设备SN号

    @Bind(R.id.mydevice_unbind)
    TextView mydevice_unbind;//解除\绑定

    @Bind(R.id.personal_mydevice_bindtextA)
    TextView personal_mydevice_bindtextA;//A设备绑定的消息

    @Bind(R.id.personal_mydevice_bindA)
    TextView personal_mydevice_bindA;//绑定或者编辑A设备

    @Bind(R.id.personal_mydevice_bindtextB)
    TextView personal_mydevice_bindtextB;//B设备绑定的消息

    @Bind(R.id.personal_mydevice_bindB)
    TextView personal_mydevice_bindB;//绑定或者编辑A设备

    @Bind(R.id.personal_mydevice_bindtextC)
    TextView personal_mydevice_bindtextC;//C设备绑定的消息

    @Bind(R.id.personal_mydevice_bindC)
    TextView personal_mydevice_bindC;//绑定或者编辑A设备

    HomeActivity mActivity;

    String sing_title1[];
    private static final int REQUEST_QRCODE = 0x01;
    PopupWindow bint_pop;//绑定或者编制设备模块
    private  final  int PERSSION_CARAM = 100;
    int bind_flage = 0 ;
    String bing_data[]={null};
    OkHttpClientManager ok;
    Document document;
    Document document1;
    Document document2;
    Document document3;
    Document document4;
    String code="";//扫描道德盒子id
    public static  int start_bind_flage=0;
    PopupWindow bind_pop;
    Handler handler;
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_mydevice_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity= (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.GONE);
        handler = new MyHander();
        sing_title1 = new String[3];
        sing_title1 = getArguments().getStringArray("data");

        personal_mydevice_back_linear.setOnClickListener(this);

        mydevice_unbind.setOnClickListener(this);//解除绑定

        personal_mydevice_bindA.setOnClickListener(this);//绑定或者编辑A设备

        personal_mydevice_bindB.setOnClickListener(this);

        personal_mydevice_bindC.setOnClickListener(this);

        mydevice_sn.setText(sing_title1[0]);
        mydevice_id.setText("型号:"+box_model);
        mydevice_unbind.setText("解绑");
        bind_flage = 0;

        inint_ui();//初始化绑定设备的界面


    }

    private void inint_ui() {
        if(box_userA_flage ==0 )
        {
            personal_mydevice_bindtextA.setText("未绑定");
            personal_mydevice_bindtextA.setTextColor(Color.parseColor("#FD5EA1"));
            personal_mydevice_bindA.setBackgroundResource(R.mipmap.btn_binding);
            personal_mydevice_bindA.setText("绑定");
         }else {
            personal_mydevice_bindtextA.setText(nameA+"("+init_ABC_name(relationA,relationA)+")");
            personal_mydevice_bindtextA.setTextColor(Color.parseColor("#898EA6"));
            personal_mydevice_bindA.setBackgroundResource(R.mipmap.btn_edit);
            personal_mydevice_bindA.setText("编辑");
        }
        if(box_userB_flage ==0 )
        {
            personal_mydevice_bindtextB.setText("未绑定");
            personal_mydevice_bindtextB.setTextColor(Color.parseColor("#FD5EA1"));
            personal_mydevice_bindB.setBackgroundResource(R.mipmap.btn_binding);
            personal_mydevice_bindB.setText("绑定");

        }else {
            personal_mydevice_bindtextB.setText(nameB+"("+init_ABC_name(relationB,relationB)+")");
            personal_mydevice_bindB.setBackgroundResource(R.mipmap.btn_edit);
            personal_mydevice_bindtextB.setTextColor(Color.parseColor("#898EA6"));
            personal_mydevice_bindB.setText("编辑");
        }

        if(box_userC_flage ==0 )
        {
            personal_mydevice_bindtextC.setText("未绑定");
            personal_mydevice_bindtextC.setTextColor(Color.parseColor("#FD5EA1"));
            personal_mydevice_bindC.setBackgroundResource(R.mipmap.btn_binding);
            personal_mydevice_bindC.setText("绑定");
        }else {
            personal_mydevice_bindtextC.setText(nameC+"("+init_ABC_name(relationC,relationC)+")");
            personal_mydevice_bindC.setBackgroundResource(R.mipmap.btn_edit);
            personal_mydevice_bindtextC.setTextColor(Color.parseColor("#898EA6"));
            personal_mydevice_bindC.setText("编辑");
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.personal_mydevice_back_linear:
                mActivity.onBackPressed();
                break;
            case R.id.mydevice_unbind://解除绑定的网络请求
                if(bind_flage == 0)//正在处于绑定状态  可解绑
                {
                    show(getView());
                }else {//正在处于解绑定状态  可绑定
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
                break;
            case R.id.personal_mydevice_bindA:
                if(box_id!=null) {
                    String a[]=new String[7];
                    Bundle bundle = new Bundle();

                    a[0]="UserA";
                    if(personal_mydevice_bindA.getText().toString().equals("绑定"))
                    {
                        a[1]="0";
                    }else {
                        a[1]="1";
                    }
                    if (code.equals("")) {
                        a[2] = box_sn;
                    }else {
                        a[2] = code;
                    }
                    if (relationA == relation)
                    {
                        a[3] = user_id;
                    }else {
                        a[3] = user_idA;
                    }
                    a[4] = relationA+"";
                    a[5] = nameA;
                    a[6] = phoneA;
                    bundle.putStringArray("data",a);
                    mActivity.pushFragment(new PersonalMydeviceNextFragment(), true, bundle);
                }else {
                    SAToast.makeText(getContext(),"请先绑定盒子!").show();
                }
                break;
            case R.id.personal_mydevice_bindB:
                if(box_id!=null) {
                    String a[]=new String[7];
                    Bundle bundle = new Bundle();
                    a[0]="UserB";
                    if(personal_mydevice_bindB.getText().toString().equals("绑定"))
                    {
                        a[1]="0";
                    }else {
                        a[1]="1";
                    }
                    if (code.equals("")) {
                        a[2] = box_sn;
                    }else {
                        a[2] = code;
                    }
                    if (relationB == relation)
                    {
                        a[3] = user_id;
                    }else {
                        a[3] = user_idB;
                    }
                    a[4] = relationB+"";
                    a[5] = nameB;
                    a[6] = phoneB;
                    bundle.putStringArray("data",a);
                    mActivity.pushFragment(new PersonalMydeviceNextFragment(), true, bundle);
                }else {
                    SAToast.makeText(getContext(),"请先绑定盒子!").show();
                }
                break;
            case R.id.personal_mydevice_bindC:
                if(box_id!=null) {
                    String a[]=new String[7];
                    Bundle bundle = new Bundle();
                    a[0]="UserC";
                    if(personal_mydevice_bindC.getText().toString().equals("绑定"))
                    {
                        a[1]="0";
                    }else {
                        a[1]="1";
                    }
                    if (code.equals("")) {
                        a[2] = box_sn;
                    }else {
                        a[2] = code;
                    } if (relationC == relation)
                    {
                        a[3] = user_id;
                    }else {
                        a[3] = user_idC;
                    }
                    a[4] = relationC+"";
                    a[5] = nameC;
                    a[6] = phoneC;
                    bundle.putStringArray("data",a);
                    mActivity.pushFragment(new PersonalMydeviceNextFragment(), true, bundle);
                }else {
                    SAToast.makeText(getContext(),"请先绑定盒子!").show();
                }
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,int[] grantResults) {
        switch (requestCode) {
            case PERSSION_CARAM:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startActivityForResult(new Intent(mActivity, ZxingActivity.class),REQUEST_QRCODE);
                }
                break;
            default:
                break;
        }

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

                    } else {
                        thread_binddevice(user_id,"0");//绑定
                    }

                }else if(requestCode ==Activity.RESULT_FIRST_USER)
                {
                    code =data.getStringExtra("EDIT_SN");
                    System.out.println("code***********"+code);
                    if (!code.equals("1")) {
                        thread_binddevice(user_id,"0");
                    }
                }
                else if(resultCode == 300){
                    //从本地相册扫描后的业务逻辑
                    String code = data.getStringExtra("LOCAL_PHOTO_RESULT");
                    Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    private void thread_binddevice(final String user_id, final String type) {
        //绑定盒子线程
        LoadingDialog.showDialogForLoading(mActivity, "正在加载..", true);
        ok=  new OkHttpClientManager();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home;
                if(type.equals("1")) {  //解绑
                          params_home  = new OkHttpClientManager.Param[]{
                            //主界面
                            new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                            new OkHttpClientManager.Param("sn", box_sn),
                            new OkHttpClientManager.Param("model",sing_title1[1]),
                            new OkHttpClientManager.Param("name", ""),
                            new OkHttpClientManager.Param("user_id", user_id),
                            new OkHttpClientManager.Param("type", type)
                    };
                }else {
                    params_home  = new OkHttpClientManager.Param[]{
                            //主界面
                            new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                            new OkHttpClientManager.Param("sn", code),
                            new OkHttpClientManager.Param("model", ""),//型号
                            new OkHttpClientManager.Param("name", ""),
                            new OkHttpClientManager.Param("user_id", user_id),
                            new OkHttpClientManager.Param("type", type)
                    };
                }
                try {
                    bing_data[0] = ok.postOneHeadAsString(bing_url, device_no, params_home);//体征数据
                    System.out.println("绑定盒子返回的数据:" + bing_data[0]);

                    document = Document.parse(bing_data[0]);//解析主界面绑定的盒子数据

                    if(type.equals("0"))
                    {
                        if(document.getInteger("code")==205)
                        {
                            bind_flage = 1;
                            Message message= new Message();
                            message.what = document.getInteger("code");
                            handler.sendMessage(message);
                            return;
                        }else if(document.getInteger("code")==200){//绑定成功
                            document1 =  document.get("obj",Document.class);//obj目录极数据
                            document2 = document1.get("boxInfo",Document.class);
                            document3 = document2.get("keys",Document.class);
                            famile_id = document1.getString("family_id");
                            box_model = document1.getString("model");
                            box_time = document2.getLong("times")+"";
                            box_id  = document2.getString("box_id");
                            Message msg = new Message();
                            msg.what = 2;
                            handler.sendMessage(msg);
                        }
                    }else {  //解绑后的数据
                        if(document.getInteger("code")==200) {
                            bind_flage = 1;
                            box_userA_flage = 0 ;
                            box_userB_flage = 0 ;
                            box_userC_flage = 0 ;
                            box_array_flage = 0 ;
                            box_id=null;
                            bind_flage =1;
                            Message msg = new Message();
                            msg.what = 1;
                            handler.sendMessage(msg);
                        }else {
                            bind_flage=0;
                        }
                    }
                    LoadingDialog.cancelDialogForLoading();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void init_user() {
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
    private void toast(final String msg) {
        mActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
            }
        });
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

    class MyHander extends Handler {
        //跟新数据handler
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 205:
                    bind_file_pop();
                    break;
                case 1:
                    mydevice_sn.setText("");
                    mydevice_unbind.setText("绑定");
                    mydevice_id.setText("型号:"+"");
                    inint_ui();
                    break;
                case 2://绑定成功
                    mydevice_unbind.setText("解绑");
                    mydevice_sn.setText(code);
                    mydevice_id.setText("型号:"+box_model);
                    bind_flage = 0;
                    init_user();
                    inint_ui();
                    break;
            }
        }
    }

    public  static  String init_ABC_name(int i,int j) {
        String name = null;
        switch (i)//查看与当前用户的关系
        {
            case 1:
          /*      if (j == relation)//判断是不是当前登录的用户
                {
              *//*      name = "自己";
                } else {*/
                    name = "父亲";
               // }
                break;
            case 2:
              //  if (j == relation)//判断是不是当前登录的用户
              //  {
         /*           name = "自己";
                } else {*/
                    name = "母亲";
               // }
                break;
            case 3:
               // if (j == relation)//判断是不是当前登录的用户
             //   {
            /*        name = "自己";
                } else {*/
                    name = "祖父";
               // }
                break;
            case 4:
              //  if (j == relation)//判断是不是当前登录的用户
              //  {
           /*         name = "自己";
                } else {*/
                    name = "祖母";
               // }
                break;
            case 5:
               // if (j == relation)//判断是不是当前登录的用户
              //  {
        /*            name = "自己";
                } else {*/
                    name = "外祖父";
               // }
                break;
            case 6:
                if (j == relation)//判断是不是当前登录的用户
              //  {
            /*        name = "自己";
                } else {*/
                    name = "外祖父";
               // }
                break;
            case 7:
               // if (j == relation)//判断是不是当前登录的用户
              //  {
       /*             name = "自己";
                } else {*/
                    name = "儿子";
               // }
                break;
            case 8:
              //  if (j == relation)//判断是不是当前登录的用户
               // {
          /*          name = "自己";
                } else {*/
                    name = "女儿";
               // }
                break;
            case 9:
               // if (j == relation)//判断是不是当前登录的用户
                //{
          /*          name = "自己";
                } else {*/
                    name = "丈夫";
               // }
                break;
            case 10:
               // if (j == relation)//判断是不是当前登录的用户
              //  {
        /*            name = "自己";
                } else {*/
                    name = "妻子";
              //  }
                break;
            default:
                name = "其他";
                break;
        }
        return name;
    }

    public void show(View v){
        //实例化建造者
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //设置警告对话框的标题
        builder.setTitle("解绑盒子");
        //设置警告显示的图片
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置警告对话框的提示信息
        builder.setMessage("解绑盒子后将不能收到测量数据和医生服务");
        //设置”正面”按钮，及点击事件
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                thread_binddevice(user_id,"1");//解绑
            }
        });
        //设置“反面”按钮，及点击事件
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //设置“中立”按钮，及点击事件
        builder.setNeutralButton("等等看吧", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        //显示对话框
        builder.show();
    }
}
