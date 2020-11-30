/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitejavafxapp.data;

import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author alex
 */
public class DatabaseLayer {

    /**
     * Anvender en SELECT-forespørgsel til at hente data fra databasen. For at
     * modificere dataen der bliver hentet SQL forespørgslen blot ændres.
     *
     * @return Finder en liste af alle passagerer.
     */
    public ObservableList readPassengers() {
        this.connect();
        ObservableList list = FXCollections.observableArrayList();
        try {
            Statement statement = connection.createStatement();
            ResultSet sqlResult = statement.executeQuery("SELECT * FROM titanic_passengers;");
            while (sqlResult.next()) {
                ResultSetMetaData md = sqlResult.getMetaData();
                int columns = md.getColumnCount();
                HashMap row = new HashMap(columns);
                for (int i = 1; i <= columns; ++i) {
                    row.put(md.getColumnName(i), sqlResult.getObject(i));
                }
                list.add(row);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.disconnect();
        System.out.println(list);
        return list;
    }

    /**
     * Anvender en INSERT-forespørgsel til at indsætte en ny passager i
     * databasen
     */
    public void addPassenger(String name, String ticket, String fare, String age, String gender, String survived) {
        this.connect();
        String query = "INSERT INTO titanic_passengers (Name, Ticket, Fare, Age, Sex, Survived) "
                + "VALUES (?,?,?,?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, ticket);
            ps.setString(3, fare);
            ps.setString(4, age);
            ps.setString(5, gender);
            ps.setString(6, survived);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseLayer.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.disconnect();
    }

    /**
     * Anvender en UPDATE-forespørgsel til at opdatere data for en bestemt
     * passager
     */
    public void updatePassenger() {

    }

    /**
     * Anvender en DELETE-forespørgsel til at slette en bestemt passager.
     */
    public void deletePassenger(HashMap passenger) {

    }

    Connection connection = null;

    /**
     * Denne funktion bruges til at skabe forbindelse til databasen, og bør ikke
     * ændres.
     */
    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:"+Paths.get("").toAbsolutePath().toString()+
                    "\\src\\sqlitejavafxapp\\data\\titanic_database.sqlite");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    /**
     * Denne funktion bruges til at forbryde forbindelsen til en database, og
     * bør ikke ændres.
     */
    public void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

}
