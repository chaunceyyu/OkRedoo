package com.youzi.okredoo.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hjw on 2017/4/15.
 */

public class AppConfig implements Serializable {

    private List<GradeHocs> gradeHocs;
    private String videoRecordMax;
    private String videoRecordMin;
    private String livePlayUrl;

    private String liveFrameRate;   //帧率
    private String liveCodeRateMin; //最小码率，单位kb
    private String liveCodeRateMax; //最大码率，单位kb

    public String getLivePlayUrl() {
        return livePlayUrl;
    }

    public List<GradeHocs> getGradeHocs() {
        return gradeHocs;
    }

    public String getVideoRecordMax() {
        return videoRecordMax;
    }

    public String getVideoRecordMin() {
        return videoRecordMin;
    }

    public int getLiveFrameRate() {
        int n = 0;
        try {
            n = Integer.parseInt(liveFrameRate);
        } catch (Exception e) {
        }
        return n > 0 ? n : 20;
    }

    public int getLiveCodeRateMin() {
        int n = 0;
        try {
            n = Integer.parseInt(liveCodeRateMin);
        } catch (Exception e) {
        }
        return (n > 0 ? n : 500) * 1024;
    }

    public int getLiveCodeRateMax() {
        int n = 0;
        try {
            n = Integer.parseInt(liveCodeRateMax);
        } catch (Exception e) {
        }
        if (n > 0) {
            return n * 1024;
        }
        int fps = getLiveFrameRate();
        int base = fps > 20 ? 1500 : fps > 15 ? 1200 : 1000;
        return base * 1024;
    }
}
