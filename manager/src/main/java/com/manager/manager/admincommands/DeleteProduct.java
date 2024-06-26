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

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

public class DeleteProduct extends databaseConnection implements ProductFactory {

    private static final Logger logger = LogManager.getLogger(DeleteProduct.class);

    private TextField productNameField;
    private TextField priceField;
    private TextField quantityField;
    private ListView<Product> productList;
    private ObservableList<Product> products;

    private SaveData newSaveData;
    private ClearInputFields newClear;

    public DeleteProduct(TextField productNameField, TextField priceField, TextField quantityField, ListView<Product> productList, ObservableList<Product> products){
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
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
            confirmation.setTitle("Confirmation");
            confirmation.setHeaderText("Are you sure?");
            confirmation.setContentText("This will delete the selected item. Do you want to proceed?");

            Optional<ButtonType> result = confirmation.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                try {
                    products.remove(selectedProduct);

                    try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                         PreparedStatement statement = connection.prepareStatement("DELETE FROM termekek WHERE name = ?")) {

                        statement.setString(1, selectedProduct.getName());
                        statement.executeUpdate();

                        logger.info("Termék törölve: " + selectedProduct.getName());
                    }
                } catch (SQLException e) {
                    logger.error("Hiba történt a termék törlése során: " + e.getMessage());
                }
                newClear.clearInputFields();
            }
        }
    }
}
