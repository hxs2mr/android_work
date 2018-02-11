package microtech.hxswork.com.android_work.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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


import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.CountDownTimerUtils;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.Check_Sms_Bean;
import microtech.hxswork.com.android_work.bean.Sms_UrlBean;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.AccBean;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;

/**
 * Created by microtech on 2017/8/31.
 */

public class ForgePassword extends AppCompatActivity {
    //忘记密码模块
    PopupWindow forgetpassword_pop;
    EditText edittext_forgetpass_password;//找回时的密码
    EditText edittext_forgetpass_password_some;//找回时的密码二遍

    EditText edittext_forgetpass_name;//找回用户时的用户名
    EditText edittext_forgetpass_phone;//找回用户的验证码
    ImageView forgetpass_delete;//注册时的清除用户名
    ImageView forgetpass_look;//注册是的查看密码
    ImageView forgetpass_look_2;//注册是的查看密码
    int forgetpass_int =0 ;
    TextView forgetpassword_ok ;
    LinearLayout next_linear ;
     LinearLayout forget_linear;
    TextView forpassword_next ;
     TextView forgetpassword_miao;
    ImageView forpassword_exit;
    String phone_url = "";//发生短信的地址
    OkHttpClientManager ok;

    String sms_url = "https://doc.newmicrotech.cn/otsmobile/app/user_sms_create?";//获取验证码

    String sms_check_url = "https://doc.newmicrotech.cn/otsmobile/app/user_sms_check?"; //验证验证码

    final String[] forget_next = {null};
    final String[] forget_check_next = {null};
    final String[] forget_data = {null};
    Sms_UrlBean sms_urlBean;
    Check_Sms_Bean check_sms_bean;
    String forget_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_pop_forgetpawword);
        initview();
    }

    private void initview() {
        ok = new OkHttpClientManager();
        forgetpass_delete = (ImageView) findViewById(R.id.forgetpassword_delete);

        edittext_forgetpass_name = (EditText) findViewById(R.id.forgetpassword_username);
        edittext_forgetpass_phone = (EditText) findViewById(R.id.forgetpassword_phon);


        next_linear = (LinearLayout) findViewById(R.id.next_linear);

        forpassword_next = (TextView) findViewById(R.id.forpassword_next);//下一步
        forgetpassword_miao = (TextView) findViewById(R.id.forgetpassword_miao);
        forpassword_exit = (ImageView) findViewById(R.id.forpassword_exit1);


            forgetpassword_miao.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!edittext_forgetpass_name.getText().toString().trim().equals("")&& edittext_forgetpass_name.length()>10) {
                        CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(forgetpassword_miao, 60000, 1000);//发送手机验证码 timer验证码
                        mCountDownTimerUtils.start();
                        String m = edittext_forgetpass_phone.getText().toString();
                        String u = "yyf098598";
                        String p = "68df77c9117553d91094c2f30d4b6fbc";
                        Random rand = new Random();
                        forgetpass_int = rand.nextInt(9999);//产生的四位数字的随机数
                        phone_url = "https://api.smsbao.com/sms?u=yyf098598&p=68df77c9117553d91094c2f30d4b6fbc&m=" + edittext_forgetpass_phone.getText().toString() + "&c=" + "【米可】你正在登陆米可APP平台,登陆验证码为：" + forgetpass_int + "如非本人操作，请勿泄露";
                        // new MyPhone().start();

                        if(AccBean.getCode() == 200)
                        {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{
                                            //登录token
                                            new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                            new OkHttpClientManager.Param("type", "forget"),
                                            new OkHttpClientManager.Param("phone", edittext_forgetpass_name.getText().toString().trim().replace(" ", "")),
                                            new OkHttpClientManager.Param("code","" )
                                    };

                                    try {
                                        forget_next[0] = ok.postHeadAsString(sms_url, AccBean.getObj(), "user_sms_create", params_login);
                                        Gson gson3 = new Gson();
                                        sms_urlBean = gson3.fromJson(forget_next[0], Sms_UrlBean.class);
                                        System.out.println("******* acc*******;" + forget_next[0]);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }).start();

                        }else {
                            LoadingDialog.cancelDialogForLoading();
                            toast("ACC授权失败!");
                            return;
                        }
                    } else{
                        SAToast.makeText(getApplicationContext(), "手机号不能为空或格式不对").show();
                    }
                }
            });

        //*******  show_forgetpassword_pop();
        edittext_forgetpass_name.addTextChangedListener(new forgetpass_edittext_name());//监听edittext_phone的文本变化

        forpassword_next.setOnClickListener(new View.OnClickListener() {   //下一步验证验证码
            @Override
            public void onClick(View view) {
                if (!edittext_forgetpass_phone.getText().toString().equals("") && !edittext_forgetpass_name.getText().toString().equals("")) {

                    new Thread(new Runnable() {
                        @Override
                        public void run() {

                            final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{
                                    //登录token
                                    new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                    new OkHttpClientManager.Param("type", "forget"),
                                    new OkHttpClientManager.Param("phone", edittext_forgetpass_name.getText().toString().trim().replace(" ", "")),
                                    new OkHttpClientManager.Param("code",edittext_forgetpass_phone.getText().toString().trim() )
                            };

                            try {
                                forget_check_next[0] = ok.postHeadAsString(sms_check_url, AccBean.getObj(), "user_sms_check", params_login);
                                Gson gson3 = new Gson();
                                check_sms_bean = gson3.fromJson(forget_check_next[0], Check_Sms_Bean.class);
                                System.out.println("*******check_acc_code*******;" + check_sms_bean.getCode());
                                if(check_sms_bean.getCode() == 200)
                                {
                                    Intent intent = new Intent(ForgePassword.this,ForgetPasswordNextActivity.class);//当用户注册成功之后跳转到登录页
                                    String a=edittext_forgetpass_name.getText().toString().trim();
                                    intent.putExtra("forgetpaswword",a);
                                    startActivity(intent);
                                }else
                                {
                                    toast("验证码错误");
                                }

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }else
                {
                    SAToast.makeText(getApplicationContext(), "用户名和验证码不能为空").show();
                }

            }

        });

        forpassword_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        forgetpass_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edittext_forgetpass_name.getText().toString().equals("")) {
                    edittext_forgetpass_name.setText("");
                }
            }
        });
    }


    class forgetpass_edittext_name implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_delete.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_delete.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String username = edittext_forgetpass_name.getText().toString();
            if (username.equals("")) {
                forgetpass_delete.setVisibility(View.INVISIBLE);
            } else
            {
                forgetpass_delete.setVisibility(View.VISIBLE);
            }
        }
    }
    class forgetpass_edittext_passsword implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_look.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_look.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String password = edittext_forgetpass_password.getText().toString();
            if (password.equals("")) {
                forgetpass_look.setVisibility(View.INVISIBLE);
                edittext_forgetpass_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else
            {
                forgetpass_look.setVisibility(View.VISIBLE);
            }

        }
    }
    class forgetpass_edittext_passsword_some implements TextWatcher{

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_look_2.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_look_2.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String password = edittext_forgetpass_password_some.getText().toString();
            if (password.equals("")) {
                forgetpass_look_2.setVisibility(View.INVISIBLE);
                edittext_forgetpass_password_some.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else
            {
                forgetpass_look_2.setVisibility(View.VISIBLE);
            }

        }
    }
    private void toast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ForgePassword.this,msg,Toast.LENGTH_SHORT).show();
            }
        });
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
