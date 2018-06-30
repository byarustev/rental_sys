/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import GeneralClasses.CurrentUser;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author steve
 */
public class LoginController implements Initializable {
    
   

    @FXML // fx:id="usernameTxt"
    private TextField usernameTxt; // Value injected by FXMLLoader

    @FXML // fx:id="passwordTxt"
    private PasswordField passwordTxt; // Value injected by FXMLLoader

      @Override
    public void initialize(URL location, ResourceBundle resources) {
    
    }
    @FXML
    void submitLogin(MouseEvent event) {
        if(usernameTxt.getText().isEmpty()){
            usernameTxt.requestFocus();
        }
        else if(passwordTxt.getText().isEmpty()){
            passwordTxt.requestFocus();
        }
        if(CurrentUser.authenticate(usernameTxt.getText(), passwordTxt.getText()) != null){
             try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("views/dashboard.fxml"));
                Parent root = (Parent)fxmlLoader.load();
                Stage dashboardStage = new Stage();
                dashboardStage.setTitle("Daaki Investiments Rental Management System");
                dashboardStage.setScene(new Scene(root));
               dashboardStage.setResizable(false);
                ((Node)(event.getSource())).getScene().getWindow().hide();
                dashboardStage.show();   
            }
        catch (IOException e) {
            e.printStackTrace();
        }
        }else{
            Alert alert = new Alert(Alert.AlertType.WARNING, "Wrong Credentials",ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setHeaderText(null);
            alert.setTitle("Login Failed");
            alert.show();
        }
    }

    void initialize() {
        assert usernameTxt != null : "fx:id=\"usernameTxt\" was not injected: check your FXML file 'login.fxml'.";
        assert passwordTxt != null : "fx:id=\"passwordTxt\" was not injected: check your FXML file 'login.fxml'.";

    }
}
