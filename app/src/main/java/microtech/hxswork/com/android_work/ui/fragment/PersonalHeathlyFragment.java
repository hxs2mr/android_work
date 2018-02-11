package microtech.hxswork.com.android_work.ui.fragment;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;


import microtech.hxswork.com.android_work.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import microtech.hxswork.com.android_work.bean.NextBean1;
import microtech.hxswork.com.android_work.contract.PersonalHeathlyContract;
import microtech.hxswork.com.android_work.model.PersonalHeathlyModelImpl;
import microtech.hxswork.com.android_work.presenter.PersonalHeathlyPresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;
import microtech.hxswork.com.commom.commonwidget.StatusBarCompat;

/**
 * Created by microtech on 2017/9/5.健康知识入口
 */

public class PersonalHeathlyFragment extends BaseFragment<PersonalHeathlyPresenterImpl,PersonalHeathlyModelImpl> implements PersonalHeathlyContract.View, View.OnClickListener {

    @Bind(R.id.heathly_btn_back)
    ImageView heathly_btn_back;//返回

    @Bind(R.id.heathly_btn_back_linear)
    LinearLayout heathly_btn_back_linear;

    @Bind(R.id.personal_heathly_linear1)
    LinearLayout personal_heathly_linear1;//第一个linear

    @Bind(R.id.personal_heathly_linear2)
    LinearLayout personal_heathly_linear2;//第一个linear

    @Bind(R.id.personal_heathly_linear3)
    LinearLayout personal_heathly_linear3;//第一个linear

    @Bind(R.id.personal_heathly_linear4)
    LinearLayout personal_heathly_linear4;//第一个linear

    @Bind(R.id.personal_heathly_linear5)
    LinearLayout personal_heathly_linear5;//第五个linear



    public  static  List<NextBean1> list1 ;//= new ArrayList<>();
    public  static List<NextBean1> list2;
    public  static List<NextBean1> list3;
    public  static List<NextBean1> list4;
    public  static List<NextBean1> list5;//数据的来源

    HomeActivity mActivity;
    @Override
    protected int getLayoutResource() {
        return R.layout.personal_heathly_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    protected void initView() {
        //StatusBarCompat.setStatusBarColor((Activity) getContext(), ContextCompat.getColor(getContext(), R.color.blue));
        mActivity = (HomeActivity) getActivity();
        mActivity.mFlToolbar.setVisibility(View.GONE);
        heathly_btn_back_linear.setOnClickListener(this);
        personal_heathly_linear1.setOnClickListener(this);
        personal_heathly_linear2.setOnClickListener(this);
        personal_heathly_linear3.setOnClickListener(this);
        personal_heathly_linear4.setOnClickListener(this);
        personal_heathly_linear5.setOnClickListener(this);

        //初始化测试数据 前期测试数据 后期服务器获取

       /// NextBean1(String name, String url, String image_url, String time)

        NextBean1 bean1 = new NextBean1("60-70%的中风病人经抢救治疗后快速恢复办法","http://mp.weixin.qq.com/s/iwkZ0D4GMJgYzekVC23orA","http://p0.so.qhimgs1.com/bdr/_240_/t01e9783b7417515cc0.jpg","2017/12/26");
        NextBean1 bean2 = new NextBean1("美国科学家研究表明，心肌梗塞的人不能吃香蕉","http://mp.weixin.qq.com/s/iwkZ0D4GMJgYzekVC23orA","http://p3.so.qhmsg.com/bdr/_240_/t014125947e3be23398.jpg","2017/12/26");
        NextBean1 bean3 = new NextBean1("防止冠心病，脑溢血。提高心机能自行车是不错的选择","http://mp.weixin.qq.com/s/iwkZ0D4GMJgYzekVC23orA","http://p0.so.qhmsg.com/bdr/_240_/t01a4dee3efe8629f85.jpg","2017/12/26");
        NextBean1 bean4 = new NextBean1("没应保持2小时运动，每组20分钟","http://mp.weixin.qq.com/s/iwkZ0D4GMJgYzekVC23orA","http://p2.so.qhimgs1.com/bdr/_240_/t0109eba9af6b750b72.jpg","2017/12/26");
        NextBean1 bean5 = new NextBean1("没应保持2小时运动，每组20分钟","http://mp.weixin.qq.com/s/iwkZ0D4GMJgYzekVC23orA","http://p0.so.qhmsg.com/bdr/_240_/t011199bae49defdc8f.jpg","2017/12/26");
        NextBean1 bean6 = new NextBean1("没应保持2小时运动，每组20分钟","http://mp.weixin.qq.com/s/iwkZ0D4GMJgYzekVC23orA","http://p3.so.qhimgs1.com/bdr/_240_/t012eb3c5acb68612b3.jpg","2017/12/26");

        list1 = new ArrayList<>();
        list2 = new ArrayList<>();
        list3 = new ArrayList<>();
        list4 = new ArrayList<>();
        list5 = new ArrayList<>();

        list1.add(bean1);
        list1.add(bean2);
        list1.add(bean3);
        list1.add(bean4);
        list1.add(bean5);
        list1.add(bean6);

        list2.add(bean1);
        list2.add(bean2);
        list2.add(bean3);
        list2.add(bean4);
        list2.add(bean5);
        list2.add(bean6);

        list3.add(bean1);
        list3.add(bean2);
        list3.add(bean3);
        list3.add(bean4);
        list3.add(bean5);
        list3.add(bean6);

        list4.add(bean1);
        list4.add(bean2);
        list4.add(bean3);
        list4.add(bean4);
        list4.add(bean5);
        list4.add(bean6);

        list5.add(bean1);
        list5.add(bean2);
        list5.add(bean3);
        list5.add(bean4);
        list5.add(bean5);
        list5.add(bean6);
    }

    @Override
    public void onClick(View view) {
            switch (view.getId()){
                case R.id.heathly_btn_back_linear:
                    mActivity.onBackPressed();
                    break;
                case R.id.personal_heathly_linear1:
                    Bundle bundle1 = new Bundle();
                    bundle1.putInt("data",1);
                    mActivity.pushFragment(new PersonalHeathlyNextFragment(),true,bundle1);
                    break;
                case R.id.personal_heathly_linear2:
                    Bundle bundle2 = new Bundle();
                    bundle2.putInt("data",2);
                    mActivity.pushFragment(new PersonalHeathlyNextFragment(),true,bundle2);
                    break;
                case R.id.personal_heathly_linear3:
                    Bundle bundle3 = new Bundle();
                    bundle3.putInt("data",3);
                    mActivity.pushFragment(new PersonalHeathlyNextFragment(),true,bundle3);
                    break;
                case R.id.personal_heathly_linear4:
                    Bundle bundle4 = new Bundle();
                    bundle4.putInt("data",4);
                    mActivity.pushFragment(new PersonalHeathlyNextFragment(),true,bundle4);
                    break;
                case R.id.personal_heathly_linear5:
                    Bundle bundle5 = new Bundle();
                    bundle5.putInt("data",5);
                    mActivity.pushFragment(new PersonalHeathlyNextFragment(),true,bundle5);
                    break;

            }
    }
}
