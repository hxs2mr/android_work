package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/9/18.
 */

public class HeathMoreBean {
    int code;
    more_data xueya;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public more_data getXueya() {
        return xueya;
    }

    public void setXueya(more_data xueya) {
        this.xueya = xueya;
    }

    public HeathMoreBean(int code, more_data xueya) {
        this.code = code;
        this.xueya = xueya;
    }

    public static class more_data {
        String time;
        String zhuan;
        String zhuan_zhi;
        String zhuan_fen;
        int flage;

        public more_data(String time, String zhuan, String zhuan_zhi, String zhuan_fen, int flage) {
            this.time = time;
            this.zhuan = zhuan;
            this.zhuan_zhi = zhuan_zhi;
            this.zhuan_fen = zhuan_fen;
            this.flage = flage;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getZhuan() {
            return zhuan;
        }

        public void setZhuan(String zhuan) {
            this.zhuan = zhuan;
        }

        public String getZhuan_zhi() {
            return zhuan_zhi;
        }

        public void setZhuan_zhi(String zhuan_zhi) {
            this.zhuan_zhi = zhuan_zhi;
        }

        public String getZhuan_fen() {
            return zhuan_fen;
        }

        public void setZhuan_fen(String zhuan_fen) {
            this.zhuan_fen = zhuan_fen;
        }

        public int getFlage() {
            return flage;
        }

        public void setFlage(int flage) {
            this.flage = flage;
        }
    }
}
