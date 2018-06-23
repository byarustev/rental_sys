/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Block;
import GeneralClasses.House;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.Payment;
import GeneralClasses.ReloadableController;
import GeneralClasses.RentalContract;
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author robert
 */
public class EditPaymentController implements Initializable {
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="paymentDatePicker"
    private DatePicker paymentDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="amountPaidTxt"
    private TextField amountPaidTxt; // Value injected by FXMLLoader

    @FXML // fx:id="blockCombo"
    private ComboBox<Block> blockCombo; // Value injected by FXMLLoader

    @FXML // fx:id="rentalCombo"
    private ComboBox<House> rentalCombo; // Value injected by FXMLLoader

    @FXML // fx:id="tenantNameTxt"
    private TextField tenantNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="paymentMethodCombo"
    private ComboBox<String> paymentMethodCombo; // Value injected by FXMLLoader

    @FXML // fx:id="receivedByTxt"
    private TextField receivedByTxt; // Value injected by FXMLLoader

    @FXML // fx:id="referenceNumberTxt"
    private TextField referenceNumberTxt; // Value injected by FXMLLoader
    private Payment payment;
    ObservableList blocksList,rentalsList,modesOfPaymentList,allPaymentsList;
    private String selectedContractId,selectedTenantId;
    private ReloadableController parentController;
    @FXML
    void clearForm(MouseEvent event) {
        Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        thisStage.close();
    }

    @FXML
    void savePayment(MouseEvent event) {
         if(paymentDatePicker.getValue() ==null){
            paymentDatePicker.requestFocus();
        }
        else if(amountPaidTxt.getText().isEmpty()){
            amountPaidTxt.requestFocus();
        }else if(blockCombo.getValue()==null){
            blockCombo.requestFocus();
        }else if(rentalCombo.getValue()==null){
            rentalCombo.requestFocus();
        }else if(tenantNameTxt.getText().isEmpty()){
            tenantNameTxt.requestFocus();
        }
        else if(paymentMethodCombo.getValue() ==null){
            paymentMethodCombo.requestFocus();
        }
        
        String referenceNumber="", receivedBy ="";
        if(!receivedByTxt.getText().isEmpty()){
            receivedBy=receivedByTxt.getText();
        }
        
        if(!referenceNumberTxt.getText().isEmpty()){
            referenceNumber=referenceNumberTxt.getText();
        }
        String date="";
        try{
            date= paymentDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch(Exception ex){
            paymentDatePicker.requestFocus();
        }
        payment.setPaymentDate(date) ;
        payment.setPaymentAmount(Double.parseDouble(amountPaidTxt.getText()));
        payment.setRentalContractId(selectedContractId);
        payment.setReceivedBy(receivedBy);
        payment.setTenantId(selectedTenantId);
        payment.setModeOfPayment(paymentMethodCombo.getValue());
        payment.setReferenceNumber(referenceNumber);
       
        if(payment.update()){
            Alert alert = new Alert(Alert.AlertType.NONE, "Payment Updated",ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText(null);
            alert.setTitle("Success");
            alert.show();
            clearForm(event);
            parentController.reload();
        }
        else{
               Alert alert = new Alert(Alert.AlertType.WARNING, "Payment Not Updated",ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText(null);
            alert.setTitle("Failed");
            alert.show();
        }
    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        blocksList= FXCollections.observableArrayList(DatabaseHandler.getInstance().getBlocks());
        blockCombo.setItems(blocksList.filtered(new Predicate<Block>(){
            @Override
            public boolean test(Block t) {
                //remove blocks that have no rented rooms
                return t.getNumberOfAvailableRentals() != t.getNumberOfRentals();
            }

        }));
      rentalsList = FXCollections.observableArrayList();
      rentalCombo.setItems(rentalsList);
      rentalCombo.setDisable(true);
        String []paymentOtions ={"Cash","Airtel Money","MTN Money","Bank"};
      modesOfPaymentList = FXCollections.observableArrayList(paymentOtions);
      paymentMethodCombo.setItems(modesOfPaymentList);
       blockCombo.valueProperty().addListener(new ChangeListener(){
           @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              changeRentals();
          }
      });
      rentalCombo.valueProperty().addListener(new ChangeListener(){
           @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              House h = rentalCombo.getValue();
             
              try{
                  RentalContract x = DatabaseHandler.getInstance().getCurrentContract(null, h.getHouseId()) ;
                  Tenant t = x.getAssociatedTenant();
                  tenantNameTxt.setText(t.getLastName()+" "+t.getFirstName());
                  selectedContractId = ((HouseRentalContract)x).getContractId();
                  selectedTenantId = t.getTenantId();
              }
              catch(Exception e){
                  tenantNameTxt.setText("");
                   
                }
          }
      });
      
    }    
       void changeRentals(){
        try{
            Block e = blockCombo.getValue();
           
            ObservableList list = FXCollections.observableArrayList(e.getHousesList(false));
            
            rentalsList.setAll(list.filtered(new Predicate<House>(){
                @Override
                public boolean test(House t) {
                    return !t.isAvailable();
                }
            }));
            
          
            if(rentalsList.isEmpty()){
               rentalCombo.setDisable(true); 
            }
            else{
                 rentalCombo.setValue((House)rentalsList.get(0));
                 rentalCombo.setDisable(false);
            }
       }catch(NullPointerException e){
           System.out.print(e.getMessage());
       }
    }
       
    public void initPayment(Payment p, ReloadableController parentController){
        this.parentController = parentController;
        this.payment = p;
        paymentDatePicker.setValue(LocalDate.parse(p.getPaymentDate()));
        amountPaidTxt.setText(p.getPaymentAmount()+"");
        blockCombo.setValue(p.getAssociatedContract().getCurrentHouse().getBlock());
        paymentMethodCombo.setValue(p.getModeOfPayment());
        receivedByTxt.setText(p.getReceivedBy());
        referenceNumberTxt.setText(p.getReferenceNumber());
    }
    
     @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert paymentDatePicker != null : "fx:id=\"paymentDatePicker\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert amountPaidTxt != null : "fx:id=\"amountPaidTxt\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert rentalCombo != null : "fx:id=\"rentalCombo\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert tenantNameTxt != null : "fx:id=\"tenantNameTxt\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert paymentMethodCombo != null : "fx:id=\"paymentMethodCombo\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert receivedByTxt != null : "fx:id=\"receivedByTxt\" was not injected: check your FXML file 'editPayment.fxml'.";
        assert referenceNumberTxt != null : "fx:id=\"referenceNumberTxt\" was not injected: check your FXML file 'editPayment.fxml'.";

    }
}
