package com.manager.manager.admincommands;

import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

import java.sql.*;
import java.util.HashMap;

public class LoadCombobox extends databaseConnection{
    ComboBox<String> myCombobox = new ComboBox<>();
    ObservableList<String> options = FXCollections.observableArrayList();

    HashMap<String, Integer> typeMap = new HashMap<>();
    public LoadCombobox(){}

    public LoadCombobox(ComboBox<String> myCombobox){
        loadItems(myCombobox);
    }

    public void loadItems(ComboBox<String> myCombobox){
        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM tipus")) {

            while (rs.next()) {
                String item = rs.getString("termek_tipus");
                int id = rs.getInt("id");
                options.add(item);
                typeMap.put(item,id);
            }

            myCombobox.setItems(options);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public int getID(String type){
        if(typeMap.containsKey(type)){
            return typeMap.get(type);
        }else{
            return -1;
        }
    }
}
