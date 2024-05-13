package com.manager.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class NyilvantartoController {

    private static final String URL = "jdbc:mysql://localhost:3306/nyilvantartas";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";


    @FXML
    private ListView<Product> productList;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField quantityField; // New field

    private ObservableList<Product> products;

    public NyilvantartoController() {
        this.products = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        productList.setItems(products);
        productList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productNameField.setText(newValue.getName());
                priceField.setText(String.valueOf(newValue.getPrice()));
                quantityField.setText(String.valueOf(newValue.getQuantity())); // New field
            }
        });

        loadData();
    }

    @FXML
    private void handleAdd() {
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim(); // New field

        if (!name.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty()) {
            int price = Integer.parseInt(priceText);
            int quantity = Integer.parseInt(quantityText); // New field
            Product newProduct = new Product(name, price, quantity); // New field
            products.add(newProduct);

            saveData();

            clearInputFields();
        }
    }

    @FXML
    private void handleUpdate() {
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

                clearInputFields();
            }
        }
    }



    @FXML
    private void handleDelete() {
        Product selectedProduct = productList.getSelectionModel().getSelectedItem();
        if (selectedProduct != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure?");
            confirmation.setContentText("This will delete the selected item. Do you want to proceed?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                products.remove(selectedProduct);

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     PreparedStatement statement = connection.prepareStatement("DELETE FROM termekek WHERE name = ?")) {

                    statement.setString(1, selectedProduct.getName());
                    statement.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                clearInputFields();
            }
        }
    }


    @FXML
    private void handleEmergencyDelete() {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Emergency Delete");
        confirmation.setHeaderText("Warning: This will delete all items!");
        confirmation.setContentText("Do you want to proceed with deleting everything?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            products.clear();
            saveData();
        }
    }

    private void saveData() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement deleteStatement = connection.createStatement();
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO termekek (name, price, quantity) VALUES (?, ?, ?)")) {

            deleteStatement.executeUpdate("DELETE FROM termekek");

            for (Product product : products) {
                insertStatement.setString(1, product.getName());
                insertStatement.setDouble(2, product.getPrice());
                insertStatement.setInt(3, product.getQuantity()); // New field
                insertStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void loadData() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM termekek")) {

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                int price = resultSet.getInt("price");
                int quantity = resultSet.getInt("quantity"); // New field
                products.add(new Product(name, price, quantity)); // New field
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void clearInputFields() {
        productNameField.clear();
        priceField.clear();
        quantityField.clear(); // New field
        productList.getSelectionModel().clearSelection();
    }
}
