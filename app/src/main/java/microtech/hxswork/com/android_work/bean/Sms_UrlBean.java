package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/29.
 */
public class Sms_UrlBean {
    int code;
    String msg;
    String obj;

    public Sms_UrlBean(int code, String msg, String obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }
}
