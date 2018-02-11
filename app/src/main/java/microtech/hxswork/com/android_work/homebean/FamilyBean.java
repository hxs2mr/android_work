package microtech.hxswork.com.android_work.homebean;

/**
 * Created by microtech on 2017/9/27.家庭实体类
 */

public class FamilyBean {

    /**
     * _id : str
     * region_id : str
     * doctor_id : str
     * doctor_name : str
     * follow_id : str
     * name : str
     * sex : int
     * phone : str
     * address : str
     * family_name : str
     * box_id : str
     * location : {"lng":"Double","lat":"Double"}
     * counts : {"members":"int","visits":"int","orders":"int","trades":"int"}
     * status : int
     * join : bool
     * times : {"joined":"long","updated":"long"}
     */

    private String _id;
    private String region_id;
    private String doctor_id;
    private String doctor_name;
    private String follow_id;
    private String name;
    private int sex;
    private String phone;
    private String address;
    private String family_name;
    private String[] box_id;
    private LocationBean location;
    private CountsBean counts;
    private int status;
    private boolean join;
    private TimesBean times;

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

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getDoctor_name() {
        return doctor_name;
    }

    public void setDoctor_name(String doctor_name) {
        this.doctor_name = doctor_name;
    }

    public String getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(String follow_id) {
        this.follow_id = follow_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFamily_name() {
        return family_name;
    }

    public void setFamily_name(String family_name) {
        this.family_name = family_name;
    }

    public String[] getBox_id() {
        return box_id;
    }

    public void setBox_id(String[] box_id) {
        this.box_id = box_id;
    }
    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public CountsBean getCounts() {
        return counts;
    }

    public void setCounts(CountsBean counts) {
        this.counts = counts;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isJoin() {
        return join;
    }

    public void setJoin(boolean join) {
        this.join = join;
    }

    public TimesBean getTimes() {
        return times;
    }

    public void setTimes(TimesBean times) {
        this.times = times;
    }

    public static class LocationBean {
        /**
         * lng : Double
         * lat : Double
         */

        private double lng;
        private double lat;

        public double getLng() {
            return lng;
        }

        public void setLng(double lng) {
            this.lng = lng;
        }

        public double getLat() {
            return lat;
        }

        public void setLat(double lat) {
            this.lat = lat;
        }
    }

    public static class CountsBean {
        /**
         * members : int
         * visits : int
         * orders : int
         * trades : int
         */

        private int members;
        private int visits;
        private int orders;
        private int trades;

        public int getMembers() {
            return members;
        }

        public void setMembers(int members) {
            this.members = members;
        }

        public int getVisits() {
            return visits;
        }

        public void setVisits(int visits) {
            this.visits = visits;
        }

        public int getOrders() {
            return orders;
        }

        public void setOrders(int orders) {
            this.orders = orders;
        }

        public int getTrades() {
            return trades;
        }

        public void setTrades(int trades) {
            this.trades = trades;
        }
    }

    public static class TimesBean {
        /**
         * joined : long
         * updated : long
         */

        private long joined;
        private long updated;

        public long getJoined() {
            return joined;
        }

        public void setJoined(long joined) {
            this.joined = joined;
        }

        public long getUpdated() {
            return updated;
        }

        public void setUpdated(long updated) {
            this.updated = updated;
        }
    }
}
