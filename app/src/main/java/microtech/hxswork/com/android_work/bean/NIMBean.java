package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/11/9.
 */

public class NIMBean {
    String content;
    String time;

    public NIMBean(String content, String time) {
        this.content = content;
        this.time = time;
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
