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
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.net.URL;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javax.swing.text.DateFormatter;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class RegisterTenantController implements Initializable {

    
    ObservableList blocksList,rentalsList,countriesList,idTypeList,modesOfPaymentList; 
     @FXML // fx:id="blockCombo"
    private ComboBox<Block> blockCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="idTypeCombo"
    private ComboBox<String> idTypeCombo; // Value injected by FXMLLoader
    @FXML // fx:id="rentalsCombo"
    private ComboBox<House> rentalsCombo; // Value injected by FXMLLoader

    @FXML // fx:id="countriesCombo"
    private ComboBox<String> countriesCombo; // Value injected by FXMLLoader
    
    @FXML // fx:id="fNameTxt"
    private TextField fNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTxt"
    private TextField lastNameTxt; // Value injected by FXMLLoader
    
    @FXML // fx:id="phoneNumberTxt"
    private TextField phoneNumberTxt; // Value injected by FXMLLoader
       @FXML // fx:id="idNumberTxt"
    private TextField idNumberTxt; // Value injected by FXMLLoader

    @FXML // fx:id="noFamMembersTxt"
    private TextField noFamMembersTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokNameTxt"
    private TextField nokNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokContactTxt"
    private TextField nokContactTxt; // Value injected by FXMLLoader
    @FXML // fx:id="depositTxt"
    private TextField depositTxt; // Value injected by FXMLLoader
    @FXML
    private TextField monthlyFeeTxt;
    @FXML // fx:id="receivedByTxt"
    private TextField receivedByTxt; // Value injected by FXMLLoader
     @FXML // fx:id="dobDatePicker"
    private DatePicker dobDatePicker; // Value injected by FXMLLoader
      @FXML // fx:id="statusSingleRadio"
    private RadioButton statusSingleRadio; // Value injected by FXMLLoader

    @FXML // fx:id="statusMarriedRadio"
    private RadioButton statusMarriedRadio; // Value injected by FXMLLoader
    
       @FXML // fx:id="paymentModeCombo"
    private ComboBox<String> paymentModeCombo; // Value injected by FXMLLoader

    @FXML // fx:id="referenceNumberTxt"
    private TextField referenceNumberTxt; // Value injected by FXMLLoader

    
    /**
     * Initializes the controller class.
     */
    private final ToggleGroup statusGroup = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      blocksList= FXCollections.observableArrayList(DatabaseHandler.getInstance().getBlocks());
      blockCombo.setItems(blocksList);
      rentalsList = FXCollections.observableArrayList();
      rentalsCombo.setItems(rentalsList);
      rentalsCombo.setDisable(true);
      //get a list of country codes from the Locale Library
      String [] countryCodes = Locale.getISOCountries();
      countriesList = FXCollections.observableArrayList();
      for (String countryCode : countryCodes){
            //pick the corresponding country names
            Locale locale = new Locale("", countryCode);
            String name = locale.getDisplayCountry();
            countriesList.add(name);
        }
     
      countriesCombo.setItems(countriesList);
      countriesCombo.setValue("Uganda");
      String [] idTypes = {"National ID", "Driving Permit","Passport"};
      idTypeList =FXCollections.observableArrayList(idTypes);
      idTypeCombo.setItems(idTypeList);
      idTypeCombo.setEditable(true);
      statusSingleRadio.setToggleGroup(statusGroup);
      statusSingleRadio.setSelected(true);
      statusMarriedRadio.setToggleGroup(statusGroup);
      rentalsCombo.valueProperty().addListener(new ChangeListener(){
          @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              try{
                     monthlyFeeTxt.setText(((House)newValue).getMonthlyAmount().toString());
              }catch(NullPointerException e){}
          }
      });
      blockCombo.valueProperty().addListener(new ChangeListener(){
           @Override
          public void changed(ObservableValue observable, Object oldValue, Object newValue) {
              changeRentals();
          }
      });
     
      String []paymentOtions ={"Cash","Airtel Moneny","MTN Money","Bank"};
      modesOfPaymentList = FXCollections.observableArrayList(paymentOtions);
      paymentModeCombo.setItems(modesOfPaymentList);
      paymentModeCombo.setValue(paymentOtions[0]);
    }    
    
    @FXML
    void changeRentals(MouseEvent event) {
       //changeRentals();
    }
    
    void changeRentals(){
        try{
            Block e = blockCombo.getValue();
            rentalsList.clear();
            rentalsList.setAll(e.getHousesList());
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
    void saveTenant(MouseEvent event) {
        lastNameTxt.setText("Kasumba"); phoneNumberTxt.setText("0752615075");fNameTxt.setText("Kasumba");idNumberTxt.setText("7892374HJF");noFamMembersTxt.setText("5");nokNameTxt.setText("Lubega");nokContactTxt.setText("07867542452");
        if(fNameTxt.getText().isEmpty()){
            fNameTxt.requestFocus();
        }else if(lastNameTxt.getText().isEmpty()){
            lastNameTxt.requestFocus();
        } else if(countriesCombo.getValue() ==null){
            countriesCombo.requestFocus();
        }else if(phoneNumberTxt.getText().isEmpty()){
            phoneNumberTxt.requestFocus();
        }else if(idTypeCombo.getValue()==null){
            idTypeCombo.requestFocus();
        }else if(idNumberTxt.getText().isEmpty()){
            idNumberTxt.requestFocus();
        }else if(noFamMembersTxt.getText().isEmpty()){
            noFamMembersTxt.requestFocus();
        }else if(nokNameTxt.getText().isEmpty()){
            nokNameTxt.requestFocus();
        }else if(nokContactTxt.getText().isEmpty()){
            nokContactTxt.requestFocus();
        }else if(blockCombo.getValue()== null){
            blockCombo.requestFocus();
        }else if(rentalsCombo.getValue() == null){
            rentalsCombo.requestFocus();
        }else if(dobDatePicker.getValue() ==null){
            dobDatePicker.requestFocus();
        } else if(monthlyFeeTxt.getText().isEmpty()){
            monthlyFeeTxt.requestFocus();
        } else if(depositTxt.getText().isEmpty() && !receivedByTxt.getText().isEmpty()){
            depositTxt.requestFocus();
        }else if(!depositTxt.getText().isEmpty() && receivedByTxt.getText().isEmpty()){
            receivedByTxt.requestFocus();
        }
        else{            
            String date="";
            try{
                date= dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }catch(NullPointerException ex){
                dobDatePicker.requestFocus();
            }
            String maritalStatus=((RadioButton)statusGroup.getSelectedToggle()).getText();
            Tenant tenant = new Tenant(lastNameTxt.getText(), fNameTxt.getText(),date, countriesCombo.getValue()
                    , phoneNumberTxt.getText(), idTypeCombo.getValue(),idNumberTxt.getText(), maritalStatus, Integer.parseInt(noFamMembersTxt.getText()), nokNameTxt.getText(), nokContactTxt.getText());
           // lastNameTxt.setText("Kasumba"); phoneNumberTxt.setText("0752615075");fNameTxt.setText("Kasumba");idNumberTxt.setText("7892374HJF");noFamMembersTxt.setText("5");nokNameTxt.setText("Lubega");nokContactTxt.setText("07867542452");
            String tenantId =tenant.save();
            
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime now  = LocalDateTime.now();
            String startDate = now.format(dtf);
            String houseId = rentalsCombo.getValue().getHouseId();
            //amountPaid,datePaid,receiptNumber,modeOfPayment,tenantID,contractID,receivedBy
            HouseRentalContract contract = new HouseRentalContract(startDate, tenantId,houseId,Double.parseDouble(monthlyFeeTxt.getText()));
            String contractId=contract.saveContract();
            if(contractId !=null && !depositTxt.getText().isEmpty()){
                Payment payment = new Payment(startDate, Double.parseDouble(depositTxt.getText()), contractId, receivedByTxt.getText(), tenantId, paymentModeCombo.getValue(),referenceNumberTxt.getText());
                payment.savePayment();
            }
            
            
            
        }
    }
  

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert fNameTxt != null : "fx:id=\"fNameTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert lastNameTxt != null : "fx:id=\"lastNameTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert countriesCombo != null : "fx:id=\"countriesCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert phoneNumberTxt != null : "fx:id=\"phoneNumberTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert idTypeCombo != null : "fx:id=\"idTypeCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert idNumberTxt != null : "fx:id=\"idNumberTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert statusSingleRadio != null : "fx:id=\"statusSingleRadio\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert statusMarriedRadio != null : "fx:id=\"statusMarriedRadio\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert noFamMembersTxt != null : "fx:id=\"noFamMembersTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert nokNameTxt != null : "fx:id=\"nokNameTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert nokContactTxt != null : "fx:id=\"nokContactTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert rentalsCombo != null : "fx:id=\"rentalsCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert monthlyFeeTxt != null : "fx:id=\"monthlyFeeTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert depositTxt != null : "fx:id=\"depositTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert receivedByTxt != null : "fx:id=\"receivedByTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert paymentModeCombo != null : "fx:id=\"paymentModeCombo\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert referenceNumberTxt != null : "fx:id=\"referenceNumberTxt\" was not injected: check your FXML file 'registerTenant.fxml'.";
        assert dobDatePicker != null : "fx:id=\"dobDatePicker\" was not injected: check your FXML file 'registerTenant.fxml'.";

    }
    
}
