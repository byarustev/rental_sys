/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.util.Date;

/**
 *
 * @author robert
 */
public class Payment {
    String paymentDate;
    Double paymentAmount;
    String rentalContractId;
    String receivedBy;
    String tenantId;

    public Payment(String paymentDate, Double paymentAmount, String rentalContractId, String receivedBy, String tenantId, String modeOfPayment, String referenceNumber) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.rentalContractId = rentalContractId;
        this.receivedBy = receivedBy;
        this.tenantId = tenantId;
        this.modeOfPayment = modeOfPayment;
        this.referenceNumber = referenceNumber;
    }
    String modeOfPayment;
    String referenceNumber;

    public String getRentalContractId() {
        return rentalContractId;
    }

    public void setRentalContractId(String rentalContractId) {
        this.rentalContractId = rentalContractId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }

   
    
    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getRentalContract() {
        return rentalContractId;
    }

    public void setRentalContract(String rentalContract) {
        this.rentalContractId = rentalContract;
    }

    public String getReceivedBy() {
        return receivedBy;
    }

    public void setReceivedBy(String receivedBy) {
        this.receivedBy = receivedBy;
    }
   
    public String savePayment(){
        String id = DatabaseHandler.getInstance().savePayment(paymentDate,paymentAmount, rentalContractId, receivedBy,tenantId,modeOfPayment,referenceNumber);
        return id;
    }
    
}
