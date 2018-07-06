/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;


/**
 *
 * @author robert
 */
public class Payment {
   private String paymentDate;
   private Double paymentAmount;
   private String rentalContractId;
   private String receivedBy;
   private String tenantId;
   private String paymentId;
   private String addedByUserId;

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    /**
     *
     * @param paymentDate
     * @param paymentAmount
     * @param rentalContractId
     * @param receivedBy
     * @param tenantId
     * @param modeOfPayment
     * @param referenceNumber
     */
    public Payment(String paymentDate, Double paymentAmount, String rentalContractId, String receivedBy, String tenantId, String modeOfPayment, String referenceNumber) {
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.rentalContractId = rentalContractId;
        this.receivedBy = receivedBy;
        this.tenantId = tenantId;
        this.modeOfPayment = modeOfPayment;
        this.referenceNumber = referenceNumber;
    }
    public Payment(String paymentId,String paymentDate, Double paymentAmount, String rentalContractId, String receivedBy, String tenantId, String modeOfPayment, String referenceNumber,String addedById) {
        this.addedByUserId = addedById;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;
        this.rentalContractId = rentalContractId;
        this.receivedBy = receivedBy;
        this.tenantId = tenantId;
        this.modeOfPayment = modeOfPayment;
        this.referenceNumber = referenceNumber;
        this.paymentId = paymentId;
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
    
    public HouseRentalContract getAssociatedContract(){
        return DatabaseHandler.getInstance().getContract(this.rentalContractId);
    }

    public boolean update() {
      return DatabaseHandler.getInstance().updatePayment(this);
    }
    
    public JSONObject toJSON(){
        JSONObject payment = new JSONObject();
       try {
            payment.put("paymentAmount", this.paymentAmount);
            payment.put("paymentDate", this.paymentDate);
            payment.put("rentalContractId", this.rentalContractId);
            payment.put("receivedBy", this.receivedBy);
            payment.put("tenantId", this.tenantId);
            payment.put("paymentId", this.paymentId);
            payment.put("addedByUserId", this.addedByUserId);
            return payment;
       } catch (JSONException ex) {
           Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
    }
}
