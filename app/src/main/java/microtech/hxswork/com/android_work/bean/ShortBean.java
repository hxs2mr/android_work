package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/27.短信实体类
 */

public class ShortBean {

    /**
     * _id : str
     * phone : str
     * type : int
     * code : str
     * status : int
     * times : {"expired":"long","timed":"long","send":"long","checked":"long"}
     */

    private String _id;
    private String phone;
    private int type;
    private String code;
    private int status;
    private TimesBean times;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TimesBean getTimes() {
        return times;
    }

    public void setTimes(TimesBean times) {
        this.times = times;
    }

    public static class TimesBean {
        /**
         * expired : long
         * timed : long
         * send : long
         * checked : long
         */

        private long expired;
        private long timed;
        private long send;
        private long checked;

        public long getExpired() {
            return expired;
        }

        public void setExpired(long expired) {
            this.expired = expired;
        }

        public long getTimed() {
            return timed;
        }

        public void setTimed(long timed) {
            this.timed = timed;
        }

        public long getSend() {
            return send;
        }

        public void setSend(long send) {
            this.send = send;
        }

        public long getChecked() {
            return checked;
        }

        public void setChecked(long checked) {
            this.checked = checked;
        }
    }
}
