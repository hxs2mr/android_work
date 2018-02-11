package microtech.hxswork.com.android_work.ui.fragment;


import microtech.hxswork.com.android_work.contract.DoctorContract;
import microtech.hxswork.com.android_work.model.DoctorModelImpl;
import microtech.hxswork.com.android_work.presenter.DoctorPressenterImpl;
import microtech.hxswork.com.commom.base.BaseFragment;

/**
 * Created by microtech on 2017/8/25.医生消息模块
 */

public class DoctorInformationFragment extends BaseFragment<DoctorPressenterImpl,DoctorModelImpl> implements DoctorContract.View{

    @Override
    protected int getLayoutResource() {
        return 0;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {

    }
}
