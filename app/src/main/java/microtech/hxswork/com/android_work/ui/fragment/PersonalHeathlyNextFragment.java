package microtech.hxswork.com.android_work.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import microtech.hxswork.com.android_work.R;

import java.util.List;

import butterknife.Bind;
import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.adapter.MyHeathlyNextAdapter;
import microtech.hxswork.com.android_work.bean.NextBean1;
import microtech.hxswork.com.android_work.contract.PersonalHeathlyNextContract;
import microtech.hxswork.com.android_work.model.PersonalHeathlyNextModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalHeathlyNextPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;

import static microtech.hxswork.com.android_work.ui.fragment.PersonalHeathlyFragment.list1;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalHeathlyFragment.list2;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalHeathlyFragment.list3;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalHeathlyFragment.list4;
import static microtech.hxswork.com.android_work.ui.fragment.PersonalHeathlyFragment.list5;

/**
 * Created by microtech on 2017/9/6.
 */

public class PersonalHeathlyNextFragment extends BaseFragment<PersonalHeathlyNextPresenterImpl,PersonalHeathlyNextModelImpl> implements PersonalHeathlyNextContract.View, View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    @Bind(R.id.next_back)
    ImageView next_back;//返回

    @Bind(R.id.next_back_linear)
    LinearLayout next_back_linear;
    @Bind(R.id.next_title_text)
    TextView next_title_text;//文本标题

    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;

    @Bind(R.id.next_list)
    RecyclerView  next_list;

    HomeActivity mActivity;
    int init_flage=0;
    MyHeathlyNextAdapter adapter;
    List<NextBean1> listmap;
    List<NextBean1> listmap1;
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_heathly_next_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
        swipeLayout.setOnRefreshListener(this);
        mActivity.mFlToolbar.setVisibility(View.GONE);
        next_list.setLayoutManager(new LinearLayoutManager(mActivity));
        init_flage = getArguments().getInt("data");
        next_back_linear.setOnClickListener(this);
        if (init_flage == 1)  {
            next_title_text.setText("神经系统知识库");
            listmap = list1;

            adapter  = new MyHeathlyNextAdapter(getContext(),listmap);
            next_list.setAdapter(adapter);
        } else if (init_flage == 2) {
            next_title_text.setText("消化系统知识库");
            listmap = list2;
            adapter  = new MyHeathlyNextAdapter(getContext(),listmap);
            next_list.setAdapter(adapter);
        } else if (init_flage == 3) {
            next_title_text.setText("循环系统知识库");
            listmap = list3;
            adapter  = new MyHeathlyNextAdapter(getContext(),listmap);
            next_list.setAdapter(adapter);
        } else if (init_flage == 4){
            next_title_text.setText("运动系统知识库");
            listmap = list4;
            adapter  = new MyHeathlyNextAdapter(getContext(),listmap);
            next_list.setAdapter(adapter);
        }else if(init_flage == 5) {
            next_title_text.setText("其他系统知识库");
            listmap = list5;
            adapter  = new MyHeathlyNextAdapter(getContext(),listmap);
            next_list.setAdapter(adapter);
        }

        adapter.setOnItemClickListener(new MyHeathlyNextAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle=new Bundle();
                bundle.putString("weburl",listmap.get(position).getUrl());
                mActivity.pushFragment( new HeathlyWebViewFragment(),true,bundle);
                SAToast.makeText(getContext(),""+position).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }

            @Override
            public void onDeleteBtnCilck(View view, int position) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.next_back_linear:
                mActivity.onBackPressed();
                break;
        }
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
}
