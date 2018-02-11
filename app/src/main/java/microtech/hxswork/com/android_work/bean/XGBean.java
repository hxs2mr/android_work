package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/11/7.
 */

public class XGBean {
    String title;
    String content;
    String coustment;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCoustment() {
        return coustment;
    }

    public void setCoustment(String coustment) {
        this.coustment = coustment;
    }

    public XGBean(String title, String content, String coustment) {
        this.title = title;
        this.content = content;
        this.coustment = coustment;
    }
}
