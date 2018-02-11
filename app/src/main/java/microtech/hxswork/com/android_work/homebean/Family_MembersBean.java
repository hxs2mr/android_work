package microtech.hxswork.com.android_work.homebean;

/**
 * Created by microtech on 2017/9/27.家庭成员表
 */

public class Family_MembersBean {

    /**
     * _id : str
     * family_id : str
     * region_id : str
     * user_id : str
     * name : str
     * id_card : str
     * relation : int
     * id_type : int
     * sex : int
     * phone : str
     * birthday : str
     * status : int
     * times : {"joined":"long","updated":"long"}
     */

    private String _id;
    private String family_id;
    private String region_id;
    private String user_id;
    private String name;
    private String id_card;
    private int relation;
    private int id_type;
    private int sex;
    private String phone;
    private String birthday;
    private int status;
    private TimesBean times;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getFamily_id() {
        return family_id;
    }

    public void setFamily_id(String family_id) {
        this.family_id = family_id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public int getRelation() {
        return relation;
    }

    public void setRelation(int relation) {
        this.relation = relation;
    }

    public int getId_type() {
        return id_type;
    }

    public void setId_type(int id_type) {
        this.id_type = id_type;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
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
