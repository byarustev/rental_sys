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
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author robert
 */
public class DatabaseHandler {
    
    private Connection connection;
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
        createConnection();
        createTables();
    }
    public static DatabaseHandler getInstance(){
        return DB_HANDLER;
    }
    private void createTables(){
        try {
            DatabaseMetaData md = connection.getMetaData();
            ResultSet rs = md.getTables(null, null, BLOCKS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println("Blocks table is already created");
            }
            else{
                String sql ="CREATE TABLE "+BLOCKS_TABLE_NAME+"("
                        + "id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n" +
"                (START WITH 1, INCREMENT BY 1) ,"
                        + "block_name varchar(30) NOT NULL,"
                        + "location varchar(80) NOT NULL,"
                        + "number_of_rentals int NOT NULL,"
                        + "added_by int DEFAULT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
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
                       "  houseNumber varchar(20) NOT NULL,"+
                        "houseName varchar(30) NOT NULL,"+
                        "monthlyPrice double NOT NULL,"+
                        "numberOfRooms int NOT NULL,"+
                        "avaibility int DEFAULT 1,"+
                        "blockID int  NOT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
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
                        " startDate date NOT NULL,"+
                        "agreedMonthlyAmount double NOT NULL,"+
                        "houseID varchar(20) NOT NULL,"+
                        "tenantID varchar(20) NOT NULL ,"
                        + "isTerminated INT DEFAULT 0,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
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
                        " amountPaid double NOT NULL,"+
                        "datePaid date NOT NULL,"+
                        "receiptNumber varchar(30) DEFAULT NULL,"+
                        "modeOfPayment varchar(30) NOT NULL,"+
                         " tenantID int NOT NULL,"+""
                        + "contractID int NOT NULL,"+
                        "receivedBy varchar(30) DEFAULT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
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
                        " roomID varchar(20) NOT NULL,"+
                        "roomNumber varchar(30) NOT NULL,"+
                        "monthlyPrice double NOT NULL,"+
                        "houseID int NOT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
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
                        " startDate date NOT NULL,"+
                        "agreedAmount double NOT NULL,"+
                        "roomID int NOT NULL,"+
                        "tenantID int NOT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                System.out.println(sql);
                
                statement = connection.createStatement();
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
                        " startDate date NOT NULL,"+
                        "agreedAmount double NOT NULL,"+
                        "roomID int NOT NULL,"+
                        "tenantID int NOT NULL ,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
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
                        + "nextOfKinContact varchar(30) DEFAULT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
            
            //Creating the USERS_TABLE_NAME table
            rs = md.getTables(null, null, USERS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                
                System.out.println(USERS_TABLE_NAME+" table is already created");
                // connection.createStatement().execute("INSERT INTO "+USERS_TABLE_NAME+" (fistName,lastName,email,password) VALUES('Robert','Kasumba','robein@ymail.com','robert123')");
            }
            else{
                String sql ="CREATE TABLE "+USERS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n," +
                         "fistName varchar(30) NOT NULL,"
                        + "lastName varchar(30) NOT NULL,"
                        + "email varchar(40) NOT NULL,"
                        + "password varchar(250) NOT NULL)";
                statement = connection.createStatement();
                statement.execute(sql);
                connection.createStatement().execute("INSERT INTO "+USERS_TABLE_NAME+" (fistName,lastName,email,password) VALUES('Yvonne','Nalinnya','yvonne@gmail.com','yvonne123')");
                System.out.println(sql);
            }
            //Creating the BANK_STATEMENTS_TABLE_NAME table
            rs = md.getTables(null, null, BANK_STATEMENTS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(BANK_STATEMENTS_TABLE_NAME+" table is already created");
                statement = connection.createStatement();
               // statement.execute("DROP TABLE "+BANK_STATEMENTS_TABLE_NAME);
            }
            else{
                String sql ="CREATE TABLE "+BANK_STATEMENTS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n," +
                         "transactionDate DATE NOT NULL,"
                        + "valueDate DATE NOT NULL,"
                        + "transactionDescription VARCHAR(100) NOT NULL,"
                        + "creditAmount DOUBLE NOT NULL,"
                        + "accountBalance DOUBLE NOT NULL,"
                        + "dateCreated TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "tenantId INT DEFAULT NULL,"
                        + "addedByUserId INT DEFAULT NULL,"
                        + "dateLastModified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "hasSynced INT DEFAULT 0"
                        + ")";
                statement = connection.createStatement();
                statement.execute(sql);
                System.out.println(sql);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
        
    }
    
    
    
    
    public void createConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URL);
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
        String blockInsertSql ="INSERT INTO "+BLOCKS_TABLE_NAME+" (block_name,location,number_of_rentals,addedByUserId) VALUES(?,?,?,?)";
        String blockIdQuery ="SELECT id FROM "+BLOCKS_TABLE_NAME+" WHERE block_name = ? AND location = ? AND number_of_rentals = ?";
        String houseInsertSql ="INSERT INTO "+HOUSES_TABLE_NAME+" (houseNumber,houseName,monthlyPrice,numberOfRooms,blockID,addedByUserId) VALUES (?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(blockInsertSql);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            preparedStatement.setString(4, CurrentUser.getInstance().getUserId());
            preparedStatement.execute();
            //get the created Id
            preparedStatement = connection.prepareStatement(blockIdQuery);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                int blockId=rs.getInt("id");
                for(House x: block.getHousesList()){
                    System.out.println(x.getUnitNo()+" : "+x.getRentalName()+" : "+x.getRentalNumOfUnits());
                    preparedStatement = connection.prepareStatement(houseInsertSql);
                    preparedStatement.setString(1, x.getUnitNo());
                    preparedStatement.setString(2, x.getRentalName());
                    preparedStatement.setDouble(3, x.getMonthlyAmount());
                    preparedStatement.setInt(4,x.getRentalNumOfUnits());
                    preparedStatement.setInt(5,blockId);
                    preparedStatement.setString(6, CurrentUser.getInstance().getUserId());
                    preparedStatement.execute();
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
            PreparedStatement preparedStatement = connection.prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<House> list = new ArrayList();
            while(rs.next()){
                list.add(new House(rs.getString("id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility"),
                     rs.getString("addedByUserId")));
            }   
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null;
    }
    public House getHouse(String databaseId) {
        String housesQuery ="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return new House(rs.getString("id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility"),
                     rs.getString("addedByUserId"));
            }   
            
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null;
    }
    public ArrayList<Block> getBlocks(){
        String blockQuery ="SELECT * FROM "+BLOCKS_TABLE_NAME+"";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(blockQuery);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Block> blocksList = new ArrayList();
            while(rs.next()){
                blocksList.add(new Block(rs.getString("id"),rs.getString("block_name"),rs.getString("location"),rs.getInt("number_of_rentals"),
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
          String blockQuery ="SELECT * FROM "+BLOCKS_TABLE_NAME+" WHERE id =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(blockQuery);
            preparedStatement.setString(1, blockId);
            ResultSet rs = preparedStatement.executeQuery();
            Block block=null;
            if(rs.next()){
                block =new Block(rs.getString("id"),rs.getString("block_name"),rs.getString("location"),rs.getInt("number_of_rentals"),
                     rs.getString("addedByUserId"));
            }
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
    public String insertTenant(String firstName, String lastName, String maritalStatus, String nationality, String idType, String idNumber, int numOfFamMembers, String dateOfBirth, String phoneNumber, String nokName, String nokContack) {
        String sql ="INSERT INTO "+TENANTS_TABLE_NAME+" (firstName,lastName,maritalStatus,dateOfBirth,nationality,"
                + "IdType,IdNumber,photoPath,phoneNumber1,phoneNumber2,"
                + "numOfFamilyMembers,nextOfKinName,nextOfKinContact,addedByUserId) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
            preparedStatement.execute();
            String idQuery = "SELECT id FROM "+TENANTS_TABLE_NAME+" WHERE firstName = ? AND lastName = ? AND dateOfBirth = ? AND phoneNumber1 = ?";
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setString(3, dateOfBirth);
            preparedStatement.setString(4, phoneNumber);
            
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("id");
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String insertBankStatement(String transactionDate, String valueDate, String transactionDescription, Double creditAmount, Double accountBalance, String tenantId){
        String insertSql ="INSERT INTO "+BANK_STATEMENTS_TABLE_NAME+" (transactionDate,valueDate,transactionDescription,creditAmount,accountBalance,tenantId,addedByUserId) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, transactionDate);
            preparedStatement.setString(2, valueDate);
            preparedStatement.setString(3, transactionDescription);
            preparedStatement.setDouble(4, creditAmount);
            preparedStatement.setDouble(5, accountBalance);
            preparedStatement.setString(6, tenantId);
            preparedStatement.setString(7, CurrentUser.getInstance().getUserId());
            preparedStatement.execute();
            String idQuery = "SELECT id FROM "+BANK_STATEMENTS_TABLE_NAME+" WHERE transactionDate=? AND valueDate=?  AND creditAmount=? AND tenantId=?";
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, transactionDate);
            preparedStatement.setString(2, valueDate);
            preparedStatement.setDouble(3, creditAmount);
            preparedStatement.setString(4, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    /**
     * This is used to store a new contract in the database
     * @param associatedTenant
     * @param associatedHouse
     * @param startDate
     * @param agreedMonthlyAmount
     * @return contractId
     */
    public String insertHouseContract(String associatedTenant, String associatedHouse, String startDate, Double agreedMonthlyAmount) {
        String insertSql ="INSERT INTO " +HOUSE_RENTAL_CONTRACT_TABLE_NAME+" (startDate,agreedMonthlyAmount,houseID,tenantID,addedByUserId) VALUES(?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setString(1, startDate);
            preparedStatement.setDouble(2, agreedMonthlyAmount);
            preparedStatement.setString(3, associatedHouse);
            preparedStatement.setString(4, associatedTenant);
            preparedStatement.setString(5, CurrentUser.getInstance().getUserId());
            preparedStatement.execute();     
            //set the  house as unavailable
            changeHouseAvailability(associatedHouse, this.HOUSE_NOT_AVAILABLE);
            
            String idQuery = "SELECT id FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE startDate = ? AND houseID = ? AND tenantID = ? AND agreedMonthlyAmount = ?";
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, associatedHouse);
            preparedStatement.setString(3, associatedTenant);
            preparedStatement.setDouble(4, agreedMonthlyAmount);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
                String houseId=rs.getString("id");
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
        String sql ="INSERT INTO "+PAYMENTS_TABLE_NAME+" (amountPaid,datePaid,receiptNumber,modeOfPayment,tenantID,contractID,receivedBy,addedByUserId) VALUES(?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, paymentAmount);
            preparedStatement.setString(2, paymentDate);
            preparedStatement.setString(3, referenceNumber);
            preparedStatement.setString(4, modeOfPayment);
            preparedStatement.setString(5, tenantId);
            preparedStatement.setInt(6, Integer.parseInt(rentalContractId));
            preparedStatement.setString(7, receivedBy);
            preparedStatement.setString(8, CurrentUser.getInstance().getUserId());
            preparedStatement.execute();
            
            String paymentQuery = "SELECT id FROM "+PAYMENTS_TABLE_NAME+" WHERE amountPaid =? AND datePaid =? AND receiptNumber =? AND tenantID =? AND contractID =?";
            preparedStatement = connection.prepareStatement(paymentQuery);
            preparedStatement.setDouble(1, paymentAmount);
            preparedStatement.setString(2, paymentDate);
            preparedStatement.setString(3, referenceNumber);
            preparedStatement.setString(4, tenantId);
            preparedStatement.setInt(5, Integer.parseInt(rentalContractId));
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                return rs.getString("id");    
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
     * @return contract
     * This methods find the non-terminated contract for a house or tenant
     */
    public HouseRentalContract getCurrentContract(String tenantId, String houseId) {
        String contractQuery;
           try {
            PreparedStatement prepartedStatement=null;
            if(tenantId != null && houseId==null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE tenantID = ? AND isTerminated=0 ORDER BY id DESC";
                prepartedStatement= connection.prepareStatement(contractQuery);
                prepartedStatement.setString(1, tenantId);
            }
            else if(tenantId == null && houseId!=null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE houseID = ? AND isTerminated=0 ORDER BY id DESC";
                prepartedStatement= connection.prepareStatement(contractQuery);
                prepartedStatement.setString(1, houseId);
            }
            ResultSet rs =prepartedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("id") , 
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
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getInt("id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
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
            preparedStatement = connection.prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                tenants.add(new Tenant(rs.getString("id"), rs.getString("lastName") , rs.getString("firstName"), 
                        rs.getString("dateOfBirth") , rs.getString("nationality") , rs.getString("phoneNumber1") ,rs.getString("idType"),
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") ,  rs.getString("nextOfKinContact"),
                     rs.getString("addedByUserId")));
            }
            return tenants;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null;      
    }
    public Tenant getTenant(String tenantId){
        String idQuery = "SELECT * FROM "+TENANTS_TABLE_NAME+" WHERE id =?";
        PreparedStatement preparedStatement;
        try {
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, tenantId);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()){
              return new Tenant(rs.getString("id"), rs.getString("lastName") , rs.getString("firstName"), 
                        rs.getString("dateOfBirth") , rs.getString("nationality") , rs.getString("phoneNumber1") ,rs.getString("idType"),
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") ,  rs.getString("nextOfKinContact"),
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
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, contractId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getInt("id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
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
            preparedStatement = connection.prepareStatement(idQuery);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getInt("id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
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
            preparedStatement = connection.prepareStatement(idQuery);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<Payment> tenantPayments = new ArrayList();
            while(rs.next()){
             tenantPayments.add(new Payment(rs.getInt("id"),rs.getString("datePaid"), rs.getDouble("amountPaid"), 
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
        String sql ="UPDATE "+TENANTS_TABLE_NAME+" SET firstName=?,lastName=?,maritalStatus=?,dateOfBirth=?,nationality=?,"
                + "IdType=?,IdNumber=?,photoPath=?,phoneNumber1=?,phoneNumber2=?,"
                + "numOfFamilyMembers=?,nextOfKinName=?,nextOfKinContact=?,dateLastModified=? WHERE id=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
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
            preparedStatement.setString(14,tenant.getTenantId());
              preparedStatement.setString(15, modifiedDate);
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
          
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               statements.add(new BankStatement(rs.getString("transactionDate"),rs.getString("valueDate"),rs.getString("transactionDescription"),
                       rs.getDouble("creditAmount"), rs.getDouble("accountBalance"),rs.getString("tenantId"),rs.getString("id"),
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               statements.add(new BankStatement(rs.getString("transactionDate"),rs.getString("valueDate"),rs.getString("transactionDescription"),
                       rs.getDouble("creditAmount"), rs.getDouble("accountBalance"),rs.getString("tenantId"),rs.getString("id"),
                     rs.getString("addedByUserId")));
            }
            return statements;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
    }
    public boolean updatePayment(Payment payment) {
       String sql ="UPDATE "+PAYMENTS_TABLE_NAME+" SET amountPaid=?,datePaid=?,receiptNumber=?,modeOfPayment=?,tenantID=?,contractID=?,receivedBy=? WHERE id=?";
       try {
          
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1,payment.getPaymentAmount() );
            preparedStatement.setString(2, payment.getPaymentDate());
            preparedStatement.setString(3, payment.getReferenceNumber());
            preparedStatement.setString(4, payment.getModeOfPayment());
            preparedStatement.setString(5, payment.getTenantId());
            preparedStatement.setString(6, payment.getRentalContractId());
            preparedStatement.setString(7, payment.getReceivedBy());
            preparedStatement.setInt(8, payment.getPaymentId());
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
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, tenantId);
           
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
               statements.add(new BankStatement(rs.getString("transactionDate"),rs.getString("valueDate"),rs.getString("transactionDescription"),
                       rs.getDouble("creditAmount"), rs.getDouble("accountBalance"),rs.getString("tenantId"),rs.getString("id"),
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
           //terminate contract 
            String updateContractSql = "UPDATE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" SET isTerminated=1, dateLastModified=?  WHERE id=?";
           try {
               PreparedStatement preparedStatement = connection.prepareStatement(updateContractSql);
               preparedStatement.setString(2, contractId);
               preparedStatement.setTimestamp(1,new Timestamp(calendar.getTime().getTime()));
               preparedStatement.execute();
               if(houseId==null){
                   //do not create new contract
                   return null;
                }else{
                    String id =this.insertHouseContract(tenantId, houseId, startDate, agreedAmount);
                    changeHouseAvailability(houseId, this.HOUSE_NOT_AVAILABLE);
                    if(!prevHouseId.equals(houseId)){
                         //make previous house available
                         changeHouseAvailability(prevHouseId, this.HOUSE_AVAILABLE);
                    }
                    return id;
               }
           } catch (SQLException ex) {
               Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
           }
           
       }
       else{
           //update the current contract
           String updateContractSql = "UPDATE "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" SET startDate =?,agreedMonthlyAmount=? ,houseID=?, tenantID=?,dateLastModified=? WHERE id=?";
           try {
               PreparedStatement preparedStatement = connection.prepareStatement(updateContractSql);
               preparedStatement.setString(1, startDate);
               preparedStatement.setDouble(2, agreedAmount);
               preparedStatement.setString(3, houseId);
               preparedStatement.setString(4, tenantId);
               preparedStatement.setTimestamp(5,new Timestamp(calendar.getTime().getTime()));
               preparedStatement.setString(6, contractId);
               preparedStatement.execute();
               //set the new house as unavailable
               changeHouseAvailability(houseId, this.HOUSE_NOT_AVAILABLE);
               if(!prevHouseId.equals(houseId)){
                    //make previous house available
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
       String  updateHouseSql="UPDATE "+HOUSES_TABLE_NAME+" SET avaibility =?,dateLastModified=? WHERE id = ?";
       PreparedStatement  preparedStatement = connection.prepareStatement(updateHouseSql);
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
            preparedStatement = connection.prepareStatement(queryUserSql);
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
            String contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE id=?";
            PreparedStatement preparedStatement= connection.prepareStatement(contractQuery);
            preparedStatement.setString(1, rentalContractId);
            ResultSet rs =preparedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("id") , 
                        rs.getString("houseID") , rs.getDouble("agreedMonthlyAmount"),
                     rs.getString("addedByUserId"));
                return contract;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
  }
