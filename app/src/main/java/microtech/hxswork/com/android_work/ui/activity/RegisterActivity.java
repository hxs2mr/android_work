package microtech.hxswork.com.android_work.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import microtech.hxswork.com.android_work.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import microtech.hxswork.com.android_work.Util.CountDownTimerUtils;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.AuthBean;
import microtech.hxswork.com.android_work.bean.LoginBean;
import microtech.hxswork.com.android_work.bean.RegisterBean;
import microtech.hxswork.com.android_work.bean.Sms_UrlBean;
import microtech.hxswork.com.android_work.bean.TokenBean;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.AccBean;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.MD5;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.commom.commonutils.FormatUtil.isMobileNO;

/**
 * Created by microtech on 2017/8/31.
 */

public class RegisterActivity extends AppCompatActivity{
    //注册模块
    PopupWindow restiger_pop;
    EditText edittext_resgister_password;//注册时的密码
    EditText edittext_resgister_name;//注册时的用户名
    EditText edittext_resgiste_phone;//找回用户的验证码

    ImageView register_delete;//注册时的清除用户名
    ImageView register_look;//注册是的查看密码
    int register_int = 0;
    String phone_url = "";//发生短信的地址\
    EditText et_sms_input;//验证码
    TextView restiger_login;//注册
    ImageView forpassword_exit;
    TextView bt_getvercode;//发送手机验证码
    final String[] data = {null};
    final String[] accc_token = {null};
    final String[] register_token = {null};
    final String[] mes_data_check = {null};
    final String[] mes_data = {null};
    OkHttpClientManager ok;

    Sms_UrlBean sms_urlBean;

    String register_url = "https://doc.newmicrotech.cn/otsmobile/app/register?";//
    String sms_url = "https://doc.newmicrotech.cn/otsmobile/app/user_sms_create?";//获取验证码
    String sms_check_url = "http://192.168.1.135:9999/otsmobile/app/user_sms_check?"; //验证验证码
    Handler handler;
    int register_flage=0;
    final OkHttpClientManager.Param[] params_token = new OkHttpClientManager.Param[]{//获取token
            new OkHttpClientManager.Param("skin", "null"), new OkHttpClientManager.Param("procedure", "null")};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_pop_resgister);
        initview();

        handler = new MyHandler();
        forpassword_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register_look.setOnClickListener(new View.OnClickListener() {//密码查看
            @Override
            public void onClick(View view) {
                if(!edittext_resgister_password.getText().toString().equals("")) {
                    edittext_resgister_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        register_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edittext_resgister_name.getText().toString().equals("")) {
                    edittext_resgister_name.setText("");
                }
            }
        });
        bt_getvercode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    String m = edittext_resgister_name.getText().toString();
                String u  = "yyf098598";
                String p = "68df77c9117553d91094c2f30d4b6fbc";
                Random rand = new Random();
                register_int = rand.nextInt(9999);//产生的四位数字的随机数
                phone_url = "https://api.smsbao.com/sms?u=yyf098598&p=68df77c9117553d91094c2f30d4b6fbc&m="+edittext_resgister_name.getText().toString()+"&c="+"【米可】你正在登陆米可APP平台,登陆验证码为："+register_int+"如非本人操作，请勿泄露";
                if(AccBean.getCode() == 200)
                {
                    if (isMobileNO(edittext_resgister_name.getText().toString().trim().replace(" ",""))) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{
                                        //登录token
                                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                        new OkHttpClientManager.Param("type","confirm"),
                                        new OkHttpClientManager.Param("phone", edittext_resgister_name.getText().toString().trim().replace(" ", "")),
                                        new OkHttpClientManager.Param("code","" )
                                };

                                try {
                                    mes_data[0] = ok.postHeadAsString(sms_url, AccBean.getObj(), "user_sms_create", params_login);
                                    Gson gson3 = new Gson();
                                    sms_urlBean = gson3.fromJson(mes_data[0], Sms_UrlBean.class);
                                    System.out.println("*******mes_data acc*******;" + mes_data[0]);
                                    switch (sms_urlBean.getCode())
                                    {
                                        case 200:
                                            Message msg = new Message();
                                            msg.what = 200;
                                            handler.sendMessage(msg);
                                            break;
                                        case 209:
                                            toast("手机号已被注册，请直接登录！");
                                            break;
                                        case 208:
                                            toast("手机号格式不对!");
                                            break;
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else {
                        SAToast.makeText(getApplicationContext(), "手机号不能为空或格式不对!").show();
                        return;
                    }
                }else {
                    LoadingDialog.cancelDialogForLoading();
                    toast("ACC授权失败!");
                    return;
                }
            }
        });

        restiger_login.setOnClickListener(new View.OnClickListener() {  //注册按钮
            @Override
            public void onClick(View view) {

                if (!edittext_resgister_password.getText().toString().equals("") && !edittext_resgister_password.getText().toString().equals("") && !et_sms_input.getText().toString().equals(""))
                {
                    if(isConnectingToInternet()) { //判断网络是否链接
                        LoadingDialog.showDialogForLoading(RegisterActivity.this, "注册中..", true);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {

                                            final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{      //注册token
                                                    new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                                    new OkHttpClientManager.Param("phone", edittext_resgister_name.getText().toString().trim().replace(" ", "")),
                                                    new OkHttpClientManager.Param("password", MD5(edittext_resgister_password.getText().toString().trim())),
                                                    new OkHttpClientManager.Param("code",et_sms_input.getText().toString() ),
                                                    new OkHttpClientManager.Param("channel","Android")

                                            };
                                                    register_token[0] = ok.postHeadAsString(register_url, AccBean.getObj(), "register", params_login);
/*
                                                Document document = JacksonUtil.readJson(login_token[0], Document.class);
                                                Document document1 = document.get("times",Document.class);
                                                System.err.println(document1.get("timed")+"--"+document.getString("times"));
*/
                                                    // toast("登录成功!"+login_token[0]);
                                                    Gson gson3 = new Gson();
                                                    System.out.println("********register******;" + register_token[0]);
                                                    RegisterBean registerBean = gson3.fromJson(register_token[0], RegisterBean.class);
                                                    switch (registerBean.getCode())
                                                    {
                                                        case 200:
                                                            SaveData(AccBean.getObj(),edittext_resgister_name.getText().toString().trim().replace(" ",""), "");//保存文件
                                                            LoadingDialog.cancelDialogForLoading();
                                                            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);//当用户注册成功之后跳转到登录页
                                                            String a[] = new String[2];
                                                            a[0] =  edittext_resgister_name.getText().toString().trim();
                                                            a[1] =edittext_resgister_password.getText().toString().trim();
                                                            intent.putExtra("register",a);
                                                            startActivity(intent);
                                                            toast("注册成功!");
                                                            finish();
                                                            break;
                                                        case 210:
                                                            LoadingDialog.cancelDialogForLoading();
                                                            toast("验证码不正确");
                                                            break;
                                                        case 202:
                                                            LoadingDialog.cancelDialogForLoading();
                                                            toast("用户名也存在");
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

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }else {
                        SAToast.makeText(getApplicationContext(), "网络未链接!").show();
                    }

                }else
                {
                    SAToast.makeText(getApplicationContext(), "用户名,密码,验证码不能为空!").show();
                }
            }
        });
    }
    private void initview() {
        ok = new OkHttpClientManager();
        edittext_resgister_name = (EditText)findViewById(R.id.resgister_username);//输入手机
        edittext_resgister_password = (EditText) findViewById(R.id.resgister_password_look);//密码
        register_delete = (ImageView)findViewById(R.id.register_username_delete);
        register_look =(ImageView)findViewById(R.id.register_look);
        et_sms_input =  (EditText)findViewById(R.id.resgister_phone);//验证码
        restiger_login =  (TextView)findViewById(R.id.restiger_login);//注册
        forpassword_exit = (ImageView)findViewById(R.id.resgister_exit);//退出pop
         bt_getvercode = (TextView) findViewById(R.id.resgister_miao1);

         edittext_resgister_name.addTextChangedListener(new register_edittext_name());//监听edittext的文本变化
        edittext_resgister_password.addTextChangedListener(new register_edittext_passsword());

    }
    class register_edittext_name implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            register_delete.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            register_delete.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String username = edittext_resgister_name.getText().toString();
            if (username.equals("")) {
                register_delete.setVisibility(View.INVISIBLE);
            } else
            {
                register_delete.setVisibility(View.VISIBLE);
            }

        }
    }
    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            CountDownTimerUtils countDownTimerUtils  = new CountDownTimerUtils(bt_getvercode,60000,1000);
            countDownTimerUtils.start();
        }
    }
    class register_edittext_passsword implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            register_look.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            register_look.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String password = edittext_resgister_password.getText().toString();
            if (password.equals("")) {
                register_look.setVisibility(View.INVISIBLE);
                edittext_resgister_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else
            {
                register_look.setVisibility(View.VISIBLE);
            }

        }
    }
    private void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void SaveData(String device_no,String phone,String user_id) {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences(phone, MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("phone", phone);         //根据键值对添加数据
        edit.putString("device_no",device_no);
        edit.putString("user_id",user_id);
        edit.commit();  //保存数据信息
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



}
