package microtech.hxswork.com.android_work.InfoRes;

/**
 * Created by Administrator on 2017/4/18.
 */

public class LoginRes {

    /**
     * usermsg : {"uid":1,"username":"user1","password":"10001","email":"979137@qq.com","qq":979137,"gold":1500}
     * isLogin : true
     * unread : 4
     * untask : 3
     */
    private UsermsgBean usermsg;
    private boolean isLogin;
    private int unread;
    private int untask;

    public UsermsgBean getUsermsg() {
        return usermsg;
    }

    public void setUsermsg(UsermsgBean usermsg) {
        this.usermsg = usermsg;
    }

    public boolean isIsLogin() {
        return isLogin;
    }

    public void setIsLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    public int getUnread() {
        return unread;
    }

    public void setUnread(int unread) {
        this.unread = unread;
    }

    public int getUntask() {
        return untask;
    }

    public void setUntask(int untask) {
        this.untask = untask;
    }

    public static class UsermsgBean {
        /**
         * uid : 1
         * username : user1
         * password : 10001
         * email : 979137@qq.com
         * qq : 979137
         * gold : 1500
         */
        private int uid;
        private String username;
        private String password;
        private String email;
        private int qq;
        private int gold;

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getQq() {
            return qq;
        }

        public void setQq(int qq) {
            this.qq = qq;
        }

        public int getGold() {
            return gold;
        }

        public void setGold(int gold) {
            this.gold = gold;
        }
    }
}
