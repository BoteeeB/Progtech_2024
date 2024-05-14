package com.manager.manager.abstraction;

import com.manager.manager.Product;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.*;

public abstract class databaseConnection {

    public static final String URL = "jdbc:mysql://localhost:3306/nyilvantartas";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";

}
