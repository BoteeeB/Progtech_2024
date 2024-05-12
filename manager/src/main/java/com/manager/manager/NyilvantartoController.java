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
            }
        });

        loadData();
    }

    @FXML
    private void handleAdd() {
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();

        if (!name.isEmpty() && !priceText.isEmpty()) {
            double price = Double.parseDouble(priceText);
            Product newProduct = new Product(name, price);
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

            if (!newName.isEmpty() && !newPriceText.isEmpty()) {
                double newPrice = Double.parseDouble(newPriceText);

                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                     PreparedStatement statement = connection.prepareStatement("UPDATE termekek SET name = ?, price = ? WHERE name = ?")) {

                    statement.setString(1, newName);
                    statement.setDouble(2, newPrice);
                    statement.setString(3, selectedProduct.getName());
                    statement.executeUpdate();

                    selectedProduct.setName(newName);
                    selectedProduct.setPrice(newPrice);
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
             PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO termekek (name, price) VALUES (?, ?)")) {

            deleteStatement.executeUpdate("DELETE FROM termekek");

            for (Product product : products) {
                insertStatement.setString(1, product.getName());
                insertStatement.setDouble(2, product.getPrice());
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
                double price = resultSet.getDouble("price");
                products.add(new Product(name, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void clearInputFields() {
        productNameField.clear();
        priceField.clear();
        productList.getSelectionModel().clearSelection();
    }
}
