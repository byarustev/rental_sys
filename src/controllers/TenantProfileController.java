/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.HouseRentalContract;
import GeneralClasses.Payment;
import GeneralClasses.Tenant;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author robert
 */
public class TenantProfileController implements Initializable{
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
        this.numFamMemLabel.setText(this.tenant.getNumOfFamMembers()+"");
        try{
            HouseRentalContract contract = (HouseRentalContract)this.tenant.getCurrentContract();
            this.balanceLbl.setText(contract.computeBalance()+"");
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

    }
}
