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
public class Block {
    private String name;
    private String location;
    private int numberOfRentals;
    private int numberOfAvailableRentals=0;

     private String addedByUserId;

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }
    public int getNumberOfAvailableRentals() {
        if(numberOfAvailableRentals==0){
            setNumberOfAvailableRentals();
        }
        return numberOfAvailableRentals;
    }

    public void setNumberOfAvailableRentals(int numberOfAvailableRentals) {
        this.numberOfAvailableRentals = this.getHousesList(true).size();
    }
    public void setNumberOfAvailableRentals() {
        this.numberOfAvailableRentals = this.getHousesList(true).size();
    }
    private ArrayList<House> housesList;
    private String databaseId;

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
        try{
            housesList.forEach(x->x.setBlockId(databaseId));
            setNumberOfAvailableRentals();
        }catch(NullPointerException e){
            
        }
    }
    
    public Block(String name, String location, int number_of_retals, ArrayList<House> housesList){
        this(name,location,number_of_retals);
        this.setHousesList(housesList);
    }

    public Block(String name, String location, int number_of_retals){
        this.setName(name);
        this.setLocation(location);
        this.setNumberOfRentals(number_of_retals); 
    }
    public Block(String id,String name, String location, int number_of_retals ,String addedById) {
        this(name,location,number_of_retals);
        this.addedByUserId = addedById;
        this.setDatabaseId(id); 
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumberOfRentals() {
        return getHousesList().size();
    }

    public void setNumberOfRentals(int number_of_retals) {
        this.numberOfRentals = number_of_retals;
    }

    public ArrayList<House> getHousesList() {
        if(housesList != null){
            return housesList;
        }
        else{
            ArrayList <House> list = DatabaseHandler.getInstance().getHousesList(this.databaseId,false);
            setHousesList(list);
            return list;
        }
    }
    
    public ArrayList<House> getHousesList(boolean availableOnly) {
            ArrayList <House> list = DatabaseHandler.getInstance().getHousesList(this.databaseId,availableOnly);
            return list;
    }

    public void setHousesList(ArrayList<House> housesList) {
        this.housesList = housesList;
        try{
        housesList.forEach(x->x.setBlockId(databaseId));
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }
    
    /**
     *
     * @return True/False to indicate success or failure
     */
    public Boolean save(){
        if(this.databaseId ==null){
             return DatabaseHandler.getInstance().insertBlock(this);
        }
        else{
            try {
                return DatabaseHandler.getInstance().updateBlock(this);
            } catch (Exception ex) {
                Logger.getLogger(Block.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
        return false;
       
    }
    
    @Override
    public String toString(){
     return String.format("%s - %s", this.name,this.location);
    }
    
    public JSONObject toJSON(){
        JSONObject payment = new JSONObject();
       try {
            payment.put("name", this.name);
            payment.put("location", this.location);
            payment.put("numberOfRentals", this.numberOfRentals);
            payment.put("numberOfAvailableRentals", this.getNumberOfAvailableRentals());
            payment.put("addedByUserId", this.addedByUserId);  
            return payment;
       } catch (JSONException ex) {
           Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
    }
    
    
}
