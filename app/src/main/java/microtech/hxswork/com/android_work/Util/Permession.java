package microtech.hxswork.com.android_work.Util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import java.util.List;
import java.util.UUID;

import static com.netease.nimlib.sdk.msg.constant.MsgTypeEnum.location;

/**
 * Created by microtech on 2017/9/29.获取手机信息和地理信息的封装类
 */

public class Permession {
    static TelephonyManager tm;//获取手机信息服务
    private static String locationProvider;//获取经纬度
    static LocationManager locationManager;//获取位置服务
    Location location;
    public static Context context;
    private static final int BAIDU_READ_PHONE_STATE = 100;
    public  Permession(Context context)
    {
        this.context = context;
    }
}
