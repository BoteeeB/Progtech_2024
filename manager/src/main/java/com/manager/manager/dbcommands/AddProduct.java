package com.manager.manager.dbcommands;

import com.manager.manager.Interfaces.ProductFactory;
import com.manager.manager.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import javafx.scene.control.ListView;

public class AddProduct extends databaseConnection implements ProductFactory {

    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    private SaveData newSaveData;
    private ClearInputFields newClear;

    public AddProduct(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList, ObservableList<Product> products){
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
        String name = productNameField.getText().trim();
        String priceText = priceField.getText().trim();
        String quantityText = quantityField.getText().trim();

        if (!name.isEmpty() && !priceText.isEmpty() && !quantityText.isEmpty()) {
            double price = Double.parseDouble(priceText);
            int quantity = Integer.parseInt(quantityText);
            Product newProduct = new Product(name,(int) price, quantity);
            products.add(newProduct);

            newSaveData.execute();
            newClear.clearInputFields();
        }
    }
}
