/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lan_chat_client;

import java.net.URL;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Guenaoui Amine MIV 1 USTHB
 */
public class FXML_Login_Client_Controller implements Initializable {

    @FXML
    private TextField logger_name;
    
    Remote stub;
    public static User me;
    public static String location;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        // getting object 
        location = "";
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Launching ");
        dialog.setHeaderText("Server Domain Input ");
        dialog.setContentText("Please the server domain: like 192.168.1.4");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
         
            System.out.println("the domain: ." + result.get()+".");
            location = "rmi://"+result.get().replaceAll("\\s+","")+":1099/";
            try {
            
            stub = Naming.lookup(location+"Chat_logger");
            //stub = Naming.lookup("rmi://"+InetAddress.getLocalHost().getHostAddress()+"/Notes");
            System.out.println(stub);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        }
        location = "rmi://192.168.1.4:1099/";
        
    }    

    @FXML
    private void login_client(ActionEvent event) {
        if (stub instanceof Chat_Logger_Int ){
            Chat_Logger_Int s = (Chat_Logger_Int)stub;
            try {
                String name = logger_name.getText();
                me = new User(s.get_logged_users_number(), name);
                s.add_user(name);
                System.out.println(name+"logging in .. ");
            } catch (RemoteException ex) {
                System.out.println("not an instance");
                ex.printStackTrace();
            }
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Lan_Chat_Main_Interface.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
//            stage.initModality(Modality.NONE);
//            stage.initStyle(StageStyle.UNDECORATED);
            stage.setTitle("Lan Chat Client");
            stage.setScene(new Scene(root1));  
            stage.show();
            Stage current = (Stage)logger_name.getScene().getWindow();
            current.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
}
