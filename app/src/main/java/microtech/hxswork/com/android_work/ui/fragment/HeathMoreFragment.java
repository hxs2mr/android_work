package microtech.hxswork.com.android_work.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.adapter.HeathMoreAdapter;
import microtech.hxswork.com.android_work.bean.HeathMoreBean;
import microtech.hxswork.com.android_work.contract.HeathMoreContract;
import microtech.hxswork.com.android_work.model.HeathMoreModelImpl;
import microtech.hxswork.com.android_work.presenter.HeathMorePresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;

/**
 * Created by microtech on 2017/9/18.
 */

public class HeathMoreFragment extends BaseFragment<HeathMorePresenterImpl,HeathMoreModelImpl> implements HeathMoreContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;

    @Bind(R.id.more_back_linear)
    LinearLayout more_back_linear;
    @Bind(R.id.more_notice)
    ImageView more_notice;
    HomeActivity mActivity;
    List<HeathMoreBean> list;
    HeathMoreAdapter heathMoreAdapter;
    @Override
    protected int getLayoutResource() {
        return R.layout.heathmore_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        swipeLayout.setOnRefreshListener(this);
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.GONE);
        recyclerview.setLayoutManager(new LinearLayoutManager(mActivity));
        more_back_linear.setOnClickListener(this);
        more_notice.setOnClickListener(this);
        list = new ArrayList<>();
        HeathMoreBean.more_data more_data1 = new HeathMoreBean.more_data("2017/02/14  19:17","血压正常","7.1mmol/L","血压正常，继续保持测量",1);//测试数据
        HeathMoreBean.more_data more_data2= new HeathMoreBean.more_data("2017/02/14  19:17","心动过速","80bpm","高血压患者的膳食原则及要求1.多吃降压的食物如：芹菜、菠菜、海带、萝卜等；多吃一些素食和粗粮杂粮，新鲜蔬果，瘦肉，鱼。少吃白糖，咖啡、辛辣油腻的食物、浓茶；2.降低食盐食用量；3.戒烟、戒酒；4.多饮用泉水，深井水、天然矿泉水等含有钙离子和镁离子硬水；5.三餐饮食定时、定量。\n" +
                "高血压的治疗：",2);
        HeathMoreBean.more_data more_data3 = new HeathMoreBean.more_data("2017/02/14  19:17","血氧饱和度偏低","90%","高血压患者的膳食原则及要求1.多吃降压的食物如：芹菜、菠菜、海带、萝卜等；多吃一些素食和粗粮杂粮，新鲜蔬果，瘦肉，鱼。少吃白糖，咖啡、辛辣油腻的食物、浓茶；2.降低食盐食用量；3.戒烟、戒酒；4.多饮用泉水，深井水、天然矿泉水等含有钙离子和镁离子硬水；5.三餐饮食定时、定量。\n" +
                "高血压的治疗：",0);
        HeathMoreBean.more_data more_data4 = new HeathMoreBean.more_data("2017/02/14  19:17","血糖正常","7.1mmol/L","血糖正常，继续保持测量。",1);
        HeathMoreBean.more_data more_data5 = new HeathMoreBean.more_data("2017/02/14  19:17","体温正常","38.6℃","发烧",2);

        HeathMoreBean bean1=  new HeathMoreBean(0,more_data1);
        HeathMoreBean bean2=  new HeathMoreBean(0,more_data2);
        HeathMoreBean bean3=  new HeathMoreBean(0,more_data3);
        HeathMoreBean bean4=  new HeathMoreBean(0,more_data4);
        HeathMoreBean bean5=  new HeathMoreBean(0,more_data5);
        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        heathMoreAdapter = new HeathMoreAdapter(getContext(),list);
        recyclerview.setAdapter(heathMoreAdapter);

    }
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(swipeLayout!=null) {
                    swipeLayout.setRefreshing(false);
                }
            }
        }, 200);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.more_back_linear:
            mActivity.onBackPressed();
                break;
            case R.id.more_notice:
                Bundle bundle = new Bundle();
                bundle.putString("data","more");
                mActivity.pushFragment(new AllFragment(),true,bundle);
                break;
        }
    }
}
