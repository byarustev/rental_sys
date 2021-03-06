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
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robert
 */
public class NewHouseController implements Initializable {

    private final ObservableList<Block> blocksList = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blocksList.addAll(DatabaseHandler.getInstance().getBlocks());
        blocksCombo.setItems(blocksList);
        // TODO
    }
    
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="contentBox"
    private VBox contentBox; // Value injected by FXMLLoader

    @FXML // fx:id="blockNameTxt"
    private TextField houseNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="numUnitsTxt"
    private TextField numUnitsTxt; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtn"
    private Button saveBtn; // Value injected by FXMLLoader

    @FXML // fx:id="blocksCombo"
    private ComboBox<Block> blocksCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="monthlyAmount"
    private TextField monthlyAmount; // Value injected by FXMLLoader
    
       @FXML // fx:id="umemeMeterNumberTxt"
    private TextField umemeMeterNumberTxt; // Value injected by FXMLLoader

    @FXML // fx:id="waterMeterNumber"
    private TextField waterMeterNumberTxt; // Value injected by FXMLLoader

    
    @FXML
    void saveHouse(MouseEvent event) {
         House house = this.generateHouse();
         
         if( house != null && blocksCombo.getValue() != null){
             house.setBlockId(blocksCombo.getValue().getDatabaseId());
             if(house.save()){
                Alert alert = new Alert(Alert.AlertType.NONE, "House added successfully",ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.setHeaderText(null);
                alert.setTitle("Success");
                alert.show();
                closeWindow(event);
             }
         }
         
    }
    public House generateHouse(){
            if(this.houseNameTxt.getCharacters().length()==0){
                 this.houseNameTxt.requestFocus();
                 return null;
             }
             else if(this.numUnitsTxt.getCharacters().length()==0){
                 this.numUnitsTxt.requestFocus();
                  return null;
             }
            return new House(this.houseNameTxt.getText(),this.houseNameTxt.getText(),Integer.parseInt(this.numUnitsTxt.getText()),
                    Double.parseDouble(monthlyAmount.getText()),null,umemeMeterNumberTxt.getText(),waterMeterNumberTxt.getText());
        }
    
    @FXML
    void closeWindow(MouseEvent event) {
        Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void updateRentalUnitsTable(MouseEvent event) {

    }

     @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert contentBox != null : "fx:id=\"contentBox\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert houseNameTxt != null : "fx:id=\"houseNameTxt\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert blocksCombo != null : "fx:id=\"blocksCombo\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert numUnitsTxt != null : "fx:id=\"numUnitsTxt\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert monthlyAmount != null : "fx:id=\"monthlyAmount\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert umemeMeterNumberTxt != null : "fx:id=\"umemeMeterNumberTxt\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert waterMeterNumberTxt != null : "fx:id=\"waterMeterNumber\" was not injected: check your FXML file 'addHouse.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'addHouse.fxml'.";

    }
    
    public void initBlock(Block block){
        this.blocksCombo.setValue(block);
    }
    
}
