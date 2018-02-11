package microtech.hxswork.com.android_work;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatDelegate;
import android.widget.RemoteViews;

import com.bugtags.library.Bugtags;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.Observer;
import com.netease.nimlib.sdk.SDKOptions;
import com.netease.nimlib.sdk.StatusBarNotificationConfig;
import com.netease.nimlib.sdk.auth.AuthServiceObserver;
import com.netease.nimlib.sdk.auth.ClientType;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.auth.OnlineClient;
import com.netease.nimlib.sdk.msg.constant.SessionTypeEnum;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.tencent.android.tpush.XGCustomPushNotificationBuilder;
import com.tencent.android.tpush.XGNotifaction;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.XGPushNotifactionCallback;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.util.List;

import microtech.hxswork.com.android_work.Util.SystemUtil;
import microtech.hxswork.com.android_work.ui.activity.InformationActivity;
import microtech.hxswork.com.android_work.ui.activity.WelcomeActivity;
import microtech.hxswork.com.android_work.update.SystemParams;
import microtech.hxswork.com.commom.BuildConfig;
import microtech.hxswork.com.commom.baseapp.BaseApplication;

import static microtech.hxswork.com.android_work.Util.Permession.context;

/**
 * Created by microtech on 2017/8/22.
 */

public class MyApplication extends BaseApplication {
    private static Context mAppContext;
    private Context mContext;
    // 记录是否已经初始化
    private boolean isInit = false;

    public static Context appContext;
    public static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppContext = this;
        SystemParams.init(this);
        Bugtags.start("3905e7f54f9ce98abc3c9bbb7f60a87c", this, Bugtags.BTGInvocationEventBubble);
        PlatformConfig.setWeixin("wxace207babfef510d", "92f333b0f0f8eae8ee65d4860b36a1e9");
        PlatformConfig.setQQZone("1105461976", "2CnuTbECo9181TND");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("1993153674", "f72a21a260a5f716d5f367b880ff952a","http://open.weibo.com/apps/1993153674/privilege/oauth");
        UMShareAPI.get(this);
        Config.DEBUG = true;
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        NIMClient.init(this, LoginInfo(),options());
        if (inMainProcess()) {
            // 在主进程中初始化UI组件，判断所属进程方法请参见demo源码。
            initUIKit();
        }
        mContext = this;
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());

            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

        //NimUIKit.init(this);
    }

   private void initUIKit() {
        // 初始化，使用 uikit 默认的用户信息提供者
        NimUIKit.init(this);
        // 设置地理位置提供者。如果需要发送地理位置消息，该参数必须提供。如果不需要，可以忽略。
        //NimUIKit.setLocationProvider(new NimDemoLocationProvider());

        // 会话窗口的定制初始化。
        //SessionHelper.init();

        // 通讯录列表定制初始化
       // ContactHelper.init();

        // 添加自定义推送文案以及选项，请开发者在各端（Android、IOS、PC、Web）消息发送时保持一致，以免出现通知不一致的情况
        // NimUIKit.CustomPushContentProvider(new DemoPushContentProvider());
       // NimUIKit.setOnlineStateContentProvider(new DemoOnlineStateContentProvider());
    }


    public static Context getAppContext() {
        return mAppContext;
    }
    private SDKOptions options() {

        SDKOptions options = new SDKOptions();

     // 如果将新消息通知提醒托管给 SDK 完成，需要添加以下配置。否则无需设置。
        StatusBarNotificationConfig config = new StatusBarNotificationConfig();
        config.notificationEntrance = WelcomeActivity.class; // 点击通知栏跳转到该Activity
        config.notificationSmallIconId = R.mipmap.ironman;
    // 呼吸灯配置
        config.ledARGB = Color.GREEN;
        config.ledOnMs = 1000;
        config.ledOffMs = 1500;
    // 通知铃声的uri字符串
        config.notificationSound = "android.resource://com.netease.nim.demo/raw/msg";
        options.statusBarNotificationConfig = config;
    // 配置保存图片，文件，log 等数据的目录
    // 如果 options 中没有设置这个值，SDK 会使用下面代码示例中的位置作为 SDK 的数据目录。
    // 该目录目前包含 log, file, image, audio, video, thumb 这6个目录。
    // 如果第三方 APP 需要缓存清理功能， 清理这个目录下面个子目录的内容即可。
        String sdkPath = Environment.getExternalStorageDirectory() + "/" + getPackageName() + "/nim";
        options.sdkStorageRootPath = sdkPath;

    // 配置是否需要预下载附件缩略图，默认为 true
        options.preloadAttach = true;

// 配置附件缩略图的尺寸大小。表示向服务器请求缩略图文件的大小
// 该值一般应根据屏幕尺寸来确定， 默认值为 Screen.width / 2
        options.thumbnailSize = 480 / 2;
// 用户资料提供者, 目前主要用于提供用户资料，用于新消息通知栏中显示消息来源的头像和昵称
        options.userInfoProvider = new UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String account) {
                return null;
            }

            @Override
            public int getDefaultIconResId() {
                return R.mipmap.ic_launcher;
            }

            @Override
            public Bitmap getTeamIcon(String tid) {
                return null;
            }

            @Override
            public Bitmap getAvatarForMessageNotifier(String account) {
                return null;
            }

            @Override
            public String getDisplayNameForMessageNotifier(String account, String sessionId,
                                                           SessionTypeEnum sessionType) {
                return null;
            }
        };
        return options;
    }
    private LoginInfo LoginInfo(){
        return  null;
    }
    public boolean inMainProcess() {
        String packageName = getPackageName();
        String processName = SystemUtil.getProcessName(this);
        return packageName.equals(processName);
    }
    private void initCustomPushNotificationBuilder(Context context) {
        XGCustomPushNotificationBuilder build = new XGCustomPushNotificationBuilder();
        RemoteViews remoteViews =new RemoteViews("microtech.hxswork.com.android_work.ui.activity",R.layout.test_xingge);
        build.setbigContentView(remoteViews);

        build.setSound(
                RingtoneManager.getActualDefaultRingtoneUri(
                       this, RingtoneManager.TYPE_ALARM))
             /*   .setSound(
                        Uri.parse("android.resource://" + getPackageName()
                                + "/" + R.raw.tixin))//设置声音*/
                .setDefaults(Notification.DEFAULT_VIBRATE) // 振动
                .setFlags(Notification.FLAG_NO_CLEAR); // 是否可清除
        // 设置自定义通知layout,通知背景等可以在layout里设置
        build.setLayoutId(R.layout.test_xingge);
        // 设置自定义通知内容id
        build.setLayoutTextId(R.id.content);
        // 设置自定义通知标题id
        build.setLayoutTitleId(R.id.title);
        // 设置自定义通知图片id
        build.setLayoutIconId(R.id.icon);
        // 设置自定义通知图片资源
        build.setLayoutIconDrawableId(R.drawable.test_icon);
        // 设置状态栏的通知小图标
        //build.setbigContentView()
        build.setIcon(R.drawable.test1_icon);
        // 设置时间id
        build.setLayoutTimeId(R.id.time);
        // 若不设定以上自定义layout，又想简单指定通知栏图片资源
        //build.setNotificationLargeIcon(R.drawable.ic_action_search);
        // 客户端保存build_id
        XGPushManager.setPushNotificationBuilder(this, 1, build);
        XGPushManager.setDefaultNotificationBuilder(this,build);
    }
}

