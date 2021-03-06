/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.CurrentUser;
import java.io.IOException;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class DashboardController implements Initializable {

    private SplitPane dynamicSplitPane; 
    public DashboardController()  {
        //this.location = new URL("views/dashboard.fxml");
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       if(CurrentUser.getInstance()!=null){
           usernameLabel.setText(CurrentUser.getInstance().getFullName());
       }
       else{
           logout(null);
       }
        try {  
            mainPane.getChildren().setAll((SplitPane)FXMLLoader.load(getClass().getResource("/views/tenants.fxml")));
        } catch (IOException ex) {  
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    //@FXML // URL location of the FXML file that was given to the FXMLLoader
    //private URL location;

    @FXML // fx:id="paymentsTab"
    private Label paymentsTab; // Value injected by FXMLLoader

    @FXML
    private Label usernameLabel;
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
            mainPane.getChildren().setAll((SplitPane)FXMLLoader.load(getClass().getResource("/views/blocks.fxml")));
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }
    
     @FXML
    void showPaymentsTab(MouseEvent event) {
        mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            dynamicSplitPane =(SplitPane)FXMLLoader.load(getClass().getResource("/views/payments.fxml"));
            dynamicSplitPane.setPrefSize(mainPane.getHeight(),mainPane.getWidth());
            mainPane.getChildren().setAll(dynamicSplitPane);
        } catch (IOException ex) {
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }

   @FXML
    void showTenantsTab(MouseEvent event) {
         mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            mainPane.getChildren().setAll((SplitPane)FXMLLoader.load(getClass().getResource("/views/tenants.fxml")));
        } catch (IOException ex) {
            
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }
    
    @FXML
    void showStatementsTab(MouseEvent event) {
        mainPane.getChildren().clear();//remove any contents of the main pane
        try {
            mainPane.getChildren().setAll((SplitPane)FXMLLoader.load(getClass().getResource("/views/statements.fxml")));
        } catch (IOException ex) {
            
            Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("FAILED to load block report");
        }
    }
     @FXML
    void logout(MouseEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/login.fxml"));
                Parent root;
                try {
                    root = (Parent)fxmlLoader.load();
                    Stage loginStage = new Stage();
                        loginStage.setTitle("Login");
                        loginStage.setScene(new Scene(root));
                        loginStage.setResizable(false);
                        ((Node)mainPane).getScene().getWindow().hide();
                        loginStage.show(); 
                } catch (IOException ex) {
                    Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
                }
                
    }
      @FXML
    void initialize() {
        assert tenantsTab != null : "fx:id=\"tenantsTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert paymentsTab != null : "fx:id=\"paymentsTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert blocksTab != null : "fx:id=\"blocksTab\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert usernameLabel != null : "fx:id=\"usernameLabel\" was not injected: check your FXML file 'dashboard.fxml'.";
        assert mainPane != null : "fx:id=\"mainPane\" was not injected: check your FXML file 'dashboard.fxml'.";
    }
    
}
