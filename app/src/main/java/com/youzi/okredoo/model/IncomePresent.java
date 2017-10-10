package com.youzi.okredoo.model;

/**
 * 赠送 粮票／土豆泥 记录
 * Created by suteng on 2017/6/12.
 */
public class IncomePresent {

    private String userName;
    private String day;
    private String hots;
    private String userHots;
    private String experience;
    private String userExperience;

    /** @see #hots */
    public String getHots() {
        return hots;
    }

    /** @see #day */
    public String getDay() {
        return day;
    }

    /** @see #userHots */
    public String getUserHots() {
        return userHots;
    }

    /** @see #userName */
    public String getUserName() {
        return userName;
    }

    /** @see #experience */
    public String getExperience() {
        return experience;
    }

    /** @see #userExperience */
    public String getUserExperience() {
        return userExperience;
    }

    /** @see #userName */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** @see #day */
    public void setDay(String day) {
        this.day = day;
    }

    /** @see #hots */
    public void setHots(String hots) {
        this.hots = hots;
    }

    /** @see #userHots */
    public void setUserHots(String userHots) {
        this.userHots = userHots;
    }

    /** @see #experience */
    public void setExperience(String experience) {
        this.experience = experience;
    }

    /** @see #userExperience */
    public void setUserExperience(String userExperience) {
        this.userExperience = userExperience;
    }
}
