package microtech.hxswork.com.android_work.homebean;

/**
 * Created by microtech on 2017/9/27. 主界面健康分析表
 */

public class HealthFenBean {

    /**
     * _id : str
     * region_id : str
     * user_id : str
     * box_id : str
     * content : str
     * times : long
     * status : int
     */

    private String _id;
    private String region_id;
    private String user_id;
    private String box_id;
    private String content;
    private long times;
    private int status;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimes() {
        return times;
    }

    public void setTimes(long times) {
        this.times = times;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
