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
       private String rentalName, rentalNumOfUnits;
       private String unitNo;
       private Double monthlyAmount;

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

        public String getRentalNumOfUnits() {
            return rentalNumOfUnits;
        }

        public void setRentalNumOfUnits(String rentalNumOfUnits) {
            this.rentalNumOfUnits = rentalNumOfUnits;
        }
       public House(String unitNo, String rentalName, String rentalNumOfUnits, Double monthlyAmount){
            this.setUnitNo(unitNo);
            this.setRentalName(rentalName);
            this.setRentalNumOfUnits(rentalNumOfUnits);
            this.setMonthlyAmount(monthlyAmount);
       }
    }
