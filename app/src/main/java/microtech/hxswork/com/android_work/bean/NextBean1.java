package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/6.
 */

public class NextBean1 {
    String name;//名称
    String url;//webview链接
    String image_url;
    String time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public NextBean1(String name, String url, String image_url, String time) {
        this.name = name;
        this.url = url;
        this.image_url = image_url;
        this.time = time;
    }
}
