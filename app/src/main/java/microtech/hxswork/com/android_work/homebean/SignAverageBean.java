package microtech.hxswork.com.android_work.homebean;

/**
 * Created by microtech on 2017/9/27.体征数据平均表
 */

public class SignAverageBean {

    /**
     * _id : str
     * region_id : str
     * user_id : str
     * box_id : str
     * sugar : {"total":"dubble","counts":"int"}
     * pressure_dia : {"total":"dubble","counts":"int"}
     * pressure_sys : {"total":"dubble","counts":"int"}
     * temperature : {"total":"dubble","counts":"int"}
     * heart : {"total":"dubble","counts":"int"}
     * oxygen : {"total":"dubble","counts":"int"}
     * timesDay : long
     * status : int
     */

    private String _id;
    private String region_id;
    private String user_id;
    private String box_id;
    private SugarBean sugar;
    private PressureDiaBean pressure_dia;
    private PressureSysBean pressure_sys;
    private TemperatureBean temperature;
    private HeartBean heart;
    private OxygenBean oxygen;
    private long timesDay;
    private int status;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public SugarBean getSugar() {
        return sugar;
    }

    public void setSugar(SugarBean sugar) {
        this.sugar = sugar;
    }

    public PressureDiaBean getPressure_dia() {
        return pressure_dia;
    }

    public void setPressure_dia(PressureDiaBean pressure_dia) {
        this.pressure_dia = pressure_dia;
    }

    public PressureSysBean getPressure_sys() {
        return pressure_sys;
    }

    public void setPressure_sys(PressureSysBean pressure_sys) {
        this.pressure_sys = pressure_sys;
    }

    public TemperatureBean getTemperature() {
        return temperature;
    }

    public void setTemperature(TemperatureBean temperature) {
        this.temperature = temperature;
    }

    public HeartBean getHeart() {
        return heart;
    }

    public void setHeart(HeartBean heart) {
        this.heart = heart;
    }

    public OxygenBean getOxygen() {
        return oxygen;
    }

    public void setOxygen(OxygenBean oxygen) {
        this.oxygen = oxygen;
    }

    public long getTimesDay() {
        return timesDay;
    }

    public void setTimesDay(long timesDay) {
        this.timesDay = timesDay;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public static class SugarBean {
        /**
         * total : dubble
         * counts : int
         */

        private double total;
        private int counts;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }

    public static class PressureDiaBean {
        /**
         * total : dubble
         * counts : int
         */

        private double total;
        private int counts;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }

    public static class PressureSysBean {
        /**
         * total : dubble
         * counts : int
         */

        private double total;
        private int counts;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }

    public static class TemperatureBean {
        /**
         * total : dubble
         * counts : int
         */

        private double total;
        private int counts;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }

    public static class HeartBean {
        /**
         * total : dubble
         * counts : int
         */

        private double total;
        private int counts;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }

    public static class OxygenBean {
        /**
         * total : dubble
         * counts : int
         */

        private double total;
        private int counts;

        public double getTotal() {
            return total;
        }

        public void setTotal(double total) {
            this.total = total;
        }

        public int getCounts() {
            return counts;
        }

        public void setCounts(int counts) {
            this.counts = counts;
        }
    }
}
