/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Tenant;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EditTenantController implements Initializable{

    ObservableList countriesList,idTypeList ;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="fNameTxt"
    private TextField fNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="lastNameTxt"
    private TextField lastNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="countriesCombo"
    private ComboBox<String> countriesCombo; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberTxt"
    private TextField phoneNumberTxt; // Value injected by FXMLLoader

    @FXML // fx:id="idTypeCombo"
    private ComboBox<String> idTypeCombo; // Value injected by FXMLLoader

    @FXML // fx:id="idNumberTxt"
    private TextField idNumberTxt; // Value injected by FXMLLoader

    @FXML // fx:id="statusSingleRadio"
    private RadioButton statusSingleRadio; // Value injected by FXMLLoader

    @FXML // fx:id="statusMarriedRadio"
    private RadioButton statusMarriedRadio; // Value injected by FXMLLoader

    @FXML // fx:id="noFamMembersTxt"
    private TextField noFamMembersTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokNameTxt"
    private TextField nokNameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokContactTxt"
    private TextField nokContactTxt; // Value injected by FXMLLoader

    @FXML // fx:id="dobDatePicker"
    private DatePicker dobDatePicker; // Value injected by FXMLLoader
    //tenant to edit
    Tenant tenant;
    String []paymentOtions ={"Cash","Airtel Monny","MTN Money","Bank"};
    String [] idTypes = {"National ID", "Driving Permit","Passport"};
    private final ToggleGroup statusGroup = new ToggleGroup();
    TenantProfileController parentController;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
   
      idTypeList =FXCollections.observableArrayList(idTypes);
      idTypeCombo.setItems(idTypeList);
      idTypeCombo.setEditable(true);
      idTypeCombo.setValue(idTypes[0]);
      statusSingleRadio.setToggleGroup(statusGroup);
      statusMarriedRadio.setToggleGroup(statusGroup);
    }
    
    public void initTenant(Tenant t, TenantProfileController parentController){
        this.parentController =parentController;//used to reload the profile page after editing
        this.tenant = t;
        fNameTxt.setText(t.getFirstName());
        lastNameTxt.setText(t.getLastName());
        countriesCombo.setValue(t.getNationality());
        phoneNumberTxt.setText(t.getPhoneNumber());
        idTypeCombo.setValue(t.getIdType());
        idNumberTxt.setText(t.getIdNumber());
        if(t.getMaritalStatus().equalsIgnoreCase(statusSingleRadio.getText())){
            statusSingleRadio.setSelected(true);
        }else{
            statusMarriedRadio.setSelected(true);
        }
        noFamMembersTxt.setText(t.getNumOfFamMembers()+"");
        nokNameTxt.setText(t.getNokName());
        nokContactTxt.setText(t.getNokContack());
        dobDatePicker.setValue(LocalDate.parse(t.getDateOfBirth()));
       
    }
    
    
    @FXML
    void closeWindow(MouseEvent event) {
        Stage thisStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        thisStage.close();
    }
    

    @FXML
    void saveTenant(MouseEvent event) {
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
        }else{            
            String date="";
            try{
                date= dobDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }catch(NullPointerException ex){
                dobDatePicker.requestFocus();
            }
            String maritalStatus=((RadioButton)statusGroup.getSelectedToggle()).getText();
             tenant.setFirstName(lastNameTxt.getText());
             tenant.setLastName(fNameTxt.getText());
             tenant.setDateOfBirth(date);
             tenant.setNationality(countriesCombo.getValue());
             tenant.setPhoneNumber(phoneNumberTxt.getText());
             tenant.setIdType(idTypeCombo.getValue());
             tenant.setIdNumber(idNumberTxt.getText());
             tenant.setMaritalStatus(maritalStatus);
             tenant.setNumOfFamMembers(Integer.parseInt(noFamMembersTxt.getText()));
             tenant.setNokName(nokNameTxt.getText());
             tenant.setNokContack(nokContactTxt.getText());
             if(tenant.update()){
                 parentController.initTenant(tenant);
                 closeWindow(event);
             }
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert fNameTxt != null : "fx:id=\"fNameTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert lastNameTxt != null : "fx:id=\"lastNameTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert countriesCombo != null : "fx:id=\"countriesCombo\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert phoneNumberTxt != null : "fx:id=\"phoneNumberTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert idTypeCombo != null : "fx:id=\"idTypeCombo\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert idNumberTxt != null : "fx:id=\"idNumberTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert statusSingleRadio != null : "fx:id=\"statusSingleRadio\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert statusMarriedRadio != null : "fx:id=\"statusMarriedRadio\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert noFamMembersTxt != null : "fx:id=\"noFamMembersTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert nokNameTxt != null : "fx:id=\"nokNameTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert nokContactTxt != null : "fx:id=\"nokContactTxt\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
        assert dobDatePicker != null : "fx:id=\"dobDatePicker\" was not injected: check your FXML file 'editTenant.xml.fxml'.";
    }

    
}
