package com.manager.manager;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.*;
import java.util.Optional;
import java.util.Properties;

public class NyilvantartoController {

    @FXML
    private ListView<Product> productList;

    @FXML
    private TextField productNameField;

    @FXML
    private TextField priceField;

    private ObservableList<Product> products;

    private static final String PROPERTIES_FILE = "nyilvantartas.properties";

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
                selectedProduct.setName(newName);
                selectedProduct.setPrice(newPrice);

                saveData();

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
                saveData();
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
        Properties properties = new Properties();
        int index = 0;
        for (Product product : products) {
            properties.setProperty("product" + index + ".name", product.getName());
            properties.setProperty("product" + index + ".price", String.valueOf(product.getPrice()));
            index++;
        }

        try (OutputStream output = new FileOutputStream(PROPERTIES_FILE)) {
            properties.store(output, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(PROPERTIES_FILE)) {
            if (input != null) {
                properties.load(input);

                for (int index = 0; properties.containsKey("product" + index + ".name"); index++) {
                    String name = properties.getProperty("product" + index + ".name");
                    double price = Double.parseDouble(properties.getProperty("product" + index + ".price"));
                    products.add(new Product(name, price));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void clearInputFields() {
        productNameField.clear();
        priceField.clear();
        productList.getSelectionModel().clearSelection();
    }
}
