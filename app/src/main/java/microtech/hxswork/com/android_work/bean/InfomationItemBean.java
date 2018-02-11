package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/14.
 */

public class InfomationItemBean {
    String head_image;
    String titel;
    String content;
    String time;

    public InfomationItemBean(String head_image, String titel, String content, String time) {
        this.head_image = head_image;
        this.titel = titel;
        this.content = content;
        this.time = time;
    }

    public String getHead_image() {
        return head_image;
    }

    public void setHead_image(String head_image) {
        this.head_image = head_image;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
