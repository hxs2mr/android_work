package microtech.hxswork.com.android_work.InfoRes;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by microtech on 2017/9/11.
 */

public class ServiceInfoRes implements MultiItemEntity {

    public static final int FRIST_DATA= 0;
    public static final int SEND_DATA = 1;
    private int type;//子界面类型
    private List<Object> imgList;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<Object> getImgList() {
        return imgList;
    }

    public void setImgList(List<Object> imgList) {
        this.imgList = imgList;
    }

    public ServiceInfoRes(int type, List<Object> imgList) {
        this.type = type;
        this.imgList = imgList;
    }

    @Override
    public int getItemType() {
        return type;
    }
}
