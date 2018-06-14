/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author robert
 */
public class HouseRentalContract implements RentalContract {
     String startDate;
     String associatedTenant;
     String contractId;
     String associatedHouse;
     Double agreedMonthlyAmount; 

    /**
     * This is used to create a Contract from the database since it requires a contractId
     * @param startDate
     * @param tenantId
     * @param contractId
     * @param houseId
     * @param agreedMonthlyAmount
     */
    public HouseRentalContract(String startDate, String tenantId, String contractId, String houseId, Double agreedMonthlyAmount) {
        this(startDate,tenantId,houseId,agreedMonthlyAmount);
        this.contractId = contractId;
    } 
    
    /**
     * This is used to create a contract for the first time before its saved in the database
     * @param startDate
     * @param tenantId
     * @param houseId
     * @param agreedMonthlyAmount
     */
    public HouseRentalContract(String startDate, String tenantId, String houseId, Double agreedMonthlyAmount) {
        this.startDate = startDate;
        this.agreedMonthlyAmount = agreedMonthlyAmount;
        this.associatedHouse = houseId;
        this.associatedTenant =tenantId;
    }
    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /**
     *
     * @return tenantId
     */

    public String getTenantId() {
        return associatedTenant;
    }

    public void setTenantId(String associatedTenant) {
        this.associatedTenant = associatedTenant;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getAssociatedHouse() {
        return associatedHouse;
    }

    public void setAssociatedHouse(String associatedHouse) {
        this.associatedHouse = associatedHouse;
    }

    public Double getAgreedMonthlyAmount() {
        return agreedMonthlyAmount;
    }

    public void setAgreedAmount(Double agreedAmount) {
        this.agreedMonthlyAmount = agreedAmount;
    }

    @Override
    public Double computeBalance() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Payment> getPayments(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Payment> getPayments() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String saveContract() {
        if(this.contractId==null){
            String id = DatabaseHandler.getInstance().insertHouseContract(this.associatedTenant,this.associatedHouse, this.startDate, this.agreedMonthlyAmount);
            this.setContractId(id);
            return this.contractId;
        }
        else{
            return this.contractId;
        }
       
    }

    @Override
    public Tenant getAssociatedTenant() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
