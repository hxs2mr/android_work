package microtech.hxswork.com.android_work.ui.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.OnClick;
import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.contract.AllContract;
import microtech.hxswork.com.android_work.model.AllModelImpl;
import microtech.hxswork.com.android_work.presenter.AllpresenterImpl;
import microtech.hxswork.com.android_work.ui.activity.HomeActivity;
import microtech.hxswork.com.commom.base.BaseFragment;

/**
 * Created by microtech on 2017/9/27.
 */

public class AllFragment extends BaseFragment<AllpresenterImpl,AllModelImpl>implements AllContract.View, View.OnClickListener {
    @Bind(R.id.more_back)
    ImageView more_back;
    @Bind(R.id.more_back_linear)
    LinearLayout more_back_linear;

    @Bind(R.id.title_text)
    TextView title_text;
    @Bind(R.id.our_linear)
            LinearLayout our_linear;
    @Bind(R.id.more_linear)
            LinearLayout more_linear;
    HomeActivity mActivity;
    @Override
    protected int getLayoutResource() {
        return R.layout.heathmore_notice;
    }
    @Override
    public void initPresenter() {

    }
    @Override
    protected void initView() {
        mActivity = (HomeActivity) getActivity();
          String data = getArguments().getString("data");
        if(data.equals("more"))
        {
            title_text.setText("检测须知");
            our_linear.setVisibility(View.GONE);
            more_linear.setVisibility(View.VISIBLE);
        }else if(data.equals("our"))
        {
            title_text.setText("关于我们");
            our_linear.setVisibility(View.VISIBLE);
            more_linear.setVisibility(View.GONE);
        }
        more_back_linear.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mActivity.onBackPressed();
    }
}
