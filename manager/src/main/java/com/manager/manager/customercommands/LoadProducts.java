package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import com.manager.manager.admincommands.LoadData;
import javafx.collections.ObservableList;

import java.sql.*;

public class LoadProducts extends databaseConnection implements CustomerFactory {

    private ObservableList<Product> products;

    public LoadProducts(ObservableList<Product> products){
        this.products = products;
    }

    @Override
    public void execute() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM termekek");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                products.add(new Product(name, price, quantity));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
