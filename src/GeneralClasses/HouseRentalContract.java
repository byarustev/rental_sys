/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author robert
 */
public class HouseRentalContract implements RentalContract {
     String startDate;
     String associatedTenantId;
     String contractId;
     String associatedHouseId;
     Double agreedMonthlyAmount; 
     House house;
     Tenant tenant;
     ArrayList<Payment> contractPayments;

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
        this.associatedHouseId = houseId;
        this.associatedTenantId =tenantId;
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
        return associatedTenantId;
    }

    public void setTenantId(String associatedTenant) {
        this.associatedTenantId = associatedTenant;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getAssociatedHouse() {
        return associatedHouseId;
    }

    public void setAssociatedHouse(String associatedHouse) {
        this.associatedHouseId = associatedHouse;
    }

    public Double getAgreedMonthlyAmount() {
        return agreedMonthlyAmount;
    }

    public void setAgreedAmount(Double agreedAmount) {
        this.agreedMonthlyAmount = agreedAmount;
    }

    @Override
    public Double computeBalance() {
        int fullMonths = this.computeFullMonths();
        double totalPayment=this.computeTotalPayments();
        double expectedAmount = fullMonths*this.agreedMonthlyAmount;
        System.out.println(this.startDate+" payed "+totalPayment+" instead of "+expectedAmount+" for "+fullMonths +" months");
        return expectedAmount-totalPayment;
    }

    @Override
    public ArrayList<Payment> getPayments(Date startDate, Date endDate) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<Payment> getPayments() {
        if(this.contractPayments ==null){
            this.contractPayments = DatabaseHandler.getInstance().getContractPayments(this.contractId);
        }
        return contractPayments;
    }

    @Override
    public String saveContract() {
        if(this.contractId==null){
            String id = DatabaseHandler.getInstance().insertHouseContract(this.associatedTenantId,this.associatedHouseId, this.startDate, this.agreedMonthlyAmount);
            this.setContractId(id);
            return this.contractId;
        }
        else{
            return this.contractId;
        }
    }

    public House getCurrentHouse(){
        if(this.house ==null){
            this.house =DatabaseHandler.getInstance().getHouse(associatedHouseId);
            return this.house;
        }
        else{
            return this.house;
        }
    }
    @Override
    public Tenant getAssociatedTenant() {
        if(this.tenant==null){
            this.tenant = DatabaseHandler.getInstance().getTenant(associatedTenantId);
            return this.tenant;
        }
        else{
            return this.tenant;
        }
    }

    @Override
    public String toString() {
        return "HouseRentalContract{" + "startDate=" + startDate + ", associatedTenantId=" + associatedTenantId + ", contractId=" + contractId + ", associatedHouseId=" + associatedHouseId + ", agreedMonthlyAmount=" + agreedMonthlyAmount + ", house=" + house + ", tenant=" + tenant + '}';
    }

    public int computeFullMonths() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String start = this.startDate;
        
        LocalDateTime now  = LocalDateTime.now();
        String end = now.format(formatter);
        //end ="2018-10-07";
        LocalDate from = LocalDate.parse(start, formatter);
        LocalDate to = LocalDate.parse(end, formatter);
        int fullMonths =  (int)from.until(to, ChronoUnit.MONTHS);
        return fullMonths;
    }

    public double computeTotalPayments() {
        double totalPayment=0;
        for(Payment y :this.getPayments()){
            totalPayment+=y.getPaymentAmount();
        }
        return totalPayment;
    }
    
    
}
