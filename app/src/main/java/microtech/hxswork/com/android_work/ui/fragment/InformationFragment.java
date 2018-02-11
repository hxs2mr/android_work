package microtech.hxswork.com.android_work.ui.fragment;

import microtech.hxswork.com.android_work.R;
import microtech.hxswork.com.android_work.contract.InformationContract;
import microtech.hxswork.com.android_work.model.InformationModelImpl;
import microtech.hxswork.com.android_work.presenter.InformationPresenterImpl;
import microtech.hxswork.com.commom.base.BaseActivity;

/**
 * Created by microtech on 2017/11/9.
 */

public class InformationFragment extends BaseActivity<InformationPresenterImpl,InformationModelImpl>implements InformationContract.View {
    @Override
    public int getLayoutId() {
        return R.layout.infomation_fragment;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
}
