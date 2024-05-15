package com.manager.manager.admincommands;

import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

public class ClearInputFields extends databaseConnection {
    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;

    public ClearInputFields(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList) {
        this.productNameField = productNameField;
        this.priceField = priceField;
        this.quantityField = quantityField;
        this.productList = productList;
    }

    public void clearInputFields(){
        productNameField.clear();
        priceField.clear();
        quantityField.clear();
        productList.getSelectionModel().clearSelection();
    }
}
