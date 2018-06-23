/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.BankStatement;
import GeneralClasses.HouseRentalContract;
import GeneralClasses.MonthReport;
import GeneralClasses.Payment;
import GeneralClasses.ReloadableController;
import GeneralClasses.Tenant;
import database.DatabaseHandler;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author robert
 */
public class TenantProfileController implements Initializable,ReloadableController{
        @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="blockLabel"
    private Label blockLabel; // Value injected by FXMLLoader

    @FXML // fx:id="houseLabel"
    private Label houseLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nameLabel"
    private Label nameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nationalityLabel"
    private Label nationalityLabel; // Value injected by FXMLLoader

    @FXML // fx:id="phoneNumberLabel"
    private Label phoneNumberLabel; // Value injected by FXMLLoader

    @FXML // fx:id="idTypeLabel"
    private Label idTypeLabel; // Value injected by FXMLLoader

    @FXML // fx:id="idNumberLabel"
    private Label idNumberLabel; // Value injected by FXMLLoader

    @FXML // fx:id="dobLabel"
    private Label dobLabel; // Value injected by FXMLLoader

    @FXML // fx:id="maritalStatusLabel"
    private Label maritalStatusLabel; // Value injected by FXMLLoader

    @FXML // fx:id="numFamMemLabel"
    private Label numFamMemLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nokNameLabel"
    private Label nokNameLabel; // Value injected by FXMLLoader

    @FXML // fx:id="nokContactLabel"
    private Label nokContactLabel; // Value injected by FXMLLoader
    @FXML // fx:id="paymentsTable"
    private TableView<Payment> paymentsTable; // Value injected by FXMLLoader
    @FXML // fx:id="dateJoinedLabel"
    private Label dateJoinedLabel; // Value injected by FXMLLoader
    @FXML // fx:id="monthlyAmountLabel"
    private Label monthlyAmountLabel; // Value injected by FXMLLoader
    @FXML // fx:id="monthsSpentLabel"
    private Label monthsSpentLabel; // Value injected by FXMLLoader

    @FXML // fx:id="totalAmountPaidLabel"
    private Label totalAmountPaidLabel; // Value injected by FXMLLoader
    @FXML // fx:id="balanceLbl"
    private Label balanceLbl; // Value injected by FXMLLoader
    ObservableList paymentsList ;
    @FXML // fx:id="statementsTableView"
    private TableView<BankStatement> statementsTableView; // Value injected by FXMLLoader

    @FXML // fx:id="monthlyReportTableView"
    private TableView<MonthReport> monthlyReportTableView; // Value injected by FXMLLoader
    ObservableList allStatementsList,monthReportList;
    ObservableList dynamicStatementsList= FXCollections.observableArrayList();
    
    @FXML // This method is called by the FXMLLoader when initialization is complete
      /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }  
    Tenant tenant;
    
    public void initTenant(Tenant t){
        this.tenant = t;
        initializeTanantBio();
        initPaymentsTable();
        initStatementsTable();
        initMonthlyReportTable();
    }
    
    private void initializeTanantBio(){
        this.nameLabel.setText(this.tenant.getFullName());
        this.idTypeLabel.setText(this.tenant.getIdType());
        this.idNumberLabel.setText(this.tenant.getIdNumber());
        this.dobLabel.setText(this.tenant.getDateOfBirth());
        this.maritalStatusLabel.setText(this.tenant.getMaritalStatus());
        this.nationalityLabel.setText(this.tenant.getNationality());
        this.nokContactLabel.setText(this.tenant.getNokContack());
        this.nokNameLabel.setText(this.tenant.getNokName());
        this.phoneNumberLabel.setText(this.tenant.getPhoneNumber());
        this.numFamMemLabel.setText(this.tenant.getNumOfFamMembers()+"");
        try{
            HouseRentalContract contract = (HouseRentalContract)this.tenant.getCurrentContract();
            this.balanceLbl.setText(contract.computeAmountOwed()+"");
            this.dateJoinedLabel.setText(contract.getStartDate());
            this.houseLabel.setText(contract.getCurrentHouse().getRentalName());
            this.blockLabel.setText(contract.getCurrentHouse().getBlock().getName());
            this.monthsSpentLabel.setText(contract.computeFullMonths()+"");
            this.totalAmountPaidLabel.setText(contract.computeTotalPayments()+"");
            monthlyAmountLabel.setText(contract.getAgreedMonthlyAmount()+"");
        }catch(NullPointerException ex){
            ex.printStackTrace();
        }
    }
    
    private void initPaymentsTable(){
        if(this.tenant ==null){
            return;
        }
        TableColumn  paymentDateCol = new TableColumn("Payment Date");
        TableColumn  amountPaidCol= new TableColumn("Amount Paid");
        TableColumn  paymentMethodCol= new TableColumn("Payment Method");
        TableColumn  referenceNumCol= new TableColumn("Reference");
        TableColumn  receivedByCol= new TableColumn("Received By");
        PropertyValueFactory <Payment, String> paymentDateFactory = new PropertyValueFactory("paymentDate");
        PropertyValueFactory <Payment, Double> amountPaidFactory = new PropertyValueFactory("paymentAmount");
        PropertyValueFactory <Payment, String> paymentMethodFactory = new PropertyValueFactory("modeOfPayment");
        PropertyValueFactory <Payment, String> referenceNumFactory = new PropertyValueFactory("referenceNumber");
        PropertyValueFactory <Payment, String> receivedByColFactory = new PropertyValueFactory("receivedBy");
        paymentDateCol.setCellValueFactory(paymentDateFactory);
        amountPaidCol.setCellValueFactory(amountPaidFactory);
        paymentMethodCol.setCellValueFactory(paymentMethodFactory);
        referenceNumCol.setCellValueFactory(referenceNumFactory);
        receivedByCol.setCellValueFactory(receivedByColFactory);
        paymentsTable.getColumns().clear();
        paymentsTable.getColumns().addAll(paymentDateCol,amountPaidCol,paymentMethodCol,referenceNumCol,receivedByCol);
        paymentsList = FXCollections.observableArrayList(this.tenant.getMyPayments());
        paymentsTable.setItems(paymentsList);   
        paymentsTable.setOnMouseClicked(new EventHandler(){
            @Override
            public void handle(Event event) {
                System.out.println("I HAVE BEEN CLICKED");
               if(((MouseEvent)event).getClickCount()==2){
                   Payment p = paymentsTable.getSelectionModel().getSelectedItem();
                   System.out.println(p);
                   if(p != null){
                       Parent root;
                       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/editPayment.fxml"));
                       try {
                           root = (Parent)fxmlLoader.load();
                           EditPaymentController editorController =fxmlLoader.<EditPaymentController>getController();
                           editorController.initPayment(p,TenantProfileController.this);
                           Stage editPaymentStage = new Stage();
                           editPaymentStage.setTitle("Edit Payment");
                           editPaymentStage.setScene(new Scene(root));
                           editPaymentStage.initModality(Modality.WINDOW_MODAL);
                           editPaymentStage.initOwner(((Node)event.getSource()).getScene().getWindow());
                           editPaymentStage.show();
                       } catch (IOException ex) {
                           Logger.getLogger(PaymentsController.class.getName()).log(Level.SEVERE, null, ex);
                       }
                   }
               }
            }
        });
    }
  
    private void initStatementsTable(){
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
        statementsTableView.getColumns().clear();
        statementsTableView.getColumns().addAll(transactionDateCol,tenantCol,transactionDescCol,creditAmountCol,valueDateCol);
        dynamicStatementsList.addAll(DatabaseHandler.getInstance().getStatements(this.tenant.getTenantId()));
        statementsTableView.setItems(dynamicStatementsList);
        allStatementsList = FXCollections.observableArrayList();
        allStatementsList.addAll(DatabaseHandler.getInstance().getAllStatements());   
    }
    
    private void initMonthlyReportTable(){
        TableColumn  monthDateCol = new TableColumn("Month");
        TableColumn  expectedAmountDateCol= new TableColumn("Expected");
        TableColumn  amountPaidCol= new TableColumn("Paid ");
        TableColumn  balanceCol= new TableColumn("Balance");
        TableColumn  cumulativeBalanceCol= new TableColumn("Cumulative Balance");
        PropertyValueFactory <MonthReport, String> monthFactory = new PropertyValueFactory("month");
        PropertyValueFactory <MonthReport, Double> expectedAmountFactory = new PropertyValueFactory("expectedAmount");
        PropertyValueFactory <MonthReport, Double> amountPaidFactory = new PropertyValueFactory("actualAmountPaid");
        PropertyValueFactory <MonthReport, Double> balanceFactory = new PropertyValueFactory("balance");
        PropertyValueFactory <MonthReport, Double> cumulativeBalanceFactory = new PropertyValueFactory("cumulativeBalance");
        monthDateCol.setCellValueFactory(monthFactory);
        amountPaidCol.setCellValueFactory(amountPaidFactory);
        balanceCol.setCellValueFactory(balanceFactory);
        expectedAmountDateCol.setCellValueFactory(expectedAmountFactory);
        cumulativeBalanceCol.setCellValueFactory(cumulativeBalanceFactory);
        amountPaidCol.getStyleClass().add("column-center-align");
        expectedAmountDateCol.getStyleClass().add("column-center-align");
        balanceCol.getStyleClass().add("column-center-align");
        cumulativeBalanceCol.getStyleClass().add("column-center-align");
        monthlyReportTableView.getColumns().clear();
        monthlyReportTableView.getColumns().addAll(monthDateCol,expectedAmountDateCol,amountPaidCol,balanceCol,cumulativeBalanceCol);
        monthReportList = FXCollections.observableArrayList(this.tenant.getCurrentContract().generateMonthlyReports());
        monthlyReportTableView.setItems(monthReportList);
    }
    @FXML
    void showEditWindow(MouseEvent event) {
         Parent root;
        try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/editTenant.fxml"));
                root = (Parent)fxmlLoader.load(); 
                EditTenantController controller = fxmlLoader.<EditTenantController>getController();
                controller.initTenant(this.tenant,this);
                Stage tenantProfilestage = new Stage();
                tenantProfilestage.setTitle(this.tenant.getFullName());
                tenantProfilestage.setScene(new Scene(root));
                tenantProfilestage.initModality(Modality.WINDOW_MODAL);
                tenantProfilestage.initOwner(((Node)(event.getSource())).getScene().getWindow());
                tenantProfilestage.show();   
            }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
     

    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert nameLabel != null : "fx:id=\"nameLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert nationalityLabel != null : "fx:id=\"nationalityLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert phoneNumberLabel != null : "fx:id=\"phoneNumberLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert idTypeLabel != null : "fx:id=\"idTypeLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert idNumberLabel != null : "fx:id=\"idNumberLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert dobLabel != null : "fx:id=\"dobLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert maritalStatusLabel != null : "fx:id=\"maritalStatusLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert numFamMemLabel != null : "fx:id=\"numFamMemLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert nokNameLabel != null : "fx:id=\"nokNameLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert nokContactLabel != null : "fx:id=\"nokContactLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert houseLabel != null : "fx:id=\"houseLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert blockLabel != null : "fx:id=\"blockLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert dateJoinedLabel != null : "fx:id=\"dateJoinedLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert monthsSpentLabel != null : "fx:id=\"monthsSpentLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert monthlyAmountLabel != null : "fx:id=\"monthlyAmountLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert paymentsTable != null : "fx:id=\"paymentsTable\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert balanceLbl != null : "fx:id=\"balanceLbl\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert totalAmountPaidLabel != null : "fx:id=\"totalAmountPaidLabel\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert statementsTableView != null : "fx:id=\"statementsTableView\" was not injected: check your FXML file 'tenantProfile.fxml'.";
        assert monthlyReportTableView != null : "fx:id=\"monthlyReportTableView\" was not injected: check your FXML file 'tenantProfile.fxml'.";
    }

    @Override
    public void reload() {
       this.reloadPaymentsTable();
       initializeTanantBio();
    }
    
    public void reloadPaymentsTable(){
        paymentsList.clear();
        monthReportList.clear();
        paymentsList.addAll(this.tenant.getMyPayments());
        
        monthReportList.addAll(this.tenant.getCurrentContract(true).generateMonthlyReports());
    }
    
    
}
