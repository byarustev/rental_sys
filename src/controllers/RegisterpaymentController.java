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
import GeneralClasses.RentalContract;
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class RegisterpaymentController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ObservableList blocksList,rentalsList,modesOfPaymentList;
        
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
    
    private String selectedContractId,selectedTenantId;

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
      
      blockCombo.valueProperty().addListener(new ChangeListener(){
           @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              changeRentals();
          }
      });
      String []paymentOtions ={"Cash","Airtel Money","MTN Money","Bank"};
      modesOfPaymentList = FXCollections.observableArrayList(paymentOtions);
      paymentMethodCombo.setItems(modesOfPaymentList);
      paymentMethodCombo.setValue(paymentOtions[0]);
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
        Payment payment = new Payment(date, Double.parseDouble(amountPaidTxt.getText()),selectedContractId, receivedBy, selectedTenantId,paymentMethodCombo.getValue(), referenceNumber);
        String id=payment.savePayment();
        if(id != null){
            Alert alert = new Alert(AlertType.NONE, "Payment Saved",ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText(null);
            alert.setTitle("Success");
            alert.show();
            resetFields();
        }
            
    }
    
     @FXML
    void clearForm(MouseEvent event) {
        resetFields();
    }

    void resetFields(){
        paymentDatePicker.setValue(null);
        amountPaidTxt.setText("");
        blockCombo.setValue(null);
        rentalCombo.setValue(null);
        tenantNameTxt.setText("");
        paymentMethodCombo.setValue(null);
        receivedByTxt.setText("");
        referenceNumberTxt.setText("");
    }
    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert paymentDatePicker != null : "fx:id=\"paymentDateTxt\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert amountPaidTxt != null : "fx:id=\"amountPaidTxt\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert rentalCombo != null : "fx:id=\"rentalCombo\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert tenantNameTxt != null : "fx:id=\"tenantNameTxt\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert paymentMethodCombo != null : "fx:id=\"paymentMethodCombo\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert receivedByTxt != null : "fx:id=\"receivedByTxt\" was not injected: check your FXML file 'registerpayment.fxml'.";
        assert referenceNumberTxt != null : "fx:id=\"referenceNumberTxt\" was not injected: check your FXML file 'registerpayment.fxml'.";
    }
   
}
