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
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ListView<HashMap> passengerList;
    @FXML
    private TextArea currentPassenger;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField ticketInput;
    @FXML
    private TextField fareInput;

    DatabaseLayer db;
    @FXML
    private TextField ageInput;
    @FXML
    private TextField genderInput;
    @FXML
    private TextField survivedInput;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        db = new DatabaseLayer();
        this.passengerList.setItems(db.readPassengers());
        
    }    

    @FXML
    private void clickAddPassenger(ActionEvent event) {
        db.addPassenger(nameInput.getText(), ticketInput.getText(), fareInput.getText());
    }

    @FXML
    private void clickDeletePassenger(ActionEvent event) {
        db.deletePassenger(this.passengerList.getSelectionModel().getSelectedItem());
    }
    

    @FXML
    private void clickPassenger(MouseEvent event) {
        HashMap person = this.passengerList.getSelectionModel().getSelectedItem();
        
        String pronoun = this.figureOutPronoun(person);
        String survived = this.figureOutSurvived(person);
        double fare = Double.parseDouble((String)person.get("Fare"));
        double fareAfterInflation = this.calculateFareAfterInflation(fare);
        
        String text = person.get("Name")+"\nWas "+person.get("Age")+" years old at the time of being "
                        + "a passenger on Titanic.\n\n";
       
        text = text+pronoun+" paid "+fare+" british pounds to embark on Titanic at the time.\n"
                + "That equates to a total of "+fareAfterInflation+" british pounds in 2020\n\n";
        
        text = text + pronoun + survived;
        this.currentPassenger.setText(text);
    }
    
    public double calculateFareAfterInflation(double fare) {
        return fare*119;
    }
    
    public String figureOutPronoun(HashMap person) {
        if(person.get("Sex").equals("female")) {
            return "She";
        } else if(person.get("Sex").equals("male")) {
            return "He";
        }
        return "They";
    }
    
    public String figureOutSurvived(HashMap person) {
        if(person.get("Survived").equals("0")) {
            return " did not survive.";
        } else if (person.get("Survived").equals("1")) {
            return " managed to survive.";
        }
        return " may or may not have survived";
    }

    @FXML
    private void clickUpdateList(ActionEvent event) {
        this.passengerList.getItems().clear();
        this.passengerList.setItems(db.readPassengers());
    }
    
}