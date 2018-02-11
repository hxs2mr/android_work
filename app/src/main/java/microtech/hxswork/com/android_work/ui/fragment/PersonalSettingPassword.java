package microtech.hxswork.com.android_work.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.bson.Document;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.api.OkHttpClientManager;
import microtech.hxswork.com.android_work.contract.AllContract;
import microtech.hxswork.com.android_work.model.AllModelImpl;
import microtech.hxswork.com.android_work.presenter.AllpresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.LoginActivity;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.AccBean;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.MD5;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_model;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.box_time;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.famile_id;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.getStringToDate;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.password;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.bing_url;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.device_no;

/**
 * Created by microtech on 2017/9/27.
 */

public class PersonalSettingPassword extends BaseFragment<AllpresenterImpl,AllModelImpl>implements AllContract.View, View.OnClickListener {

    @Bind(R.id.personal_seting_back)
    ImageView personal_seting_back;

    @Bind(R.id.personal_seting_back_linear)
    LinearLayout personal_seting_back_linear;

    @Bind(R.id.setting_yanjin)
    ImageView setting_yanjin;

    @Bind(R.id.setting_password_old)
    EditText old_password;

    @Bind(R.id.setting_password_new1)
    EditText new_password1;

    @Bind(R.id.setting_password_new2)
    EditText new_password2;

    @Bind(R.id.setting_password_quren)
    TextView setting_password_quren;


    HomeActivity mActivity;
    String forget_url= "https://doc.newmicrotech.cn/otsmobile/app/user_update_password?";//修改密码的接口
    OkHttpClientManager ok;
    String bing_data;
    Document document;
    Handler handler;
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_setting_xiugaipassword;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
           mActivity= (HomeActivity) getActivity();
           old_password.addTextChangedListener(new old_password_name()); //监听edittext的文本变化
           setting_yanjin.setOnClickListener(this);
           personal_seting_back_linear.setOnClickListener(this);
            setting_password_quren.setOnClickListener(this);
           handler = new MyHander();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.setting_yanjin:
                if(!old_password.getText().toString().equals("")) {
                    old_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
                break;
            case R.id.setting_password_quren://确认修改的网络请求
                if(!old_password.getText().toString().equals("")&& !new_password1.getText().toString().equals("")&&!new_password2.getText().toString().equals(""))
                {

                if(old_password.getText().toString().trim().equals(password)) {
                    if(new_password1.getText().toString().trim().equals(new_password2.getText().toString().trim()))
                    {
                        thread_binddevice();
                    }else {
                        SAToast.makeText(getContext(),"两次新密码不一致").show();
                    }
                }else {
                    SAToast.makeText(getContext(),"原始密码不对").show();
                }
                }else {
                    SAToast.makeText(getContext(),"密码不能为空").show();
                }
                break;
            case R.id.personal_seting_back_linear:
                mActivity.onBackPressed();
                break;
        }
    }

    private void thread_binddevice() {
        //绑定盒子线程
        LoadingDialog.showDialogForLoading(mActivity, "修改中..", true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                final OkHttpClientManager.Param[] params_home = new OkHttpClientManager.Param[]{
                        //主界面
                        new OkHttpClientManager.Param("time_str", "" + getStringToDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date()))),//时间
                        new OkHttpClientManager.Param("phone", user),
                        new OkHttpClientManager.Param("password",  MD5(new_password1.getText().toString().trim()))

                };
                try {
                    bing_data = ok.postHeadAsString(forget_url,device_no, "user_update_password", params_home);
                    System.out.println("绑定盒子返回的数据:" + bing_data);
                    //添加逻辑判断
                    document = Document.parse(bing_data);//解析主界面绑定的盒子数据
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
                    }else  if(document.getInteger("code")==200){//次u个成功
                        Message message = new Message();
                        message.what = 1;
                        handler.sendMessage(message);
                    }else {
                        LoadingDialog.cancelDialogForLoading();
                    }
                    //    ***********
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }
    class MyHander extends Handler {
        //跟新数据handler
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            SAToast.makeText(getContext(),"修改成功!").show();
            LoadingDialog.cancelDialogForLoading();
            mActivity.onBackPressed();
        }
    }

    class old_password_name implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            setting_yanjin.setVisibility(View.INVISIBLE);
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            setting_yanjin.setVisibility(View.VISIBLE);
        }

        @Override
        public void afterTextChanged(Editable editable) {
            String username = old_password.getText().toString();
            if (username.equals("")) {
                setting_yanjin.setVisibility(View.INVISIBLE);
            } else
            {
                setting_yanjin.setVisibility(View.VISIBLE);
            }

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
}
