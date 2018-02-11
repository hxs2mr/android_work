package microtech.hxswork.com.android_work.presenter;

import microtech.hxswork.com.android_work.Util.SAToast;
import microtech.hxswork.com.android_work.bean.HomeBean;
import microtech.hxswork.com.android_work.contract.HomeContract;
import microtech.hxswork.com.commom.basebean.BaseResponse;
import microtech.hxswork.com.commom.baserx.RxSubscriber;

/**
 * Created by HXS on 2017/8/21.
 */

public class HomePresenterImpl extends HomeContract.Presenter {
    @Override
    public void getMsg() {
        mRxManage.add(mModel.getHomeInfo().subscribe(new RxSubscriber<BaseResponse<HomeBean>>(mContext) {
            @Override
            protected void _onNext(BaseResponse<HomeBean> response) {
                mView.returnToView(response);
            }

            @Override
            protected void _onError(String message) {
                SAToast.makeText(mContext,message).show();
            }
        }));
    }
}
