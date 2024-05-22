package com.manager.manager.controller;

import com.manager.manager.Products.Product;
import com.manager.manager.admincommands.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class NyilvantartoController {

    @FXML
    private TextField productNameField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField quantityField;
    @FXML
    private ListView<Product> productList;
    @FXML
    private ComboBox<String> myComboBox;

    private ObservableList<Product> products = FXCollections.observableArrayList();
    private SaveData newSaveData;
    private ClearInputFields newClear;

    public NyilvantartoController(){}

    @FXML
    private void initialize() {
        productList.setItems(products);
        productList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                productNameField.setText(newValue.getName());
                priceField.setText(String.valueOf(newValue.getPrice()));
                quantityField.setText(String.valueOf(newValue.getQuantity()));
                myComboBox.setValue(String.valueOf(newValue.getType()));
            }
        });

        this.newSaveData = new SaveData(productNameField, priceField, quantityField, productList, products);
        this.newClear = new ClearInputFields(productNameField, priceField, quantityField, productList);
        loadCombobox();
        loadData();
    }

    @FXML
    private void handleAdd() {
        AddProduct addProduct = new AddProduct(productNameField, priceField, quantityField,productList, products,myComboBox);
        addProduct.execute();
    }

    @FXML
    private void handleUpdate() {
        UpdateProduct updateProduct = new UpdateProduct(productNameField, priceField, quantityField,productList, products);
        updateProduct.execute();
    }

    @FXML
    private void loadCombobox(){
        LoadCombobox loaddata = new LoadCombobox();
        loaddata.loadItems(myComboBox);
    }

    @FXML
    private void handleDelete() {
        DeleteProduct deleteProduct = new DeleteProduct(productNameField, priceField, quantityField,productList, products);
        deleteProduct.execute();
    }


    @FXML
    private void handleEmergencyDelete() {
        EmergencyDeleteProduct emergencyDelete = new EmergencyDeleteProduct(productNameField, priceField, quantityField,productList, products);
        emergencyDelete.execute();
    }


    private void loadData() {
        LoadData loaddatas = new LoadData(products);
        loaddatas.execute();
    }


    private void clearInputFields() {
        productNameField.clear();
        priceField.clear();
        quantityField.clear();
        productList.getSelectionModel().clearSelection();
    }
}
