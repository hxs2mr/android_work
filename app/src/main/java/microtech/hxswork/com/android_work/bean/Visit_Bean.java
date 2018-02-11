package microtech.hxswork.com.android_work.bean;

/**
 * Created by microtech on 2017/10/23.
 */

public class Visit_Bean {

    /**
     * day : 4
     * forenoon : 1
     * at_noon : 1
     * evening : 0
     */

    private String day;
    private int forenoon;
    private int at_noon;
    private int evening;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getForenoon() {
        return forenoon;
    }

    public void setForenoon(int forenoon) {
        this.forenoon = forenoon;
    }

    public int getAt_noon() {
        return at_noon;
    }

    public void setAt_noon(int at_noon) {
        this.at_noon = at_noon;
    }

    public int getEvening() {
        return evening;
    }

    public void setEvening(int evening) {
        this.evening = evening;
    }
}
