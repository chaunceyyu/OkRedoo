package com.youzi.okredoo.model;

/**
 * 我的收益(土豆泥)
 */
public class MyExperience {

    /** 余额 */
    private String balance;
    /** 总收益 */
    private String income;
    /** 总支出 */
    private String expense;

    /** @see #balance */
    public String getBalance() {
        return balance;
    }

    /** @see #balance */
    public void setBalance(String balance) {
        this.balance = balance;
    }

    /** @see #income */
    public String getIncome() {
        return income;
    }

    /** @see #income */
    public void setIncome(String income) {
        this.income = income;
    }

    /** @see #expense */
    public String getExpense() {
        return expense;
    }

    /** @see #expense */
    public void setExpense(String expense) {
        this.expense = expense;
    }
}
