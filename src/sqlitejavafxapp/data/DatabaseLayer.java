/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sqlitejavafxapp.data;

import java.sql.Connection;
import java.sql.DriverManager;
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

    Connection connection = null;

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

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:C:\\Users\\alex\\Desktop\\Div informatik\\SQLiteJavaFXApp\\src\\sqlitejavafxapp\\data\\data.sqlite");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

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
