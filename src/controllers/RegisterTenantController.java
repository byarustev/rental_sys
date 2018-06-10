/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Block;
import GeneralClasses.House;
import database.DatabaseHandler;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class RegisterTenantController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      blocksList= FXCollections.observableArrayList(DatabaseHandler.getInstance().getBlocks());
      blockCombo.setItems(blocksList);
      rentalsList = FXCollections.observableArrayList();
      rentalsCombo.setItems(rentalsList);
      rentalsCombo.setDisable(true);
    }    
    
    ObservableList blocksList,rentalsList; 
     @FXML // fx:id="blockCombo"
    private ComboBox<Block> blockCombo; // Value injected by FXMLLoader
     
    @FXML // fx:id="rentalsCombo"
    private ComboBox<House> rentalsCombo; // Value injected by FXMLLoader


    @FXML
    void changeRentals(MouseEvent event) {
       try{
            Block e = blockCombo.getValue();
            rentalsList.clear();
            rentalsList.setAll(e.getHousesList());
            if(rentalsList.isEmpty()){
               rentalsCombo.setDisable(true); 
            }
            else{
                 rentalsCombo.setDisable(false);
            }
       }catch(NullPointerException e){
           System.out.print(e.getMessage());
       }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert rentalsCombo != null : "fx:id=\"rentalsCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";

    }
}
