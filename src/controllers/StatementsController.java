/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.Block;
import GeneralClasses.House;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.RentalContract;
import GeneralClasses.BankStatement;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author robert
 */
public class StatementsController implements Initializable {

     /**
     * Initializes the controller class.
     */
    ObservableList blocksList,rentalsList,modesOfPaymentList,allStatementsList;
        
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="paymentDatePicker"
    private DatePicker paymentDatePicker; // Value injected by FXMLLoader

    @FXML // fx:id="amountPaidTxt"
    private TextField amountPaidTxt; // Value injected by FXMLLoader
     @FXML
    private DatePicker valueDatePicker;


    @FXML // fx:id="blockCombo"
    private ComboBox<Block> blockCombo; // Value injected by FXMLLoader

    @FXML // fx:id="rentalCombo"
    private ComboBox<House> rentalCombo; // Value injected by FXMLLoader

    @FXML // fx:id="tenantNameTxt"
    private TextField tenantNameTxt; // Value injected by FXMLLoader
    @FXML // fx:id="transactionDescTxt"
    private TextField transactionDescTxt; // Value injected by FXMLLoader

  
    
    private String selectedContractId,selectedTenantId;
    @FXML // fx:id="paymentsTable"
    private TableView<BankStatement> paymentsTable; // Value injected by FXMLLoader
    @FXML // fx:id="startDatePicker"
    private DatePicker startDatePicker; // Value injected by FXMLLoader
    @FXML // fx:id="endDatePicker"
    private DatePicker endDatePicker; // Value injected by FXMLLoader
    @FXML // fx:id="totalAmountLabel"
    private Label totalAmountLabel; // Value injected by FXMLLoader
    @FXML
    private TextField nameSearchTxt;
    ObservableList dynamicStatementsList = FXCollections.observableArrayList();
    @FXML // fx:id="blockSearchTxt"
    private TextField blockSearchTxt; // Value injected by FXMLLoader
     @FXML
    private TextField accountBalance;
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
      
      setupStatementsTable();
    }    

    void setupStatementsTable(){
        
        TableColumn  transactionDateCol = new TableColumn("Transaction Date");
        TableColumn  valueDateCol= new TableColumn("Value Date");
        TableColumn  tenantCol= new TableColumn("Tenant ");
        TableColumn  transactionDescCol= new TableColumn("Transaction Description");
        TableColumn  creditAmountCol= new TableColumn("Credit Amout");
        PropertyValueFactory <BankStatement, String> transactionDateFactory = new PropertyValueFactory("transactionDate");
        PropertyValueFactory <BankStatement, Double> amountPaidFactory = new PropertyValueFactory("creditAmount");
        PropertyValueFactory <BankStatement, String> valueDateFactory = new PropertyValueFactory("valueDate");
        PropertyValueFactory <BankStatement, Double> transactionDescFactory = new PropertyValueFactory("transactionDescription");
        transactionDateCol.setCellValueFactory(transactionDateFactory);
        valueDateCol.setCellValueFactory(valueDateFactory);
        transactionDescCol.setCellValueFactory(transactionDescFactory);
        creditAmountCol.setCellValueFactory(amountPaidFactory);
        tenantCol.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<BankStatement, String>, ObservableValue<Double>>(){
            @Override
            public ObservableValue<Double> call(TableColumn.CellDataFeatures<BankStatement, String> param) {
               try{
           return new ReadOnlyObjectWrapper(((BankStatement)param.getValue()).getAssociatedContract().getAssociatedTenant().getFullName());
           }
            catch(NullPointerException ex){
                ex.printStackTrace();
               return new ReadOnlyObjectWrapper("");
           }  
            }
        });
        
      
        paymentsTable.getColumns().clear();
        paymentsTable.getColumns().addAll(transactionDateCol,tenantCol,transactionDescCol,creditAmountCol,valueDateCol);
        dynamicStatementsList.addListener(new ListChangeListener(){
            @Override
            public void onChanged(ListChangeListener.Change c) {
                double sum=0;
                for(Object x : dynamicStatementsList){
                    sum+=((BankStatement)x).getCreditAmount();
                }
                totalAmountLabel.setText(""+sum);
            }
        });
        dynamicStatementsList.addAll(DatabaseHandler.getInstance().getAllStatements());
        paymentsTable.setItems(dynamicStatementsList);
        allStatementsList = FXCollections.observableArrayList();
        allStatementsList.addAll(DatabaseHandler.getInstance().getAllStatements());
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
               dynamicStatementsList.clear();
               dynamicStatementsList.addAll(allStatementsList.filtered(new Predicate<BankStatement>(){
                   @Override
                   public boolean test(BankStatement t) {
                       return t.getAssociatedContract().getAssociatedTenant().getFullName().toLowerCase().contains(newValue.toString().toLowerCase());
                   }
               }));
            }
        });
      blockSearchTxt.textProperty().addListener(new ChangeListener(){
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
               dynamicStatementsList.clear();
               dynamicStatementsList.addAll(allStatementsList.filtered(new Predicate<BankStatement>(){
                   @Override
                   public boolean test(BankStatement t) {
                       return t.getAssociatedContract().getCurrentHouse().getBlock().getName().toLowerCase().contains(newValue.toString().toLowerCase());
                   }
               }));
            }
        });
    }
    void actionDatesChanged(){
        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if(startDatePicker.getValue() != null && endDatePicker.getValue() != null){
            dynamicStatementsList.clear();
            dynamicStatementsList.addAll(DatabaseHandler.getInstance().getStatements(startDatePicker.getValue().format(formatter), 
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
    void saveStatement(MouseEvent event) {
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
        String referenceNumber="", receivedBy ="";
        if(!transactionDescTxt.getText().isEmpty()){
            receivedBy=transactionDescTxt.getText();
        }
        String transactionDate="";
        String valueDate="";
        try{
            transactionDate= paymentDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            valueDate= valueDatePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        }catch(Exception ex){
            paymentDatePicker.requestFocus();
        }
        
        BankStatement statement = new BankStatement(transactionDate, valueDate, transactionDescTxt.getText(), Double.parseDouble(amountPaidTxt.getText()) , Double.parseDouble(accountBalance.getText()),  selectedTenantId);
        String id=statement.saveStatement();
        if(id != null){
            Alert alert = new Alert(Alert.AlertType.NONE, "Statement Saved",ButtonType.OK);
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
        transactionDescTxt.setText("");
        amountPaidTxt.setText("");
        accountBalance.setText("");
    }
     @FXML
    void initialize() {
        assert nameSearchTxt != null : "fx:id=\"nameSearchTxt\" was not injected: check your FXML file 'statements.fxml'.";
        assert blockSearchTxt != null : "fx:id=\"blockSearchTxt\" was not injected: check your FXML file 'statements.fxml'.";
        assert startDatePicker != null : "fx:id=\"startDatePicker\" was not injected: check your FXML file 'statements.fxml'.";
        assert endDatePicker != null : "fx:id=\"endDatePicker\" was not injected: check your FXML file 'statements.fxml'.";
        assert paymentsTable != null : "fx:id=\"paymentsTable\" was not injected: check your FXML file 'statements.fxml'.";
        assert totalAmountLabel != null : "fx:id=\"totalAmountLabel\" was not injected: check your FXML file 'statements.fxml'.";
        assert paymentDatePicker != null : "fx:id=\"paymentDatePicker\" was not injected: check your FXML file 'statements.fxml'.";
        assert valueDatePicker != null : "fx:id=\"valueDatePicker\" was not injected: check your FXML file 'statements.fxml'.";
        assert blockCombo != null : "fx:id=\"blockCombo\" was not injected: check your FXML file 'statements.fxml'.";
        assert rentalCombo != null : "fx:id=\"rentalCombo\" was not injected: check your FXML file 'statements.fxml'.";
        assert tenantNameTxt != null : "fx:id=\"tenantNameTxt\" was not injected: check your FXML file 'statements.fxml'.";
        assert transactionDescTxt != null : "fx:id=\"transactionDescTxt\" was not injected: check your FXML file 'statements.fxml'.";
        assert amountPaidTxt != null : "fx:id=\"amountPaidTxt\" was not injected: check your FXML file 'statements.fxml'.";
        assert accountBalance != null : "fx:id=\"accountBalance\" was not injected: check your FXML file 'statements.fxml'.";
    }
    
}
