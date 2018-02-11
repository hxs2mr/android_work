package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/11.
 */

public class SIBean {
        String image_url;
        String title;
        String mokey;
        String renshu;
        String nerong;
        String shuoming;
        String pingjial;
        String shiyong;

    public SIBean(String image_url, String title, String mokey, String renshu, String nerong, String shuoming, String pingjial, String shiyong) {
        this.image_url = image_url;
        this.title = title;
        this.mokey = mokey;
        this.renshu = renshu;
        this.nerong = nerong;
        this.shuoming = shuoming;
        this.pingjial = pingjial;
        this.shiyong = shiyong;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMokey() {
        return mokey;
    }

    public void setMokey(String mokey) {
        this.mokey = mokey;
    }

    public String getRenshu() {
        return renshu;
    }

    public void setRenshu(String renshu) {
        this.renshu = renshu;
    }

    public String getNerong() {
        return nerong;
    }

    public void setNerong(String nerong) {
        this.nerong = nerong;
    }

    public String getShuoming() {
        return shuoming;
    }

    public void setShuoming(String shuoming) {
        this.shuoming = shuoming;
    }

    public String getPingjial() {
        return pingjial;
    }

    public void setPingjial(String pingjial) {
        this.pingjial = pingjial;
    }

    public String getShiyong() {
        return shiyong;
    }

    public void setShiyong(String shiyong) {
        this.shiyong = shiyong;
    }
}
