/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;

/**
 *
 * @author robert
 */
public class BankStatement {
    private String transactionDate;
    private String valueDate;
    private String transactionDescription;
    private Double creditAmount;
    private Double accountBalance;
    private String tenantId;
    private String statementId;
     private String addedByUserId;

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public BankStatement(String transactionDate, String valueDate, String transactionDescription, Double creditAmount, Double accountBalance, String tenantId, String statementId,String addedById) {
        this.addedByUserId = addedById;
        this.transactionDate = transactionDate;
        this.valueDate = valueDate;
        this.transactionDescription = transactionDescription;
        this.creditAmount = creditAmount;
        this.accountBalance = accountBalance;
        this.tenantId = tenantId;
        this.statementId = statementId;
    }
    

    public BankStatement(String transactionDate, String valueDate, String transactionDescription, Double creditAmount, Double accountBalance, String tenantId) {
        this.transactionDate = transactionDate;
        this.valueDate = valueDate;
        this.transactionDescription = transactionDescription;
        this.creditAmount = creditAmount;
        this.accountBalance = accountBalance;
        this.tenantId = tenantId;
    }

    public String getStatementId() {
        return statementId;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(String transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public Double getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Double creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String saveStatement() {
        String id =DatabaseHandler.getInstance().insertBankStatement(this.transactionDate,this.valueDate, this.transactionDescription, this.creditAmount, this.accountBalance, this.tenantId);
        this.statementId =id;
        return this.statementId;
    }
    public HouseRentalContract getAssociatedContract(){
        return DatabaseHandler.getInstance().getCurrentContract(tenantId, null);
    }
}
