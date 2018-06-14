package GeneralClasses;

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
       public House(String unitNo, String rentalName, int rentalNumOfUnits, Double monthlyAmount){
            this.setUnitNo(unitNo);
            this.setRentalName(rentalName);
            this.setRentalNumOfUnits(rentalNumOfUnits);
            this.setMonthlyAmount(monthlyAmount);
       }
       
       public House(String id,String unitNo, String rentalName, int rentalNumOfUnits, Double monthlyAmount){
            this(unitNo,rentalName,rentalNumOfUnits,monthlyAmount);
            this.houseId =id;
       }
       
       @Override
        public String toString(){
          return String.format("%s(%s)-%s",this.rentalName,this.rentalNumOfUnits,this.monthlyAmount);
        }
    }
