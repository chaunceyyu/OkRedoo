package com.youzi.okredoo.model;

/**
 * Created by houjingwei on 2016/6/11.
 */
public class Other {

    private String isFollow;

    private String hots;
    private String fans;
    private String collects;
    private String balance;
    private String loves;
    private String status;
    private String result;
    private String gid;
    private String gName;
    private String coins;
    private String amount;
    private String url;
    private String format;
    private String isParise;

    public String getCoins() {
        return coins;
    }

    public void setCoins(String coins) {
        this.coins = coins;
    }

    public String getIsFollow() {
        return isFollow;
    }

    public boolean isFollow() {
        return "1".equals(getIsFollow());
    }

    public void setIsFollow(String isFollow) {
        this.isFollow = isFollow;
    }

    public String getHots() {
        return hots;
    }

    public void setHots(String hots) {
        this.hots = hots;
    }

    public String getFans() {
        return fans;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public String getCollects() {
        return collects;
    }

    public void setCollects(String collects) {
        this.collects = collects;
    }


    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getLoves() {
        return loves;
    }

    public void setLoves(String loves) {
        this.loves = loves;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getgName() {
        return gName;
    }

    public void setgName(String gName) {
        this.gName = gName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getIsParise() {
        return isParise;
    }

    public void setIsParise(String isParise) {
        this.isParise = isParise;
    }
}
