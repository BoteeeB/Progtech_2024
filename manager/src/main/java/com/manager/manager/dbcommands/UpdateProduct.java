package com.manager.manager.dbcommands;

import com.manager.manager.Interfaces.ProductFactory;
import com.manager.manager.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateProduct extends databaseConnection implements ProductFactory {
    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    private SaveData newSaveData;
    private ClearInputFields newClear;

    public UpdateProduct(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList, ObservableList<Product> products){
        this.productNameField = productNameField;
        this.priceField = priceField;
        this.quantityField = quantityField;
        this.productList = productList;
        this.products = products;

        this.newSaveData = new SaveData(productNameField, priceField, quantityField, productList, products);
        this.newClear = new ClearInputFields(productNameField, priceField, quantityField, productList);
    }
    @Override
    public void execute() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            String newName = productNameField.getText().trim();
            String newPriceText = priceField.getText().trim();
            String newQuantityText = quantityField.getText().trim(); // New field

            if (!newName.isEmpty() && !newPriceText.isEmpty() && !newQuantityText.isEmpty()) {
                int newPrice = Integer.parseInt(newPriceText);
                int newQuantity = Integer.parseInt(newQuantityText); // New field

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     PreparedStatement statement = connection.prepareStatement("UPDATE termekek SET name = ?, price = ?, quantity = ? WHERE name = ?")) {

                    statement.setString(1, newName);
                    statement.setDouble(2, newPrice);
                    statement.setInt(3, newQuantity); // New field
                    statement.setString(4, selectedProduct.getName());
                    statement.executeUpdate();

                    selectedProduct.setName(newName);
                    selectedProduct.setPrice(newPrice);
                    selectedProduct.setQuantity(newQuantity); // New field
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                newClear.clearInputFields();
            }
        }
    }
}
