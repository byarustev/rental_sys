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
import org.mindrot.jbcrypt.BCrypt;

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
    private Connection connection;
    //eager instatiation of the of the instance
    
    private static final  DatabaseHandler DB_HANDLER =new DatabaseHandler();
    
    private DatabaseHandler(){
         createConnection();
        createTables();
       
    }
    public static DatabaseHandler getInstance(){
        return DB_HANDLER;
    }
    private void createTables(){
        try {
            DatabaseMetaData md = this.connection.getMetaData();
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
                statement = this.connection.createStatement();
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
                        "umemeMeterNumber varchar(50),"+
                        "waterMeterNumber varchar(50),"+
                        "availability int DEFAULT 1,"+
                        "blockID varchar(40)  NOT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = this.connection.createStatement();
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
                        "tenantID varchar(40) NOT NULL ,"+
                         "advanceAmountBeforeStart double DEFAULT 0,"+
                         "arrearsBeforeStart double DEFAULT 0,"
                        + "isTerminated INT DEFAULT 0,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";

                statement = this.connection.createStatement();
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
                statement = this.connection.createStatement();
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
                statement = this.connection.createStatement();
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
                
                statement = this.connection.createStatement();
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
                statement = this.connection.createStatement();
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
                        + "numOfFamilyMembers int DEFAULT 0,"
                        + "nextOfKinName varchar(50) NOT NULL,"
                        + "nextOfKinDistrict varchar(30),"
                        + "nextOfKinCounty varchar(30),"
                        + "nextOfKinSubCounty varchar(30),"
                        + "nextOfKinParish varchar(30) ,"
                        + "nextOfKinVillage varchar(30) ,"
                        + "nextOfKinContact varchar(30)  NOT NULL,"
                        + "nokPlaceOfWork varchar(50)  ,"
                        + "addedByUserId INT DEFAULT NULL,"
                         + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                //
                statement = this.connection.createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            
            //Creating the USERS_TABLE_NAME table
            rs = md.getTables(null, null, USERS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){   
                System.out.println(USERS_TABLE_NAME+" table is already created");
               
            }
            else{
                String sql ="CREATE TABLE "+USERS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY  \n," +
                        
                        "fistName varchar(30) NOT NULL,"
                        + "lastName varchar(30) NOT NULL,"
                        + "email varchar(40) NOT NULL,"
                        + "password varchar(250) NOT NULL)";
                statement = this.connection.createStatement();
                statement.execute(sql);
                //this.,','','yvonne@gmail.com','$2y$12$pBFV4Kx4pguBBiD7/gop/uVKVFcIXxcW1a4geYdtI7BqazJho6ktq')");
                 PreparedStatement preparedStatement = this.connection.prepareStatement("INSERT INTO "+USERS_TABLE_NAME+" (id,fistName,lastName,email,password) VALUES(?,?,?,?,?)");
                 preparedStatement.setInt(1, 1);
                 preparedStatement.setString(2, "Defualt");
                 preparedStatement.setString(3, "Admin");
                 preparedStatement.setString(4, "admin@daaki.com");
                 preparedStatement.setString(5, "$2y$12$0g0idkUQa5lwJEQdLHLit.g.cV0EyS6eK8w9JmzEQAII.faBwJtaq");
                 preparedStatement.execute(); 
                 System.out.println(sql);
            }
            //Creating the BANK_STATEMENTS_TABLE_NAME table
            rs = md.getTables(null, null, BANK_STATEMENTS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(BANK_STATEMENTS_TABLE_NAME+" table is already created");
                statement = this.connection.createStatement();
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
                statement = this.connection.createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
    }
    
    
    
    
    public void createConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            this. connection = DriverManager.getConnection(DB_URL);
            System.out.println("SUCCEEDED ");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED CLASS");
        } catch (InstantiationException | IllegalAccessException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
      
    }

    public boolean insertBlock(Block block) {
        String blockInsertSql ="INSERT INTO "+BLOCKS_TABLE_NAME+" (block_name,location,number_of_rentals,addedByUserId,record_id) VALUES(?,?,?,?,?)";
        String blockIdQuery ="SELECT record_id FROM "+BLOCKS_TABLE_NAME+" WHERE block_name = ? AND location = ? AND number_of_rentals = ?";
        
        try {
             //" varchar(40) UNIQUE NOT NULL,"+
            PreparedStatement preparedStatement = this.connection.prepareStatement(blockInsertSql);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            preparedStatement.setString(4, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(4, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(5, DatabaseHandler.generateId("BL"));
            preparedStatement.execute();
            //get the created Id
            preparedStatement = this.connection.prepareStatement(blockIdQuery);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                String blockId=rs.getString("record_id");
                for(House x: block.getHousesList()){
                    x.setBlockId(blockId);
                    try {
                        this.insertHouse(x);
                    } catch (Exception ex) {
                        Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
               return true;
            }
            
        } catch (SQLException ex) {
           
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
     public Boolean updateBlock(Block block) throws Exception {
        Calendar calendar =Calendar.getInstance();
        String blockInsertSql ="UPDATE "+BLOCKS_TABLE_NAME+" SET block_name=?,location=?,dateLastModified=?,hasSynced=0 WHERE record_id=?";
             //" varchar(40) UNIQUE NOT NULL,"+
        PreparedStatement preparedStatement;
        try {
            String blockId = block.getDatabaseId();
            if(blockId ==null){
                throw new Exception("The block has no Id");
            }
            preparedStatement = this.connection.prepareStatement(blockInsertSql);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setTimestamp(3,new Timestamp(calendar.getTime().getTime()));
            preparedStatement.setString(4, blockId);
            preparedStatement.execute();
            return true;
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
        
    }

    public void insertHouse(House x) throws Exception{
        String houseInsertSql ="INSERT INTO "+HOUSES_TABLE_NAME+" (houseNumber,houseName,monthlyPrice,numberOfRooms,blockID,addedByUserId,record_id,umemeMeterNumber,waterMeterNumber) VALUES (?,?,?,?,?,?,?,?,?)";
        
       
        try {
            String blockId = x.getBlockId();
            if (blockId == null){
                throw new Exception("No Block Id");
            }
            PreparedStatement preparedStatement = this.connection.prepareStatement(houseInsertSql);
            preparedStatement.setString(1, x.getUnitNo());
            preparedStatement.setString(2, x.getRentalName());
            preparedStatement.setDouble(3, x.getMonthlyAmount());
            preparedStatement.setInt(4,x.getRentalNumOfUnits());
            preparedStatement.setString(5,blockId);
            preparedStatement.setString(6, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(7, DatabaseHandler.generateId("HS"));
            preparedStatement.setString(8, x.getUmemeMeterNumber());
            preparedStatement.setString(9, x.getWaterMeterNumber());
            preparedStatement.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }
    public ArrayList<House> getHousesList(String databaseId,boolean availableOnly) {
        String housesQuery;
        if(availableOnly){
              housesQuery="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE blockID = ? AND availability =1";
        }
        else{
            housesQuery ="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE blockID = ?";
        }
       
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<House> list = new ArrayList();
            while(rs.next()){
                list.add(new House(rs.getString("record_id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("availability"),
                     rs.getString("umemeMeterNumber"), rs.getString("waterMeterNumber"), rs.getString("addedByUserId")));
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(housesQuery);
         
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<House> list = new ArrayList();
            while(rs.next()){
                list.add(new House(rs.getString("record_id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("availability"),
                      rs.getString("umemeMeterNumber"), rs.getString("waterMeterNumber"),rs.getString("addedByUserId")));
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new House(rs.getString("record_id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("availability"),
                      rs.getString("umemeMeterNumber"), rs.getString("waterMeterNumber"),rs.getString("addedByUserId"));
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(blockQuery);
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(blockQuery);
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
            String dateOfBirth, String phoneNumber, String nokName, String nokContack, String nokDistrict, String nokCounty,String nokSubCounty,String nokParish,String nokVillage, String nokPlaceOfWork) {
        String sql ="INSERT INTO "+TENANTS_TABLE_NAME+" (firstName,lastName,maritalStatus,dateOfBirth,nationality,"
                + "IdType,IdNumber,photoPath,phoneNumber1,phoneNumber2,"
                + "numOfFamilyMembers,nextOfKinName,nextOfKinContact,addedByUserId,nextOfKinDistrict ,nextOfKinCounty ,"
                + "nextOfKinSubCounty ,nextOfKinParish,nextOfKinVillage,record_id, nokPlaceOfWork) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
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
             preparedStatement.setString(21,nokPlaceOfWork);
            preparedStatement.execute();
            String idQuery = "SELECT record_id FROM "+TENANTS_TABLE_NAME+" WHERE firstName = ? AND lastName = ? AND dateOfBirth = ? AND phoneNumber1 = ?";
            preparedStatement = this.connection.prepareStatement(idQuery);
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(insertSql);
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
            preparedStatement = this.connection.prepareStatement(idQuery);
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
    public String insertHouseContract(String associatedTenant, String associatedHouse, String startDate, Double agreedMonthlyAmount, Double advanceAmountBeforeStart,Double arrearsBeforeStart) {
        String insertSql ="INSERT INTO " +HOUSE_RENTAL_CONTRACT_TABLE_NAME+" (startDate,agreedMonthlyAmount,houseID,tenantID,addedByUserId,record_id,advanceAmountBeforeStart,arrearsBeforeStart) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(insertSql);
            preparedStatement.setString(1, startDate);
            preparedStatement.setDouble(2, agreedMonthlyAmount);
            preparedStatement.setString(3, associatedHouse);
            preparedStatement.setString(4, associatedTenant);
            preparedStatement.setString(5, CurrentUser.getInstance().getUserId());
            preparedStatement.setString(6,DatabaseHandler.generateId("CT"));
            preparedStatement.setDouble(7, advanceAmountBeforeStart);
            preparedStatement.setDouble(8, arrearsBeforeStart);
            preparedStatement.execute();     
            //set the  statements as unavailable
            changeHouseAvailability(associatedHouse, this.HOUSE_NOT_AVAILABLE);
            
            String idQuery = "SELECT record_id FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE startDate = ? AND houseID = ? AND tenantID = ? AND agreedMonthlyAmount = ?";
            preparedStatement = this.connection.prepareStatement(idQuery);
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
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
            preparedStatement = this.connection.prepareStatement(paymentQuery);
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
                prepartedStatement= this.connection.prepareStatement(contractQuery);
                prepartedStatement.setString(1, tenantId);
            }
            else if(tenantId == null && houseId!=null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE houseID = ? AND isTerminated=0 ORDER BY dateCreated DESC";
                prepartedStatement= this.connection.prepareStatement(contractQuery);
                prepartedStatement.setString(1, houseId);
            }
            ResultSet rs =prepartedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("record_id") , 
                        rs.getString("houseID") , rs.getDouble("agreedMonthlyAmount"),
                        rs.getDouble("advanceAmountBeforeStart"),rs.getDouble("arrearsBeforeStart"),
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
            preparedStatement = this.connection.prepareStatement(idQuery);
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
            preparedStatement = this.connection.prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                tenants.add(new Tenant(rs.getString("record_id"), rs.getString("lastName") , rs.getString("firstName"), 
                        rs.getString("dateOfBirth") , rs.getString("nationality") , rs.getString("phoneNumber1") ,rs.getString("idType"),
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") , 
                        rs.getString("nextOfKinContact"),rs.getString("nextOfKinDistrict"),rs.getString("nextOfKinCounty"),rs.getString("nextOfKinSubCounty"),rs.getString("nextOfKinParish"),rs.getString("nextOfKinVillage"),
                        rs.getString("nokPlaceOfWork"),
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
            preparedStatement = this.connection.prepareStatement(idQuery);
            preparedStatement.setString(1, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
              return new Tenant(rs.getString("record_id"), rs.getString("lastName") , rs.getString("firstName"), 
                        rs.getString("dateOfBirth") , rs.getString("nationality") , rs.getString("phoneNumber1") ,rs.getString("idType"),
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") , 
                        rs.getString("nextOfKinContact"),rs.getString("nextOfKinDistrict"),rs.getString("nextOfKinCounty"),rs.getString("nextOfKinSubCounty"),rs.getString("nextOfKinParish"),rs.getString("nextOfKinVillage"),
                      rs.getString("nokPlaceOfWork"),
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
            preparedStatement = this.connection.prepareStatement(idQuery);
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
            preparedStatement = this.connection.prepareStatement(idQuery);
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
            preparedStatement = this.connection.prepareStatement(idQuery);
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
                + "nextOfKinSubCounty=? ,nextOfKinParish=?,nextOfKinVillage=?,nokPlaceOfWork=?, hasSynced=0 WHERE record_id=?";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
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
            preparedStatement.setString(20,tenant.getNokPlaceOfWork());
             preparedStatement.setString(21,tenant.getTenantId());
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
          
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
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
       String sql ="UPDATE "+PAYMENTS_TABLE_NAME+" SET amountPaid=?,datePaid=?,receiptNumber=?,modeOfPayment=?,tenantID=?,contractID=?,receivedBy=?, hasSynced=0 WHERE record_id=?";
       try {
          
            PreparedStatement preparedStatement = this.connection.prepareStatement(sql);
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
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
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
       String houseId,String prevHouseId ,String tenantId, Double agreedAmount, String startDate, Double advanceAmountBeforeStart,Double arrearsBeforeStart) {
        Calendar calendar = Calendar.getInstance();
        
        if(shouldTerminate){
           //terminate statements 
            String updateContractSql = "UPDATE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" SET isTerminated=1, dateLastModified=?, hasSynced=0  WHERE record_id=?";
           try {
               PreparedStatement preparedStatement = this.connection.prepareStatement(updateContractSql);
               preparedStatement.setString(2, contractId);
               preparedStatement.setTimestamp(1,new Timestamp(calendar.getTime().getTime()));
               preparedStatement.execute();
               this.changeHouseAvailability(prevHouseId, this.HOUSE_AVAILABLE);
               if(houseId==null){
                   //do not create new contract
                   return null;
                }else{
                    String id =this.insertHouseContract(tenantId, houseId, startDate, agreedAmount,advanceAmountBeforeStart,arrearsBeforeStart);
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
           String updateContractSql = "UPDATE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" SET startDate =?,agreedMonthlyAmount=? ,houseID=?, tenantID=?,dateLastModified=?,advanceAmountBeforeStart=?,arrearsBeforeStart=?, hasSynced=0 WHERE record_id=?";
           try {
               PreparedStatement preparedStatement = this.connection.prepareStatement(updateContractSql);
               preparedStatement.setString(1, startDate);
               preparedStatement.setDouble(2, agreedAmount);
               preparedStatement.setString(3, houseId);
               preparedStatement.setString(4, tenantId);
               preparedStatement.setTimestamp(5,new Timestamp(calendar.getTime().getTime()));
               preparedStatement.setDouble(6, advanceAmountBeforeStart);
               preparedStatement.setDouble(7, arrearsBeforeStart);
               preparedStatement.setString(8, contractId);
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
    private void changeHouseAvailability(String houseId, int availability){
       Calendar calendar = Calendar.getInstance();
       String  updateHouseSql="UPDATE "+HOUSES_TABLE_NAME+" SET availability =?,dateLastModified=?,hasSynced=0 WHERE record_id = ?";
       PreparedStatement  preparedStatement;
        try {
            preparedStatement = this.connection.prepareStatement(updateHouseSql);
            preparedStatement.setInt(1, availability);
            preparedStatement.setTimestamp(2,new Timestamp(calendar.getTime().getTime()));
            preparedStatement.setString(3, houseId);
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       
    }
    public boolean updateHouse(House house){
       Calendar calendar = Calendar.getInstance();
       String  updateHouseSql="UPDATE "+HOUSES_TABLE_NAME+
               " SET houseName =?,monthlyPrice=?,numberOfRooms=?,blockID=?,dateLastModified=?,hasSynced=0 ,umemeMeterNumber=?,waterMeterNumber=? WHERE record_id = ?";
       PreparedStatement  preparedStatement;
        try {
            preparedStatement = this.connection.prepareStatement(updateHouseSql);
            preparedStatement.setString(1, house.getRentalName());
            preparedStatement.setDouble(2, house.getMonthlyAmount());
            preparedStatement.setInt(3, house.getRentalNumOfUnits());
            preparedStatement.setString(4, house.getBlockId());
            preparedStatement.setTimestamp(5,new Timestamp(calendar.getTime().getTime()));
            preparedStatement.setString(6, house.getUmemeMeterNumber());
            preparedStatement.setString(7, house.getWaterMeterNumber());
            preparedStatement.setString(8, house.getHouseId());
            preparedStatement.execute();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    public HashMap validateUser(String email, String password) {
       HashMap <String, String> userMap = new HashMap();
       String queryUserSql = "SELECT * FROM "+USERS_TABLE_NAME+" WHERE email=?";
       PreparedStatement  preparedStatement;
        try {
            preparedStatement = this.connection.prepareStatement(queryUserSql);
            preparedStatement.setString(1, email);
            ResultSet rs=preparedStatement.executeQuery();
            if(rs.next()){
               // System.out.println("COMPARING "+password+" : "+rs.getString("password").replaceFirst("2y", "2a"));
                if(BCrypt.checkpw(password, rs.getString("password").replaceFirst("2y", "2a"))){
                     userMap.put(CurrentUser.KEY_USERNAME, rs.getString("email"));
                    userMap.put(CurrentUser.KEY_USER_ID, rs.getString("id"));
                    userMap.put(CurrentUser.KEY_FULL_NAME, rs.getString("fistName")+" "+rs.getString("lastName"));
                    return userMap;
                }
                else{
                    System.out.println("FAILED SO MUCH");
                }
               
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;
    }

    public HouseRentalContract getContract(String rentalContractId) {
        try {
            String contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE record_id=?";
            PreparedStatement preparedStatement= this.connection.prepareStatement(contractQuery);
            preparedStatement.setString(1, rentalContractId);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("record_id") , 
                        rs.getString("houseID") , rs.getDouble("agreedMonthlyAmount"),
                         rs.getDouble("advanceAmountBeforeStart"),rs.getDouble("arrearsBeforeStart"),
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
        String blockQuery ="SELECT * FROM "+BLOCKS_TABLE_NAME+" WHERE hasSynced=0";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(blockQuery);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()){
                JSONObject blocksObject = new JSONObject();
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
            //System.out.println(blocksArray+"Yess");
            return blocksArray;    
        } catch (SQLException ex) {
            
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
             ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return blocksArray;     
    }
    public JSONArray getHousesJSON(){
        JSONArray housesArray =new  JSONArray();
          
        String housesQuery="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE hasSynced=0";
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(housesQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                JSONObject house = new JSONObject();
                house.put("record_id", rs.getString("record_id"));
                house.put("houseNumber", rs.getString("houseNumber"));
                house.put("houseName", rs.getString("houseName"));
                house.put("numberOfRooms", rs.getString("numberOfRooms"));
                house.put("waterMeterNumber", rs.getString("waterMeterNumber"));
                house.put("umemeMeterNumber", rs.getString("umemeMeterNumber"));
                house.put("monthlyPrice", rs.getString("monthlyPrice"));
                house.put("blockID", rs.getString("blockID"));
                house.put("availability", rs.getString("availability"));
                house.put("addedByUserId", rs.getString("addedByUserId"));
                house.put("dateCreated", rs.getString("dateCreated"));
                house.put("dateLastModified", rs.getString("dateLastModified"));
                house.put("hasSynced", rs.getString("hasSynced"));
                housesArray.put(house);
               
            }   
            System.out.println(housesArray.toString());
            return housesArray;
            
        } catch (SQLException | JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return housesArray;
        
    }
    public JSONArray getRentalContractsJSON(){
        JSONArray contractsArray =new  JSONArray();
          
        String contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE hasSynced=0";;
        PreparedStatement preparedStatement;
       try{
        preparedStatement = this.connection.prepareStatement(contractQuery);
        ResultSet rs =preparedStatement.executeQuery();

        
        while(rs.next()){
            JSONObject contract = new JSONObject();
            contract.put("record_id", rs.getString("record_id"));
            contract.put("tenantID", rs.getString("tenantID"));
            contract.put("startDate", rs.getString("startDate"));
            contract.put("houseID", rs.getString("houseID"));
            contract.put("agreedMonthlyAmount", rs.getString("agreedMonthlyAmount"));
            contract.put("advanceAmountBeforeStart", rs.getDouble("advanceAmountBeforeStart"));
            contract.put("arrearsBeforeStart", rs.getDouble("arrearsBeforeStart"));
            contract.put("addedByUserId", rs.getString("addedByUserId"));
            contract.put("isTerminated", rs.getString("isTerminated"));
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
        String idQuery = "SELECT * FROM "+TENANTS_TABLE_NAME+" WHERE hasSynced=0";
            PreparedStatement preparedStatement;
        try {
           
            preparedStatement = this.connection.prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                JSONObject tenant = new JSONObject();
                tenant.put("record_id", rs.getString("record_id"));
                tenant.put("lastName", rs.getString("lastName"));
                tenant.put("dateOfBirth", rs.getString("dateOfBirth"));
                tenant.put("firstName", rs.getString("firstName"));
                tenant.put("nationality", rs.getString("nationality"));
                tenant.put("phoneNumber1", rs.getString("phoneNumber1"));
                tenant.put("idType", rs.getString("idType"));
                tenant.put("IdNumber", rs.getString("IdNumber"));
                tenant.put("maritalStatus", rs.getString("maritalStatus"));
                tenant.put("numOfFamilyMembers", rs.getString("numOfFamilyMembers"));
                tenant.put("nextOfKinName", rs.getString("nextOfKinName"));
                tenant.put("nextOfKinContact", rs.getString("nextOfKinContact"));
                tenant.put("nextOfKinDistrict", rs.getString("nextOfKinDistrict"));
                tenant.put("nextOfKinCounty", rs.getString("nextOfKinCounty"));
                tenant.put("nextOfKinSubCounty", rs.getString("nextOfKinSubCounty"));
                tenant.put("nextOfKinParish", rs.getString("nextOfKinParish"));
                tenant.put("nextOfKinVillage", rs.getString("nextOfKinVillage"));
                tenant.put("nokPlaceOfWork", rs.getString("nokPlaceOfWork"));
                tenant.put("addedByUserId", rs.getString("addedByUserId"));
                tenant.put("dateCreated", rs.getString("dateCreated"));
                tenant.put("dateLastModified", rs.getString("dateLastModified"));
                tenant.put("hasSynced", rs.getString("hasSynced"));
                System.out.println(rs.getString("nokPlaceOfWork"));
                tenantsArray.put(tenant);     
            }
            return tenantsArray;
        }catch(SQLException | JSONException e){
            e.printStackTrace();
        }
        return tenantsArray;
        
    }
    
     public JSONArray getPaymentsJSON(){
        JSONArray paymentsArray =new  JSONArray();
          String idQuery = "SELECT * FROM "+PAYMENTS_TABLE_NAME+" WHERE hasSynced=0";
         PreparedStatement preparedStatement;
        try {
            preparedStatement = this.connection.prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                JSONObject payment = new JSONObject();
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
          String idQuery = "SELECT * FROM "+BANK_STATEMENTS_TABLE_NAME+" WHERE hasSynced=0";
         PreparedStatement preparedStatement;
        try {
            preparedStatement = this.connection.prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
           
            while(rs.next()){
                JSONObject statements = new JSONObject();
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
     
    public void truncateDatabase(){
        String usersTable ="TRUNCATE TABLE "+USERS_TABLE_NAME;
        String truncateBlocksTable = "TRUNCATE TABLE "+BLOCKS_TABLE_NAME;
        String truncateHousesTable = "TRUNCATE TABLE "+HOUSES_TABLE_NAME;
        String truncateTenantsTable ="TRUNCATE TABLE "+TENANTS_TABLE_NAME;
        String truncateContractsTable ="TRUNCATE TABLE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME;
        String statementsTable ="TRUNCATE TABLE "+BANK_STATEMENTS_TABLE_NAME;
        String paymentsTable ="TRUNCATE TABLE "+PAYMENTS_TABLE_NAME;
        
        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(truncateBlocksTable);
            preparedStatement.execute();
            preparedStatement = this.connection.prepareStatement(truncateHousesTable);
            preparedStatement.execute();
            preparedStatement = this.connection.prepareStatement(truncateTenantsTable);
            preparedStatement.execute();
            preparedStatement = this.connection.prepareStatement(truncateContractsTable);
            preparedStatement.execute();
            preparedStatement = this.connection.prepareStatement(paymentsTable);
            preparedStatement.execute();
            preparedStatement = this.connection.prepareStatement(statementsTable);
            preparedStatement.execute();
            preparedStatement = this.connection.prepareStatement(usersTable);
            preparedStatement.execute();
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * This method is used to synchronize blocks received from the web server with what the local database has.
     * @param blocksArray
     */
    public void syncBlocks(JSONArray blocksArray){
        
        for(int i=0; i<blocksArray.length();i++){
            try {
                insertSyncedBlock(blocksArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void syncHouses(JSONArray blocksArray){
        
        for(int i=0; i<blocksArray.length();i++){
            try {
                this.insertSyncedHouse(blocksArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }  
    public void syncTenants(JSONArray tenantsArray){
        
        for(int i=0; i<tenantsArray.length();i++){
            try {
                this.insertSyncedTenant(tenantsArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void syncPayments(JSONArray paymentsArray){
        
        for(int i=0; i<paymentsArray.length();i++){
            try {
                this.insertSyncedPayment(paymentsArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void syncRentalContracts(JSONArray contractsArray){
        
        for(int i=0; i<contractsArray.length();i++){
            try {
                this.insertSyncedContract(contractsArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void syncStatements(JSONArray statementsArray){
        
        for(int i=0; i<statementsArray.length();i++){
            try {
                this.insertSyncedStatement(statementsArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
      public void syncUsers(JSONArray usersArray){
        for(int i=0; i<usersArray.length();i++){
            try {
                this.insertSyncedUser(usersArray.getJSONObject(i));
            } catch (JSONException ex) {
                ex.printStackTrace();
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    public void insertSyncedBlock(JSONObject block){
        String blockInsertSql ="INSERT INTO "+BLOCKS_TABLE_NAME+" (block_name,location,number_of_rentals,addedByUserId,record_id,dateCreated,dateLastModified,hasSynced) VALUES(?,?,?,?,?,?,?,?)";
        try {
             //" varchar(40) UNIQUE NOT NULL,"+
            PreparedStatement preparedStatement = this.connection.prepareStatement(blockInsertSql);
            preparedStatement.setString(1, block.getString("block_name"));
            preparedStatement.setString(2, block.getString("location"));
            preparedStatement.setInt(3, block.getInt("number_of_rentals"));
            preparedStatement.setInt(4, block.getInt("addedByUserId"));
            preparedStatement.setString(5, block.getString("record_id"));
            preparedStatement.setString(6, block.getString("dateCreated"));
            preparedStatement.setString(7, block.getString("dateLastModified"));
            preparedStatement.setInt(8, 1);
            preparedStatement.execute();
        }
        catch(SQLException e){
            e.printStackTrace();
        } catch (JSONException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void insertSyncedHouse(JSONObject house){
        String houseInsertSql ="INSERT INTO "+HOUSES_TABLE_NAME+" (houseNumber,houseName,monthlyPrice,numberOfRooms,blockID,addedByUserId,record_id,"
                + "dateCreated,dateLastModified,hasSynced,availability) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
                PreparedStatement preparedStatement;
                preparedStatement = this.connection.prepareStatement(houseInsertSql);
                preparedStatement.setString(1, house.getString("houseNumber"));
                preparedStatement.setString(2,  house.getString("houseName"));
                preparedStatement.setDouble(3, house.getDouble("monthlyPrice"));
                preparedStatement.setInt(4, house.getInt("numberOfRooms"));
                preparedStatement.setString(5, house.getString("blockID"));
                preparedStatement.setInt(6, house.getInt("addedByUserId"));
                preparedStatement.setString(7,  house.getString("record_id"));
                preparedStatement.setString(8, house.getString("dateCreated"));
                preparedStatement.setString(9, house.getString("dateLastModified"));
                preparedStatement.setInt(10,  1);
                preparedStatement.setInt(11, house.getInt("availability"));
                preparedStatement.execute();
                
            }catch(SQLException | JSONException ex){
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
                          
    }
    public void insertSyncedTenant(JSONObject tenant){
        String houseInsertSql ="INSERT INTO "+TENANTS_TABLE_NAME+" (record_id,lastName,dateOfBirth,firstName,nationality,phoneNumber1,idType,IdNumber,maritalStatus,numOfFamilyMembers,"
                + "nextOfKinName,nextOfKinContact,nextOfKinDistrict,nextOfKinCounty,nextOfKinSubCounty,nextOfKinParish,nextOfKinVillage,addedByUserId,dateCreated,dateLastModified,hasSynced) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
                PreparedStatement preparedStatement;
                System.out.println("Test "+tenant.get("IdNumber"));
                preparedStatement = this.connection.prepareStatement(houseInsertSql);
                 preparedStatement.setString(1, tenant.getString("record_id"));
                 preparedStatement.setString(2, tenant.getString("lastName"));
                 preparedStatement.setString(3, tenant.getString("dateOfBirth"));
                 preparedStatement.setString(4, tenant.getString("firstName"));
                 preparedStatement.setString(5, tenant.getString("nationality"));
                 preparedStatement.setString(6, tenant.getString("phoneNumber1"));
                 preparedStatement.setString(7, tenant.getString("IdType"));
                 preparedStatement.setString(8, tenant.getString("IdNumber"));
                 preparedStatement.setString(9, tenant.getString("maritalStatus"));
                 preparedStatement.setInt(10, tenant.getInt("numOfFamilyMembers"));
                 preparedStatement.setString(11, tenant.getString("nextOfKinName"));
                 preparedStatement.setString(12, tenant.getString("nextOfKinContact"));
                 preparedStatement.setString(13, tenant.getString("nextOfKinDistrict"));
                 preparedStatement.setString(14, tenant.getString("nextOfKinCounty"));
                 preparedStatement.setString(15, tenant.getString("nextOfKinSubCounty"));
                 preparedStatement.setString(16, tenant.getString("nextOfKinParish"));
                 preparedStatement.setString(17, tenant.getString("nextOfKinVillage"));
                 preparedStatement.setInt(18, tenant.getInt("addedByUserId"));
                 preparedStatement.setString(19, tenant.getString("dateCreated"));
                 preparedStatement.setString(20, tenant.getString("dateLastModified"));
                 preparedStatement.setInt(21, 1);
                preparedStatement.execute();
            }catch(SQLException | JSONException ex){
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }   
    public void insertSyncedContract(JSONObject contract){
        String contractInsertSql ="INSERT INTO "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" (record_id,tenantID,startDate,houseID,agreedMonthlyAmount,addedByUserId,"
                + "dateCreated,dateLastModified,hasSynced,isTerminated,advanceAmountBeforeStart,arrearsBeforeStart) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
                PreparedStatement preparedStatement;
                preparedStatement = this.connection.prepareStatement(contractInsertSql);
                preparedStatement.setString(1, contract.getString("record_id"));
                preparedStatement.setString(2, contract.getString("tenantID"));
                preparedStatement.setString(3, contract.getString("startDate"));
                preparedStatement.setString(4, contract.getString("houseID"));
                preparedStatement.setDouble(5, contract.getDouble("agreedMonthlyAmount"));
                preparedStatement.setInt(6, contract.getInt("addedByUserId"));
                preparedStatement.setString(7, contract.getString("dateCreated"));
                preparedStatement.setString(8, contract.getString("dateLastModified"));
                preparedStatement.setInt(9, 1);
                preparedStatement.setInt(10, contract.getInt("isTerminated"));
                preparedStatement.setDouble(11, contract.getDouble("advanceAmountBeforeStart"));
                preparedStatement.setDouble(12, contract.getDouble("arrearsBeforeStart"));
                preparedStatement.execute();
            }catch(SQLException | JSONException ex){
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
                      
    }   
    public void insertSyncedPayment(JSONObject payment){
        String houseInsertSql ="INSERT INTO "+PAYMENTS_TABLE_NAME+" (record_id,datePaid,amountPaid,contractID,receivedBy,tenantID,"
                + "modeOfPayment,receiptNumber,addedByUserId,dateCreated,dateLastModified,hasSynced) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
                PreparedStatement preparedStatement;
                preparedStatement = this.connection.prepareStatement(houseInsertSql);
                 preparedStatement.setString(1, payment.getString("record_id"));
                 preparedStatement.setString(2, payment.getString("datePaid"));
                 preparedStatement.setDouble(3, payment.getDouble("amountPaid"));
                 preparedStatement.setString(4, payment.getString("contractID"));
                 preparedStatement.setString(5, payment.getString("receivedBy"));
                 preparedStatement.setString(6, payment.getString("tenantID"));
                 preparedStatement.setString(7, payment.getString("modeOfPayment"));
                 preparedStatement.setString(8, payment.getString("receiptNumber"));
                 preparedStatement.setInt(9, payment.getInt("addedByUserId"));
                 preparedStatement.setString(10, payment.getString("dateCreated"));
                 preparedStatement.setString(11, payment.getString("dateLastModified"));
                 preparedStatement.setInt(12, 1);        
                preparedStatement.execute();
            }catch(SQLException | JSONException ex){
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
                      
    }
    public void insertSyncedStatement(JSONObject statement){
        String houseInsertSql ="INSERT INTO "+BANK_STATEMENTS_TABLE_NAME+" (record_id,transactionDate,valueDate,transactionDescription,creditAmount,accountBalance,"
                + "tenantId,addedByUserId,dateCreated,dateLastModified,hasSynced) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try {
                PreparedStatement preparedStatement;
                preparedStatement = this.connection.prepareStatement(houseInsertSql);
                 preparedStatement.setString(1, statement.getString("record_id"));
                preparedStatement.setString(2, statement.getString("transactionDate"));
                preparedStatement.setString(3, statement.getString("valueDate"));
                preparedStatement.setString(4, statement.getString("transactionDescription"));
                preparedStatement.setDouble(5, statement.getDouble("creditAmount"));
                preparedStatement.setDouble(6, statement.getDouble("accountBalance"));
                preparedStatement.setString(7, statement.getString("tenantId"));
                preparedStatement.setInt(8, statement.getInt("addedByUserId"));
                preparedStatement.setString(9, statement.getString("dateCreated"));
                preparedStatement.setString(10, statement.getString("dateLastModified"));
                preparedStatement.setInt(11, 1);    
                preparedStatement.execute();
            }catch(SQLException | JSONException ex){
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            }                  
    }
    
    public void insertSyncedUser(JSONObject user){
        String queryUser = "INSERT INTO "+USERS_TABLE_NAME+" (id,fistName,lastName,email,password) VALUES(?,?,?,?,?)";
         try {
                PreparedStatement preparedStatement;
                preparedStatement = this.connection.prepareStatement(queryUser);
                preparedStatement.setInt(1, user.getInt("id"));
                String name[] = user.getString("name").split(" ");
                try{
                    preparedStatement.setString(2, name[0]);
                    preparedStatement.setString(3, name[1]);
                }
                catch( ArrayIndexOutOfBoundsException ex){
                    //in case there is second name
                    preparedStatement.setString(3, " ");
                }
                preparedStatement.setString(4, user.getString("email"));
                preparedStatement.setString(5, user.getString("password"));
                preparedStatement.execute();    
         }catch(SQLException | JSONException | ArrayIndexOutOfBoundsException ex){
                Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            } 
    }

   
  }
