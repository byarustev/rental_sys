package GeneralClasses;

import database.DatabaseHandler;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author robert
 */
public class House{
    private String rentalName;
    private int rentalNumOfUnits;
    private String unitNo;
    private Double monthlyAmount;
    private String houseId;
    private String blockId;
    private Block block;
    private int availability=1;
    private String addedByUserId;
    private String umemeMeterNumber;

    public String getUmemeMeterNumber() {
        return umemeMeterNumber;
    }

    public void setUmemeMeterNumber(String umemeMeterNumber) {
        this.umemeMeterNumber = umemeMeterNumber;
    }

    public String getWaterMeterNumber() {
        return waterMeterNumber;
    }

    public void setWaterMeterNumber(String waterMeterNumber) {
        this.waterMeterNumber = waterMeterNumber;
    }
    private String waterMeterNumber;

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }

    public int getAvailability() {
        return availability;
    }
    public boolean isAvailable(){
        return availability==1;
    }
    public void setAvailability(int availability) {
        this.availability = availability;
        
    }

    public String getBlockId() {
        return blockId;
    }

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public Double getMonthlyAmount() {
        return monthlyAmount;
    }

    public void setMonthlyAmount(Double monthlyAmount) {
        this.monthlyAmount = monthlyAmount;
    }

        public String getUnitNo() {
            return unitNo;
        }

        public void setUnitNo(String unitNo) {
            this.unitNo = unitNo;
        }
        public String getRentalName() {
            return rentalName;
        }

        public void setRentalName(String rentalName) {
            this.rentalName = rentalName;
        }

        public int getRentalNumOfUnits() {
            return rentalNumOfUnits;
        }

        public void setRentalNumOfUnits(int rentalNumOfUnits) {
            this.rentalNumOfUnits = rentalNumOfUnits;
        }
       public House(String unitNo, String rentalName, int rentalNumOfUnits, Double monthlyAmount, String blockId,String umemeMeterNumber, String waterMeterNumber){
            this.setUnitNo(unitNo);
            this.setRentalName(rentalName);
            this.setRentalNumOfUnits(rentalNumOfUnits);
            this.setMonthlyAmount(monthlyAmount);
            this.setBlockId(blockId);
            this.setWaterMeterNumber(waterMeterNumber);
            this.setUmemeMeterNumber(umemeMeterNumber);
       }
       
       public House(String id,String unitNo, String rentalName, int rentalNumOfUnits, Double monthlyAmount, String blockId, int availability,String umemeMeterNumber, String waterMeterNumber,String addedById) {
            this(unitNo,rentalName,rentalNumOfUnits,monthlyAmount,blockId,umemeMeterNumber,waterMeterNumber);
            this.addedByUserId = addedById;
            this.houseId =id;
            this.setAvailability(availability);
       }
       
       @Override
        public String toString(){
          return String.format("%s(%s)-%s",this.rentalName,this.rentalNumOfUnits,this.monthlyAmount);
        }

        public void setBlockId(String databaseId) {
            this.blockId=databaseId;
            
        }
        
        public Block getBlock(){
            if(this.block==null){
                this.block = DatabaseHandler.getInstance().getBlock(this.blockId);
                return this.block;
            }
            else{
                return this.block;
            }
        }
        
        public HouseRentalContract getAssociatedContract(){
            return DatabaseHandler.getInstance().getCurrentContract(null, houseId);
        }
        
        public boolean isOccupied(){
            return DatabaseHandler.getInstance().getCurrentContract(null, houseId) != null;
        }
        
        public JSONObject toJSON(){
            JSONObject house = new JSONObject();
            try {
                 house.put("rentalName", this.rentalName);
                 house.put("rentalNumOfUnits", this.rentalNumOfUnits);
                 house.put("unitNo", this.unitNo);
                 house.put("monthlyAmount", this.monthlyAmount);
                 house.put("houseId", this.houseId);
                 house.put("blockId", this.blockId);
                 house.put("availability", this.availability);
                 house.put("monthlyAmount", this.monthlyAmount);
                 house.put("houseId", this.houseId);
                 house.put("addedByUserId", this.addedByUserId);
                 house.put("waterMeterNumber", this.waterMeterNumber);
                 house.put("umemeMeterNumber", this.umemeMeterNumber);
                 return house;
            } catch (JSONException ex) {
                Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
            }
        return null;
            
        }
        
        public boolean save(){
            if(this.houseId != null){
                //just make updates
                 return DatabaseHandler.getInstance().updateHouse(this);
            }else{
                try {
                    DatabaseHandler.getInstance().insertHouse(this);
                } catch (Exception ex) {
                    Logger.getLogger(House.class.getName()).log(Level.SEVERE, null, ex);
                    return false;
                }
            }
            return true;
        }
    }


