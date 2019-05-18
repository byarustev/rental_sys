/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Block;
import GeneralClasses.House;
import GeneralClasses.ReloadableController;
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
public class EditBlockController implements Initializable {

    
     @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="contentBox"
    private VBox contentBox; // Value injected by FXMLLoader

    @FXML // fx:id="blockNameTxt"
    private TextField blockNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="locationTxt"
    private TextField locationTxt; // Value injected by FXMLLoader

    @FXML // fx:id="saveBtn"
    private Button saveBtn; // Value injected by FXMLLoader
    private Block currentBlock;
    private ReloadableController parentController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    @FXML
    void closeWindow(MouseEvent event) {
        Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void saveBlock(MouseEvent event) {
        if(this.blockNameTxt.getCharacters().length()==0){
               this.blockNameTxt.requestFocus(); 
               return ;
        }
        else if(this.locationTxt.getCharacters().length()==0){
            this.locationTxt.requestFocus();
            return ;
        }
        this.currentBlock.setName(this.blockNameTxt.getText());
        this.currentBlock.setLocation(this.locationTxt.getText());
        if(this.currentBlock.save()){
                Alert alert = new Alert(Alert.AlertType.NONE, "House updated successfully",ButtonType.OK);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                alert.setHeaderText(null);
                alert.setTitle("Success");
                alert.show();
                this.parentController.reload();
                closeWindow(event);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR, "Block Update Failed",ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText(null);
            alert.setTitle("Failed");
            alert.show();
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert contentBox != null : "fx:id=\"contentBox\" was not injected: check your FXML file 'editBlock.fxml'.";
        assert blockNameTxt != null : "fx:id=\"blockNameTxt\" was not injected: check your FXML file 'editBlock.fxml'.";
        assert locationTxt != null : "fx:id=\"locationTxt\" was not injected: check your FXML file 'editBlock.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'editBlock.fxml'.";
    }

    public void initBlock(Block block, ReloadableController parent){
        this.parentController = parent;
        this.currentBlock=block;
        this.blockNameTxt.setText(block.getName());
        this.locationTxt.setText(block.getLocation());
    }
    
    
}
