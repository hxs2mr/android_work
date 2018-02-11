package microtech.hxswork.com.android_work.ui.fragment.servicefragmentItem;

import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import microtech.hxswork.com.android_work.R;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import butterknife.Bind;
import microtech.hxswork.com.android_work.adapter.BaseRecyclerViewAdapter;
import microtech.hxswork.com.android_work.adapter.ServiceItemAdapter;
import microtech.hxswork.com.android_work.bean.SIBean;
import microtech.hxswork.com.android_work.contract.ServiceitemContract;
import microtech.hxswork.com.android_work.model.ServiceitemModelImpl;
import microtech.hxswork.com.android_work.presenter.ServiceitemPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.android_work.ui.activity.ServiceItemActivity;
import microtech.hxswork.com.commom.base.BaseFragment;

/**
 * Created by microtech on 2017/9/8.
 */

public class ServiceitemFragment1 extends BaseFragment<ServiceitemPresenterImpl,ServiceitemModelImpl> implements ServiceitemContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeLayout;
    @Bind(R.id.sevice1_service)
    RecyclerView sevice1_service;
    HomeActivity mActivity;
    ServiceItemAdapter serviceItemAdapter;
    List<SIBean>  list = new ArrayList<>();//数据来源1

    BaseRecyclerViewAdapter<SIBean>  mAdapter;
    private int pageNo = 1;
    private int status; // 0  正常加载更多， 1 加载完成 ， 2 加载失败。

    @Override
    protected int getLayoutResource() {
        return R.layout.serviceitem_fragment1;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
            swipeLayout.setRefreshing(false);
        swipeLayout.setOnRefreshListener(this);
       /* swipeLayout.setColorSchemeColors(Color.parseColor("#7FBDFF"));*/
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL,false);

        sevice1_service.setLayoutManager(layoutManager);
        //测试数据\
        //public SIBean(String image_url, String title, String mokey, String renshu, String nerong, String shuoming, String pingjial, String shiyong) {
        SIBean bean1 = new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t010a13f9d2bb1ca344.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");

        SIBean bean2 = new SIBean("http://p2.so.qhimgs1.com/bdr/_240_/t015a590422c26a793e.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");

        SIBean bean3 = new SIBean("http://p1.so.qhimgs1.com/bdr/_240_/t01768c4a780c57fb31.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");

        SIBean bean4 = new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                         "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean5= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean6= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean7= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean8= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean9= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean10= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean11= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");
        SIBean bean12= new SIBean("http://p4.so.qhimgs1.com/bdr/_240_/t017cd09154035c0564.jpg","三甲医院私家医生服务包","123","1.2k",
                "● 体检服务预约至少提前2个工作日。取消服务须提前1个工作日拨打24小时白金贵宾服务专线400-619-5599进行取消。如您未在规定时间内接受服务或取消服务，视同您已使用该服务并扣除相应服务权限。\n" +
                        "● 为方便体检服务，体检当日接受体检者需携带本人身份证原件。\n" +
                        "● 体检服务预约范围为下列推荐的医疗机构，如有变更，以农业银行最新公告为准。","说明","评价","1-5婴幼儿");

        list.add(bean1);
        list.add(bean2);
        list.add(bean3);
        list.add(bean4);
        list.add(bean5);
        list.add(bean6);
        list.add(bean7);
        list.add(bean8);
        list.add(bean9);
        list.add(bean10);
        list.add(bean11);
        list.add(bean12);

        initAdapte();
    }

    private void initAdapte() {
        mAdapter = new BaseRecyclerViewAdapter<SIBean>(R.layout.serviceitem_griditem,list) {
            @Override
            protected void convert(BaseViewHolder helper, SIBean item) {
                ImageView  image= helper.convertView.findViewById(R.id.item_image);
                helper.setText(R.id.item_monkey,item.getMokey());
                helper.setText(R.id.item_title,item.getTitle());
                helper.setText(R.id.item_renshu,item.getRenshu());
                Glide.with(getContext()).load(item.getImage_url()).into(image);
            }
        };
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, sevice1_service);
        sevice1_service.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mActivity.startActivity(ServiceItemActivity.class);
            }
        });
    }

    @Override
    public void onRefresh() {//刷新操作
        if(swipeLayout!=null)
        {            swipeLayout.setRefreshing(true);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mAdapter.setNewData(list);
                if(swipeLayout!=null) {
                    swipeLayout.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);
            }
        }, 300);
    }

    @Override
    public void onLoadMoreRequested() {
        if (swipeLayout!=null) {
            swipeLayout.setEnabled(false);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (swipeLayout!=null) {
                    swipeLayout.setEnabled(true);
                }
                mAdapter.addData(list);
                mAdapter.loadMoreEnd();

  /*              switch (status) {
                    case 0://正在加载
                        pageNo++;
                        //initData(pageNo);//网络请求
                        mAdapter.setNewData(list);
                        mAdapter.loadMoreComplete();
                        break;
                    case 1://加载完成
                        pageNo++;
                        //initData(pageNo);//网络请求
                        mAdapter.setNewData(list);
                        mAdapter.loadMoreEnd();
                        break;
                    case 2://加载失败
                        mAdapter.loadMoreFail();
                        break;
                }*/

            }
        }, 300);
    }
}
