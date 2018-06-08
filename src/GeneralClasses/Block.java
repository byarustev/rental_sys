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
    private ArrayList<House> housesList;
    private String databaseId;

    public String getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(String databaseId) {
        this.databaseId = databaseId;
    }
    
    public Block(String name, String location, int number_of_retals, ArrayList<House> housesList){
        this.setName(name);
        this.setLocation(location);
        this.setNumberOfRentals(number_of_retals);
        this.setHousesList(housesList);
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
        return housesList;
    }

    public void setHousesList(ArrayList<House> housesList) {
        this.housesList = housesList;
    }
    
    public void save(){
        DatabaseHandler.getInstance().insertBlock(this);
    }
}
