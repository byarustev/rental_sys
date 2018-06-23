/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

/**
 *
 * @author robert
 */
public class MonthReport {
    String month;
    Double expectedAmount;
    Double actualAmountPaid;
    Double balance;
    Double cumulativeBalance;

    public MonthReport(String month, Double expectedAmount, Double actualAmountPaid, Double balance, Double cumulativeBalance) {
        this.month = month;
        this.expectedAmount = expectedAmount;
        this.actualAmountPaid = actualAmountPaid;
        this.balance = balance;
        this.cumulativeBalance = cumulativeBalance;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public Double getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(Double expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public Double getActualAmountPaid() {
        return actualAmountPaid;
    }

    public void setActualAmountPaid(Double actualAmountPaid) {
        this.actualAmountPaid = actualAmountPaid;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getCumulativeBalance() {
        return cumulativeBalance;
    }

    public void setCumulativeBalance(Double cumulativeBalance) {
        this.cumulativeBalance = cumulativeBalance;
    }
}
