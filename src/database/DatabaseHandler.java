/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import GeneralClasses.Block;
import GeneralClasses.House;

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
    private String DB_URL = "jdbc:derby:database;create=true";
    private String BLOCKS_TABLE_NAME = "BLOCKS";  
    private String HOUSES_TABLE_NAME = "houses";  
    private String PAYMENTS_TABLE_NAME = "payments"; 
    private String ROOMS_TABLE_NAME = "rooms"; 
    private String ROOM_RENTAL_CONTRACT_TABLE_NAME = "room_rental_contracts"; 
    private String TENANTS_TABLE_NAME = "tenants"; 
    private String USERS_TABLE_NAME = "users"; 
    private String HOUSE_RENTAL_CONTRACT_TABLE_NAME = "house_rental_contracts";  
    //eager instatiation of the of the instance
    
    private static DatabaseHandler dbHandler =new DatabaseHandler();
    
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
                        "receiptNumber int NOT NULL,"+
                        "modeOfPayment varchar(30) NOT NULL,"+
                         " tenantID int NOT NULL,"+""
                        + "contractID int NOT NULL,"+
                        "recordedBy int NOT NULL )";
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
                        + "IdType varchar(15) NOT NULL,"
                        + "IdNumber varchar(30) NOT NULL,"
                        + "photoPath varchar(200) NOT NULL,"
                        + "phoneNumber1 varchar(15) NOT NULL,"
                        + "phoneNumber2 varchar(15) NOT NULL,"
                        + "maritalStatus varchar(15) NOT NULL,"
                        + "numOfFamilyMembers int NOT NULL,"
                        + "nextOfKinName varchar(50) NOT NULL,"
                        + "nextOfKinContact varchar(30) NOT NULL)";
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
        String houseInsertSql ="INSERT INTO "+HOUSES_TABLE_NAME+" (houseNumber,houseName,monthlyPrice,numberOfRooms,blockID) VALUES (?,?,?,?)";
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
                     preparedStatement.setInt(4,id);
                    preparedStatement.execute();
                }
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    public ArrayList<House> getHousesList(String databaseId) {
        String housesQuery ="SELECT * FROM "+HOUSES_TABLE_NAME+" WHERE blockID = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(housesQuery);
            preparedStatement.setString(1,databaseId);
            ResultSet rs = preparedStatement.executeQuery();
            ArrayList<House> list = new ArrayList();
            while(rs.next()){
                list.add(new House(rs.getString("houseNumber"),rs.getString("houseName"),rs.getInt("numberOfRooms"),rs.getDouble("monthlyPrice")));
            }
            
            return list;
            
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
         
         return null;
    }
}
