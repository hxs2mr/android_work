package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/8/30.
 */

public class InforMationReycleViewBean {

    String type;//消息类型 //医生消息还是系统消息 0标识医生发来的消息  1标识系统消息
    String name; //用户名
    String headimage_url;//用户头像
    String name_message;//用户说的话
    String time;//发送时间
    String message_nums;//多少条数据

    public InforMationReycleViewBean(String type, String name, String headimage_url, String name_message, String time, String message_nums) {
        this.type = type;
        this.name = name;
        this.headimage_url = headimage_url;
        this.name_message = name_message;
        this.time = time;
        this.message_nums = message_nums;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadimage_url() {
        return headimage_url;
    }

    public void setHeadimage_url(String headimage_url) {
        this.headimage_url = headimage_url;
    }

    public String getName_message() {
        return name_message;
    }

    public void setName_message(String name_message) {
        this.name_message = name_message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage_nums() {
        return message_nums;
    }

    public void setMessage_nums(String message_nums) {
        this.message_nums = message_nums;
    }
}
