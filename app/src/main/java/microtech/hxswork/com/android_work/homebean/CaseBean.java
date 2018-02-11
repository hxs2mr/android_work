package microtech.hxswork.com.android_work.homebean;

import java.util.List;

/**
 * Created by microtech on 2017/9/27.病例实体类
 */

public class CaseBean {

    /**
     * id : str
     * region_id : str
     * gov_id : str
     * user_id : str
     * name : str
     * nation : str
     * phone : str
     * marriage : str
     * id_card : str
     * profession : str
     * sex : int
     * age : int
     * birth_data : long
     * birth_region : str
     * birth_addr : str
     * suit : str
     * doctor_date : long
     * gov_ext_id : str
     * type : int
     * number : str
     * illness : str
     * always : str
     * personage : str
     * marital : str
     * familys : str
     * diagnose : [{"doctor_date":"long","record_des":"str","images":[{"url":"str","ratio":"Float"}]}]
     * box_id : str
     * status : int
     */

    private String id;
    private String region_id;
    private String gov_id;
    private String user_id;
    private String name;
    private String nation;
    private String phone;
    private String marriage;
    private String id_card;
    private String profession;
    private int sex;
    private int age;
    private long birth_data;
    private String birth_region;
    private String birth_addr;
    private String suit;
    private long doctor_date;
    private String gov_ext_id;
    private int type;
    private String number;
    private String illness;
    private String always;
    private String personage;
    private String marital;
    private String familys;
    private String box_id;
    private int status;
    private List<DiagnoseBean> diagnose;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRegion_id() {
        return region_id;
    }

    public void setRegion_id(String region_id) {
        this.region_id = region_id;
    }

    public String getGov_id() {
        return gov_id;
    }

    public void setGov_id(String gov_id) {
        this.gov_id = gov_id;
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

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getId_card() {
        return id_card;
    }

    public void setId_card(String id_card) {
        this.id_card = id_card;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public long getBirth_data() {
        return birth_data;
    }

    public void setBirth_data(long birth_data) {
        this.birth_data = birth_data;
    }

    public String getBirth_region() {
        return birth_region;
    }

    public void setBirth_region(String birth_region) {
        this.birth_region = birth_region;
    }

    public String getBirth_addr() {
        return birth_addr;
    }

    public void setBirth_addr(String birth_addr) {
        this.birth_addr = birth_addr;
    }

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public long getDoctor_date() {
        return doctor_date;
    }

    public void setDoctor_date(long doctor_date) {
        this.doctor_date = doctor_date;
    }

    public String getGov_ext_id() {
        return gov_ext_id;
    }

    public void setGov_ext_id(String gov_ext_id) {
        this.gov_ext_id = gov_ext_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIllness() {
        return illness;
    }

    public void setIllness(String illness) {
        this.illness = illness;
    }

    public String getAlways() {
        return always;
    }

    public void setAlways(String always) {
        this.always = always;
    }

    public String getPersonage() {
        return personage;
    }

    public void setPersonage(String personage) {
        this.personage = personage;
    }

    public String getMarital() {
        return marital;
    }

    public void setMarital(String marital) {
        this.marital = marital;
    }

    public String getFamilys() {
        return familys;
    }

    public void setFamilys(String familys) {
        this.familys = familys;
    }

    public String getBox_id() {
        return box_id;
    }

    public void setBox_id(String box_id) {
        this.box_id = box_id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<DiagnoseBean> getDiagnose() {
        return diagnose;
    }

    public void setDiagnose(List<DiagnoseBean> diagnose) {
        this.diagnose = diagnose;
    }

    public static class DiagnoseBean {
        /**
         * doctor_date : long
         * record_des : str
         * images : [{"url":"str","ratio":"Float"}]
         */

        private long doctor_date;
        private String record_des;
        private List<ImagesBean> images;

        public long getDoctor_date() {
            return doctor_date;
        }

        public void setDoctor_date(long doctor_date) {
            this.doctor_date = doctor_date;
        }

        public String getRecord_des() {
            return record_des;
        }

        public void setRecord_des(String record_des) {
            this.record_des = record_des;
        }

        public List<ImagesBean> getImages() {
            return images;
        }

        public void setImages(List<ImagesBean> images) {
            this.images = images;
        }

        public static class ImagesBean {
            /**
             * url : str
             * ratio : Float
             */

            private String url;
            private float ratio;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public float getRatio() {
                return ratio;
            }

            public void setRatio(float ratio) {
                this.ratio = ratio;
            }
        }
    }
}
