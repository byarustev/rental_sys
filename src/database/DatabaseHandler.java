/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import GeneralClasses.Block;
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
import java.util.ArrayList;
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
    //eager instatiation of the of the instance
    
    private static  DatabaseHandler dbHandler =new DatabaseHandler();
    
    private DatabaseHandler(){
        createConnection();
        createTables();
    }
    public static DatabaseHandler getInstance(){
        return dbHandler;
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
                        + "added_by int DEFAULT NULL)";
                statement = connection.createStatement();
                statement.execute(sql);
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
                        "blockID int  NOT NULL)";
                statement = connection.createStatement();
                statement.execute(sql);
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
                        "tenantID varchar(20) NOT NULL )";
                statement = connection.createStatement();
                statement.execute(sql);
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
                        "receivedBy varchar(30) DEFAULT NULL )";
                statement = connection.createStatement();
                statement.execute(sql);
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
                        "houseID int NOT NULL )";
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
                        "tenantID int NOT NULL )";
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
                        "tenantID int NOT NULL )";
                statement = connection.createStatement();
                statement.execute(sql);
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
                        + "nextOfKinContact varchar(30) DEFAULT NULL)";
                statement = connection.createStatement();
                statement.execute(sql);
            }
            
            //Creating the USERS_TABLE_NAME table
            rs = md.getTables(null, null, USERS_TABLE_NAME.toUpperCase(), null);
            if(rs.next()){
                //Blocks table is present
                System.out.println(USERS_TABLE_NAME+" table is already created");
            }
            else{
                String sql ="CREATE TABLE "+USERS_TABLE_NAME+" (id int NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY \n," +
                         "fistName varchar(30) NOT NULL,"
                        + "lastName varchar(30) NOT NULL,"
                        + "email varchar(40) NOT NULL,"
                        + "password varchar(250) NOT NULL)";
                statement = connection.createStatement();
                statement.execute(sql);
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
        } catch (InstantiationException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
    }

    public boolean insertBlock(Block block) {
        String blockInsertSql ="INSERT INTO "+BLOCKS_TABLE_NAME+" (block_name,location,number_of_rentals) VALUES(?,?,?)";
        String blockIdQuery ="SELECT id FROM "+BLOCKS_TABLE_NAME+" WHERE block_name = ? AND location = ? AND number_of_rentals = ?";
        String houseInsertSql ="INSERT INTO "+HOUSES_TABLE_NAME+" (houseNumber,houseName,monthlyPrice,numberOfRooms,blockID) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(blockInsertSql);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            preparedStatement.execute();
            //get the created Id
            preparedStatement = connection.prepareStatement(blockIdQuery);
            preparedStatement.setString(1, block.getName());
            preparedStatement.setString(2, block.getLocation());
            preparedStatement.setInt(3, block.getNumberOfRentals());
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.next()){
                int id=rs.getInt("id");
                String defHouseName = block.getName().length()>8?block.getName().substring(0, 7):block.getName();
                System.out.println("ID GIVEN "+id);
                for(House x: block.getHousesList()){
                    System.out.println(x.getUnitNo()+" : "+x.getRentalName()+" : "+x.getRentalNumOfUnits());
                    preparedStatement = connection.prepareStatement(houseInsertSql);
                    preparedStatement.setString(1, x.getUnitNo());
                    preparedStatement.setString(2, x.getRentalName());
                    preparedStatement.setDouble(3, x.getMonthlyAmount());
                    preparedStatement.setInt(4,x.getRentalNumOfUnits());
                     preparedStatement.setInt(5,id);
                    preparedStatement.execute();
                }
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
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
                list.add(new House(rs.getString("id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility")));
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
                return new House(rs.getString("id"),rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice"),rs.getString("blockID"),rs.getInt("avaibility"));
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
                blocksList.add(new Block(rs.getString("id"),rs.getString("block_name"),rs.getString("location"),rs.getInt("number_of_rentals")));
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
                block =new Block(rs.getString("id"),rs.getString("block_name"),rs.getString("location"),rs.getInt("number_of_rentals"));
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
                + "numOfFamilyMembers,nextOfKinName,nextOfKinContact) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?)";
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

    /**
     * This is used to store a new contract in the database
     * @param associatedTenant
     * @param associatedHouse
     * @param startDate
     * @param agreedMonthlyAmount
     * @return contractId
     */
    public String insertHouseContract(String associatedTenant, String associatedHouse, String startDate, Double agreedMonthlyAmount) {
        String insertSql ="INSERT INTO " +HOUSE_RENTAL_CONTRACT_TABLE_NAME+" (startDate,agreedMonthlyAmount,houseID,tenantID) VALUES(?,?,?,?)";
         String updateSql=        "UPDATE "+HOUSES_TABLE_NAME+" SET avaibility =0 WHERE id = ?";
        
        try {
            PreparedStatement prepartedStatement = connection.prepareStatement(insertSql);
            prepartedStatement.setString(1, startDate);
            prepartedStatement.setDouble(2, agreedMonthlyAmount);
            prepartedStatement.setString(3, associatedHouse);
            prepartedStatement.setString(4, associatedTenant);
            prepartedStatement.execute();
            
            
            prepartedStatement = connection.prepareStatement(updateSql);
            prepartedStatement.setInt(1, Integer.parseInt(associatedHouse));
            prepartedStatement.execute();
            
            String idQuery = "SELECT id FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE startDate = ? AND houseID = ? AND tenantID = ? AND agreedMonthlyAmount = ?";
            prepartedStatement = connection.prepareStatement(idQuery);
            prepartedStatement.setString(1, startDate);
            prepartedStatement.setString(2, associatedHouse);
            prepartedStatement.setString(3, associatedTenant);
            prepartedStatement.setDouble(4, agreedMonthlyAmount);
            ResultSet rs = prepartedStatement.executeQuery();
            while(rs.next()){
                return rs.getString("id");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }           
         return null;
    }

    public String savePayment(String paymentDate, Double paymentAmount, String rentalContractId, String receivedBy,String tenantId,String modeOfPayment,String referenceNumber) {
        String sql ="INSERT INTO "+PAYMENTS_TABLE_NAME+" (amountPaid,datePaid,receiptNumber,modeOfPayment,tenantID,contractID,receivedBy) VALUES(?,?,?,?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, paymentAmount);
            preparedStatement.setString(2, paymentDate);
            preparedStatement.setString(3, referenceNumber);
            preparedStatement.setString(4, modeOfPayment);
            preparedStatement.setString(5, tenantId);
            preparedStatement.setInt(6, Integer.parseInt(rentalContractId));
            preparedStatement.setString(7, receivedBy);
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

    

    public HouseRentalContract getCurrentContract(String tenantId, String houseId) {
        String contractQuery;
           try {
            PreparedStatement prepartedStatement=null;
            if(tenantId != null && houseId==null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE tenantID = ? ORDER BY id DESC";
                prepartedStatement= connection.prepareStatement(contractQuery);
                prepartedStatement.setString(1, tenantId);
            }
            else if(tenantId == null && houseId!=null){
                contractQuery= "SELECT * FROM "+HOUSE_RENTAL_CONTRACT_TABLE_NAME+" WHERE houseID = ? ORDER BY id DESC";
                prepartedStatement= connection.prepareStatement(contractQuery);
                prepartedStatement.setString(1, houseId);
            }
            ResultSet rs =prepartedStatement.executeQuery();
            if(rs.next()){
                HouseRentalContract contract = new HouseRentalContract(rs.getString("startDate"),
                        rs.getString("tenantID") , rs.getString("id") , 
                        rs.getString("houseID") , rs.getDouble("agreedMonthlyAmount"));
                System.out.println(contract);
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
             tenantPayments.add(new Payment(rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), rs.getString("receiptNumber")));
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
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") ,  rs.getString("nextOfKinContact")));
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
                        rs.getString("idNumber") ,rs.getString("maritalStatus"), rs.getInt("numOfFamilyMembers"), rs.getString("nextOfKinName") ,  rs.getString("nextOfKinContact"));
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
             tenantPayments.add(new Payment(rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), rs.getString("receiptNumber")));
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
             tenantPayments.add(new Payment(rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), rs.getString("receiptNumber")));
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
             tenantPayments.add(new Payment(rs.getString("datePaid"), rs.getDouble("amountPaid"), 
                      rs.getString("contractID"),rs.getString("receivedBy"), 
                      rs.getString("tenantID"), rs.getString("modeOfPayment"), rs.getString("receiptNumber")));
            }
            return tenantPayments;
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
      return null; 
    }
  }
