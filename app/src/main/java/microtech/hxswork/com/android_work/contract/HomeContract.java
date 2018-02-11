package microtech.hxswork.com.android_work.contract;


import microtech.hxswork.com.android_work.bean.HomeBean;
import microtech.hxswork.com.commom.base.BaseModel;
import microtech.hxswork.com.commom.base.BasePresenter;
import microtech.hxswork.com.commom.base.BaseView;
import microtech.hxswork.com.commom.basebean.BaseResponse;
import rx.Observable;

/**
 * Created by HXS on 2017/8/21.
 */

public class HomeContract  {
    public interface View extends BaseView {
        void returnToView(BaseResponse<HomeBean> response);
    }
    public static abstract class Presenter extends BasePresenter<View,Model> {
        public abstract void getMsg();
    }
public interface  Model extends BaseModel {
    Observable<BaseResponse<HomeBean>>getHomeInfo();
}
}
