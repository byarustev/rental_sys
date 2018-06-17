package GeneralClasses;

import database.DatabaseHandler;
import java.util.ArrayList;

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
    public Block(String id,String name, String location, int number_of_retals){
        this(name,location,number_of_retals);
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
        return numberOfRentals;
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
        housesList.forEach(x->x.setBlockId(databaseId));
    }
    
    public void save(){
        DatabaseHandler.getInstance().insertBlock(this);
    }
    
    @Override
    public String toString(){
     return String.format("%s - %s", this.name,this.location);
    }
    
    
}
