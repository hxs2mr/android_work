package microtech.hxswork.com.android_work.homebean;

/**
 * Created by microtech on 2017/9/27.主界面体征表
 */

public class SignBean {

    /**
     * _id : str
     * user_id : str
     * box_id : str
     * sugar : {"value":"double","unit":"str","status":"int","times":"long"}
     * pressure : {"dia_value":"dubble","sys_value":"dubble","unit":"str","status":"int","times":"long"}
     * temperature : {"value":"dubble","unit":"str","status":"int","times":"long"}
     * heart : {"value":"dubble","unit":"str","status":"int","times":"long"}
     * oxygen : {"value":"dubble","unit":"str","status":"int","times":"long"}
     * times : {"timed":"long","timeu":"long"}
     */

    private String _id;
    private String user_id;
    private String box_id;
    private SugarBean sugar;
    private PressureBean pressure;
    private TemperatureBean temperature;
    private HeartBean heart;
    private OxygenBean oxygen;
    private TimesBean times;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public PressureBean getPressure() {
        return pressure;
    }

    public void setPressure(PressureBean pressure) {
        this.pressure = pressure;
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

    public TimesBean getTimes() {
        return times;
    }

    public void setTimes(TimesBean times) {
        this.times = times;
    }

    public static class SugarBean {
        /**
         * value : double
         * unit : str
         * status : int
         * times : long
         */

        private double value;
        private String unit;
        private int status;
        private long times;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }

    public static class PressureBean {
        /**
         * dia_value : dubble
         * sys_value : dubble
         * unit : str
         * status : int
         * times : long
         */

        private double dia_value;
        private double sys_value;
        private String unit;
        private int status;
        private long times;

        public double getDia_value() {
            return dia_value;
        }

        public void setDia_value(double dia_value) {
            this.dia_value = dia_value;
        }

        public double getSys_value() {
            return sys_value;
        }

        public void setSys_value(double sys_value) {
            this.sys_value = sys_value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }

    public static class TemperatureBean {
        /**
         * value : dubble
         * unit : str
         * status : int
         * times : long
         */

        private double value;
        private String unit;
        private int status;
        private long times;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }

    public static class HeartBean {
        /**
         * value : dubble
         * unit : str
         * status : int
         * times : long
         */

        private double value;
        private String unit;
        private int status;
        private long times;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }

    public static class OxygenBean {
        /**
         * value : dubble
         * unit : str
         * status : int
         * times : long
         */

        private double value;
        private String unit;
        private int status;
        private long times;

        public double getValue() {
            return value;
        }

        public void setValue(double value) {
            this.value = value;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public long getTimes() {
            return times;
        }

        public void setTimes(long times) {
            this.times = times;
        }
    }

    public static class TimesBean {
        /**
         * timed : long
         * timeu : long
         */

        private long timed;
        private long timeu;

        public long getTimed() {
            return timed;
        }

        public void setTimed(long timed) {
            this.timed = timed;
        }

        public long getTimeu() {
            return timeu;
        }

        public void setTimeu(long timeu) {
            this.timeu = timeu;
        }
    }
}
