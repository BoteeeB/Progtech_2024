package com.manager.manager.admincommands;

import com.manager.manager.Interfaces.ProductFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;

import java.sql.*;

public class LoadData extends databaseConnection implements ProductFactory {

    private ObservableList<Product> products;

    public LoadData(ObservableList<Product> products){
        this.products = products;
    }
    @Override
    public void execute() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM termekek")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity");
                int type = resultSet.getInt("type");
                products.add(new Product(name, price, quantity,type ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
