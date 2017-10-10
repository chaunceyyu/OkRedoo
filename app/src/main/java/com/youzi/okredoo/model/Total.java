package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/15.
 */

public class Total implements Serializable {
    private String dayCount;
    private String total;
    private String weekCount;
    private String monthCount;


    public String getDayCount() {
        return dayCount;
    }

    public void setDayCount(String dayCount) {
        this.dayCount = dayCount;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getWeekCount() {
        return weekCount;
    }

    public void setWeekCount(String weekCount) {
        this.weekCount = weekCount;
    }

    public String getMonthCount() {
        return monthCount;
    }

    public void setMonthCount(String monthCount) {
        this.monthCount = monthCount;
    }
}
