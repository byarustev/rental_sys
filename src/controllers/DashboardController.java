/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Priority;
import database.DatabaseHandler;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class DashboardController implements Initializable {

    private AnchorPane dynamicAnchorPane; 
    public DashboardController()  {
        //this.location = new URL("views/dashboard.fxml");
        new DatabaseHandler();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
               // AnchorPane.setLeftAnchor(mainPane, 0.0);
               // AnchorPane.setRightAnchor(mainPane, 0.0);
    }    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    //@FXML // URL location of the FXML file that was given to the FXMLLoader
    //private URL location;

    @FXML // fx:id="paymentsTab"
    private Label paymentsTab; // Value injected by FXMLLoader

    @FXML // fx:id="roomsTab"
    private Label roomsTab; // Value injected by FXMLLoader
    
    @FXML // fx:id="tenantsTab"
    private Label tenantsTab; // Value injected by FXMLLoader
    
    @FXML
    private Label blocksTab;

    @FXML
    private AnchorPane mainPane;

    @FXML
    void showBlockTab(MouseEvent event) {
        mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            mainPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/views/blockreport.fxml")));
        } catch (IOException ex) {
            
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }
    
     @FXML
    void showPaymentsTab(MouseEvent event) {
        mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            dynamicAnchorPane =(AnchorPane)FXMLLoader.load(getClass().getResource("/views/registerpayment.fxml"));
            dynamicAnchorPane.setPrefSize(mainPane.getHeight(),mainPane.getWidth());
            mainPane.getChildren().setAll(dynamicAnchorPane);
        } catch (IOException ex) {
            
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }

      @FXML
    void showRoomsTab(MouseEvent event) {
         mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            mainPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/views/updateRoom.fxml")));
        } catch (IOException ex) {
            
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }
    
   @FXML
    void showTenantsTab(MouseEvent event) {
         mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            mainPane.getChildren().setAll((AnchorPane)FXMLLoader.load(getClass().getResource("/views/registerTenant.fxml")));
        } catch (IOException ex) {
            
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert paymentsTab != null : "fx:id=\"paymentsTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert tenantsTab != null : "fx:id=\"tenantsTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert roomsTab != null : "fx:id=\"roomsTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert blocksTab != null : "fx:id=\"blocksTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'dashboard.fxml'.";

    }
    
}
