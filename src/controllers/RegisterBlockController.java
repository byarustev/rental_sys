/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import database.DatabaseHandler;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class RegisterBlockController implements Initializable {

    /**
     * Initializes the controller class.
     */
    DatabaseHandler handler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        TableColumn column1 = new TableColumn("Rental Unit No");
        TableColumn column2 = new TableColumn("Rental Name");
        TableColumn column3 = new TableColumn("No of Rooms");
        PropertyValueFactory<RentalUnitTableRow, TextField> rentalNameFactory = new PropertyValueFactory("rentalName");
        PropertyValueFactory<RentalUnitTableRow, TextField> unitNoFactory = new PropertyValueFactory("unitNo");
        PropertyValueFactory<RentalUnitTableRow, TextField> rentalNumOfUnitsFactory = new PropertyValueFactory("rentalNumOfUnits");
        column1.setCellValueFactory(unitNoFactory);
        column2.setCellValueFactory(rentalNameFactory);
        column3.setCellValueFactory(rentalNumOfUnitsFactory);
        column1.setMinWidth(150);
        column2.setMinWidth(150);
        column3.setMinWidth(150);
        rentalUnitsTable.getColumns().clear();
        rentalUnitsTable.setItems(rentalUnitsList);
        rentalUnitsTable.getColumns().addAll(column1,column2,column3);
    }    
    
    
    @FXML
    private TextField blockNameTxt;

    @FXML
    private TextField blockLocationTxt;

    @FXML
    private TextField numUnitsTxt;
    
    @FXML
    private Label submitButton;

    @FXML
    private Button saveBtn;
    @FXML
    private TableView<RentalUnitTableRow> rentalUnitsTable;
    private ObservableList<RentalUnitTableRow> rentalUnitsList = FXCollections.observableArrayList();
    @FXML
    void submitEntry(MouseEvent event) {
        
        
    }

    
     @FXML
    void saveBlock(MouseEvent event) {
        //validate input length
        ArrayList <RentalUnit> housesList = new ArrayList();
         if(blockNameTxt.getCharacters().length()==0){
             blockNameTxt.requestFocus();
             return;
         }
         else if(blockLocationTxt.getCharacters().length()==0){
             blockLocationTxt.requestFocus();
             return;
         }
         else if(Integer.parseInt(numUnitsTxt.getCharacters().toString())<1){
             numUnitsTxt.requestFocus();
             return;
         }
         for(RentalUnitTableRow x:rentalUnitsList){
             if(x.getRentalName().getCharacters().length()==0){
                 x.getRentalName().requestFocus();
                 break;
             }
             else if(x.getRentalNumOfUnits().getCharacters().length()==0){
                 x.getRentalNumOfUnits().requestFocus();
                  break;
             }
             housesList.add(new RentalUnit(x.getUnitNo(),x.getRentalName().getText(),x.getRentalNumOfUnits().getText()));
         }
         
         System.out.println("NAME: "+blockNameTxt.getCharacters()+"\nLocation "+blockLocationTxt.toString()+"\n Rentals"+numUnitsTxt.toString());
         DatabaseHandler.getInstance().insertBlock(blockNameTxt.getCharacters().toString(), blockLocationTxt.getCharacters().toString(), Integer.parseInt(numUnitsTxt.getCharacters().toString()),housesList);
         
        
    }

    @FXML
    void updateRentalUnitsTable(MouseEvent event) {
        try{
                int count =Integer.parseInt(numUnitsTxt.getCharacters().toString());
                int currentUnits = rentalUnitsList.size();
                if(count>currentUnits){
                    //should add units
                    for(int i=currentUnits; i<count; i++){
                        rentalUnitsList.add(new RentalUnitTableRow((i+1)+""));
                    }
                }
                else if(count<currentUnits){
                    for(int i=currentUnits; i>count; i--){
                        rentalUnitsList.remove(i-1);
                    }
                }        
        }
        catch(Exception e){
             System.out.println(e.getMessage());
        }
                 System.out.println("I was called");
    }
        

    @FXML
    void initialize() {
        assert blockNameTxt != null : "fx:id=\"blockNameTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert blockLocationTxt != null : "fx:id=\"blockLocationTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert numUnitsTxt != null : "fx:id=\"numUnitsTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert rentalUnitsTable != null : "fx:id=\"rentalUnitsTable\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'registerBlock.fxml'.";
    }
    
    public static class RentalUnitTableRow{
       private TextField rentalName, rentalNumOfUnits;
       private String unitNo;

        public String getUnitNo() {
            return unitNo;
        }

        public void setUnitNo(String unitNo) {
            this.unitNo = unitNo;
        }
        public TextField getRentalName() {
            return rentalName;
        }

        public void setRentalName(TextField rentalName) {
            this.rentalName = rentalName;
        }

        public TextField getRentalNumOfUnits() {
            return rentalNumOfUnits;
        }

        public void setRentalNumOfUnits(TextField rentalNumOfUnits) {
            this.rentalNumOfUnits = rentalNumOfUnits;
        }
       public RentalUnitTableRow(String unitNo){
            this.setUnitNo(unitNo);
            this.setRentalName(new TextField());
            this.setRentalNumOfUnits(new TextField());
       }
    }
    
    
    
}

