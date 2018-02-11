package microtech.hxswork.com.android_work.ui.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import microtech.hxswork.com.android_work.R;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import microtech.hxswork.com.android_work.InfoRes.ServiceInfoRes;
import microtech.hxswork.com.android_work.contract.ServiceContract;
import microtech.hxswork.com.android_work.model.ServiceModelImpl;
import microtech.hxswork.com.android_work.presenter.ServicePresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;

/**
 * Created by microtech on 2017/9/11.
 */

public class NewServiceFragment extends BaseFragment<ServicePresenterImpl,ServiceModelImpl> implements ServiceContract.View{
    @Bind(R.id.servicefragment_recyclerview)
    RecyclerView servicefragment_recyclerview;

    HomeActivity mActivity;
    private List<Object> imgList;
    List<ServiceInfoRes> listServiceInfoRes;
    private int pageNo = 1;
    FragmentManager fm;
    String[] tabTitle  = new String[]{"私家医生","慢病管理","体检服务","三甲专科"};
    View head2;
    @Override
    protected int getLayoutResource() {
        return R.layout.new_servicefragment;
    }

    @Override
    public void initPresenter() {
        mPresenter.setVM(this, mModel);
    }


    @Override
    protected void initView() {
        StatusBarCompat.setStatusBarColor((Activity) getContext(), ContextCompat.getColor(getContext(),R.color.service));
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.VISIBLE);
        initImgList();
    }

    public void initImgList(){
        imgList=new ArrayList<>();
        imgList.add(R.mipmap.bitmap1);
        imgList.add(R.mipmap.home_text_banner_test1);
        imgList.add(R.mipmap.bitmap3);
        imgList.add(R.mipmap.bitmap4);
        imgList.add(R.mipmap.bitmap5);
    }


    public int dp2px(float dpValue) {
        final float scale =getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
