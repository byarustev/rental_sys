/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.util.HashMap;

/**
 *
 * @author robert
 */
public class CurrentUser {
    private String username;
    private String userId;
    private String fullName;
    public static final String KEY_USERNAME ="username";
    public static final String KEY_FULL_NAME ="username";
    public static final String KEY_USER_ID ="userid";
    private static CurrentUser curUser=null;
    private String addedByUserId;

    public String getAddedByUserId() {
        return addedByUserId;
    }

    public void setAddedByUserId(String addedByUserId) {
        this.addedByUserId = addedByUserId;
    }
    
    public static CurrentUser getInstance(){
        return CurrentUser.curUser;
    }
    private CurrentUser(String username, String userId, String fullName){
        this.username = username;
        this.userId = userId;
        this.fullName = fullName;
    }
    public static CurrentUser authenticate(String username, String password){
        HashMap <String, String>userMap = DatabaseHandler.getInstance().validateUser(username, password);
        try{
            CurrentUser.curUser = new CurrentUser(userMap.get(CurrentUser.KEY_USERNAME),userMap.get(CurrentUser.KEY_USER_ID),
            userMap.get(CurrentUser.KEY_FULL_NAME));
            return CurrentUser.curUser;
        }catch(Exception ex){
            return null;
        }
    
    }
    
     public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
}
