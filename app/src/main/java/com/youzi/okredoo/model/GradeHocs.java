package com.youzi.okredoo.model;

import java.io.Serializable;

/**
 * Created by hjw on 2017/4/15.
 */

public class GradeHocs implements Serializable {
    private String code;
    private String startGrade;
    private String endGrade;
    private String anime;
    private String animeUrl;
    private String tip;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStartGrade() {
        return startGrade;
    }

    public void setStartGrade(String startGrade) {
        this.startGrade = startGrade;
    }

    public String getEndGrade() {
        return endGrade;
    }

    public void setEndGrade(String endGrade) {
        this.endGrade = endGrade;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getAnimeUrl() {
        return animeUrl;
    }

    public void setAnimeUrl(String animeUrl) {
        this.animeUrl = animeUrl;
    }

    public String getTip() {
        return tip;
    }

    public void setTip(String tip) {
        this.tip = tip;
    }


    public String getDownloadPath() {
        return Gift.getDownloadPath(anime);
    }
}
