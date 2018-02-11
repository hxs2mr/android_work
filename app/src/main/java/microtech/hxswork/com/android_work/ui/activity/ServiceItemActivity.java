package microtech.hxswork.com.android_work.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import microtech.hxswork.com.android_work.R;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import microtech.hxswork.com.android_work.widget.GlideImageLoader;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;

import static microtech.hxswork.com.commom.commonwidget.StatusBarCompat.translucentStatusBar;

/**
 * Created by microtech on 2017/9/11.
 */

public class ServiceItemActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView back;
    LinearLayout linear1;
    LinearLayout linear2;
    LinearLayout linear3;
    ImageView im1;
    ImageView im2;
    ImageView im3;
    TextView t1;
    TextView t2;
    TextView t3;

   Banner home_banner;
    LinearLayout service_back_linear;
    TextView text_content;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serviceitem_activity);
        initView();
        showBanner();
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this,R.color.__picker_item_photo_border_n1));
    }

    private void initView() {
        service_back_linear = (LinearLayout) findViewById(R.id.service_back_linear);
        back  = (ImageView) findViewById(R.id.service_back);
        linear1 = (LinearLayout) findViewById(R.id.linear1);
        linear2 = (LinearLayout) findViewById(R.id.linear2);
        linear3 = (LinearLayout) findViewById(R.id.linear3);
        im1 = (ImageView) findViewById(R.id.im1);
        im2 = (ImageView) findViewById(R.id.im2);
        im3 = (ImageView) findViewById(R.id.im3);
        t1 = (TextView) findViewById(R.id.t1);
        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        home_banner = (Banner) findViewById(R.id.home_head_banner);
        text_content = (TextView) findViewById(R.id.text_content);

        linear1.setOnClickListener(this);
        linear2.setOnClickListener(this);
        linear3.setOnClickListener(this);
        service_back_linear.setOnClickListener(this);
        im1.setVisibility(View.VISIBLE);
        t1.setTextColor(Color.parseColor("#38E6FF"));
    }


    private void showBanner()
    {
        List<Integer> images = new ArrayList<>();//前期测试用本地的图片 后期重服务器端获取
        images.add(R.mipmap.home_text_banner_test1);
        images.add(R.mipmap.home_text_banner_test2);
        images.add(R.mipmap.home_text_banner_test3);
        images.add(R.mipmap.home_text_banner_test4);
        home_banner.setImageLoader(new GlideImageLoader());
        home_banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);//设置banner样式
        home_banner.setImages(images);//设置图片集合
        home_banner.setBannerAnimation(Transformer.Default);//设置banner动画效果
        home_banner.isAutoPlay(true);//设置自动轮播，默认为true
        home_banner.setDelayTime(3000);//设置轮播时间
        home_banner.setIndicatorGravity(BannerConfig.CENTER); //设置指示器位置（当banner模式中有指示器时）
        home_banner.start();//banner设置方法全部调用完毕时最后调用
    }
    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.service_back_linear:
                finish();
                break;
            case R.id.linear1:
                initback();
                im1.setVisibility(View.VISIBLE);
                t1.setTextColor(Color.parseColor("#38E6FF"));
                text_content.setText("● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。");
                break;
            case R.id.linear2:
                initback();
                im2.setVisibility(View.VISIBLE);
                t2.setTextColor(Color.parseColor("#38E6FF"));
                text_content.setText("啊是卡雷拉斯肯定会");
                break;
            case R.id.linear3:
                initback();
                im3.setVisibility(View.VISIBLE);
                t3.setTextColor(Color.parseColor("#38E6FF"));
                text_content.setText("啊时间考虑2");
                break;
        }
    }
    private  void initback(){
        im1.setVisibility(View.INVISIBLE);
        im2.setVisibility(View.INVISIBLE);
        im3.setVisibility(View.INVISIBLE);
        t1.setTextColor(Color.parseColor("#8FACC8"));
        t2.setTextColor(Color.parseColor("#8FACC8"));
        t3.setTextColor(Color.parseColor("#8FACC8"));
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resId > 0) {
            result = context.getResources().getDimensionPixelOffset(resId);
        }
        return result;
    }
}
