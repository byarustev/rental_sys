/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
    
    
    public DatabaseHandler(){
        createConnection();
    }
    private void createTables(){
        String BLOCKS_TABLE_NAME = "blocks";  
       
        try {
            statement = connection.createStatement();
            statement.execute("CREATE IF NOT EXISTS TABLE "+BLOCKS_TABLE_NAME+" ("
                    + "id int(11) NOT NULL,"
                    + "block_name varchar(30) NOT NULL,"
                    + "location varchar(80) NOT NULL,"
                    + "number_of_rentals int(11) NOT NULL,"
                    + "added_by int(11) DEFAULT NULL");
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
    public void createConnection(){
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            connection = DriverManager.getConnection(DB_URL);
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
}
