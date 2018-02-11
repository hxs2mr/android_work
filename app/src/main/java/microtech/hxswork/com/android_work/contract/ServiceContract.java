package microtech.hxswork.com.android_work.contract;

import microtech.hxswork.com.commom.base.BaseModel;
import microtech.hxswork.com.commom.base.BasePresenter;
import microtech.hxswork.com.commom.base.BaseView;

/**
 * Created by microtech on 2017/8/22.
 */

public class ServiceContract {
    public interface View extends BaseView
    {

    }
    public static abstract class Presenter extends BasePresenter<View,Model> {

    }
    public interface Model extends BaseModel {

    }
}
