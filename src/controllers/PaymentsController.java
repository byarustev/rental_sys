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
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author steve
 */
public class PaymentsController implements Initializable {

    /**
     * Initializes the controller class.
     */
    ObservableList blocksList,rentalsList,modesOfPaymentList,allPaymentsList;
        
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
    @FXML // fx:id="paymentsTable"
    private TableView<Payment> paymentsTable; // Value injected by FXMLLoader
    @FXML // fx:id="startDatePicker"
    private DatePicker startDatePicker; // Value injected by FXMLLoader
    @FXML // fx:id="endDatePicker"
    private DatePicker endDatePicker; // Value injected by FXMLLoader
    @FXML // fx:id="totalAmountLabel"
    private Label totalAmountLabel; // Value injected by FXMLLoader
    @FXML
    private TextField nameSearchTxt;
    ObservableList payments = FXCollections.observableArrayList();
    @FXML // fx:id="blockSearchTxt"
    private TextField blockSearchTxt; // Value injected by FXMLLoader
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
      
      setupPaymentsTable();
    }    

    void setupPaymentsTable(){
        
        TableColumn  paymentDateCol = new TableColumn("Payment Date");
        TableColumn  amountPaidCol= new TableColumn("Amount Paid");
        TableColumn  tenantCol= new TableColumn("Tenant ");
        TableColumn  houseCol= new TableColumn("Room");
        TableColumn  blockCol= new TableColumn("Block");
        PropertyValueFactory <Payment, String> paymentDateFactory = new PropertyValueFactory("paymentDate");
        PropertyValueFactory <Payment, Double> amountPaidFactory = new PropertyValueFactory("paymentAmount");
      /*PropertyValueFactory <Payment, String> paymentMethodFactory = new PropertyValueFactory("modeOfPayment");
        PropertyValueFactory <Payment, String> referenceNumFactory = new PropertyValueFactory("referenceNumber");
        PropertyValueFactory <Payment, String> receivedByColFactory = new PropertyValueFactory("receivedBy");*/
        paymentDateCol.setCellValueFactory(paymentDateFactory);
        amountPaidCol.setCellValueFactory(amountPaidFactory);
        tenantCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Payment, String>, ObservableValue<Double>>(){
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Payment, String> param) {
               try{
           return new ReadOnlyObjectWrapper(((Payment)param.getValue()).getAssociatedContract().getAssociatedTenant().getFullName());
           }
            catch(NullPointerException ex){
                ex.printStackTrace();
               return new ReadOnlyObjectWrapper("");
           }  
            }
        });
        
        houseCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Payment, String>, ObservableValue<Double>>(){
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Payment, String> param) {
               try{
                return new ReadOnlyObjectWrapper(((Payment)param.getValue()).getAssociatedContract().getCurrentHouse().getRentalName());
                }
                catch(NullPointerException ex){
                   ex.printStackTrace();
                   return new ReadOnlyObjectWrapper("");
               }  
            }
        });
        blockCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Payment, String>, ObservableValue<Double>>(){
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<Payment, String> param) {
               try{
                return new ReadOnlyObjectWrapper(((Payment)param.getValue()).getAssociatedContract().getCurrentHouse().getBlock().getName());
                }
                catch(NullPointerException ex){
                     ex.printStackTrace();
                   return new ReadOnlyObjectWrapper("");
               }  
            }
        });
        
        paymentsTable.getColumns().clear();
        paymentsTable.getColumns().addAll(paymentDateCol,tenantCol,houseCol,blockCol,amountPaidCol);
        payments.addListener(new ListChangeListener(){
            @Override
            public void onChanged(ListChangeListener.Change c) {
                double sum=0;
                for(Object x : payments){
                    sum+=((Payment)x).getPaymentAmount();
                }
                totalAmountLabel.setText(""+sum);
            }
        });
        payments.addAll(DatabaseHandler.getInstance().getAllPayments());
        paymentsTable.setItems(payments);
        allPaymentsList = FXCollections.observableArrayList();
        allPaymentsList.addAll(DatabaseHandler.getInstance().getAllPayments());
        startDatePicker.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                actionDatesChanged();
            }
        });
        endDatePicker.valueProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                actionDatesChanged();
            }
        });
        nameSearchTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               payments.clear();
               payments.addAll(allPaymentsList.filtered(new Predicate<Payment>(){
                   @Override
                   public boolean test(Payment t) {
                       return t.getAssociatedContract().getAssociatedTenant().getFullName().toLowerCase().contains(newValue.toString().toLowerCase());
                   }
               }));
            }
        });
      blockSearchTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               payments.clear();
               payments.addAll(allPaymentsList.filtered(new Predicate<Payment>(){
                   @Override
                   public boolean test(Payment t) {
                       return t.getAssociatedContract().getCurrentHouse().getBlock().getName().toLowerCase().contains(newValue.toString().toLowerCase());
                   }
               }));
            }
        });
    }
    void actionDatesChanged(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(startDatePicker.getValue() != null && endDatePicker.getValue() != null){
            payments.clear();
            payments.addAll(DatabaseHandler.getInstance().getPayments(startDatePicker.getValue().format(formatter), 
                    endDatePicker.getValue().format(formatter)));
        }
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
      @FXML
    void initialize() {
        assert paymentDatePicker != null : "fx:id=\"paymentDatePicker\" was not injected: check your FXML file 'payments.fxml'.";
        assert amountPaidTxt != null : "fx:id=\"amountPaidTxt\" was not injected: check your FXML file 'payments.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'payments.fxml'.";
        assert rentalCombo != null : "fx:id=\"rentalCombo\" was not injected: check your FXML file 'payments.fxml'.";
        assert tenantNameTxt != null : "fx:id=\"tenantNameTxt\" was not injected: check your FXML file 'payments.fxml'.";
        assert paymentMethodCombo != null : "fx:id=\"paymentMethodCombo\" was not injected: check your FXML file 'payments.fxml'.";
        assert receivedByTxt != null : "fx:id=\"receivedByTxt\" was not injected: check your FXML file 'payments.fxml'.";
        assert referenceNumberTxt != null : "fx:id=\"referenceNumberTxt\" was not injected: check your FXML file 'payments.fxml'.";
        assert nameSearchTxt != null : "fx:id=\"nameSearchTxt\" was not injected: check your FXML file 'payments.fxml'.";
        assert blockSearchTxt != null : "fx:id=\"blockSearchTxt\" was not injected: check your FXML file 'payments.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'payments.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'payments.fxml'.";
        assert paymentsTable != null : "fx:id=\"paymentsTable\" was not injected: check your FXML file 'payments.fxml'.";
        assert totalAmountLabel != null : "fx:id=\"totalAmountLabel\" was not injected: check your FXML file 'payments.fxml'.";

    }
}
