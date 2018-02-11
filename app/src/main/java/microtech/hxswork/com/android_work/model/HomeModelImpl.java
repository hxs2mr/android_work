package microtech.hxswork.com.android_work.model;


import microtech.hxswork.com.android_work.api.RetrofitService;
import microtech.hxswork.com.android_work.bean.HomeBean;
import microtech.hxswork.com.android_work.contract.HomeContract;
import microtech.hxswork.com.commom.basebean.BaseResponse;
import microtech.hxswork.com.commom.baserx.RxSchedulers;
import rx.Observable;

/**
 * Created by HXS on 2017/8/21.
 */

public class HomeModelImpl implements HomeContract.Model {
    @Override
    public Observable<BaseResponse<HomeBean>> getHomeInfo() {
        return RetrofitService.getInstance().mAPI.getHomeInfo()
                .compose(RxSchedulers.<BaseResponse<HomeBean>>io_main());
    }
}
