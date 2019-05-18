/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package GeneralClasses;

import javafx.scene.control.TextField;


/**
 *
 * @author robert
 */
public class NumberValidator {
    
    public static void validateDouble(TextField numberField){
        try{
            Double.parseDouble(numberField.getText());
        }catch(Exception ex){
            try{
                numberField.setText(numberField.getText(0, numberField.getText().length()-1));
            }catch(Exception exe){
                numberField.clear();
            }
        }
    }
    
    public static void validateInteger(TextField numberField){
        try{
            Integer.parseInt(numberField.getText());
        }catch(Exception ex){
           try{
                numberField.setText(numberField.getText(0, numberField.getText().length()-1));
            }catch(Exception exe){
                numberField.clear();
            }
        }
    }
}
