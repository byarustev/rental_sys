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
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class BlocksController implements Initializable {

    /**
     * Initializes the controller class.
     */
    DatabaseHandler handler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contentBox.maxHeightProperty().bind(contentBox.heightProperty());
        TableColumn column1 = new TableColumn("Rental Unit No");
        TableColumn column2 = new TableColumn("Rental Name");
        TableColumn column3 = new TableColumn("No of Rooms");
        TableColumn column4 = new TableColumn("Monthly Amount");
        PropertyValueFactory<RentalUnitTableRow, TextField> rentalNameFactory = new PropertyValueFactory("rentalName");
        PropertyValueFactory<RentalUnitTableRow, TextField> unitNoFactory = new PropertyValueFactory("unitNo");
        PropertyValueFactory<RentalUnitTableRow, TextField> rentalNumOfUnitsFactory = new PropertyValueFactory("rentalNumOfUnits");
        PropertyValueFactory<RentalUnitTableRow, TextField> monthlyAmountFactory = new PropertyValueFactory("monthlyAmount");
        column1.setCellValueFactory(unitNoFactory);
        column2.setCellValueFactory(rentalNameFactory);
        column3.setCellValueFactory(rentalNumOfUnitsFactory);
        column4.setCellValueFactory(monthlyAmountFactory);
        column1.setMinWidth(150);
        column2.setMinWidth(150);
        column3.setMinWidth(150);
        column4.setMinWidth(150);
        rentalUnitsTable.getColumns().clear();
        rentalUnitsTable.setItems(rentalUnitsList);
        rentalUnitsTable.getColumns().addAll(column1,column2,column3,column4);
        
        //initialize Find Blocks Pane
        TableColumn blockName = new TableColumn("Name");
        TableColumn blockLocation = new TableColumn("Location");
        TableColumn blockNumRentals = new TableColumn("Number of Rentals");
        TableColumn blockAvailableRentals = new TableColumn("Available Rentals");
        
        PropertyValueFactory<Block, String> blockNameFactory = new PropertyValueFactory("name");
        PropertyValueFactory<Block, String> blockNameLocation = new PropertyValueFactory("location");
        PropertyValueFactory<Block, Integer> blockNumRentalsFactory = new PropertyValueFactory("numberOfRentals");
        PropertyValueFactory<Block, Integer> blockRentalsAvailFactory = new PropertyValueFactory("numberOfAvailableRentals");
        
        blockName.setCellValueFactory(blockNameFactory);
        blockLocation.setCellValueFactory(blockNameLocation);
        blockNumRentals.setCellValueFactory(blockNumRentalsFactory);
        blockAvailableRentals.setCellValueFactory(blockRentalsAvailFactory);
        blockName.setMinWidth(150);
        blockLocation.setMinWidth(150);
        blockNumRentals.setMinWidth(150);
        blockAvailableRentals.setMinWidth(150);
        
        blocksDisplayTable.getColumns().clear();
       
        blocksDisplayTable.getColumns().addAll(blockName,blockLocation,blockNumRentals,blockAvailableRentals);
        filteredBlocksList.addAll(DatabaseHandler.getInstance().getBlocks());
        blocksDisplayTable.setItems(filteredBlocksList);
        blocksList.setAll(DatabaseHandler.getInstance().getBlocks());
        idSearchTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                
                filteredBlocksList.setAll(blocksList.filtered(new Predicate<Block>() {
                    @Override
                    public boolean test(Block p) {
                        return p.toString().contains(newValue.toString());
                    }
                }));
            }
        });

        //blocksList
    }    
    
    @FXML // fx:id="blocksDisplayTable"
    private TableView<Block> blocksDisplayTable; // Value injected by FXMLLoader

    
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
    @FXML // fx:id="idSearchTxt"
    private TextField idSearchTxt; // Value injected by FXMLLoader
    @FXML
    private TableView<RentalUnitTableRow> rentalUnitsTable;
    private ObservableList<RentalUnitTableRow> rentalUnitsList = FXCollections.observableArrayList();
   
    private ObservableList<Block> filteredBlocksList = FXCollections.observableArrayList();
    private ObservableList<Block> blocksList = FXCollections.observableArrayList();
    @FXML // fx:id="contentBox"
    private VBox contentBox; // Value injected by FXMLLoader

      
    @FXML
    void submitEntry(MouseEvent event) {
        
        
    }

    
     @FXML
    void saveBlock(MouseEvent event) {
        //validate input length
        ArrayList <House> housesList = new ArrayList();
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
             House y = x.generateHouse();
             if(y==null){
                 return;
             }
             housesList.add(y);
         }
         
         System.out.println("NAME: "+blockNameTxt.getCharacters()+"\nLocation "+blockLocationTxt.toString()+"\n Rentals"+numUnitsTxt.toString());
         
         Block enteredBlock = new Block(blockNameTxt.getCharacters().toString(), blockLocationTxt.getCharacters().toString(), Integer.parseInt(numUnitsTxt.getCharacters().toString()),housesList);
         enteredBlock.save();
         
         
         //update the blocks table
          filteredBlocksList.clear();
          filteredBlocksList.addAll(DatabaseHandler.getInstance().getBlocks());
         
        
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
                    //remove Units
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
    void filterBlocks(InputMethodEvent event) {
       
    }
    

     @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert contentBox != null : "fx:id=\"contentBox\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert blockNameTxt != null : "fx:id=\"blockNameTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert blockLocationTxt != null : "fx:id=\"blockLocationTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert numUnitsTxt != null : "fx:id=\"numUnitsTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert rentalUnitsTable != null : "fx:id=\"rentalUnitsTable\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert idSearchTxt != null : "fx:id=\"idSearchTxt\" was not injected: check your FXML file 'registerBlock.fxml'.";
        assert blocksDisplayTable != null : "fx:id=\"blocksDisplayTable\" was not injected: check your FXML file 'registerBlock.fxml'.";

    }
    
    public static class RentalUnitTableRow{
       private TextField rentalName, rentalNumOfUnits, monthlyAmount;
       public RentalUnitTableRow(String unitNo){
            this.setUnitNo(unitNo);
            this.setRentalName(new TextField());
            this.setRentalNumOfUnits(new TextField());
            this.setMonthlyAmount(new TextField());
       }
        public TextField getMonthlyAmount() {
            return monthlyAmount;
        }

        public void setMonthlyAmount(TextField monthlyAmount) {
            this.monthlyAmount = monthlyAmount;
        }
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
            //this.rentalName.
        }

        public TextField getRentalNumOfUnits() {
            return rentalNumOfUnits;
        }

        public void setRentalNumOfUnits(TextField rentalNumOfUnits) {
            this.rentalNumOfUnits = rentalNumOfUnits;
        }
        
        public House generateHouse(){
            if(this.getRentalName().getCharacters().length()==0){
                 this.getRentalName().requestFocus();
                 return null;
             }
             else if(this.getRentalNumOfUnits().getCharacters().length()==0){
                 this.getRentalNumOfUnits().requestFocus();
                  return null;
             }
            return new House(this.getUnitNo(),this.getRentalName().getText(),Integer.parseInt(this.getRentalNumOfUnits().getText()),Double.parseDouble(monthlyAmount.getText()),null);
        }
    }
    
    
    
}
