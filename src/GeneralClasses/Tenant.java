/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.util.HashMap;

/**
 *
 * @author robert
 */
public class Tenant {
    private String tenantId;
    private String lastName;
    private String firstName;
    private String dateOfBirth;
    private String nationality;
    private String phoneNumber;
    private String idType;
    private String maritalStatus;
    private int numOfFamMembers;
    //nok is Next Of Kin
    private String nokName;
    private String nokContack;
    private String idNumber;
    private HouseRentalContract houseContract;

   
    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public Tenant(String tenantId, String lastName, String firstName, String dateOfBirth, String nationality, String phoneNumber, String idType,String idNumber,String maritalStatus, int numOfFamMembers, String nokName, String nokContack) {
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
     */
    public Tenant(String lastName, String firstName, String dateOfBirth, String nationality, String phoneNumber, String idType,String idNumber ,String maritalStatus, int numOfFamMembers, String nokName, String nokContack) {
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
                this.nationality,this.idType,this.idNumber,this.numOfFamMembers,this.dateOfBirth,this.phoneNumber,this.nokName,this.nokContack);
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
         if(this.houseContract == null){
             this.houseContract = DatabaseHandler.getInstance().getCurrentContract(this.tenantId,null);
             return this.houseContract;   
         }
         else{
         return this.houseContract;
                 }
    }
    
    public String getFullName(){
         return String.format("%s %s", this.firstName,this.lastName);
    }
}
