package com.manager.manager.customercommands;

import com.manager.manager.Interfaces.CustomerFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProductAmount extends databaseConnection implements CustomerFactory {
    private Product product;
    private int quantityChange;
    public UpdateProductAmount(Product product, int quantityChange){
        this.product = product;
        this.quantityChange = quantityChange;
    }

    @Override
    public void execute() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE termekek SET quantity = quantity - ? WHERE name = ?")) {
            statement.setInt(1, Math.abs(quantityChange)); // Negatív érték a levonáshoz
            statement.setString(2, product.getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int returnExecute() {
        return 0;
    }
}
