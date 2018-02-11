package microtech.hxswork.com.android_work.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
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


import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.AuthService;

import microtech.hxswork.com.android_work.R;

import butterknife.Bind;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.contract.PersonalContract;
import microtech.hxswork.com.android_work.model.PersonalModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalPresenter;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.LoginActivity;
import microtech.hxswork.com.commom.base.BaseFragment;

import static android.content.Context.MODE_PRIVATE;
import static com.tencent.android.tpush.XGPushManager.registerPush;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.commom.commonutils.DataCleanManager.clearAllCache;
import static microtech.hxswork.com.commom.commonutils.DataCleanManager.getTotalCacheSize;


/**
 * Created by microtech on 2017/8/31.个人中心设置界面
 */

public class PersonalSettingFragment extends BaseFragment<PersonalPresenter,PersonalModelImpl> implements PersonalContract.View, View.OnClickListener {
    @Bind(R.id.personal_seting_back_linear)
    LinearLayout personal_seting_back_linear;//返回

    @Bind(R.id.personal_setting_xiugai)
    LinearLayout personal_setting_xiugai;//修改密码

    @Bind(R.id.personal_setting_cookie)
    LinearLayout personal_setting_cookie;//清除缓存

    @Bind(R.id.cookite_size)
    TextView cookite_size;//缓存大小


    @Bind(R.id.personal_setting_our)
    LinearLayout personal_setting_our;//关于我们

    @Bind(R.id.personal_setting_exit)
    TextView personal_setting_exit;//退出账号

    HomeActivity mActivity;

    //修改密码模块
    PopupWindow restiger_pop;//忘记密码
    ImageView setting_yanjin;//密码可见
    EditText old_password;

    //清除缓存模块
    PopupWindow cookie_pop;

    @Override
    protected int getLayoutResource() {
        return R.layout.personal_setting_frgment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.GONE);
        personal_setting_xiugai.setOnClickListener(this);
        personal_seting_back_linear.setOnClickListener(this);
        personal_setting_cookie.setOnClickListener(this);
        personal_setting_our.setOnClickListener(this);
        personal_setting_exit.setOnClickListener(this);//退出账号
        try {
            cookite_size.setText(getTotalCacheSize(getContext()));//获取缓存大小
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.personal_setting_xiugai://修改密码
                mActivity.pushFragment(new PersonalSettingPassword(),true);
                //show_restiger_pop();
                break;
            case R.id.personal_setting_cookie://清除缓存
                show_cookie_pop();
                break;
            case R.id.personal_setting_our://关于我们
                Bundle bundle = new Bundle();
                bundle.putString("data","our");
                mActivity.pushFragment(new AllFragment(),true,bundle);
                break;
            case R.id.personal_setting_exit: //退出账号
                mActivity.startActivity(LoginActivity.class);
                NIMClient.getService(AuthService.class).logout();
                registerPush(getContext(), user);//解绑信鸽账号
                SharedPreferences share = getContext().getSharedPreferences("bindUser", MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit(); //编辑文件
                edit.putString("bindUser", "0");
                edit.commit();
                mActivity.finish();
                break;
            case R.id.personal_seting_back_linear:
                mActivity.onBackPressed();
                break;

        }

    }

   /* private void show_our() {
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.personal_setting_our, null);

        ImageView personal_seting_back= view.findViewById(R.id.personal_seting_back);//返回

        restiger_pop = new PopupWindow(view);
        restiger_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        restiger_pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        restiger_pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画   改

        restiger_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        restiger_pop.setOutsideTouchable(true);
        restiger_pop.setFocusable(true);
        restiger_pop.showAtLocation(view, Gravity.CENTER, 0, 0);
        restiger_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        personal_seting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restiger_pop.dismiss();
            }
        });

    }*/


    private void show_cookie_pop() {
        // mokHttpClientManager = new OkHttpClientManager();//网络接口
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.personal_setting_cookiepopwindow, null);

            TextView delete_cookie = view.findViewById(R.id.delete_cookie);//清除

            TextView delete_quxiao = view.findViewById(R.id.delete_quxiao);//取消

        cookie_pop = new PopupWindow(view);
        cookie_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        cookie_pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        cookie_pop.setAnimationStyle(R.style.LoginPopWindowAnim);//设置进入和出场动画
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha=0.5f;
        mActivity.getWindow().setAttributes(lp);
        cookie_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        cookie_pop.setOutsideTouchable(true);
        cookie_pop.setFocusable(true);
        cookie_pop.showAtLocation(view, Gravity.BOTTOM, 0, 0);


        cookie_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha=1f;
                mActivity.getWindow().setAttributes(lp);
            }
        });

        delete_cookie.setOnClickListener(new View.OnClickListener() {//清除缓存
            @Override
            public void onClick(View view) {
                clearAllCache(getContext());
                cookite_size.setText("0KB");
                SAToast.makeText(getContext(),"清除成功").show();
                cookie_pop.dismiss();
            }
        });
        delete_quxiao.setOnClickListener(new View.OnClickListener() {//取消
            @Override
            public void onClick(View view) {
                cookie_pop.dismiss();
            }
        });
    }


    private void show_restiger_pop() {
        // mokHttpClientManager = new OkHttpClientManager();//网络接口
        final View view = LayoutInflater.from(getContext()).inflate(R.layout.personal_setting_xiugaipassword, null);

                 ImageView personal_seting_back= view.findViewById(R.id.personal_seting_back);//返回
                 setting_yanjin = view.findViewById(R.id.setting_yanjin);//是否可见密码

                old_password =  (EditText)view.findViewById(R.id.setting_password_old);//验证旧密码
                EditText new_password1 = view.findViewById(R.id.setting_password_new1);//新密码
                EditText new_password2= view.findViewById(R.id.setting_password_new2);//确认新密码
                TextView setting_password_quren = view.findViewById(R.id.setting_password_quren);//确认修改

        restiger_pop = new PopupWindow(view);
        restiger_pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        restiger_pop.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        restiger_pop.setAnimationStyle(R.style.BintPopWindowAnim);//设置进入和出场动画   改

        restiger_pop.setBackgroundDrawable(this.getResources().getDrawable(R.color.white));
        restiger_pop.setOutsideTouchable(true);
        restiger_pop.setFocusable(true);
        restiger_pop.showAtLocation(view, Gravity.CENTER, 0, 0);

        old_password.addTextChangedListener(new old_password_name()); //监听edittext的文本变化

        restiger_pop.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {

            }
        });
        personal_seting_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restiger_pop.dismiss();
            }
        });

        setting_yanjin.setOnClickListener(new View.OnClickListener() {
            //密码查看
            @Override
            public void onClick(View view) {
                if(!old_password.getText().toString().equals("")) {
                    old_password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }
            }
        });
        setting_password_quren.setOnClickListener(new View.OnClickListener() {//确认修改的网络请求
            @Override
            public void onClick(View view) {

            }
        });

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

}
