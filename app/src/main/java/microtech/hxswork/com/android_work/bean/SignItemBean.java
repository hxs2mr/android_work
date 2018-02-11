package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/25.
 */

public class SignItemBean {

    int type;//0标识当天时间
    String sign_time;

    String number;
    String zhuantai;
    String time;


    public SignItemBean(int type, String sign_time, String number, String zhuantai, String time) {
        this.type = type;
        this.sign_time = sign_time;
        this.number = number;
        this.zhuantai = zhuantai;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getSign_time() {
        return sign_time;
    }

    public void setSign_time(String sign_time) {
        this.sign_time = sign_time;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getZhuantai() {
        return zhuantai;
    }

    public void setZhuantai(String zhuantai) {
        this.zhuantai = zhuantai;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
