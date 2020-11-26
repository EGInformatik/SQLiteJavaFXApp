/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package sqlitejavafxapp;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import sqlitejavafxapp.data.DatabaseLayer;

/**
 *
 * @author alex
 */
public class FXMLDocumentController implements Initializable {
    

    @FXML
    private Color x2;
    @FXML
    private Font x1;
    @FXML
    private Color x4;
    @FXML
    private Font x3;
    @FXML
    private ListView<HashMap> passengerList;
    @FXML
    private TextArea currentPassenger;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DatabaseLayer dbl = new DatabaseLayer();
        this.passengerList.setItems(dbl.readPassengers());
        
    }    

    @FXML
    private void clickAddPassenger(ActionEvent event) {
    }

    @FXML
    private void clickDeletePassenger(ActionEvent event) {
    }

    @FXML
    private void clickPassenger(MouseEvent event) {
        HashMap person = this.passengerList.getSelectionModel().getSelectedItem();
        
        String text = person.get("Name")+"\nWas "+person.get("Age")+" years old at the time of being "
                        + "a passenger on Titanic.\n\n";
        String pronoun = "";
        if(person.get("Sex").equals("female")) {
            pronoun = "She";
        } else if(person.get("Sex").equals("male")) {
            pronoun = "He";
        }
        double fare = Double.parseDouble((String)person.get("Fare"));
        text = text+pronoun+" paid "+person.get("Fare")+" british pounds to embark on Titanic at the time.\n"
                + "That equates to a total of "+this.calculateTodayPrice(fare)+" british pounds in 2020\n\n";
        
        if(person.get("Survived").equals("0")) {
            text = text+pronoun+" did not survive.";
        } else if (person.get("Survived").equals("1")) {
            text = text+pronoun+" managed to survive.";
        }
        this.currentPassenger.setText(text);
    }
    
    public double calculateTodayPrice(double fare) {
        return fare*119;
    }
    
}
