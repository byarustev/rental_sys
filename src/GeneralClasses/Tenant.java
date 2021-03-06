/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author robert
 */
public class Tenant {
    //String nokDistrict, String nokCounty,String nokSubCountyTxt,String nokParish,String nokVillage
    private String tenantId;
    private String lastName;
    private String firstName;
    private String dateOfBirth;
    private String nationality;
    private String phoneNumber;
    private String idType;
    private String maritalStatus;
    private int numOfFamMembers;
    private HouseRentalContract currentHouseContract;
    private ArrayList <Payment> myPayments;
    //nok is Next Of Kin
    private String nokName;
    private String nokContack;
    private String idNumber;
    private String addedByUserId;
    private String nokDistrict ;
    private String nokCounty;
    private String nokSubCounty ;
    private String nokParish ;
    private String nokVillage ;
    private String nokPlaceOfWork;

    public String getNokPlaceOfWork() {
        return nokPlaceOfWork;
    }

    public void setNokPlaceOfWork(String nokPlaceOfWork) {
        this.nokPlaceOfWork = nokPlaceOfWork;
    }

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public HouseRentalContract getHouseContract() {
        this.getCurrentContract();
        return currentHouseContract;
    }

    public void setHouseContract(HouseRentalContract houseContract) {
        this.currentHouseContract = houseContract;
    }

    public ArrayList<Payment> getMyPayments() {
        if(myPayments == null){
            this.myPayments = DatabaseHandler.getInstance().getTenantPayments(this.tenantId);
        }
        return myPayments;
    }

    
   
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Tenant(String tenantId, String lastName, String firstName, String dateOfBirth, String nationality, String phoneNumber, String idType,
            String idNumber,String maritalStatus, int numOfFamMembers, String nokName, String nokContack,String nokDistrictTxt, String nokCountyTxt,String nokSubCountyTxt,String nokParishTxt,String nokVillageTxt,String nokPlaceOfWork,String addedById) {
        
        this.addedByUserId = addedById;
        this.tenantId = tenantId;
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.idType = idType;
        this.idNumber = idNumber;
        this.maritalStatus = maritalStatus;
        this.numOfFamMembers = numOfFamMembers;
        this.nokName = nokName;
        this.nokContack = nokContack;
        this. nokDistrict=nokDistrictTxt;
        this.nokCounty=nokCountyTxt;
        this.nokSubCounty=nokSubCountyTxt;
        this. nokParish=nokParishTxt;
        this.nokVillage=nokVillageTxt;
         this.nokPlaceOfWork=nokPlaceOfWork;
    }  
    /**
     *
     * @param lastName
     * @param firstName
     * @param dateOfBirth
     * @param nationality
     * @param phoneNumber
     * @param idType
     * @param idNumber
     * @param maritalStatus
     * @param numOfFamMembers
     * @param nokName
     * @param nokContack
     * @param nokDistrictTxt
     * @param nokCountyTxt
     * @param nokSubCountyTxt
     * @param nokParishTxt
     * @param nokVillageTxt
     * @param nokPlaceOfWork
     */
    public Tenant(String lastName, String firstName, String dateOfBirth, String nationality, String phoneNumber, String idType,String idNumber ,String maritalStatus, int numOfFamMembers, String nokName, String nokContack,String nokDistrictTxt,
            String nokCountyTxt,String nokSubCountyTxt,String nokParishTxt,String nokVillageTxt,
            String nokPlaceOfWork) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.dateOfBirth = dateOfBirth;
        this.nationality = nationality;
        this.phoneNumber = phoneNumber;
        this.idType = idType;
        this.idNumber = idNumber;
        this.maritalStatus = maritalStatus;
        this.numOfFamMembers = numOfFamMembers;
        this.nokName = nokName;
        this.nokContack = nokContack;
        this. nokDistrict=nokDistrictTxt;
        this.nokCounty=nokCountyTxt;
        this.nokSubCounty=nokSubCountyTxt;
        this. nokParish=nokParishTxt;
        this.nokVillage=nokVillageTxt;
         this.nokPlaceOfWork=nokPlaceOfWork;
    }

    public Tenant(HashMap tenant){
        
    }
    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public int getNumOfFamMembers() {
        return numOfFamMembers;
    }

    public void setNumOfFamMembers(int numOfFamMembers) {
        this.numOfFamMembers = numOfFamMembers;
    }

    public String getNokName() {
        return nokName;
    }

    public void setNokName(String nokName) {
        this.nokName = nokName;
    }

    public String getNokContack() {
        return nokContack;
    }

    public void setNokContack(String nokContack) {
        this.nokContack = nokContack;
    }
    
    /**
     * This saves the tenant in the database if the Tenant has no id. The return id is the created record Id
     * @return tenantId
     */
    public String save(){
        if(this.tenantId ==null){
            String id = DatabaseHandler.getInstance().insertTenant(this.firstName,this.lastName,this.maritalStatus,
                this.nationality,this.idType,this.idNumber,this.numOfFamMembers,this.dateOfBirth,this.phoneNumber,this.nokName,this.nokContack,
                this.nokDistrict,this.nokCounty,this.nokSubCounty,this.nokParish, this.nokVillage,this.nokPlaceOfWork);
            this.setTenantId(id);
            return this.tenantId;
        }
        else{
             return this.tenantId;
        }
    }
    
    public String toString(){
        return String.format("%s: %s: %s: %s: %s: %s: %s: %s :%s :%s :%s", this.firstName,this.lastName,this.maritalStatus,
                this.nationality,this.idType,this.idNumber,this.numOfFamMembers,this.dateOfBirth,this.phoneNumber,this.nokName,this.nokContack);
    }
    
    public RentalContract getCurrentContract(){
         if(this.currentHouseContract == null){
             this.currentHouseContract = DatabaseHandler.getInstance().getCurrentContract(this.tenantId,null);
             return this.currentHouseContract;   
         }
         else{
            return this.currentHouseContract;
         }
    }
    
    public String getFullName(){
         return String.format("%s %s", this.firstName,this.lastName);
    }

    public boolean update() {
        return DatabaseHandler.getInstance().updateTenant(this);
    }

    /**
     * This method is used to force refreshing on a contract as something might have changed about it. 
     * @param reload
     * @return RentalContract
     */
    public RentalContract getCurrentContract(boolean reload) {
        if(reload){
           this.currentHouseContract = null ;
           
        }
       return this.getCurrentContract();
    }
       public String getNokDistrict() {
        return nokDistrict;
    }

    public void setNokDistrict(String nokDistrictTxt) {
        this.nokDistrict = nokDistrictTxt;
    }

    public String getNokCounty() {
        return nokCounty;
    }

    public void setNokCounty(String nokCountyTxt) {
        this.nokCounty = nokCountyTxt;
    }

    public String getNokSubCounty() {
        return nokSubCounty;
    }

    public void setNokSubCounty(String nokSubCountyTx) {
        this.nokSubCounty = nokSubCountyTx;
    }

    public String getNokParish() {
        return nokParish;
    }

    public void setNokParish(String nokParishTxt) {
        this.nokParish = nokParishTxt;
    }

    public String getNokVillage() {
        return nokVillage;
    }

    public void setNokVillage(String nokVillageTxt) {
        this.nokVillage = nokVillageTxt;
    }
    
    public JSONObject toJSON(){
       JSONObject tenant = new JSONObject();
       try {
            tenant.put("tenantId", this.tenantId);
            tenant.put("lastName", this.lastName);
            tenant.put("firstName", this.firstName);
            tenant.put("dateOfBirth", this.dateOfBirth);
            tenant.put("nationality", this.nationality);
            tenant.put("phoneNumber", this.phoneNumber);
            tenant.put("idType", this.idType);
            tenant.put("idNumber", this.idNumber);
            tenant.put("maritalStatus", this.maritalStatus);
            tenant.put("numOfFamMembers", this.numOfFamMembers);
            tenant.put("nokName", this.nokName);
            tenant.put("nokContack", this.nokContack);
            tenant.put("nokDistrict", this.nokDistrict);
            tenant.put("nokCounty", this.nokCounty);
            tenant.put("nokSubCounty", this.nokSubCounty);
            tenant.put("nokParish", this.nokParish);
            tenant.put("nokVillage", this.nokVillage);
            tenant.put("nokPlaceOfWork", this.nokPlaceOfWork);
            tenant.put("idType", this.idType);
            tenant.put("addedByUserId", this.addedByUserId);
            return tenant;
       } catch (JSONException ex) {
           Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
    }
}

