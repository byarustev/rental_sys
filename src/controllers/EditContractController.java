/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Block;
import GeneralClasses.House;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.ReloadableController;
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robert
 */
public class EditContractController implements Initializable {
    ObservableList blocksList,rentalsList;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="blockCombo"
    private ComboBox<Block> blockCombo; // Value injected by FXMLLoader

    @FXML // fx:id="rentalsCombo"
    private ComboBox<House> rentalsCombo; // Value injected by FXMLLoader

    @FXML // fx:id="monthlyFeeTxt"
    private TextField monthlyFeeTxt; // Value injected by FXMLLoader

    @FXML // fx:id="startDatePicker"
    private DatePicker startDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="actionsOptionBox"
    private VBox actionsOptionBox; // Value injected by FXMLLoader

    @FXML // fx:id="changeRoomRadio"
    private RadioButton changeRoomRadio; // Value injected by FXMLLoader

    @FXML // fx:id="terminateContractRadio"
    private RadioButton terminateContractRadio; // Value injected by FXMLLoader

    @FXML // fx:id="terminateButton"
    private Button terminateButton; // Value injected by FXMLLoader
    private HouseRentalContract contract;
    ToggleGroup actionToggleGroup;
    private ReloadableController parentController;
    String tenantId;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blocksList= FXCollections.observableArrayList(DatabaseHandler.getInstance().getBlocks());
      blockCombo.setItems(blocksList);
      rentalsList = FXCollections.observableArrayList();
      rentalsCombo.setItems(rentalsList);
      
      blockCombo.valueProperty().addListener(new ChangeListener(){
           @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              changeRentals();
          }
      });
      rentalsCombo.valueProperty().addListener(new ChangeListener(){
           @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              changeRoomRadio.setSelected(true);
              try{
                monthlyFeeTxt.setText(rentalsCombo.getValue().getMonthlyAmount()+"");
              }catch(Exception e){
              }
          }
      });
      actionToggleGroup = new ToggleGroup();
      changeRoomRadio.setToggleGroup(actionToggleGroup);
      terminateContractRadio.setToggleGroup(actionToggleGroup);
    }    

    public void initContract(Tenant tenant, ReloadableController parentController){
        this.contract =tenant.getHouseContract();
        this.tenantId=tenant.getTenantId();
        if(this.contract != null){
            this.parentController = parentController;
            blockCombo.setValue(this.contract.getCurrentHouse().getBlock());
            rentalsCombo.setValue(this.contract.getCurrentHouse());
            monthlyFeeTxt.setText(this.contract.getAgreedMonthlyAmount()+"");
            startDatePicker.setValue(LocalDate.parse(this.contract.getStartDate()));
        }
        else{
            actionsOptionBox.setVisible(false);
            terminateContractRadio.setVisible(false);
        }
   }
    
    
    
    void changeRentals(){
        try{
            Block e = blockCombo.getValue();
            rentalsList.clear();
            rentalsList.setAll(e.getHousesList(true));
            if(rentalsList.isEmpty()){
               rentalsCombo.setDisable(true); 
            }
            else{
                 rentalsCombo.setValue((House)rentalsList.get(0));
                 rentalsCombo.setDisable(false);
            }
       }catch(NullPointerException e){
           System.out.print(e.getMessage());
       }
    }

    @FXML
    void clearForm(MouseEvent event) {
         Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
         thisStage.close();
    }

    @FXML
    void saveTenant(MouseEvent event) {
       if(blockCombo.getValue()==null){
           blockCombo.requestFocus();
       }else if(rentalsCombo.getValue()==null){
           rentalsCombo.requestFocus();
       }else if(monthlyFeeTxt.getText().isEmpty()){
           monthlyFeeTxt.requestFocus();
       }else if(startDatePicker.getValue()==null){
           startDatePicker.requestFocus();
       }else{
           Double agreedAmount = Double.parseDouble(this.monthlyFeeTxt.getText());
           DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
           String startDate = startDatePicker.getValue().format(dtf);
           House newHouse = rentalsCombo.getValue();
           if(this.contract==null){
            HouseRentalContract contract = new HouseRentalContract(startDate, tenantId,newHouse.getHouseId(),Double.parseDouble(monthlyFeeTxt.getText()));
            String contractId=contract.saveContract();
            if(contractId != null){
                    Alert alert = new Alert(Alert.AlertType.NONE, "New Contract Created",ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setHeaderText(null);
                    alert.setTitle("Success");
                    alert.show();
                    clearForm(event);
                    parentController.reload();
               }
           }
           else if(!newHouse.getHouseId().equals(this.contract.getAssociatedHouse())){
               String contractId=null;
               if(changeRoomRadio.isSelected()){
                   contractId=DatabaseHandler.getInstance().updateContract(false,this.contract.getContractId(),newHouse.getHouseId(),this.contract.getAssociatedHouse(),
                           this.contract.getAssociatedTenant().getTenantId(),agreedAmount,startDate);
               }
               else if(terminateContractRadio.isSelected()){
                   contractId=DatabaseHandler.getInstance().updateContract(true,this.contract.getContractId(),newHouse.getHouseId(),this.contract.getAssociatedHouse(),
                           this.contract.getAssociatedTenant().getTenantId(),agreedAmount,startDate);
               }
               if(contractId != null){
                    Alert alert = new Alert(Alert.AlertType.NONE, "Rental Contract Updated",ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setHeaderText(null);
                    alert.setTitle("Success");
                    alert.show();
                    clearForm(event);
                    parentController.reload();
               }
               
           }
       }
    }

    @FXML
    void startTerminatePrompt(MouseEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to end this contract? You wont be able to reverse this action", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
        alert.showAndWait();
        if (alert.getResult() == ButtonType.YES) {
           if(this.contract.terminate()){
                Alert newalert = new Alert(Alert.AlertType.NONE, "Rental Contract Terminated",ButtonType.OK);
                newalert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                newalert.setHeaderText(null);
                newalert.setTitle("Terminated");
                newalert.show();
                clearForm(event);
                parentController.reload();
            }
        }
    }

     @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'editContract.fxml'.";
        assert rentalsCombo != null : "fx:id=\"rentalsCombo\" was not injected: check your FXML file 'editContract.fxml'.";
        assert monthlyFeeTxt != null : "fx:id=\"monthlyFeeTxt\" was not injected: check your FXML file 'editContract.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'editContract.fxml'.";
        assert actionsOptionBox != null : "fx:id=\"actionsOptionBox\" was not injected: check your FXML file 'editContract.fxml'.";
        assert changeRoomRadio != null : "fx:id=\"changeRoomRadio\" was not injected: check your FXML file 'editContract.fxml'.";
        assert terminateContractRadio != null : "fx:id=\"terminateContractRadio\" was not injected: check your FXML file 'editContract.fxml'.";
        assert terminateButton != null : "fx:id=\"terminateButton\" was not injected: check your FXML file 'editContract.fxml'.";

    }
}



