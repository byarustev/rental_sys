/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GeneralClasses;

import database.DatabaseHandler;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



/**
 *
 * @author robert
 */
public class SyncManager {
    
    private static final SyncManager syncManager = new SyncManager();
    
    public static SyncManager getInstance(){
        return syncManager;
    }
    public SyncManager(){
       sync();
       
    }
    
    public final void sync(){
        File syncFile = new File("sync.txt");
        try {
            FileOutputStream writer = new FileOutputStream(syncFile);
            JSONObject rentalSys = new JSONObject();
            JSONObject syncObject = new JSONObject();
            syncObject.put("blocks", DatabaseHandler.getInstance().getBlocksJSON());
            syncObject.put("tenants", DatabaseHandler.getInstance().getTenantsJSON());
            syncObject.put("contracts", DatabaseHandler.getInstance().getRentalContractsJSON());
            syncObject.put("payments", DatabaseHandler.getInstance().getPaymentsJSON());
            syncObject.put("statements", DatabaseHandler.getInstance().getStatementsJSON());
            syncObject.put("houses", DatabaseHandler.getInstance().getHousesJSON());
            //System.out.println(syncObject);
            sendJSONtoServer(syncObject);
            writer.write(syncObject.toString().getBytes());
            writer.close();
            
        } catch (FileNotFoundException | JSONException ex) {
            Logger.getLogger(SyncManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SyncManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
    
    private void sendJSONtoServer(JSONObject jsonData) {
            // URL and parameters for the connection, This particulary returns the information passed
            URL url;
        try {
            
            url = new URL("http://localhost:8000/api/syncdata/post/");
            HttpURLConnection httpConnection  = (HttpURLConnection) url.openConnection();
            httpConnection.setDoOutput(true);
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestProperty("Accept", "application/json");
            // Not required
            // urlConnection.setRequestProperty("Content-Length", String.valueOf(input.getBytes().length));

            // Writes the JSON parsed as string to the connection
            DataOutputStream wr = new DataOutputStream(httpConnection.getOutputStream());
            wr.write(jsonData.toString().getBytes());
            Integer responseCode = httpConnection.getResponseCode();
            BufferedReader bufferedReader;
            // Creates a reader buffer
            if (responseCode > 199 && responseCode < 300) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getErrorStream()));
            }
            // To receive the response
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line).append("\n");
            }
            bufferedReader.close();

            // Prints the response
            System.out.println(content.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(SyncManager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SyncManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
}
