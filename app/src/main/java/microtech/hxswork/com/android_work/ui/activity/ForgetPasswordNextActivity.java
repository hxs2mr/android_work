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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.bean.Sms_UrlBean;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.AccBean;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.MD5;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;

/**
 * Created by microtech on 2017/9/30.
 */

public class ForgetPasswordNextActivity extends AppCompatActivity {

    TextView forgetpassword_ok ;
    LinearLayout forget_linear;
    ImageView forpassword_exit ;
    EditText edittext_forgetpass_password ;
    EditText edittext_forgetpass_password_some;
    ImageView forgetpass_look ;
    ImageView forgetpass_look2 ;
    OkHttpClientManager ok;
    final String[] forget_data = {null};
    String forget_url= "https://doc.newmicrotech.cn/otsmobile/app/user_update_password?";
    Sms_UrlBean sms_urlBean;
    String username;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgetpassword_xiugai);
        initview();
    }

    private void initview() {
        ok= new OkHttpClientManager();
        forgetpassword_ok = (TextView) findViewById(R.id.forgetpassword_ok);
        forget_linear = (LinearLayout) findViewById(R.id.forget_linear);
        forpassword_exit = (ImageView) findViewById(R.id.forpassword_exit);
        edittext_forgetpass_password = (EditText) findViewById(R.id.forgetpassword_password);
        edittext_forgetpass_password_some = (EditText) findViewById(R.id.forgetpassword_password_some);
        forgetpass_look = (ImageView) findViewById(R.id.forgetpassword_look);
        forgetpass_look2 = (ImageView) findViewById(R.id.forgetpassword_look_some);
        edittext_forgetpass_password.addTextChangedListener(new forgetpass_edittext_passsword());
        edittext_forgetpass_password_some.addTextChangedListener(new forgetpass_edittext_passsword_some());

        username = getIntent().getStringExtra("forgetpaswword");
        forpassword_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });

        forgetpass_look.setOnClickListener(new View.OnClickListener() {
            //密码查看
            @Override
            public void onClick(View view) {
                if(!edittext_forgetpass_password.getText().toString().equals("")) {
                    edittext_forgetpass_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        forgetpass_look2.setOnClickListener(new View.OnClickListener() {
            //密码查看
            @Override
            public void onClick(View view) {
                if(!edittext_forgetpass_password_some.getText().toString().equals("")) {
                    edittext_forgetpass_password_some.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });


        forgetpassword_ok.setOnClickListener(new View.OnClickListener() {

            //确认修改的网络请求
            @Override
            public void onClick(View view) {
                if (isConnectingToInternet()) {
                    if (!edittext_forgetpass_password.getText().toString().equals("") && !edittext_forgetpass_password_some.getText().toString().equals("")) {
                        if ((edittext_forgetpass_password.getText().toString().length() > 5) && (edittext_forgetpass_password_some.getText().toString().length() > 5)) {
                            if(edittext_forgetpass_password.getText().toString().equals(edittext_forgetpass_password_some.getText().toString()))
                            {
                                LoadingDialog.showDialogForLoading(ForgetPasswordNextActivity.this, "修改密码中请稍等..", true);
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final OkHttpClientManager.Param[] params_login = new OkHttpClientManager.Param[]{
                                                //登录token
                                                new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                                                new OkHttpClientManager.Param("phone", username.replace(" ", "")),
                                                new OkHttpClientManager.Param("password", MD5(edittext_forgetpass_password_some.getText().toString().trim()) )
                                        };

                                        try {
                                            forget_data[0] = ok.postHeadAsString(forget_url, AccBean.getObj(), "user_update_password", params_login);
                                            Gson gson3 = new Gson();
                                            sms_urlBean = gson3.fromJson(forget_data[0], Sms_UrlBean.class);
                                          /*  switch (sms_urlBean.getCode())
                                            {
                                                case 200:
                                                    break;
                                                default:
                                                    toast("验证码错误");
                                                    break;
                                            }*/
                                          if(sms_urlBean.getCode() == 200)
                                          {
                                              SAToast.makeText(ForgetPasswordNextActivity.this, "密码修改成功!").show();
                                              Intent intent = new Intent(ForgetPasswordNextActivity.this,LoginActivity.class);//当用户注册成功之后跳转到登录页
                                              String a[] = new String[2];
                                              a[0] =  username;
                                              a[1] =edittext_forgetpass_password.getText().toString().trim();
                                              intent.putExtra("register",a);
                                              startActivity(intent);
                                              LoadingDialog.cancelDialogForLoading();
                                              finish();
                                          }
                                            LoadingDialog.cancelDialogForLoading();
                                            System.out.println("*******forget acc*******;" + forget_data[0]);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }


                                    }
                                }).start();

                            }else {
                                SAToast.makeText(ForgetPasswordNextActivity.this, "两次密码不相等!").show();
                            }
                        } else {
                            SAToast.makeText(ForgetPasswordNextActivity.this, "新密码不能小于6位").show();
                        }


                    } else {
                        SAToast.makeText(ForgetPasswordNextActivity.this, "新密码不能为空").show();
                    }

                }else {
                    SAToast.makeText(ForgetPasswordNextActivity.this, "未连接网络!").show();
                }
            }
        });
    }

    class forgetpass_edittext_passsword implements TextWatcher {

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
            forgetpass_look2.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            forgetpass_look2.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String password = edittext_forgetpass_password_some.getText().toString();
            if (password.equals("")) {
                forgetpass_look2.setVisibility(View.INVISIBLE);
                edittext_forgetpass_password_some.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else
            {
                forgetpass_look2.setVisibility(View.VISIBLE);
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

}
