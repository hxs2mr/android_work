package microtech.hxswork.com.android_work.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import microtech.hxswork.com.android_work.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import microtech.hxswork.com.android_work.adapter.OlderFragmentStatePagerAdapter;
import microtech.hxswork.com.android_work.contract.ServiceContract;
import microtech.hxswork.com.android_work.model.ServiceModelImpl;
import microtech.hxswork.com.android_work.presenter.ServicePresenterImpl;
import microtech.hxswork.com.android_work.serviceui.CoverBehavior;
import microtech.hxswork.com.android_work.serviceui.NestingScrollPlanLayout;
import microtech.hxswork.com.android_work.serviceui.TargetBehavior;
import microtech.hxswork.com.android_work.serviceui.Util;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.widget.LoopViewPager;
import microtech.hxswork.com.android_work.widget.RoundImageView;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;

import static microtech.hxswork.com.android_work.R.attr.target_init_offset;
import static microtech.hxswork.com.android_work.ui.fragment.HomeFragment.service_flage;

/**
 * Created by microtech on 2017/8/22.服务模块
 */

public class ServiceFragment extends BaseFragment<ServicePresenterImpl,ServiceModelImpl> implements ServiceContract.View {

    @Bind(R.id.looviewpager)
    LoopViewPager looviewpager;
    @Bind(R.id.id_vp)
    ViewPager mViewPager;
    @Bind(R.id.mTabLayout)
    TabLayout mMtabLayout;
    @Bind(R.id.indicator)
    LinearLayout mindicator;
    private ImageView[] mImageView;
    private  List<Fragment> mTabContents = new ArrayList<>();
    FragmentPagerAdapter mAdapter;

    String[] tabTitle  = new String[]{"私家医生","慢病管理","体检服务","三甲专科","其他一","其他二","其他三","其他四","其他五"};
    HomeActivity mActivity;
    private List<Object> imgList;

    @Override
    protected int getLayoutResource() {
        return R.layout.test_service;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }

    @Override
    protected void initView() {

        StatusBarCompat.setStatusBarColor((Activity) getContext(), ContextCompat.getColor(getContext(), R.color.service));
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.VISIBLE);
        initImgList();
        looviewpager.setAdapter(new MyAdapter());
        looviewpager.setOffscreenPageLimit(3);
        looviewpager.setPageTransformer(true, new ViewPager.PageTransformer() {
            float scale = 0.8f;
            @Override
            public void transformPage(View page, float position) {
                if (position >= 0 && position <= 1) {
                    page.setScaleY(scale + (1 - scale) * (1 - position));
                } else if (position > -1 && position < 0) {
                    page.setScaleY(1 + (1 - scale) * position);
                } else {
                    page.setScaleY(scale);
                }
            }
        });
         looviewpager.autoLoop(true);
        mindicator.removeAllViews();
        initIndicator();
        looviewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setIndicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
         mViewPager.setFocusable(true);
         mViewPager.setFocusableInTouchMode(true);
         mViewPager.requestFocus();
        if(mMtabLayout.getTabCount() > 0)
        {
            mMtabLayout.removeAllTabs();
        }
        for(int i = 0 ; i < tabTitle.length ; i ++)
        {
            mMtabLayout.addTab(mMtabLayout.newTab().setText(tabTitle[i]));
        }
        mMtabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        //设置顶部标签指示条的颜色和选中页面时标签字体颜色
        mMtabLayout.setSelectedTabIndicatorColor(Color.parseColor("#00000000"));
        mMtabLayout.setTabTextColors(Color.GRAY, Color.parseColor("#FF4081"));
        //这里注意的是，因为我是在fragment中创建MyFragmentStatePagerAdapter，所以要传getChildFragmentManager()
        mViewPager.setAdapter(new OlderFragmentStatePagerAdapter(getChildFragmentManager(), tabTitle));
        //在设置viewpager页面滑动监听时，创建TabLayout的滑动监听
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mMtabLayout));
        mMtabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //在选中的顶部标签时，为viewpager设置currentitem
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dp2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    public void initImgList(){
        imgList=new ArrayList<>();
        imgList.add(R.mipmap.bitmap1);
        imgList.add(R.mipmap.home_text_banner_test1);
        imgList.add(R.mipmap.bitmap3);
        imgList.add(R.mipmap.bitmap4);
        imgList.add(R.mipmap.bitmap5);

        //imgList.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2946550071,381041431&fm=11&gp=0.jpg");
        //imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984320392&di=8290126f83c2a2c0d45be41e3f88a6d0&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201307%2F19%2F152440r9ov9ololkzdcz7d.jpg");
        //imgList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1490984407478&di=729b187f4939710e8b2436f9f1306dff&imgtype=0&src=http%3A%2F%2Ffile.mumayi.com%2Fforum%2F201505%2F05%2F172352jrr66rda0dwdwdwz.jpg");
    }


    class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(getContext(), R.layout.adapter, null);
            ImageView itemImage = view.findViewById(R.id.item_iv);
            Glide.with(getContext()).load(imgList.get(position)).into(itemImage);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        looviewpager.autoLoop(false);
    }

    private void initIndicator() {
        mImageView = new ImageView[imgList.size()];
        for (int i = 0; i < imgList.size(); i++) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.indicator_image, null);
            view.findViewById(R.id.indicator_iamge).setBackgroundResource(R.drawable.shape_origin_point_pink);
            mImageView[i] = new ImageView(getContext());
            if (i == 0) {
                mImageView[i].setBackgroundResource(R.drawable.shape_origin_point_pink);
            } else {
                mImageView[i].setBackgroundResource(R.drawable.shape_origin_point_white);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(20, 0, 0, 0);
                mImageView[i].setLayoutParams(layoutParams);
            }
                mindicator.addView(mImageView[i]);

        }
    }
    private void setIndicator(int position) {
        position %= imgList.size();
        for (int i = 0; i < imgList.size(); i++) {

            if(mImageView[i]!=null) {
                mImageView[i].setBackgroundResource(R.drawable.shape_origin_point_pink);
            }
            if (position != i) {
                if(mImageView[i]!=null) {
                    mImageView[i].setBackgroundResource(R.drawable.shape_origin_point_white);
                }
            }

        }
    }
/*
    @Override
    public void onStop() {
        super.onStop();
        looviewpager.autoLoop(false);
    }*/
}
