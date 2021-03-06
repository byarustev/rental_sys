/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.BankStatement;
import GeneralClasses.Block;
import GeneralClasses.House;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.ReloadableController;
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class BlocksController implements Initializable, ReloadableController {

    /**
     * Initializes the controller class.
     */
    DatabaseHandler handler;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       initNewBlockForm();
       initBlocksTableView();
       initRoomsTableView();
        //blocksList
    }    
    
    public void initNewBlockForm(){
        contentBox.maxHeightProperty().bind(contentBox.heightProperty());
        TableColumn column1 = new TableColumn("#");
        TableColumn column2 = new TableColumn("Rental Name");
        TableColumn column3 = new TableColumn("# of Rooms");
        TableColumn column4 = new TableColumn("Monthly Fee");
        TableColumn column5 = new TableColumn("Umeme Meter Number");
        TableColumn column6 = new TableColumn("Water Meter Number");
        PropertyValueFactory<RentalUnitTableRow, TextField> rentalNameFactory = new PropertyValueFactory("rentalName");
        PropertyValueFactory<RentalUnitTableRow, TextField> unitNoFactory = new PropertyValueFactory("unitNo");
        PropertyValueFactory<RentalUnitTableRow, TextField> rentalNumOfUnitsFactory = new PropertyValueFactory("rentalNumOfUnits");
        PropertyValueFactory<RentalUnitTableRow, TextField> monthlyAmountFactory = new PropertyValueFactory("monthlyAmount");
        PropertyValueFactory<RentalUnitTableRow, TextField> umemeMeterNumberFactory = new PropertyValueFactory("umemeMeterNumber");
        PropertyValueFactory<RentalUnitTableRow, TextField> waterMeterNumberFactory = new PropertyValueFactory("waterMeterNumber");
        column1.setCellValueFactory(unitNoFactory);
        column2.setCellValueFactory(rentalNameFactory);
        column3.setCellValueFactory(rentalNumOfUnitsFactory);
        column4.setCellValueFactory(monthlyAmountFactory);
        column5.setCellValueFactory(umemeMeterNumberFactory);
        column6.setCellValueFactory(waterMeterNumberFactory);
        column1.setMinWidth(50);
        column2.setMinWidth(150);
        column3.setMinWidth(50);
        column4.setMinWidth(100);
        column5.setMinWidth(150);
        column6.setMinWidth(150);
        rentalUnitsTable.getColumns().clear();
        rentalUnitsTable.setItems(rentalUnitsList);
        rentalUnitsTable.getColumns().addAll(column1,column2,column3,column4,column5,column6);
        
    } 
    public void initBlocksTableView(){
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
        blocksDisplayTable.setPlaceholder(new Label("No Blocks found"));
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
        
        
    }
    
    public void initRoomsTableView(){
        blocksCombo.setItems(blocksList);
        TableColumn roomNumberCol = new TableColumn("Room");
        TableColumn monthlyFeeCol = new TableColumn("Monthly Fee");
        TableColumn occupationStateCol = new TableColumn("Occupied");
        TableColumn tenantCol = new TableColumn("Tenant");
        TableColumn umemeMeter = new TableColumn("UMEME");
        TableColumn waterMeter = new TableColumn("WATER");
        
        PropertyValueFactory <House, String> roomNumberFactory = new PropertyValueFactory("rentalName");
        PropertyValueFactory <House, Double> monthlyFeeFactory = new PropertyValueFactory("monthlyAmount");
        PropertyValueFactory <House, String> umemeMeterNumberFactory = new PropertyValueFactory("umemeMeterNumber");
        PropertyValueFactory <House, String> waterMeterNumberFactory = new PropertyValueFactory("waterMeterNumber");
        roomNumberCol.setCellValueFactory(roomNumberFactory);
        monthlyFeeCol.setCellValueFactory(monthlyFeeFactory);
        umemeMeter.setCellValueFactory(umemeMeterNumberFactory);
        waterMeter.setCellValueFactory(waterMeterNumberFactory);
        occupationStateCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<House, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<House, String> param) {
                if(((House)param.getValue()).isOccupied()){
                    return new ReadOnlyObjectWrapper("YES");
                }
                else{
                    return new ReadOnlyObjectWrapper("NO");
                }
            }
        });
        tenantCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<House, String>, ObservableValue<String>>(){
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<House, String> param) {
               HouseRentalContract x = ((House)param.getValue()).getAssociatedContract();
               if(x==null){
                   return  new ReadOnlyObjectWrapper("");
               }
                else{
                   try{
                        return new ReadOnlyObjectWrapper(x.getAssociatedTenant().getFullName());
                   }catch(Exception e){
                        return  new ReadOnlyObjectWrapper("");
                   }
               }
            }
        });
       rentalsTableView.getColumns().clear();
       rentalsTableView.getColumns().addAll(roomNumberCol,monthlyFeeCol,umemeMeter,waterMeter,occupationStateCol,tenantCol);
       rentalsTableView.setItems(housesList);
       blocksCombo.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               housesList.clear();
               housesList.addAll(blocksCombo.getValue().getHousesList());
            }
        });
      if(blocksList.size()>0){
           blocksCombo.setValue(blocksList.get(0));
      }
      
      rentalsTableView.setOnMouseClicked(new EventHandler(){
        @Override
        public void handle(Event event) {
            House selectedHouse = rentalsTableView.getSelectionModel().getSelectedItem();
            if(selectedHouse != null && ((MouseEvent)event).getClickCount() == 2 ){
                Parent root;
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/editHouse.fxml"));
                root = (Parent)fxmlLoader.load(); 
                EditHouseController controller = fxmlLoader.<EditHouseController>getController();
                controller.initBlock(selectedHouse, BlocksController.this);
                Stage tenantProfilestage = new Stage();
                tenantProfilestage.setTitle(selectedHouse.getRentalName());
                tenantProfilestage.setScene(new Scene(root));
                tenantProfilestage.initModality(Modality.WINDOW_MODAL);
                tenantProfilestage.initOwner(((Node)(event.getSource())).getScene().getWindow());
                tenantProfilestage.show();   
            }
        catch (IOException e) {
            e.printStackTrace();
        }
            }
        }
        
    });
      
      blocksDisplayTable.setOnMouseClicked(new EventHandler(){
        @Override
        public void handle(Event event) {
            Block selectedBlock = blocksDisplayTable.getSelectionModel().getSelectedItem();
            if(selectedBlock != null && ((MouseEvent)event).getClickCount() == 2 ){
                Parent root;
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/editBlock.fxml"));
                root = (Parent)fxmlLoader.load(); 
                EditBlockController controller = fxmlLoader.<EditBlockController>getController();
                controller.initBlock(selectedBlock, BlocksController.this);
                Stage tenantProfilestage = new Stage();
                tenantProfilestage.setTitle("Updating block "+selectedBlock.getName());
                tenantProfilestage.setScene(new Scene(root));
                tenantProfilestage.initModality(Modality.WINDOW_MODAL);
                tenantProfilestage.initOwner(((Node)(event.getSource())).getScene().getWindow());
                tenantProfilestage.show();   
            }
        catch (IOException e) {
            e.printStackTrace();
        }
            }
        }
        
    });
      
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
    private final ObservableList<RentalUnitTableRow> rentalUnitsList = FXCollections.observableArrayList();
  
    private final ObservableList<Block> filteredBlocksList = FXCollections.observableArrayList();
    private final ObservableList<Block> blocksList = FXCollections.observableArrayList();
     private final ObservableList<House> housesList = FXCollections.observableArrayList();
    @FXML // fx:id="contentBox"
    private VBox contentBox; // Value injected by FXMLLoader

      
    @FXML
    void submitEntry(MouseEvent event) {
        
        
    }

      @FXML
    private Button add;

    @FXML
    void addNewHouse(MouseEvent event) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/addHouse.fxml"));
            Parent root; 
        try {
            root = (Parent)fxmlLoader.load();
            NewHouseController controller = fxmlLoader.<NewHouseController>getController();
            controller.initBlock(blocksCombo.getValue());
            Stage newHouseStage = new Stage();
            newHouseStage.setTitle("New House");
            newHouseStage.setScene(new Scene(root));
            newHouseStage.initModality(Modality.WINDOW_MODAL);
            newHouseStage.initOwner(((Node)(event.getSource())).getScene().getWindow());
            newHouseStage.show();
        } catch (IOException ex) {
            Logger.getLogger(BlocksController.class.getName()).log(Level.SEVERE, null, ex);
        }
               
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
         if(enteredBlock.save()){
             Alert alert = new Alert(Alert.AlertType.NONE, "Block Saved Successfully",ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText(null);
            alert.setTitle("Success");
            alert.show();
            resetForm();
         }
         reload();
         
         //update the blocks table
          
         
        
    }

    @Override
    public void reload(){
        filteredBlocksList.clear();
        filteredBlocksList.addAll(DatabaseHandler.getInstance().getBlocks());
        blocksList.clear();
        blocksList.addAll(DatabaseHandler.getInstance().getBlocks());
    }
    public void resetForm(){
        rentalUnitsList.clear();
        blockNameTxt.setText("");
        blockLocationTxt.setText("");
        numUnitsTxt.setText("");
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
    
    @FXML
    private TableView<House> rentalsTableView;
    @FXML
    private ComboBox<Block> blocksCombo;

    

    @FXML
    void initialize() {
        assert idSearchTxt != null : "fx:id=\"idSearchTxt\" was not injected: check your FXML file 'blocks.fxml'.";
        assert blocksDisplayTable != null : "fx:id=\"blocksDisplayTable\" was not injected: check your FXML file 'blocks.fxml'.";
        assert contentBox != null : "fx:id=\"contentBox\" was not injected: check your FXML file 'blocks.fxml'.";
        assert blockNameTxt != null : "fx:id=\"blockNameTxt\" was not injected: check your FXML file 'blocks.fxml'.";
        assert blockLocationTxt != null : "fx:id=\"blockLocationTxt\" was not injected: check your FXML file 'blocks.fxml'.";
        assert numUnitsTxt != null : "fx:id=\"numUnitsTxt\" was not injected: check your FXML file 'blocks.fxml'.";
        assert rentalUnitsTable != null : "fx:id=\"rentalUnitsTable\" was not injected: check your FXML file 'blocks.fxml'.";
        assert saveBtn != null : "fx:id=\"saveBtn\" was not injected: check your FXML file 'blocks.fxml'.";
        assert rentalsTableView != null : "fx:id=\"rentalsTableView\" was not injected: check your FXML file 'blocks.fxml'.";
        assert blocksCombo != null : "fx:id=\"blocksCombo\" was not injected: check your FXML file 'blocks.fxml'.";

    }
    
    public static class RentalUnitTableRow{
       private TextField rentalName, rentalNumOfUnits, monthlyAmount,umemeMeterNumber, waterMeterNumber;

       
       public RentalUnitTableRow(String unitNo){
            this.setUnitNo(unitNo);
            this.setRentalName(new TextField());
            this.setRentalNumOfUnits(new TextField());
            this.setMonthlyAmount(new TextField());
            this.setUmemeMeterNumber(new TextField());
            this.setWaterMeterNumber(new TextField());

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
            this.rentalNumOfUnits.setText("1");
        }
         public TextField getUmemeMeterNumber() {
            return umemeMeterNumber;
        }

        public void setUmemeMeterNumber(TextField umemeMeterNumber) {
            this.umemeMeterNumber = umemeMeterNumber;
        }

        public TextField getWaterMeterNumber() {
            return waterMeterNumber;
        }

        public void setWaterMeterNumber(TextField waterMeterNumber) {
            this.waterMeterNumber = waterMeterNumber;
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
            return new House(this.getUnitNo(),this.getRentalName().getText(),Integer.parseInt(this.getRentalNumOfUnits().getText()),Double.parseDouble(monthlyAmount.getText()),null, this.getUmemeMeterNumber().getText(),this.getWaterMeterNumber().getText());
        }
    }
    
    
    
}

