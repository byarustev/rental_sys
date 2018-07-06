/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import GeneralClasses.BankStatement;
import GeneralClasses.Block;
import GeneralClasses.CurrentUser;
import GeneralClasses.House;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.Payment;
import GeneralClasses.Tenant;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
public class DatabaseHandler {
    
  
    private Statement statement;
    private final String DB_URL = "jdbc:derby:database;create=true";
    private final String BLOCKS_TABLE_NAME = "BLOCKS";  
    private final String HOUSES_TABLE_NAME = "houses";  
    private final String PAYMENTS_TABLE_NAME = "payments"; 
    private final String ROOMS_TABLE_NAME = "rooms"; 
    private final String ROOM_RENTAL_CONTRACT_TABLE_NAME = "room_rental_contracts"; 
    private final String TENANTS_TABLE_NAME = "tenants"; 
    private final String USERS_TABLE_NAME = "users"; 
    private final String HOUSE_RENTAL_CONTRACT_TABLE_NAME = "house_rental_contracts"; 
    private final String BANK_STATEMENTS_TABLE_NAME ="bank_statements";
    private final int HOUSE_NOT_AVAILABLE=0;
    private final int HOUSE_AVAILABLE=1;
    //eager instatiation of the of the instance
    
    private static final  DatabaseHandler DB_HANDLER =new DatabaseHandler();
    
    private DatabaseHandler(){

        createTables();
    }
    public static DatabaseHandler getInstance(){
        return DB_HANDLER;
    }
    private void createTables(){
        try {
            DatabaseMetaData md = this.createConnection().getMetaData();
            ResultSet rs = md.getTables(null, null, BLOCKS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println("Blocks table is already created");
            }
            else{
                String sql ="CREATE TABLE "+BLOCKS_TABLE_NAME+"("
                        + "id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
"                (START WITH 1, INCREMENT BY 1) ,"
                         + "record_id varchar(40) UNIQUE NOT NULL,"
                        + "block_name varchar(30) NOT NULL,"
                        + "location varchar(80) NOT NULL,"
                        + "number_of_rentals int NOT NULL,"
                        + "added_by int DEFAULT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            //Creating the houses table
            rs = md.getTables(null, null, HOUSES_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println("Houses table is already created");
            }
            else{
                String sql ="CREATE TABLE "+HOUSES_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
"                (START WITH 1, INCREMENT BY 1) ,"+
                        "record_id varchar(40) UNIQUE NOT NULL,"+
                       "  houseNumber varchar(20) NOT NULL,"+
                        "houseName varchar(30) NOT NULL,"+
                        "monthlyPrice double NOT NULL,"+
                        "numberOfRooms int NOT NULL,"+
                        "availability int DEFAULT 1,"+
                        "blockID varchar(40)  NOT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            
                //Creating the houses table
            rs = md.getTables(null, null, HOUSE_RENTAL_CONTRACT_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(HOUSE_RENTAL_CONTRACT_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
                        "(START WITH 1, INCREMENT BY 1) ,"+
                        "record_id varchar(40) UNIQUE NOT NULL,"+
                        " startDate date NOT NULL,"+
                        "agreedMonthlyAmount double NOT NULL,"+
                        "houseID varchar(40) NOT NULL,"+
                        "tenantID varchar(40) NOT NULL ,"
                        + "isTerminated INT DEFAULT 0,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            //Creating the payments table
            rs = md.getTables(null, null, PAYMENTS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(PAYMENTS_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+PAYMENTS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
                        "(START WITH 1, INCREMENT BY 1) ,"+
                         "record_id varchar(40) UNIQUE NOT NULL,"+
                        " amountPaid double NOT NULL,"+
                        "datePaid date NOT NULL,"+
                        "receiptNumber varchar(30) DEFAULT NULL,"+
                        "modeOfPayment varchar(30) NOT NULL,"+
                         " tenantID varchar(40) NOT NULL,"+""
                        + "contractID varchar(40) NOT NULL,"+
                        "receivedBy varchar(30) DEFAULT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            //Creating the ROOMS_TABLE_NAME table
            rs = md.getTables(null, null, ROOMS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(ROOMS_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+ROOMS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
                        "(START WITH 1, INCREMENT BY 1) ,"+
                         "record_id varchar(40) UNIQUE NOT NULL,"+
                        " roomID varchar(20) NOT NULL,"+
                        "roomNumber varchar(30) NOT NULL,"+
                        "monthlyPrice double NOT NULL,"+
                        "houseID varchar(40) NOT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            
            //Creating the ROOM_RENTAL_CONTRACT_TABLE_NAME table
            rs = md.getTables(null, null, ROOM_RENTAL_CONTRACT_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(ROOM_RENTAL_CONTRACT_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+ROOM_RENTAL_CONTRACT_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
                        "(START WITH 1, INCREMENT BY 1) ,"+
                         "record_id varchar(40) UNIQUE NOT NULL,"+
                        " startDate date NOT NULL,"+
                        "agreedAmount double NOT NULL,"+
                        "roomID varchar(40) NOT NULL,"+
                        "tenantID varchar(40) NOT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                System.out.println(sql);
                
                statement = this.createConnection().createStatement();
                statement.execute(sql);
            }
            
            //Creating the ROOM_RENTAL_CONTRACT_TABLE_NAME table
            rs = md.getTables(null, null, ROOM_RENTAL_CONTRACT_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(ROOM_RENTAL_CONTRACT_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+ROOM_RENTAL_CONTRACT_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
                        "(START WITH 1, INCREMENT BY 1) ,"+
                         "record_id varchar(40) UNIQUE NOT NULL,"+
                        " startDate date NOT NULL,"+
                        "agreedAmount double NOT NULL,"+
                        "roomID varchar(40) NOT NULL,"+
                        "tenantID varchar(40) NOT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            
            //Creating the TENANTS_TABLE_NAME table
            rs = md.getTables(null, null, TENANTS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(TENANTS_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+TENANTS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n," +
                         "record_id varchar(40) UNIQUE NOT NULL,"+
                          "firstName varchar(30) NOT NULL,"
                        + "lastName varchar(30) NOT NULL,"
                        + "dateOfBirth DATE DEFAULT NULL,"
                        + "nationality varchar(30) NOT NULL,"
                        + "IdType varchar(15) NOT NULL,"
                        + "IdNumber varchar(30) NOT NULL,"
                        + "photoPath varchar(200) DEFAULT NULL,"
                        + "phoneNumber1 varchar(15) NOT NULL,"
                        + "phoneNumber2 varchar(15) DEFAULT NULL,"
                        + "maritalStatus varchar(15) NOT NULL,"
                        + "numOfFamilyMembers int NOT NULL,"
                        + "nextOfKinName varchar(50) NOT NULL,"
                        + "nextOfKinDistrict varchar(30) DEFAULT NULL,"
                        + "nextOfKinCounty varchar(30) DEFAULT NULL,"
                        + "nextOfKinSubCounty varchar(30) DEFAULT NULL,"
                        + "nextOfKinParish varchar(30) DEFAULT NULL,"
                        + "nextOfKinVillage varchar(30) DEFAULT NULL,"
                        + "nextOfKinContact varchar(30) DEFAULT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            
            //Creating the USERS_TABLE_NAME table
            rs = md.getTables(null, null, USERS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                
                System.out.println(USERS_TABLE_NAME+" table is already created");
                // this.createConnection().createStatement().execute("INSERT INTO "+USERS_TABLE_NAME+" (fistName,lastName,email,password) VALUES('Robert','Kasumba','robein@ymail.com','robert123')");
            }
            else{
                String sql ="CREATE TABLE "+USERS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n," +
                        
                        "fistName varchar(30) NOT NULL,"
                        + "lastName varchar(30) NOT NULL,"
                        + "email varchar(40) NOT NULL,"
                        + "password varchar(250) NOT NULL)";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                this.createConnection().createStatement().execute("INSERT INTO "+USERS_TABLE_NAME+" (fistName,lastName,email,password) VALUES('Yvonne','Nalinnya','yvonne@gmail.com','yvonne123')");
                System.out.println(sql);
            }
            //Creating the BANK_STATEMENTS_TABLE_NAME table
            rs = md.getTables(null, null, BANK_STATEMENTS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(BANK_STATEMENTS_TABLE_NAME+" table is already created");
                statement = this.createConnection().createStatement();
               // statement.execute("DROP TABLE "+BANK_STATEMENTS_TABLE_NAME);
            }
            else{
                String sql ="CREATE TABLE "+BANK_STATEMENTS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n," +
                          "record_id varchar(40) UNIQUE NOT NULL,"+
                        "transactionDate DATE NOT NULL,"
                        + "valueDate DATE NOT NULL,"
                        + "transactionDescription VARCHAR(100) NOT NULL,"
                        + "creditAmount DOUBLE NOT NULL,"
                        + "accountBalance DOUBLE NOT NULL,"
                        + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "tenantId varchar(40) DEFAULT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.createConnection().createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    
    
    
    public Connection createConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            Connection connection = DriverManager.getConnection(DB_URL);
            //System.out.println("SUCCEEDED ");
            return connection;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED CLASS");
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public boolean insertBlock(Block block) {
        String blockInsertSql ="INSERT INTO "+BLOCKS_TABLE_NAME+" (block_name,location,number_of_rentals,addedByUserId,record_id) VALUES(?,?,?,?,?)";
        String blockIdQuery ="SELECT record_id FROM "+BLOCKS_TABLE_NAME+" WHERE block_name = ? AND location = ? AND number_of_rentals = ?";
        String houseInsertSql ="INSERT INTO "+HOUSES_TABLE_NAME+" (houseNumber,houseName,monthlyPrice,numberOfRooms,blockID,addedByUserId,record_id) VALUES (?,?,?,?,?,?,?)";
        try {
             //" varchar(40) UNIQUE NOT NULL,"+
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(blockInsertSql);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            preparedStatement.setString(4, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(4, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(5, DatabaseHandler.generateId("BL"));
            preparedStatement.execute();
            //get the created Id
            preparedStatement = this.createConnection().prepareStatement(blockIdQuery);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                String blockId=rs.getString("record_id");
                for(House x: block.getHousesList()){
                    System.out.println(x.getUnitNo()+" : "+x.getRentalName()+" : "+x.getRentalNumOfUnits());
                    preparedStatement = this.createConnection().prepareStatement(houseInsertSql);
                    preparedStatement.setString(1, x.getUnitNo());
                    preparedStatement.setString(2, x.getRentalName());
                    preparedStatement.setDouble(3, x.getMonthlyAmount());
                    preparedStatement.setInt(4,x.getRentalNumOfUnits());
                    preparedStatement.setString(5,blockId);
                    preparedStatement.setString(6, CurrentUser.getInstance().getUserId());
                    preparedStatement.setString(7, DatabaseHandler.generateId("HS"));
                    preparedStatement.execute();
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               return true;
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public ArrayList<House> getHousesList(String databaseId,boolean availableOnly) {
        String housesQuery;
        if(availableOnly){
              housesQuery="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE blockID = ? AND avaibility =1";
        }
        else{
            housesQuery ="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE blockID = ?";
        }
       
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<House> list = new ArrayList();
            while(rs.next()){
                list.add(new House(rs.getString("record_id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility"),
                     rs.getString("addedByUserId")));
            }   
            return list;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null;
    }
    
    public ArrayList<House> getHousesList() {
        String housesQuery="SELECT * FROM "+HOUSES_TABLE_NAME;
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(housesQuery);
         
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<House> list = new ArrayList();
            while(rs.next()){
                list.add(new House(rs.getString("record_id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility"),
                     rs.getString("addedByUserId")));
            }   
            return list;
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null;
    }
    public House getHouse(String databaseId) {
        String housesQuery ="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE record_id = ?";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new House(rs.getString("record_id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility"),
                     rs.getString("addedByUserId"));
            }   
            
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null;
    }
    
    public ArrayList<Block> getBlocks(){
        String blockQuery ="SELECT * FROM "+BLOCKS_TABLE_NAME+"";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(blockQuery);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Block> blocksList = new ArrayList();
            while(rs.next()){
                blocksList.add(new Block(rs.getString("record_id"),rs.getString("block_name"),rs.getString("location"),rs.getInt("number_of_rentals"),
                     rs.getString("addedByUserId")));
            }
            blocksList.forEach((x) -> {
                x.getHousesList();
            });
            return blocksList;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;     
    }
     public Block getBlock(String blockId) {
          String blockQuery ="SELECT * FROM "+BLOCKS_TABLE_NAME+" WHERE record_id =?";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(blockQuery);
            preparedStatement.setString(1, blockId);
            ResultSet rs = preparedStatement.executeQuery();
            Block block=null;
            if(rs.next()){
                block =new Block(rs.getString("record_id"),rs.getString("block_name"),rs.getString("location"),rs.getInt("number_of_rentals"),
                     rs.getString("addedByUserId"));
            }
            rs.close();
            if(block != null){
                block.getHousesList();
                return block;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;   
    }
    public String insertTenant(String firstName, String lastName, String maritalStatus, String nationality, String idType, String idNumber, int numOfFamMembers, 
            String dateOfBirth, String phoneNumber, String nokName, String nokContack, String nokDistrict, String nokCounty,String nokSubCounty,String nokParish,String nokVillage) {
        String sql ="INSERT INTO "+TENANTS_TABLE_NAME+" (firstName,lastName,maritalStatus,dateOfBirth,nationality,"
                + "IdType,IdNumber,photoPath,phoneNumber1,phoneNumber2,"
                + "numOfFamilyMembers,nextOfKinName,nextOfKinContact,addedByUserId,nextOfKinDistrict ,nextOfKinCounty ,"
                + "nextOfKinSubCountry ,nextOfKinParish,nextOfKinVillage,record_id) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(sql);
            preparedStatement.setString(1,firstName);
            preparedStatement.setString(2,lastName);
            preparedStatement.setString(3,maritalStatus);
            preparedStatement.setString(4,dateOfBirth);
            preparedStatement.setString(5,nationality);
            preparedStatement.setString(6,idType);
            preparedStatement.setString(7,idNumber);
            preparedStatement.setString(8,"");
            preparedStatement.setString(9,phoneNumber);
            preparedStatement.setString(10,"");
            preparedStatement.setInt(11,numOfFamMembers);
            preparedStatement.setString(12,nokName);
            preparedStatement.setString(13,nokContack);
            preparedStatement.setString(14, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(15,nokDistrict);
            preparedStatement.setString(16,nokCounty);
            preparedStatement.setString(17,nokSubCounty);
            preparedStatement.setString(18,nokParish);
            preparedStatement.setString(19,nokVillage);
            preparedStatement.setString(20,DatabaseHandler.generateId("TN"));
            preparedStatement.execute();
            String idQuery = "SELECT record_id FROM "+TENANTS_TABLE_NAME+" WHERE firstName = ? AND lastName = ? AND dateOfBirth = ? AND phoneNumber1 = ?";
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, dateOfBirth);
            preparedStatement.setString(4, phoneNumber);
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("record_id");
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String insertBankStatement(String transactionDate, String valueDate, String transactionDescription, Double creditAmount, Double accountBalance, String tenantId){
        String insertSql ="INSERT INTO "+BANK_STATEMENTS_TABLE_NAME+" (transactionDate,valueDate,transactionDescription,creditAmount,accountBalance,tenantId,addedByUserId,record_id) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(insertSql);
            preparedStatement.setString(1, transactionDate);
            preparedStatement.setString(2, valueDate);
            preparedStatement.setString(3, transactionDescription);
            preparedStatement.setDouble(4, creditAmount);
            preparedStatement.setDouble(5, accountBalance);
            preparedStatement.setString(6, tenantId);
            preparedStatement.setString(7, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(8,DatabaseHandler.generateId("BS"));
            preparedStatement.execute();
            String idQuery = "SELECT record_id FROM "+BANK_STATEMENTS_TABLE_NAME+" WHERE transactionDate=? AND valueDate=?  AND creditAmount=? AND tenantId=?";
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, transactionDate);
            preparedStatement.setString(2, valueDate);
            preparedStatement.setDouble(3, creditAmount);
            preparedStatement.setString(4, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("record_id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    /**
     * This is used to store a new statements in the database
     * @param associatedTenant
     * @param associatedHouse
     * @param startDate
     * @param agreedMonthlyAmount
     * @return contractId
     */
    public String insertHouseContract(String associatedTenant, String associatedHouse, String startDate, Double agreedMonthlyAmount) {
        String insertSql ="INSERT INTO " +HOUSE_RENTAL_CONTRACT_TABLE_NAME+" (startDate,agreedMonthlyAmount,houseID,tenantID,addedByUserId,record_id) VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(insertSql);
            preparedStatement.setString(1, startDate);
            preparedStatement.setDouble(2, agreedMonthlyAmount);
            preparedStatement.setString(3, associatedHouse);
            preparedStatement.setString(4, associatedTenant);
            preparedStatement.setString(5, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(6,DatabaseHandler.generateId("CT"));
            preparedStatement.execute();     
            //set the  statements as unavailable
            changeHouseAvailability(associatedHouse, this.HOUSE_NOT_AVAILABLE);
            
            String idQuery = "SELECT record_id FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE startDate = ? AND houseID = ? AND tenantID = ? AND agreedMonthlyAmount = ?";
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, associatedHouse);
            preparedStatement.setString(3, associatedTenant);
            preparedStatement.setDouble(4, agreedMonthlyAmount);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                String houseId=rs.getString("record_id");
                this.changeHouseAvailability(houseId ,this.HOUSE_NOT_AVAILABLE);
                return houseId;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }           
         return null;
    }

    public String savePayment(String paymentDate, Double paymentAmount, String rentalContractId, String receivedBy,String tenantId,String modeOfPayment,String referenceNumber) {
        String sql ="INSERT INTO "+PAYMENTS_TABLE_NAME+" (amountPaid,datePaid,receiptNumber,modeOfPayment,tenantID,contractID,receivedBy,addedByUserId,record_id) VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(sql);
            preparedStatement.setDouble(1, paymentAmount);
            preparedStatement.setString(2, paymentDate);
            preparedStatement.setString(3, referenceNumber);
            preparedStatement.setString(4, modeOfPayment);
            preparedStatement.setString(5, tenantId);
            preparedStatement.setString(6, rentalContractId);
            preparedStatement.setString(7, receivedBy);
            preparedStatement.setString(8, CurrentUser.getInstance().getUserId());
             preparedStatement.setString(9,DatabaseHandler.generateId("PY"));
            preparedStatement.execute();
            
            String paymentQuery = "SELECT record_id FROM "+PAYMENTS_TABLE_NAME+" WHERE amountPaid =? AND datePaid =? AND receiptNumber =? AND tenantID =? AND contractID =?";
            preparedStatement = this.createConnection().prepareStatement(paymentQuery);
            preparedStatement.setDouble(1, paymentAmount);
            preparedStatement.setString(2, paymentDate);
            preparedStatement.setString(3, referenceNumber);
            preparedStatement.setString(4, tenantId);
            preparedStatement.setString(5, rentalContractId);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("record_id");    
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param tenantId
     * @param houseId
     * @return statements
 This methods find the non-terminated statements for a statements or tenant
     */
    public HouseRentalContract getCurrentContract(String tenantId, String houseId) {
        String contractQuery;
           try {
            PreparedStatement prepartedStatement=null;
            if(tenantId != null && houseId==null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE tenantID = ? AND isTerminated=0 ORDER BY id DESC";
                prepartedStatement= this.createConnection().prepareStatement(contractQuery);
                prepartedStatement.setString(1, tenantId);
            }
            else if(tenantId == null && houseId!=null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE houseID = ? AND isTerminated=0 ORDER BY dateCreated DESC";
                prepartedStatement= this.createConnection().prepareStatement(contractQuery);
                prepartedStatement.setString(1, houseId);
            }
            ResultSet rs =prepartedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("record_id") , 
                        rs.getString("houseID") , rs.getDouble("agreedMonthlyAmount"),
                     rs.getString("addedByUserId"));
                return contract;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    public ArrayList<Payment> getTenantPayments(String tenantId){
        String idQuery = "SELECT * FROM "+PAYMENTS_TABLE_NAME+" WHERE tenantID =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getString("record_id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), 
                     rs.getString("receiptNumber"),
                     rs.getString("addedByUserId")));
            }
            return tenantPayments;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null; 
    }   
    public ArrayList<Tenant> getTenants(){
         String idQuery = "SELECT * FROM "+TENANTS_TABLE_NAME;
            PreparedStatement preparedStatement;
        try {
            ArrayList<Tenant> tenants = new ArrayList();
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                tenants.add(new Tenant(rs.getString("record_id"), rs.getString("lastName") , rs.getString("firstName"), 
                        rs.getString("dateOfBirth") , rs.getString("nationality") , rs.getString("phoneNumber1") ,rs.getString("idType"),
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") , 
                        rs.getString("nextOfKinContact"),rs.getString("nextOfKinDistrict"),rs.getString("nextOfKinCounty"),rs.getString("nextOfKinSubCountry"),rs.getString("nextOfKinParish"),rs.getString("nextOfKinVillage"),
                     rs.getString("addedByUserId")));
            }
            return tenants;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;      
    }
    public Tenant getTenant(String tenantId){
        String idQuery = "SELECT * FROM "+TENANTS_TABLE_NAME+" WHERE record_id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
              return new Tenant(rs.getString("record_id"), rs.getString("lastName") , rs.getString("firstName"), 
                        rs.getString("dateOfBirth") , rs.getString("nationality") , rs.getString("phoneNumber1") ,rs.getString("idType"),
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") , 
                        rs.getString("nextOfKinContact"),rs.getString("nextOfKinDistrict"),rs.getString("nextOfKinCounty"),rs.getString("nextOfKinSubCountry"),rs.getString("nextOfKinParish"),rs.getString("nextOfKinVillage"),
                     rs.getString("addedByUserId"));
            }
            return null;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;      
    }
    public ArrayList<Payment> getContractPayments(String contractId) {
        String idQuery = "SELECT * FROM "+PAYMENTS_TABLE_NAME+" WHERE contractID =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, contractId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getString("record_id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), rs.getString("receiptNumber"),
                     rs.getString("addedByUserId")));
            }
            return tenantPayments;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null; 
    }   
    public ArrayList<Payment> getAllPayments(){
        String idQuery = "SELECT * FROM "+PAYMENTS_TABLE_NAME+" ORDER BY datePaid DESC";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getString("record_id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), rs.getString("receiptNumber"),
                     rs.getString("addedByUserId")));
            }
            return tenantPayments;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null; 
    }   
    public ArrayList<Payment> getPayments(String startDate, String endDate){
        String idQuery = "SELECT * FROM "+PAYMENTS_TABLE_NAME+" WHERE datePaid BETWEEN ? AND ?  ORDER BY datePaid DESC";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getString("record_id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), 
                     rs.getString("receiptNumber"),
                     rs.getString("addedByUserId")));
            }
            return tenantPayments;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null; 
    }
    public boolean updateTenant(Tenant tenant) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now  = LocalDateTime.now();
        String modifiedDate = now.format(dtf);
        String sql ="UPDATE "+TENANTS_TABLE_NAME+" SET firstName=?,lastName=?,maritalStatus=?,"
                + "dateOfBirth=?,nationality=?,"
                + "IdType=?,IdNumber=?,photoPath=?,phoneNumber1=?,phoneNumber2=?,"
                + "numOfFamilyMembers=?,nextOfKinName=?,nextOfKinContact=?,"
                + "dateLastModified=?,nextOfKinDistrict=? ,nextOfKinCounty=? ,"
                + "nextOfKinSubCountry=? ,nextOfKinParish=?,nextOfKinVillage=? WHERE record_id=?";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(sql);
            preparedStatement.setString(1,tenant.getFirstName());
            preparedStatement.setString(2,tenant.getLastName());
            preparedStatement.setString(3,tenant.getMaritalStatus());
            preparedStatement.setString(4,tenant.getDateOfBirth());
            preparedStatement.setString(5,tenant.getNationality());
            preparedStatement.setString(6,tenant.getIdType());
            preparedStatement.setString(7,tenant.getIdNumber());
            preparedStatement.setString(8,"");
            preparedStatement.setString(9,tenant.getPhoneNumber());
            preparedStatement.setString(10,"");
            preparedStatement.setInt(11,tenant.getNumOfFamMembers());
            preparedStatement.setString(12,tenant.getNokName());
            preparedStatement.setString(13,tenant.getNokContack());
            preparedStatement.setString(14, modifiedDate);
            preparedStatement.setString(15, tenant.getNokDistrict());
            preparedStatement.setString(16, tenant.getNokCounty());
            preparedStatement.setString(17, tenant.getNokSubCounty());
            preparedStatement.setString(18, tenant.getNokParish());
            preparedStatement.setString(19, tenant.getNokVillage());
            preparedStatement.setString(20,tenant.getTenantId());
            preparedStatement.execute();          
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public ArrayList<BankStatement> getAllStatements() {
        String query ="SELECT * FROM "+BANK_STATEMENTS_TABLE_NAME+" ORDER BY dateCreated DESC";
              //  + "(,,,,,)"
            ArrayList<BankStatement> statements = new ArrayList();
            try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(query);
          
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               statements.add(new BankStatement(rs.getString("transactionDate"),rs.getString("valueDate"),rs.getString("transactionDescription"),
                       rs.getDouble("creditAmount"), rs.getDouble("accountBalance"),rs.getString("tenantId"),rs.getString("record_id"),
                     rs.getString("addedByUserId")));
            }
            return statements;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
                   
    }
    public ArrayList<BankStatement> getStatements(String startDate, String endDate) {
        String query ="SELECT * FROM "+BANK_STATEMENTS_TABLE_NAME+" WHERE transactionDate BETWEEN ? AND ? ORDER BY dateCreated DESC";
              //  + "(,,,,,)"
            ArrayList<BankStatement> statements = new ArrayList();
            try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(query);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               statements.add(new BankStatement(rs.getString("transactionDate"),rs.getString("valueDate"),rs.getString("transactionDescription"),
                       rs.getDouble("creditAmount"), rs.getDouble("accountBalance"),rs.getString("tenantId"),rs.getString("record_id"),
                     rs.getString("addedByUserId")));
            }
            return statements;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    public boolean updatePayment(Payment payment) {
       String sql ="UPDATE "+PAYMENTS_TABLE_NAME+" SET amountPaid=?,datePaid=?,receiptNumber=?,modeOfPayment=?,tenantID=?,contractID=?,receivedBy=? WHERE record_id=?";
       try {
          
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(sql);
            preparedStatement.setDouble(1,payment.getPaymentAmount() );
            preparedStatement.setString(2, payment.getPaymentDate());
            preparedStatement.setString(3, payment.getReferenceNumber());
            preparedStatement.setString(4, payment.getModeOfPayment());
            preparedStatement.setString(5, payment.getTenantId());
            preparedStatement.setString(6, payment.getRentalContractId());
            preparedStatement.setString(7, payment.getReceivedBy());
            preparedStatement.setString(8, payment.getPaymentId());
            preparedStatement.execute();
            return true;    
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public ArrayList<BankStatement> getStatements(String tenantId) {
         String query ="SELECT * FROM "+BANK_STATEMENTS_TABLE_NAME+" WHERE tenantId =? ORDER BY dateCreated DESC";
              //  + "(,,,,,)"
            ArrayList<BankStatement> statements = new ArrayList();
            try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(query);
            preparedStatement.setString(1, tenantId);
           
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               statements.add(new BankStatement(rs.getString("transactionDate"),rs.getString("valueDate"),rs.getString("transactionDescription"),
                       rs.getDouble("creditAmount"), rs.getDouble("accountBalance"),rs.getString("tenantId"),rs.getString("record_id"),
                     rs.getString("addedByUserId")));
            }
            return statements;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    public String updateContract(boolean shouldTerminate, String contractId, 
       String houseId,String prevHouseId ,String tenantId, Double agreedAmount, String startDate) {
        Calendar calendar = Calendar.getInstance();
        
        if(shouldTerminate){
           //terminate statements 
            String updateContractSql = "UPDATE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" SET isTerminated=1, dateLastModified=?  WHERE record_id=?";
           try {
               PreparedStatement preparedStatement = this.createConnection().prepareStatement(updateContractSql);
               preparedStatement.setString(2, contractId);
               preparedStatement.setTimestamp(1,new Timestamp(calendar.getTime().getTime()));
               preparedStatement.execute();
               if(houseId==null){
                   //do not create new statements
                   return null;
                }else{
                    String id =this.insertHouseContract(tenantId, houseId, startDate, agreedAmount);
                    changeHouseAvailability(houseId, this.HOUSE_NOT_AVAILABLE);
                    if(!prevHouseId.equals(houseId)){
                         //make previous statements available
                         changeHouseAvailability(prevHouseId, this.HOUSE_AVAILABLE);
                    }
                    return id;
               }
           } catch (SQLException ex) {
               Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
       else{
           //update the current statements
           String updateContractSql = "UPDATE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" SET startDate =?,agreedMonthlyAmount=? ,houseID=?, tenantID=?,dateLastModified=? WHERE record_id=?";
           try {
               PreparedStatement preparedStatement = this.createConnection().prepareStatement(updateContractSql);
               preparedStatement.setString(1, startDate);
               preparedStatement.setDouble(2, agreedAmount);
               preparedStatement.setString(3, houseId);
               preparedStatement.setString(4, tenantId);
               preparedStatement.setTimestamp(5,new Timestamp(calendar.getTime().getTime()));
               preparedStatement.setString(6, contractId);
               preparedStatement.execute();
               //set the new statements as unavailable
               changeHouseAvailability(houseId, this.HOUSE_NOT_AVAILABLE);
               if(!prevHouseId.equals(houseId)){
                    //make previous statements available
                    changeHouseAvailability(prevHouseId, this.HOUSE_AVAILABLE);
               }
               return contractId;
           } catch (SQLException ex) {
               Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           
       }
       return null;
    }
    private void changeHouseAvailability(String houseId, int availability) throws SQLException{
       Calendar calendar = Calendar.getInstance();
       String  updateHouseSql="UPDATE "+HOUSES_TABLE_NAME+" SET avaibility =?,dateLastModified=? WHERE record_id = ?";
       PreparedStatement  preparedStatement = this.createConnection().prepareStatement(updateHouseSql);
       preparedStatement.setInt(1, availability);
        preparedStatement.setTimestamp(2,new Timestamp(calendar.getTime().getTime()));
       preparedStatement.setString(3, houseId);
       preparedStatement.execute();
    }
    public HashMap validateUser(String email, String password) {
       HashMap <String, String> userMap = new HashMap();
       String queryUserSql = "SELECT * FROM "+USERS_TABLE_NAME+" WHERE email=? AND password=?";
       PreparedStatement  preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(queryUserSql);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
                userMap.put(CurrentUser.KEY_USERNAME, rs.getString("email"));
                userMap.put(CurrentUser.KEY_USER_ID, rs.getString("id"));
                userMap.put(CurrentUser.KEY_FULL_NAME, rs.getString("fistName")+" "+rs.getString("lastName"));
                return userMap;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;
    }

    public HouseRentalContract getContract(String rentalContractId) {
        try {
            String contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE record_id=?";
            PreparedStatement preparedStatement= this.createConnection().prepareStatement(contractQuery);
            preparedStatement.setString(1, rentalContractId);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("record_id") , 
                        rs.getString("houseID") , rs.getDouble("agreedMonthlyAmount"),
                     rs.getString("addedByUserId"));
                return contract;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    /**
     * This method can be used to create timestamp and user dependent IDs
     * @param shortKey
     * @return generated Id
     */
    public static String generateId(String shortKey){
         Calendar calendar = Calendar.getInstance();
         
         Timestamp timestamp =new Timestamp(calendar.getTime().getTime());
         return String.format("%s_%s_%s", shortKey, CurrentUser.getInstance().getUserId(),timestamp.getTime());
    }
    
    public JSONArray getBlocksJSON(){
        JSONArray blocksArray =new  JSONArray();
        String blockQuery ="SELECT * FROM "+BLOCKS_TABLE_NAME+"";
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(blockQuery);
            ResultSet rs = preparedStatement.executeQuery();
            JSONObject blocksObject = new JSONObject();
            while(rs.next()){
                blocksObject.put("record_id", rs.getString("record_id"));
                blocksObject.put("block_name", rs.getString("block_name"));
                blocksObject.put("location", rs.getString("location"));
                blocksObject.put("number_of_rentals", rs.getString("number_of_rentals"));
                blocksObject.put("addedByUserId", rs.getString("addedByUserId"));
                blocksObject.put("dateCreated", rs.getString("dateCreated"));
                blocksObject.put("dateLastModified", rs.getString("dateLastModified"));
                blocksObject.put("hasSynced", rs.getString("hasSynced"));
                blocksArray.put(blocksObject); 
            }
            return blocksArray;    
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blocksArray;     
    }
    public JSONArray getHousesJSON(){
        JSONArray housesArray =new  JSONArray();
          
        String housesQuery="SELECT * FROM "+HOUSES_TABLE_NAME;
        try {
            PreparedStatement preparedStatement = this.createConnection().prepareStatement(housesQuery);
            ResultSet rs = preparedStatement.executeQuery();
            JSONObject house = new JSONObject();
            while(rs.next()){
                house.put("record_id", rs.getString("record_id"));
                house.put("houseNumber", rs.getString("houseNumber"));
                house.put("numberOfRooms", rs.getString("numberOfRooms"));
                house.put("monthlyPrice", rs.getString("monthlyPrice"));
                house.put("blockID", rs.getString("blockID"));
                house.put("availability", rs.getString("avaibility"));
                house.put("addedByUserId", rs.getString("addedByUserId"));
                house.put("dateCreated", rs.getString("dateCreated"));
                house.put("dateLastModified", rs.getString("dateLastModified"));
                house.put("hasSynced", rs.getString("hasSynced"));
                housesArray.put(house);
            }   
            return housesArray;
            
        } catch (SQLException | JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return housesArray;
        
    }
    public JSONArray getRentalContractsJSON(){
        JSONArray contractsArray =new  JSONArray();
          
        String contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+"";
        PreparedStatement preparedStatement;
       try{
        preparedStatement = this.createConnection().prepareStatement(contractQuery);
        ResultSet rs =preparedStatement.executeQuery();

        JSONObject contract = new JSONObject();
        while(rs.next()){
            contract.put("record_id", rs.getString("record_id"));
            contract.put("tenantID", rs.getString("tenantID"));
            contract.put("startDate", rs.getString("startDate"));
            contract.put("houseID", rs.getString("houseID"));
            contract.put("agreedMonthlyAmount", rs.getString("agreedMonthlyAmount"));
            contract.put("addedByUserId", rs.getString("addedByUserId"));
            contract.put("dateCreated", rs.getString("dateCreated"));
            contract.put("dateLastModified", rs.getString("dateLastModified"));
            contract.put("hasSynced", rs.getString("hasSynced"));
            contractsArray.put(contract);
        }   
        return contractsArray;
            
        } catch (SQLException | JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contractsArray;
        
    }
    public JSONArray getTenantsJSON(){
        JSONArray tenantsArray =new  JSONArray();
        String idQuery = "SELECT * FROM "+TENANTS_TABLE_NAME;
            PreparedStatement preparedStatement;
        try {
            JSONObject tenants = new JSONObject();
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                tenants.put("record_id", rs.getString("record_id"));
                tenants.put("lastName", rs.getString("lastName"));
                tenants.put("dateOfBirth", rs.getString("dateOfBirth"));
                tenants.put("firstName", rs.getString("firstName"));
                tenants.put("nationality", rs.getString("nationality"));
                tenants.put("phoneNumber1", rs.getString("phoneNumber1"));
                tenants.put("idType", rs.getString("idType"));
                tenants.put("IdNumber", rs.getString("IdNumber"));
                tenants.put("maritalStatus", rs.getString("maritalStatus"));
                tenants.put("numOfFamilyMembers", rs.getString("numOfFamilyMembers"));
                tenants.put("nextOfKinName", rs.getString("nextOfKinName"));
                tenants.put("nextOfKinContact", rs.getString("nextOfKinContact"));
                tenants.put("nextOfKinDistrict", rs.getString("nextOfKinDistrict"));
                tenants.put("nextOfKinCounty", rs.getString("nextOfKinCounty"));
                tenants.put("nextOfKinSubCounty", rs.getString("nextOfKinSubCountry"));
                tenants.put("nextOfKinParish", rs.getString("nextOfKinParish"));
                tenants.put("nextOfKinVillage", rs.getString("nextOfKinVillage"));
                tenants.put("addedByUserId", rs.getString("addedByUserId"));
                tenants.put("dateCreated", rs.getString("dateCreated"));
                tenants.put("dateLastModified", rs.getString("dateLastModified"));
                tenants.put("hasSynced", rs.getString("hasSynced"));
                tenantsArray.put(tenants);     
            }
            return tenantsArray;
        }catch(SQLException | JSONException e){
            e.printStackTrace();
        }
        return tenantsArray;
        
    }
    
     public JSONArray getPaymentsJSON(){
        JSONArray paymentsArray =new  JSONArray();
          String idQuery = "SELECT * FROM "+PAYMENTS_TABLE_NAME;
         PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
            JSONObject payment = new JSONObject();
            while(rs.next()){
                payment.put("record_id", rs.getString("record_id"));
                payment.put("datePaid", rs.getString("datePaid"));
                payment.put("amountPaid", rs.getString("amountPaid"));
                payment.put("contractID", rs.getString("contractID"));
                payment.put("receivedBy", rs.getString("receivedBy"));
                payment.put("tenantID", rs.getString("tenantID"));
                payment.put("modeOfPayment", rs.getString("modeOfPayment"));
                payment.put("receiptNumber", rs.getString("receiptNumber"));
                payment.put("addedByUserId", rs.getString("addedByUserId"));
                payment.put("dateCreated", rs.getString("dateCreated"));
                payment.put("dateLastModified", rs.getString("dateLastModified"));
                payment.put("hasSynced", rs.getString("hasSynced"));
                paymentsArray.put(payment);
            }   
            return paymentsArray;
            
        } catch (SQLException | JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return paymentsArray;
        
    }
     
     public JSONArray getStatementsJSON(){
        JSONArray statementsArray =new  JSONArray();
          String idQuery = "SELECT * FROM "+BANK_STATEMENTS_TABLE_NAME;
         PreparedStatement preparedStatement;
        try {
            preparedStatement = this.createConnection().prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
            JSONObject statements = new JSONObject();
            while(rs.next()){
                statements.put("record_id", rs.getString("record_id"));
                statements.put("transactionDate", rs.getString("transactionDate"));
                statements.put("valueDate", rs.getString("valueDate"));
                statements.put("transactionDescription", rs.getString("transactionDescription"));
                statements.put("creditAmount", rs.getString("creditAmount"));
                statements.put("accountBalance", rs.getString("accountBalance"));
                statements.put("tenantId", rs.getString("tenantId"));
                statements.put("addedByUserId", rs.getString("addedByUserId"));
                statements.put("dateCreated", rs.getString("dateCreated"));
                statements.put("dateLastModified", rs.getString("dateLastModified"));
                statements.put("hasSynced", rs.getString("hasSynced"));
                statementsArray.put(statements);
            }   
            return statementsArray;
            
        } catch (SQLException | JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return statementsArray;
        
    }
    
  }
