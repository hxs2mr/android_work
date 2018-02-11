package microtech.hxswork.com.android_work.ui.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import microtech.hxswork.com.android_work.R;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.UmengTool;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.bson.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.Util.Constants;
import microtech.hxswork.com.android_work.Util.Defaultcontent;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.Util.ShareUtils;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.Home_Bean1;
import microtech.hxswork.com.android_work.contract.PersonalContract;
import microtech.hxswork.com.android_work.model.PersonalModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalPresenter;
import microtech.hxswork.com.android_work.ui.activity.CaseActivity;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.LoginActivity;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;
import microtech.hxswork.com.zxing.ZxingActivity;

import static android.R.attr.description;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_array_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_model;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_time;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.famile_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_head_image;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.start_name;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.bing_url;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.service_flage;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userA_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userB_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_userC_flage;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.relationC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user_idC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.nameC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.phoneC;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayA;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayB;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.birthdayC;
/**
 * Created by microtech on 2017/8/22.个人中心
 */

public class PersonalFragment extends BaseFragment<PersonalPresenter,PersonalModelImpl> implements PersonalContract.View, View.OnClickListener {

    @Bind(R.id.personal_setting)
    ImageView imageView_setting;//设置功能

    @Bind(R.id.personal_headimage)
    ImageView personal_headimage;//用户头像

    @Bind(R.id.personal_username)
    TextView personal_username;///用户名称

    @Bind(R.id.personal_linear_device)
    LinearLayout personal_linear_device;//我的设备

    @Bind(R.id.personal_device_bd)
    TextView personal_device_bd;//设备绑定状态

    @Bind(R.id.personal_linear_mydoctor)
    LinearLayout personal_linear_mydoctor;//我的医生

    @Bind(R.id.personal_linear_heathily)
    LinearLayout personal_linear_heathily;//健康知识库

    @Bind(R.id.personal_linear_option)
    LinearLayout personal_linear_option;//吐槽入口

    @Bind(R.id.personal_linear_share)
    LinearLayout personal_linear_share;//分享
    @Bind(R.id.personal_ourdata)
    LinearLayout personal_ourdata;
    @Bind(R.id.personal_setting_linear)
            LinearLayout personal_setting_linear;
    PopupWindow share_pop;
    PopupWindow bind_pop;
    HomeActivity mActivity;
    String app_id = "wxace207babfef510d";
    OkHttpClientManager ok;
    String our_Data[]={null};
    String our_url = "http://doc.newmicrotech.cn:8080/otsmobile/app/userInfo?";
    String bing_data[]={null};
    Document document;
    Document document1;
    Document document2;
    Document document3;
    Document document4;
    String code;
    private  final  int PERSSION_CARAM = 100;
    private static final int REQUEST_QRCODE = 0x01;
    Handler handlera ;
   public static   PopupWindow photo_pop;
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor((Activity) getContext(), ContextCompat.getColor(getContext(),R.color.main_xia));
        mActivity= (HomeActivity) getActivity();
        handlera = new MyHander();
        mActivity.mFlToolbar.setVisibility(View.VISIBLE);
        personal_headimage.setOnClickListener(this);
        personal_linear_device.setOnClickListener(this);//我的设备
        personal_linear_mydoctor.setOnClickListener(this);//我的医生
        personal_linear_heathily.setOnClickListener(this);//健康知识库
        personal_linear_option.setOnClickListener(this);//吐槽
        personal_linear_share.setOnClickListener(this);//分享
        personal_ourdata.setOnClickListener(this);
        personal_setting_linear.setOnClickListener(this);
        System.out.println("********************************************");

        Glide.with(mActivity)
                .load("http://ov62zyke0.bkt.clouddn.com/"+start_head_image)
                .placeholder(R.mipmap.deadpool)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {

                        if(personal_headimage!=null) {
                            personal_headimage.setImageDrawable(resource);
                        }
                    }
                });
        if(start_name!=null) {
            personal_username.setText(start_name);
        }else {
            personal_username.setText("用户一");
        }
        if (box_id==null) {
            personal_device_bd.setVisibility(View.VISIBLE);
        }else {
            personal_device_bd.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.personal_setting_linear://设置
                mActivity.pushFragment(new PersonalSettingFragment(),true);
                break;
            case R.id.personal_ourdata://用户头像
                mActivity.pushFragment(new PersonalOurdataFragment() , true);
                break;
            case R.id.personal_linear_device:////我的设备
                if(box_id == null)
                {
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
                else {
                    String a[] = new String[3];
                    Bundle bundle = new Bundle();
                    a[0]=box_id;
                    a[1]=box_model;
                    a[2]="1";//标识当前数是也绑定设备的状态
                    bundle.putStringArray("data",a);
                    mActivity.pushFragment(new PersonalMydeviceFragment(), true,bundle);
                }
                break;
            case R.id.personal_linear_mydoctor://我的医生
                mActivity.startActivity(new Intent(mActivity,CaseActivity.class));
                break;
            case R.id.personal_linear_heathily://健康知识库
                mActivity.pushFragment(new PersonalHeathlyFragment(),true);
                break;
            case R.id.personal_linear_option://吐槽
                mActivity.startActivity(new Intent(mActivity,PersonalOptionFragment.class));
                break;
            case R.id.personal_linear_share://分享
                show_cookie_pop();
                break;
            case R.id.personal_headimage:
                if(!start_head_image.equals(""))
                {
                    dialog_photo(mActivity);
                }
                break;
        }

    }

    private void show_cookie_pop() {
        final View view = LayoutInflater.from(mActivity).inflate(R.layout.personal_share_pop, null);
        LinearLayout share_qq_linear = view.findViewById(R.id.share_linear_qq);
        LinearLayout share_wxyou_linear= view.findViewById(R.id.share_linear_wxyou);
        LinearLayout share_wxpeng_linear= view.findViewById(R.id.share_linear_wxpeng);
        LinearLayout share_weibo_linear= view.findViewById(R.id.share_linear_weibo);

        share_pop = new PopupWindow(view);
        share_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        share_pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        share_pop.setAnimationStyle(R.style.LoginPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha=0.5f;
        mActivity.getWindow().setAttributes(lp);
        share_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        share_pop.setOutsideTouchable(true);
        share_pop.setFocusable(true);
        share_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);

        share_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });
        share_qq_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(Build.VERSION.SDK_INT>=23){
                    String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
                    ActivityCompat.requestPermissions(mActivity,mPermissionList,123);
                }
                ShareUtils.shareWeb(mActivity, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.deadpool, SHARE_MEDIA.QZONE
                ,0);
                LoadingDialog.cancelDialogForLoading();
                share_pop.dismiss();
            }

        });
        share_weibo_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //UmengTool.getSignature(getContext());

                ShareUtils.shareWeb(mActivity, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.deadpool, SHARE_MEDIA.SINA
                ,1);
                share_pop.dismiss();
            }
        });


        share_wxpeng_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //1，创建一个WXWebPagerObject对象  用于封装要发送的url
                if (!isWebchatAvaliable()) {
                    SAToast.makeText(getContext(), "请先安装微信").show();
                    return;
                }
                ShareUtils.shareWeb(mActivity, Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.deadpool, SHARE_MEDIA.WEIXIN_CIRCLE
                ,0);
                share_pop.dismiss();
            }
        });
        share_wxyou_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWebchatAvaliable()) {
                    SAToast.makeText(getContext(), "请先安装微信").show();
                    return;
                }
                ShareUtils.shareWeb((Activity) getContext(), Defaultcontent.url, Defaultcontent.title
                        , Defaultcontent.text, Defaultcontent.imageurl, R.mipmap.deadpool, SHARE_MEDIA.WEIXIN
                ,0);
                share_pop.dismiss();
            }
        });
    }


    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    SAToast.makeText(getContext(), "QQ分享成功").show();
                    break;
                case 2:
                    SAToast.makeText(getContext(),"取消分享").show();
                    break;
                case 3:
                    SAToast.makeText(getContext(), "分享失败" + msg.obj).show();
                    break;
                case 4:
                    SAToast.makeText(getContext(), "微博分享成功" ).show();
                    break;
                case 5:
                    SAToast.makeText(getContext(), "微博分享失败" + msg.obj).show();
                    break;
                case 6:
                    SAToast.makeText(getContext(), "微博取消分享").show();
                    break;
                case 205:
                    bind_file_pop();
                    break;
                case 200:
                    break;
                default:
                    break;
            }
        }

    };
    //微信分享的唯一标识
    private  String buld(String text)
    {
        return (text == null) ? String.valueOf(System.currentTimeMillis()):text+System.currentTimeMillis();//毫秒
    }

    //bitmap转换成byte的数组

    private  byte[] bmptoarray(Bitmap bitmap , boolean need)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
        if(need)
        {
            bitmap.recycle();

        }
        byte[] result = outputStream.toByteArray();

        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        UMShareAPI.get(getContext()).onActivityResult(requestCode, resultCode, data);
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
                    } else {
                        System.out.println("code***********"+code);
                        //盒子的绑定
                        thread_binddevice(user_id);
                    }
                }else if(requestCode ==Activity.RESULT_FIRST_USER)
                {
                     code =data.getStringExtra("EDIT_SN");
                    System.out.println("code***********"+code);
                    if (!code.equals("1")) {
                        thread_binddevice(user_id);
                    }
                }
                else if(resultCode == 300){
                    //从本地相册扫描后的业务逻辑
                     code = data.getStringExtra("LOCAL_PHOTO_RESULT");
                    Toast.makeText(getContext(), code, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void thread_binddevice(final String user_id) {
        //绑定盒子线程
        LoadingDialog.showDialogForLoading(mActivity, "正在加载..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("sn", code),
                        new OkHttpClientManager.Param("model", ""),
                        new OkHttpClientManager.Param("name", ""),
                        new OkHttpClientManager.Param("user_id", user_id),
                        new OkHttpClientManager.Param("type", "0")

                };
                try {
                    bing_data[0] = ok.postOneHeadAsString(bing_url, device_no, params_home);//体征数据
                    System.out.println("绑定盒子返回的数据:" + bing_data[0]);
                    //添加逻辑判断
                    document = Document.parse(bing_data[0]);//解析主界面绑定的盒子数据
                    if(document.getInteger("code")==205)
                    {
                        Message message = new Message();
                        message.what= document.getInteger("code");
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
                    document1 =  document.get("obj",Document.class);//obj目录极数据
                    document2 = document1.get("boxInfo",Document.class);
                    document3 = document2.get("keys",Document.class);

                    famile_id = document1.getString("family_id");
                    box_model = document1.getString("model");
                    box_time = document2.getLong("times")+"";
                    box_id  = document2.getString("box_id");
                    String a[] = new String[3];
                    a[0]=code;
                    a[1]=box_model;
                    a[2]="1";//标识当前数是也绑定设备的状态
                    Message message = new Message();
                    message.what = 1;
                    message.obj = a;
                    handlera.sendMessage(message);
                    LoadingDialog.cancelDialogForLoading();
                   //    ***********
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
            box_array_flage =1;
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
            box_array_flage =1;
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
            box_array_flage =1;
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



    public static void dialog_photo(final Activity mActivity)//查看头像的大图
    {
        LayoutInflater inflater = LayoutInflater.from(mActivity);
        final View imgEntryView = inflater.inflate(R.layout.dialog_photo_entry, null); // 加载自定义的布局文件
        final ImageView img = (ImageView)imgEntryView.findViewById(R.id.large_image);
        photo_pop= new PopupWindow(imgEntryView);
        photo_pop.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        photo_pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        photo_pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha=0.5f;
        mActivity.getWindow().setAttributes(lp);
        photo_pop.setBackgroundDrawable(mActivity.getResources().getDrawable(R.color.transparent));
        photo_pop.setOutsideTouchable(true);
        photo_pop.setFocusable(true);
        photo_pop.showAtLocation(imgEntryView, Gravity.BOTTOM, 0, 0);
        Glide.with(mActivity)
                .load("http://ov62zyke0.bkt.clouddn.com/"+start_head_image)
                .placeholder(R.mipmap.deadpool)
                .into(new SimpleTarget<GlideDrawable>() {
                    @Override
                    public void onResourceReady(GlideDrawable resource,
                                                GlideAnimation<? super GlideDrawable> glideAnimation) {
                        img.setImageDrawable(resource);
                    }
                });
        img.startAnimation(AnimationUtils.loadAnimation(mActivity, R.anim.x_anim4));//加载动画
        // 点击布局文件（也可以理解为点击大图）后关闭dialog，这里的dialog不需要按钮
        imgEntryView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramView) {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1.0f;
                mActivity.getWindow().setAttributes(lp);
                photo_pop.dismiss();
            }
        });
        photo_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
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
            String a[] = (String[]) msg.obj;
            Bundle bundle = new Bundle();
            bundle.putStringArray("data",a);
            mActivity.pushFragment(new PersonalMydeviceFragment(),true,bundle);
            personal_device_bd.setVisibility(View.INVISIBLE);
            init_user();
        }
    }

    private boolean isWebchatAAvaliable() {
        //检测手机上是否安装了微信
        try {
            mActivity.getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
