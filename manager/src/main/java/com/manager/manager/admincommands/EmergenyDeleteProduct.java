package com.manager.manager.admincommands;

import com.manager.manager.Interfaces.ProductFactory;
import com.manager.manager.Products.Product;
import com.manager.manager.abstraction.databaseConnection;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class EmergenyDeleteProduct extends databaseConnection implements ProductFactory {

    private static final Logger logger = LogManager.getLogger(EmergenyDeleteProduct.class);

    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    private SaveData newSaveData;
    private ClearInputFields newClear;

    public EmergenyDeleteProduct(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList, ObservableList<Product> products){
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
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Emergency Delete");
        confirmation.setHeaderText("Warning: This will delete all items!");
        confirmation.setContentText("Do you want to proceed with deleting everything?");

        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            products.clear();
            newClear.clearInputFields();

            logger.info("Összes termék törölve");
        }
    }
}
