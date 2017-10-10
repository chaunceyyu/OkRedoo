package com.youzi.okredoo.model.response;

import java.io.Serializable;

/**
 * Created by 24020 on 2017/3/19.
 */
public class HongbaosendResponse implements Serializable {
//    amount	用户余额
//    rpid
    private String rpid;
    private String amount;

    public String getRpid() {
        return rpid;
    }

    public void setRpid(String rpid) {
        this.rpid = rpid;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
