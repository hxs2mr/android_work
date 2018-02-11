package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/27.
 */

public class OptionNextBean {

    String str1;
    String str1_time;

    String str2;
    String Str2_time;

    public OptionNextBean(String str1, String str1_time, String str2, String str2_time) {
        this.str1 = str1;
        this.str1_time = str1_time;
        this.str2 = str2;
        Str2_time = str2_time;
    }

    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr1_time() {
        return str1_time;
    }

    public void setStr1_time(String str1_time) {
        this.str1_time = str1_time;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr2_time() {
        return Str2_time;
    }

    public void setStr2_time(String str2_time) {
        Str2_time = str2_time;
    }
}
