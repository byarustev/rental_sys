package GeneralClasses;

import database.DatabaseHandler;

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
       public House(String unitNo, String rentalName, int rentalNumOfUnits, Double monthlyAmount, String blockId){
            this.setUnitNo(unitNo);
            this.setRentalName(rentalName);
            this.setRentalNumOfUnits(rentalNumOfUnits);
            this.setMonthlyAmount(monthlyAmount);
            setBlockId(blockId);
       }
       
       public House(String id,String unitNo, String rentalName, int rentalNumOfUnits, Double monthlyAmount, String blockId, int availability,String addedById) {
            this(unitNo,rentalName,rentalNumOfUnits,monthlyAmount,blockId);
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
    }
