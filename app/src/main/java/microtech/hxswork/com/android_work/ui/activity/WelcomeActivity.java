package microtech.hxswork.com.android_work.ui.activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nimlib.sdk.NimIntent;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.msg.model.IMMessage;


import java.util.ArrayList;
import java.util.List;

import microtech.hxswork.com.android_work.DemoCache;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.Util.Extras;
import microtech.hxswork.com.android_work.Util.Preferences;
import microtech.hxswork.com.android_work.Util.SysInfoUtil;

import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.accid;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.token;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;

/**
 * Created by microtech on 2017/9/22.
 */

public class WelcomeActivity extends AppCompatActivity{

    private static final String TAG = "WelcomeActivity";

    private boolean customSplash = false;

    private static boolean firstEnter = true; // 是否首次进入
    SharedPreferences share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        share = getSharedPreferences(user+"doctor_im", MODE_PRIVATE);//第一次获取医生详情
        if(isAppRunning(getApplicationContext())) {//判断这个APP是否还在后台运行
            showSplashView();
        }else {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void showSplashView() {
        NimUIKit.doLogin(new LoginInfo(accid, token), new RequestCallback<LoginInfo>() {

            @Override
            public void onSuccess(LoginInfo loginInfo) {
                NimUIKit.startP2PSession(WelcomeActivity.this, share.getString("accid","0"),share.getString("name","0"),share.getString("hospital","0"));
                finish();
            }
            @Override
            public void onFailed(int i) {

            }

            @Override
            public void onException(Throwable throwable) {

            }
        });
        customSplash = true;
    }



    private boolean isAppRunning(Context context) {
        String packageName = context.getPackageName();
        String topActivityClassName=getTopActivityName(context);

        if (packageName!=null&&topActivityClassName!=null&&topActivityClassName.startsWith(packageName)) {
            return true;
        } else {
            return false;
        }
    }
    public  String getTopActivityName(Context context){
        String topActivityClassName=null;
        ActivityManager activityManager =
                (ActivityManager)(context.getSystemService(android.content.Context.ACTIVITY_SERVICE )) ;
        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1) ;
        if(runningTaskInfos != null){
            ComponentName f=runningTaskInfos.get(0).topActivity;
            topActivityClassName=f.getClassName();
        }
        return topActivityClassName;
    }
}
