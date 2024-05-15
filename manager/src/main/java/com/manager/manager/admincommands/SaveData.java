package com.manager.manager.admincommands;

import com.manager.manager.Interfaces.ProductFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.*;

public class SaveData extends databaseConnection implements ProductFactory {

    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    public SaveData(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList, ObservableList<Product> products){
        this.productNameField = productNameField;
        this.priceField = priceField;
        this.quantityField = quantityField;
        this.productList = productList;
        this.products = products;
    }

    @Override
    public void execute() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement deleteStatement = connection.createStatement();
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO termekek (name, price, quantity) VALUES (?, ?, ?)")) {

            deleteStatement.executeUpdate("DELETE FROM termekek");

            for (Product product : products) {
                insertStatement.setString(1, product.getName());
                insertStatement.setDouble(2, product.getPrice());
                insertStatement.setInt(3, product.getQuantity());
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
