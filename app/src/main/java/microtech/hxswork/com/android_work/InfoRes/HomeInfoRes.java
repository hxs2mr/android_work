package microtech.hxswork.com.android_work.InfoRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import microtech.hxswork.com.android_work.bean.Home_Bean1;

/**
 * Created by HXS on 2017/8/21.
 */

public class HomeInfoRes implements MultiItemEntity {


    public static final int SIGN_DATA= 0;
    public static final int HEALTH_DATA = 1;
    public static final int OTHER_DATA=2;
    private int type;//子界面类型

    public  String healthBeen ;//= new ArrayList<>();//
    public   Home_Bean1 home_bean1;


    public String getHealthBeen() {
        return healthBeen;
    }

    public void setHealthBeen(String healthBeen) {
        this.healthBeen = healthBeen;
    }

    public Home_Bean1 getHome_bean1() {
        return home_bean1;
    }

    public void setHome_bean1(Home_Bean1 home_bean1) {
        this.home_bean1 = home_bean1;
    }

    public int getType() {
        return type;
    }



    public void setType(int type) {
        this.type = type;
    }

    public HomeInfoRes(int type, String healthBeen,Home_Bean1 home_bean1) {
        this.type = type;
        this.healthBeen =healthBeen;
        this.home_bean1 = home_bean1;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
