package microtech.hxswork.com.android_work.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bugtags.library.Bugtags;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.RequestCallback;
import com.netease.nimlib.sdk.StatusCode;
import com.netease.nimlib.sdk.auth.AuthService;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.msg.MsgServiceObserve;
import com.netease.nimlib.sdk.msg.model.IMMessage;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.umeng.socialize.UMShareAPI;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import microtech.hxswork.com.android_work.R;

import butterknife.Bind;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.bean.NIMBean;
import microtech.hxswork.com.android_work.receiver.MessageReceiver;
import microtech.hxswork.com.android_work.ui.fragment.DoctorDetaileFragment;
import microtech.hxswork.com.android_work.ui.fragment.HomeFragment;
import microtech.hxswork.com.android_work.ui.fragment.PersonalFragment;
import microtech.hxswork.com.android_work.ui.fragment.ServiceFragment;
import microtech.hxswork.com.android_work.update.DownloadApk;
import microtech.hxswork.com.commom.base.BaseActivity;
import microtech.hxswork.com.commom.commonwidget.LoadingDialog;

import static com.netease.nimlib.sdk.StatusCode.KICKOUT;
import static com.tencent.android.tpush.XGPushManager.registerPush;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.accid;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.list_NIM;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.token;
import static microtech.hxswork.com.android_work.ui.activity.LoginActivity.user;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.user_id;

/**
 * Created by microtech on 2017/8/21.
 */

public class HomeActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    @Bind(R.id.fl_container)
    FrameLayout mFlContainer;
    @Bind(R.id.rb1)
    RadioButton mRb1;
    @Bind(R.id.rb2)
    RadioButton mRb2;
    @Bind(R.id.rb3)
    RadioButton mRb3;
    @Bind(R.id.radiogroup)
    RadioGroup mRadiogroup;
    @Bind(R.id.rb4)
    RadioButton mRb4;
    @Bind(R.id.fl_toolbar)
    public FrameLayout mFlToolbar;
    @Bind(R.id.rv_home)
    LinearLayout mRvHome;
    private long quit_click_time;//用于记录点击返回键的时间
    InputMethodManager imm;
    Fragment mFragment;
    private HomeFragment mHomeFragment;//主界面
    private ServiceFragment mServiceFragment;//服务模块
    private DoctorDetaileFragment mInformationFragment;//消息模块
    private PersonalFragment mPersonalFragment;//个人中心

    OnlineClient onlineClient;
   public static   int flage1=0;
    public static   int flage2=0;
    public static   int flage3=0;
    public static    int flage4=0;
    public static    int flage5=0;
    private MessageReceiver updateListViewReceiver;
    Handler handler;
    int flage =0 ;
    public static   Observer<List<IMMessage>> incomingMessageObserver1;
    @Override
    public int getLayoutId() {
        return R.layout.home_activity;
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public void initView() {
        updateListViewReceiver = new MessageReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("microtech.hxswork.com.android_work.ui.activity.UPDATE_LISTVIEW");
        registerReceiver(updateListViewReceiver, intentFilter);
        mRadiogroup.setOnCheckedChangeListener(this);
        mRb1.setChecked(true);
        StatusCode status = NIMClient.getStatus();
        handler = new Myhandler();
        System.out.println("********在线状态********"+status);
        flage = 0 ;
        XGPushManager.registerPush(getApplicationContext(), user);

       // init_xinge();
        Observer<List<OnlineClient>> clientsObserver = new Observer<List<OnlineClient>>() {
            //注册多端登录
            @Override
            public void onEvent(List<OnlineClient> onlineClients) {
               if (onlineClients == null || onlineClients.size() == 0) {

                    System.out.println("********没有在线的********");
                    return;
                }

                System.out.println("********有在线********");
                OnlineClient client = onlineClients.get(0);
                onlineClient = client;
                NIMClient.getService(AuthService.class).kickOtherClient(client).setCallback(new RequestCallback<Void>() {
                    @Override
                    public void onSuccess(Void param) {
                        System.out.println("********被踢succ********");
                    }

                    @Override
                    public void onFailed(int code) {
                        System.out.println("********被踢********");
                    }

                    @Override
                    public void onException(Throwable exception) {
                        // 踢出其他端错误
                    }
                });
                switch (client.getClientType()) {
                    case ClientType.Windows:
                        // PC端
                        break;
                    case ClientType.MAC:
                        // MAC端
                        break;
                    case ClientType.Web:
                        // Web端
                        break;
                    case ClientType.iOS:
                        System.out.println("********在线iOS********");
                        // IOS端
                        break;
                    case ClientType.Android:
                        System.out.println("********在线Android********");
                        // Android端
                        break;
                    default:
                        break;
                }
            }
        };
        NIMClient.getService(AuthServiceObserver.class).observeOtherClients(clientsObserver, true);

        NIMClient.getService(AuthServiceObserver.class).observeOnlineStatus(
                new Observer<StatusCode> () {
                    public void onEvent(StatusCode status) {
                        if (status.wontAutoLogin()) {
                            flage++;
                            System.out.println("********被踢********");
                            if(flage == 1) {
                                Message msg = new Message();
                                msg.what = 1;
                                handler.sendMessage(msg);
                            }
                        }
                    }
                }, true);
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup,int i) {
        //初始化RadioButton的控件颜色值
        mRb1.setTextColor(getResources().getColor(R.color.font_dkgray));
        mRb2.setTextColor(getResources().getColor(R.color.font_dkgray));
        mRb3.setTextColor(getResources().getColor(R.color.font_dkgray));
        mRb4.setTextColor(getResources().getColor(R.color.font_dkgray));
        FragmentManager fm = this.getSupportFragmentManager();
        switch (i) {
            case R.id.rb1:
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                }
                mFragment = mHomeFragment;
                mRb1.setTextColor(getResources().getColor(R.color.login_type));
                mRb1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.radiobutton_anim));//加载动画
                break;
            case R.id.rb2:
                if (mServiceFragment == null) {
                    mServiceFragment = new ServiceFragment();
                }
                mFragment = mServiceFragment;//服务模块
                mRb2.setTextColor(getResources().getColor(R.color.login_type));
                mRb2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.radiobutton_anim));//加载动画
                break;
            case R.id.rb3:
                if (mInformationFragment == null) {
                    mInformationFragment = new DoctorDetaileFragment();
                }
                mFragment = mInformationFragment;
                mRb3.setTextColor(getResources().getColor(R.color.login_type));
                mRb3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.radiobutton_anim));//加载动画
                break;
            case R.id.rb4:
                if (mPersonalFragment == null) {
                    mPersonalFragment = new PersonalFragment();
                }
                mFragment = mPersonalFragment;
                mRb4.setTextColor(getResources().getColor(R.color.login_type));
                mRb4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.radiobutton_anim));//加载动画
                break;
        }
        FragmentTransaction  beginTransaction = fm.beginTransaction();
        beginTransaction.replace(R.id.fl_container,mFragment);
        beginTransaction.commitAllowingStateLoss();
    }
    @Override
    public void onBackPressed() {
        FragmentManager fm=getSupportFragmentManager();
        int count = fm.getBackStackEntryCount();
        if(count > 0)
        {
            fm.popBackStackImmediate();
        }else
        {
            if ((System.currentTimeMillis() - quit_click_time) > 2000) {
                SAToast.makeText(getApplicationContext(), "再按一次退出程序").show();
                quit_click_time = System.currentTimeMillis();
            } else {
                NIMClient.getService(AuthService.class).logout();//退出登录云信
                registerPush(this, user);//解绑信鸽账号
                SharedPreferences share = getSharedPreferences(user+"bindUser", MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit(); //编辑文件
                edit.putString("bindUser", "0");
                edit.commit();
                finish();
            }

        }
        hideKeyboard();
    }
    @Override
    protected void onDestroy() {
        DownloadApk.unregisterBroadcast(this);
        //防止内存泄漏
        UMShareAPI.get(HomeActivity.this).release();
        super.onDestroy();
    }
    /**
     * Fragment界面切换
     * boolean pushStack 是否设置Fragment压栈切换
     */
    public void pushFragment(Fragment fragment, boolean pushStack) {
        pushFragment(fragment, pushStack, null);
    }
    public void pushFragment(Fragment fragment, boolean pushStack, Bundle bundle) {
        // Fragment传参
        if (bundle != null) {
            fragment.setArguments(bundle);
        }
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (pushStack) {
            ft.setCustomAnimations(R.anim.trans_next_in, R.anim.trans_next_out,
                    R.anim.trans_pre_in, R.anim.trans_pre_out);
            ft.addToBackStack("MyStack");
        } else {
            int count = fm.getBackStackEntryCount();
            if (count != 0) {
                for (int j = 0; j <= count; j++) {
                    if (fm.getBackStackEntryCount() > 0) {
                        fm.popBackStack();
                    }
                }
            }
        }
        ft.replace(R.id.fl_container, fragment);
        ft.commitAllowingStateLoss();
        //隐藏键盘，如果已打开
        hideKeyboard();
    }
    /**
     * 隐藏输入法
     */
    public void hideKeyboard() {
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mRb4.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public static void start(Context context, Intent extras) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected void onResume() {
        super.onResume();
        //注：回调 1
        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //注：回调 2
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        //注：回调 3
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }

    public void show(){
        //实例化建造者
        AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
        //设置警告对话框的标题
        builder.setTitle("账号异常");
        builder.setCancelable(false);
        //设置警告显示的图片
//        builder.setIcon(android.R.drawable.ic_dialog_alert);
        //设置警告对话框的提示信息
        builder.setMessage("你的账号在另外一台手机上登录。如非本人操作，则密码可能泄露,建议在登录页通过手机修改密码");
        //设置”正面”按钮，及点击事件
        builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                flage=0;
                doLogin();
                dialog.cancel();
            }
        });
        //设置“反面”按钮，及点击事件
        builder.setNegativeButton("退出", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                flage=0;
                NIMClient.getService(AuthService.class).logout();//退出登录云信
                registerPush(HomeActivity.this, user);//解绑信鸽账号
                SharedPreferences share = getSharedPreferences("bindUser", MODE_PRIVATE);
                SharedPreferences.Editor edit = share.edit(); //编辑文件
                edit.putString("bindUser", "0");
                edit.commit();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                dialog.cancel();
            }

        });
        //显示对话框
        if( flage==1) {
            builder.show();
        }
    }

    public void doLogin()
    {

        LoginInfo info = new LoginInfo(accid,token);

        RequestCallback<LoginInfo> callback = new RequestCallback<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo param) {
                StatusCode status = NIMClient.getStatus();
                System.out.println("********新在线状态********"+status);
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
    class Myhandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            show();
        }
    }
    private void init_xinge() {

        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("microtech.hxswork.com.android_work.receiver.UPDATE_LISTVIEW");
        Context context = this;
        XGPushConfig.enableDebug(this, true);//线上不能使用
        //信鸽注册代码
        XGPushManager.registerPush(context,user,new XGIOperateCallback() {
            @Override
            public void onSuccess(Object data, int flag) {
                System.out.println("******信鸽注册成功*****"+data);
            }
            @Override
            public void onFail(Object data, int errCode, String msg) {
                System.out.println("******信鸽注册失败*****错误码"+ errCode + ",错误信息：" + msg);

            }
        });
    }
}
