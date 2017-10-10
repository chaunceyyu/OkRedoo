package com.youzi.okredoo.model;

/**
 * 邀请佣金
 */
public class MyProfit {

    /** 累计佣金 */
    private String profit = "0";

    /** 佣金余额（可提现额） */
    private String balance = "0";

    /** 未到账 */
    private String uncollected = "0";

    /** @see #profit */
    public String getProfit() {
        return profit;
    }

    /** @see #profit */
    public void setProfit(String profit) {
        this.profit = profit;
    }

    /** @see #balance */
    public String getBalance() {
        return balance;
    }

    /** @see #balance */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /** @see #uncollected */
    public String getUncollected() {
        return uncollected;
    }

    /** @see #uncollected */
    public void setUncollected(String uncollected) {
        this.uncollected = uncollected;
    }

    public double getBalanceNumber() {
        try {
            return Double.parseDouble(balance);
        } catch (Exception e) {
            return 0d;
        }
    }
}
