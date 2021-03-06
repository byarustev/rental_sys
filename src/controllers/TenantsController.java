/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Block;
import GeneralClasses.House;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.NumberValidator;
import GeneralClasses.Payment;
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.function.Predicate;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class TenantsController implements Initializable {

    
    ObservableList blocksList,rentalsList,countriesList,idTypeList,modesOfPaymentList; 
    ObservableList tenantsFilteredList=FXCollections.observableArrayList();
    ObservableList tenantsList = FXCollections.observableArrayList();
    ObservableList tenantsOwedList = FXCollections.observableArrayList();
    ObservableList tenantsOwedFilteredList=FXCollections.observableArrayList();
    @FXML // fx:id="tenantsOwedSearhTxt"
    private TextField tenantsOwedSearhTxt; // Value injected by FXMLLoader

    @FXML // fx:id="blocksOwedFilterCombo"
    private ComboBox<Block> blocksOwedFilterCombo; // Value injected by FXMLLoader

    @FXML // fx:id="tenantsOwedTable"
    private TableView<Tenant> tenantsOwedTable; // Value injected by FXMLLoader

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
   

    @FXML // fx:id="nokDistrictTxt"
    private TextField nokDistrictTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokCountyTxt"
    private TextField nokCountyTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokSubCountyTx"
    private TextField nokSubCountyTx; // Value injected by FXMLLoader

    @FXML // fx:id="nokParishTxt"
    private TextField nokParishTxt; // Value injected by FXMLLoader

    @FXML // fx:id="nokVillageTxt"
    private TextField nokVillageTxt; // Value injected by FXMLLoader
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

        @FXML // fx:id="tenantsTable"
    private TableView<Tenant> tenantsTable; // Value injected by FXMLLoader
    
    @FXML // fx:id="tenantsSearhTxt"
    private TextField tenantsSearhTxt; // Value injected by FXMLLoader
    
    @FXML // fx:id="nokPlaceOfWorkTxt"
    private TextField nokPlaceOfWorkTxt; // Value injected by FXMLLoader
    
    @FXML // fx:id="advanceBeforeSystemUpgradeTxt"
    private TextField advanceBeforeSystemUpgradeTxt; // Value injected by FXMLLoader

    @FXML // fx:id="arrearsBeforeSystemUpgradeTxt"
    private TextField arrearsBeforeSystemUpgradeTxt; // Value injected by FXMLLoader
    
    @FXML // fx:id="startDatePicker"
    private DatePicker startDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="blocksCombo"
    private ComboBox<Block> blocksFilterCombo; // Value injected by FXMLLoader
    String []paymentOtions ={"Cash","Airtel Money","MTN Money","Bank","Cheque"};
     String [] idTypes = {"National ID", "Driving Permit","Passport"};
    /**
     * Initializes the controller class.
     */
    private final ToggleGroup statusGroup = new ToggleGroup();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      initializeAddTenantsForm();
      initializeAllTenantsTable();
      initializeTenantsOwedTable();
      this.reload();
      this.resetFields();
    }    
    public void initializeAddTenantsForm(){
        //lastNameTxt.setText("Kasumba"); phoneNumberTxt.setText("0752615075");fNameTxt.setText("Kasumba");idNumberTxt.setText("7892374HJF");noFamMembersTxt.setText("5");nokNameTxt.setText("Lubega");nokContactTxt.setText("07867542452");
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
      idTypeList =FXCollections.observableArrayList(idTypes);
      idTypeCombo.setItems(idTypeList);
      idTypeCombo.setEditable(true);
      idTypeCombo.setValue(idTypes[0]);
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
     
      
      modesOfPaymentList = FXCollections.observableArrayList(paymentOtions);
      paymentModeCombo.setItems(modesOfPaymentList);
      paymentModeCombo.setValue(paymentOtions[0]);
      addNumberValidators();
    }
    
    public void addNumberValidators(){
        monthlyFeeTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                NumberValidator.validateDouble(monthlyFeeTxt);
            }
        });
        depositTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                NumberValidator.validateDouble(depositTxt);
            }
        });
        noFamMembersTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                NumberValidator.validateInteger(noFamMembersTxt);
            }
        });
        advanceBeforeSystemUpgradeTxt.textProperty().addListener(new ChangeListener(){
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               NumberValidator.validateInteger(advanceBeforeSystemUpgradeTxt);
           }
        });
        arrearsBeforeSystemUpgradeTxt.textProperty().addListener(new ChangeListener(){
           @Override
           public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               NumberValidator.validateInteger(arrearsBeforeSystemUpgradeTxt);
           }
        });
    }
    public void initializeAllTenantsTable(){ 
    TableColumn name = new TableColumn("Name");
    TableColumn phoneNumber= new TableColumn("Phone Number");
    TableColumn blockName = new TableColumn("Block");
    TableColumn houseRented = new TableColumn("Rental Occupied");
    TableColumn balance = new TableColumn("Amount Owed");
    TableColumn blockLocation = new TableColumn("Location");
    name.setCellValueFactory(new Callback<CellDataFeatures<Tenant, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<Tenant, String> param) {
            try{
            return new ReadOnlyObjectWrapper(param.getValue().getLastName()+" "+param.getValue().getFirstName()) ;//()
                    }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("");
           }
                           }
    });
    phoneNumber.setCellValueFactory(new Callback<CellDataFeatures<Tenant, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<Tenant, String> param) {
            return new ReadOnlyObjectWrapper(param.getValue().getPhoneNumber());
        }
    });
    balance.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) {
            try{
                return new ReadOnlyObjectWrapper(param.getValue().getCurrentContract().computeAmountOwed());
            }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("N/A");
           }
        }
    });
    blockName.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) { 
            try {
                    return new ReadOnlyObjectWrapper(((HouseRentalContract)param.getValue().getCurrentContract()).getCurrentHouse().getBlock().getName());
            }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("Unassigned");
           }
        }
   }); 
    blockLocation.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) {
          try{  
            return new ReadOnlyObjectWrapper(((HouseRentalContract)param.getValue().getCurrentContract()).getCurrentHouse().getBlock().getLocation());
           }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("N/A");
           }
           }
   });
    houseRented.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) { 
            try{
           return new ReadOnlyObjectWrapper(((HouseRentalContract)param.getValue().getCurrentContract()).getCurrentHouse().getRentalName());
           }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("Unassigned");
           }         
        }
   });
    tenantsTable.getColumns().clear();
    tenantsTable.getColumns().addAll(name,phoneNumber,blockName,houseRented,blockLocation,balance);
    tenantsTable.setItems(tenantsFilteredList);
    tenantsTable.setPlaceholder(new Label("No Tenants found"));
    blocksFilterCombo.setItems(blocksList);
    tenantsSearhTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               ObservableList list =tenantsList.filtered(new Predicate<Tenant>(){
                    @Override
                    public boolean test(Tenant t) {
                        return t.getFullName().toLowerCase().contains(newValue.toString().toLowerCase());       
                    }
                });
                try{
                    tenantsFilteredList.clear();
                    tenantsFilteredList.addAll(list);
               }catch(Exception e){
                    e.printStackTrace();                    
                }
            }
            
        });
    blocksFilterCombo.valueProperty().addListener(new ChangeListener(){
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            String blockId =blocksFilterCombo.getValue().getDatabaseId();
         
                try{
                    tenantsFilteredList.clear();
                    tenantsFilteredList.addAll(tenantsList.filtered(new Predicate<Tenant>(){
                    @Override
                    public boolean test(Tenant t) {
                         try{
                            return ((HouseRentalContract)t.getCurrentContract()).getCurrentHouse().getBlockId().matches(blockId);  
                        }catch(Exception e){
                            
                            return false;                            
                        }
                    }
                }));
               }catch(Exception e){
                    e.printStackTrace();                    
                }
        }
    });
    tenantsTable.setOnMouseClicked(new EventHandler(){
        @Override
        public void handle(Event event) {
            Tenant selectedTenant = tenantsTable.getSelectionModel().getSelectedItem();
            if(selectedTenant != null && ((MouseEvent)event).getClickCount() == 2 ){
                Parent root;
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/tenantProfile.fxml"));
                root = (Parent)fxmlLoader.load(); 
                TenantProfileController controller = fxmlLoader.<TenantProfileController>getController();
                controller.initTenant(selectedTenant);
                Stage tenantProfilestage = new Stage();
                tenantProfilestage.setTitle(selectedTenant.getFullName());
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
    public void initializeTenantsOwedTable(){ 
    TableColumn name = new TableColumn("Name");
    TableColumn phoneNumber= new TableColumn("Phone Number");
    TableColumn blockName = new TableColumn("Block");
    TableColumn houseRented = new TableColumn("Rental Occupied");
    TableColumn balance = new TableColumn("Amount Owed");
    TableColumn blockLocation = new TableColumn("Location");
    name.setCellValueFactory(new Callback<CellDataFeatures<Tenant, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<Tenant, String> param) {
            try{
            return new ReadOnlyObjectWrapper(param.getValue().getLastName()+" "+param.getValue().getFirstName()) ;//()
                    }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("");
           }
                           }
    });
    phoneNumber.setCellValueFactory(new Callback<CellDataFeatures<Tenant, String>, ObservableValue<String>>() {
        @Override
        public ObservableValue<String> call(CellDataFeatures<Tenant, String> param) {
            return new ReadOnlyObjectWrapper(param.getValue().getPhoneNumber());
        }
    });
    balance.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) {
            try{
                return new ReadOnlyObjectWrapper(param.getValue().getCurrentContract().computeAmountOwed());
            }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("");
           }
        }
    });
    blockName.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) { 
            try {
                    return new ReadOnlyObjectWrapper(((HouseRentalContract)param.getValue().getCurrentContract()).getCurrentHouse().getBlock().getName());
            }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("");
           }
        }
   }); 
    blockLocation.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) {
          try{  
            return new ReadOnlyObjectWrapper(((HouseRentalContract)param.getValue().getCurrentContract()).getCurrentHouse().getBlock().getLocation());
           }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("");
           }
           }
   });
    houseRented.setCellValueFactory(new Callback<CellDataFeatures<Tenant, Double>, ObservableValue<Double>>() {
        @Override
        public ObservableValue<Double> call(CellDataFeatures<Tenant, Double> param) { 
            try{
           return new ReadOnlyObjectWrapper(((HouseRentalContract)param.getValue().getCurrentContract()).getCurrentHouse().getRentalName());
           }
            catch(NullPointerException ex){
               return new ReadOnlyObjectWrapper("");
           }         
        }
   });
    tenantsOwedTable.getColumns().clear();
    tenantsOwedTable.getColumns().addAll(name,phoneNumber,blockName,houseRented,blockLocation,balance);
    tenantsOwedTable.setPlaceholder(new Label("No Tenants Owed"));
    tenantsOwedTable.setItems(tenantsOwedFilteredList);
    blocksFilterCombo.setItems(blocksList);
    tenantsOwedSearhTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               ObservableList list =tenantsOwedList.filtered(new Predicate<Tenant>(){
                    @Override
                    public boolean test(Tenant t) {
                        return t.getFullName().toLowerCase().contains(newValue.toString().toLowerCase());       
                    }
                });
                try{
                    tenantsOwedFilteredList.clear();
                    tenantsOwedFilteredList.addAll(list);
               }catch(Exception e){
                    e.printStackTrace();                    
                }
            }
            
        });
    blocksOwedFilterCombo.valueProperty().addListener(new ChangeListener(){
        @Override
        public void changed(ObservableValue observable, Object oldValue, Object newValue) {
            String blockId =blocksOwedFilterCombo.getValue().getDatabaseId();
           System.out.println("BLOCK "+blocksFilterCombo.getValue()+" and "+blockId);
                try{
                    tenantsOwedFilteredList.clear();
                    tenantsOwedFilteredList.addAll(tenantsOwedList.filtered(new Predicate<Tenant>(){
                    @Override
                    public boolean test(Tenant t) {
                         try{
                            return ((HouseRentalContract)t.getCurrentContract()).getCurrentHouse().getBlockId().matches(blockId);  
                        }catch(Exception e){
                            
                            return false;                            
                        }
                    }
                }));
               }catch(Exception e){
                    e.printStackTrace();                    
                }
        }
    });
    tenantsOwedTable.setOnMouseClicked(new EventHandler(){
        @Override
        public void handle(Event event) {
            Tenant selectedTenant = tenantsOwedTable.getSelectionModel().getSelectedItem();
            if(selectedTenant != null && ((MouseEvent)event).getClickCount() == 2 ){
                Parent root;
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/tenantProfile.fxml"));
                root = (Parent)fxmlLoader.load(); 
                TenantProfileController controller = fxmlLoader.<TenantProfileController>getController();
                controller.initTenant(selectedTenant);
                Stage tenantProfilestage = new Stage();
                tenantProfilestage.setTitle(selectedTenant.getFullName());
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
    
    public void reload(){
        tenantsOwedList.clear();
        tenantsList.clear();
        tenantsList.addAll(DatabaseHandler.getInstance().getTenants());
        tenantsFilteredList.addAll(DatabaseHandler.getInstance().getTenants());
        ObservableList temp = FXCollections.observableArrayList(DatabaseHandler.getInstance().getTenants());
        ObservableList list =temp.filtered(new Predicate<Tenant>(){
            @Override
            public boolean test(Tenant t) {
                try{
                    return t.getCurrentContract().computeAmountOwed()>0.0;
                }catch(Exception ex){

                    return false;
                }
            }
         });   
        tenantsOwedList.addAll(list);
        tenantsOwedFilteredList.addAll(list);
    }
    
    @FXML
    void changeRentals(MouseEvent event) {
       //changeRentals();
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
        }else if(noFamMembersTxt.getText().isEmpty()){
            noFamMembersTxt.requestFocus();
        }else if(nokNameTxt.getText().isEmpty()){
            nokNameTxt.requestFocus();
        }else if(nokContactTxt.getText().isEmpty()){
            nokContactTxt.requestFocus();
        }else if(nokPlaceOfWorkTxt.getText().isEmpty()){
            nokPlaceOfWorkTxt.requestFocus();
        }else if(blockCombo.getValue()== null){
            blockCombo.requestFocus();
        }else if(rentalsCombo.getValue() == null){
            rentalsCombo.requestFocus();
        }else if(dobDatePicker.getValue() ==null){
            dobDatePicker.requestFocus();
        }else if(this.startDatePicker.getValue() ==null){
            startDatePicker.requestFocus();
        }
        else if(monthlyFeeTxt.getText().isEmpty()){
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
                    , phoneNumberTxt.getText(), idTypeCombo.getValue(),idNumberTxt.getText(),
                    maritalStatus, Integer.parseInt(noFamMembersTxt.getText()), nokNameTxt.getText(), 
                    nokContactTxt.getText(),nokDistrictTxt.getText(),nokCountyTxt.getText(),nokSubCountyTx.getText(),
                    nokParishTxt.getText(),nokVillageTxt.getText(),nokPlaceOfWorkTxt.getText());
           // lastNameTxt.setText("Kasumba"); phoneNumberTxt.setText("0752615075");fNameTxt.setText("Kasumba");idNumberTxt.setText("7892374HJF");noFamMembersTxt.setText("5");nokNameTxt.setText("Lubega");nokContactTxt.setText("07867542452");
           // nokDistrictTxt.getText(),nokCountyTxt.getText(),nokSubCountyTx.getText(),nokParishTxt.getText(),nokVillageTxt.getText()
           String tenantId =tenant.save();
            //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //LocalDateTime now  = LocalDateTime.now();
            String startDate = this.startDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String houseId = rentalsCombo.getValue().getHouseId();
            //amountPaid,datePaid,receiptNumber,modeOfPayment,tenantID,contractID,receivedBy
            HouseRentalContract contract = new HouseRentalContract(startDate, tenantId,houseId,Double.parseDouble(monthlyFeeTxt.getText()),Double.parseDouble(advanceBeforeSystemUpgradeTxt.getText()),Double.parseDouble(arrearsBeforeSystemUpgradeTxt.getText()));

            String contractId=contract.saveContract();
            if(contractId !=null && !depositTxt.getText().isEmpty()){
                Payment payment = new Payment(startDate, Double.parseDouble(depositTxt.getText()), contractId, receivedByTxt.getText(), tenantId, paymentModeCombo.getValue(),referenceNumberTxt.getText());
                String id=payment.savePayment();
                if(id != null){
                    Alert alert = new Alert(Alert.AlertType.NONE, "Tenant added",ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setHeaderText(null);
                    alert.setTitle("Success");
                    alert.show();
                    resetFields();
                }
            }
            else if(contractId != null){
                 Alert alert = new Alert(Alert.AlertType.NONE, "Tenant added",ButtonType.OK);
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setHeaderText(null);
                    alert.setTitle("Success");
                    alert.show();
                    resetFields();
                   
            }
             this.reload();
           
        }
    }
     @FXML
    void clearForm(MouseEvent event) {
        resetFields();
    }
    void resetFields(){
        fNameTxt.setText("");
        lastNameTxt.setText("");
        phoneNumberTxt.setText("");
        countriesCombo.setValue("Uganda");
        phoneNumberTxt.setText("");
        idNumberTxt.setText("");
        idTypeCombo.setValue(idTypes[0]);
        statusSingleRadio.setSelected(true);
        noFamMembersTxt.setText("");
        nokNameTxt.setText("");
        nokContactTxt.setText("");
        blockCombo.setValue(null);
        monthlyFeeTxt.setText("");
        depositTxt.setText("");
        receivedByTxt.setText("");
        paymentModeCombo.setValue(paymentOtions[0]);
        dobDatePicker.setValue(null);
        this.startDatePicker.setValue(LocalDate.now());
    }
       @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert tenantsOwedSearhTxt != null : "fx:id=\"tenantsOwedSearhTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert blocksOwedFilterCombo != null : "fx:id=\"blocksOwedFilterCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert tenantsOwedTable != null : "fx:id=\"tenantsOwedTable\" was not injected: check your FXML file 'tenants.fxml'.";
        assert tenantsSearhTxt != null : "fx:id=\"tenantsSearhTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert blocksFilterCombo != null : "fx:id=\"blocksFilterCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert tenantsTable != null : "fx:id=\"tenantsTable\" was not injected: check your FXML file 'tenants.fxml'.";
        assert fNameTxt != null : "fx:id=\"fNameTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert lastNameTxt != null : "fx:id=\"lastNameTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert countriesCombo != null : "fx:id=\"countriesCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert phoneNumberTxt != null : "fx:id=\"phoneNumberTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert idTypeCombo != null : "fx:id=\"idTypeCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert idNumberTxt != null : "fx:id=\"idNumberTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert statusSingleRadio != null : "fx:id=\"statusSingleRadio\" was not injected: check your FXML file 'tenants.fxml'.";
        assert statusMarriedRadio != null : "fx:id=\"statusMarriedRadio\" was not injected: check your FXML file 'tenants.fxml'.";
        assert noFamMembersTxt != null : "fx:id=\"noFamMembersTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert dobDatePicker != null : "fx:id=\"dobDatePicker\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokNameTxt != null : "fx:id=\"nokNameTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokContactTxt != null : "fx:id=\"nokContactTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokPlaceOfWorkTxt != null : "fx:id=\"nokPlaceOfWorkTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokDistrictTxt != null : "fx:id=\"nokDistrictTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokCountyTxt != null : "fx:id=\"nokCountyTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokSubCountyTx != null : "fx:id=\"nokSubCountyTx\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokParishTxt != null : "fx:id=\"nokParishTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert nokVillageTxt != null : "fx:id=\"nokVillageTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert advanceBeforeSystemUpgradeTxt != null : "fx:id=\"advanceBeforeSystemUpgradeTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert arrearsBeforeSystemUpgradeTxt != null : "fx:id=\"arrearsBeforeSystemUpgradeTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert rentalsCombo != null : "fx:id=\"rentalsCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert monthlyFeeTxt != null : "fx:id=\"monthlyFeeTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'tenants.fxml'.";
        assert depositTxt != null : "fx:id=\"depositTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert receivedByTxt != null : "fx:id=\"receivedByTxt\" was not injected: check your FXML file 'tenants.fxml'.";
        assert paymentModeCombo != null : "fx:id=\"paymentModeCombo\" was not injected: check your FXML file 'tenants.fxml'.";
        assert referenceNumberTxt != null : "fx:id=\"referenceNumberTxt\" was not injected: check your FXML file 'tenants.fxml'.";

    }
    
}
