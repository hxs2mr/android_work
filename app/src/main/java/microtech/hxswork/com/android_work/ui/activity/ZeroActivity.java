package microtech.hxswork.com.android_work.ui.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.nineoldandroids.view.ViewHelper;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.ui.fragment.ProductTourFragment;
import microtech.hxswork.com.android_work.views.CircleProgressbar;

/**
 * Created by microtech on 2017/10/12.
 */

public class ZeroActivity extends AppCompatActivity {
    static final int NUM_PAGES = 5;
    PagerAdapter pagerAdapter;
    ViewPager pager;
    LinearLayout circles;//指示标\
    Button clear_button;
    boolean isOpaque = true;
    CircleProgressbar circleProgressbar;
    boolean isClick = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zeroactivity_main);
        initview();
    }
    private void initview() {
        pager = (ViewPager) findViewById(R.id.pager);
        circles = (LinearLayout) findViewById(R.id.circles);
        clear_button = (Button) findViewById(R.id.clear_button);
        circleProgressbar = (CircleProgressbar) findViewById(R.id.tv_red_skip);
        circleProgressbar.setOutLineColor(Color.parseColor("#d1dde6"));
        circleProgressbar.setInCircleColor(Color.parseColor("#ffffff"));
        circleProgressbar.setProgressColor(Color.parseColor("#1bb079"));
        circleProgressbar.setProgressLineWidth(5);
        circleProgressbar.setProgressType(CircleProgressbar.ProgressType.COUNT);
        circleProgressbar.setTimeMillis(10000);
        circleProgressbar.reStart();
        circleProgressbar.setCountdownProgressListener(1,progressListener);
        circleProgressbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            isClick = true;
                startActivity(new Intent(ZeroActivity.this,LoginActivity.class));
                SaveZero();
                finish();
            }
        });

        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        pager.setAdapter(pagerAdapter);
        pager.setPageTransformer(true, new CrossfadePageTransformer());
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == NUM_PAGES - 2 && positionOffset > 0) {
                    if (isOpaque) {
                        pager.setBackgroundColor(Color.TRANSPARENT);
                        isOpaque = false;
                    }
                } else {
                    if (!isOpaque) {
                        pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
                        isOpaque = true;
                    }
                }
            }
            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
                if (position == NUM_PAGES - 2) {
                    clear_button.setVisibility(View.VISIBLE);
                } else if (position < NUM_PAGES - 2) {
                    clear_button.setVisibility(View.GONE);
                } else if (position == NUM_PAGES - 1) {
                    //endTutorial();
                }
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        buildCircles();

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ZeroActivity.this,LoginActivity.class));
                SaveZero();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (pager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            pager.setCurrentItem(pager.getCurrentItem() - 1);
        }
    }
    private void buildCircles(){
        circles = LinearLayout.class.cast(findViewById(R.id.circles));

        float scale = getResources().getDisplayMetrics().density;
        int padding = (int) (5 * scale + 0.5f);
        for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
            ImageView circle = new ImageView(this);
            circle.setImageResource(R.mipmap.ic_swipe_indicator_white_18dp);
            circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            circle.setAdjustViewBounds(true);
            circle.setPadding(padding, 0, padding, 0);
            circles.addView(circle);
        }
        setIndicator(0);
    }
    private void setIndicator(int index){
        if(index < NUM_PAGES){
            for(int i = 0 ; i < NUM_PAGES - 1 ; i++){
                ImageView circle = (ImageView) circles.getChildAt(i);
                if(i == index){
                    circle.setColorFilter(getResources().getColor(R.color.text_selected));
                }else {
                    circle.setColorFilter(getResources().getColor(R.color.transparent));
                }
            }
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter
    {

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            ProductTourFragment tp = null;
            switch(position){
                case 0:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment01);
                    break;
                case 1:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment02);
                    break;
                case 2:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment03);
                    break;
                case 3:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment04);
                    break;
                case 4:
                    tp = ProductTourFragment.newInstance(R.layout.welcome_fragment5);
                    break;
            }

            return tp;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class CrossfadePageTransformer implements ViewPager.PageTransformer {

        @Override
        public void transformPage(View page, float position) {
            int pageWidth = page.getWidth();

            View backgroundView = page.findViewById(R.id.welcome_fragment);
            View text_head= page.findViewById(R.id.heading);
            View text_content = page.findViewById(R.id.content);
            View welcomeImage01 = page.findViewById(R.id.welcome_01);
            View welcomeImage02 = page.findViewById(R.id.welcome_02);
            View welcomeImage03 = page.findViewById(R.id.welcome_03);
            View welcomeImage04 = page.findViewById(R.id.welcome_04);

            if(0 <= position && position < 1){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }
            if(-1 < position && position < 0){
                ViewHelper.setTranslationX(page,pageWidth * -position);
            }

            if(position <= -1.0f || position >= 1.0f) {
            } else if( position == 0.0f ) {
            } else {
                if(backgroundView != null) {
                    ViewHelper.setAlpha(backgroundView,1.0f - Math.abs(position));
                }

                if (text_head != null) {
                    ViewHelper.setTranslationX(text_head,pageWidth * position);
                    ViewHelper.setAlpha(text_head,1.0f - Math.abs(position));
                }

                if (text_content != null) {
                    ViewHelper.setTranslationX(text_content,pageWidth * position);
                    ViewHelper.setAlpha(text_content,1.0f - Math.abs(position));
                }

                if (welcomeImage01 != null) {
                    ViewHelper.setTranslationX(welcomeImage01,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage01,1.0f - Math.abs(position));
                }

                if (welcomeImage02 != null) {
                    ViewHelper.setTranslationX(welcomeImage02,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage02,1.0f - Math.abs(position));
                }

                if (welcomeImage03 != null) {
                    ViewHelper.setTranslationX(welcomeImage03,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage03,1.0f - Math.abs(position));
                }
                if (welcomeImage04 != null) {
                    ViewHelper.setTranslationX(welcomeImage04,(float)(pageWidth/2 * position));
                    ViewHelper.setAlpha(welcomeImage04,1.0f - Math.abs(position));
                }
            }
        }
    }
    public void SaveZero() {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences("Zero", MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        edit.putString("flage", "1");         //根据键值对添加数据
        edit.commit();  //保存数据信息
    }

    CircleProgressbar.OnCountdownProgressListener progressListener = new CircleProgressbar.OnCountdownProgressListener() {
        @Override
        public void onProgress(int what, int progress) {

            if (what == 1 && progress == 100 && !isClick)
            {
                startActivity(new Intent(ZeroActivity.this,LoginActivity.class));
                SaveZero();
                finish();
            }
        }
    };
}
